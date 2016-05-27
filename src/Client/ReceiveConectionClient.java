package Client;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Map;


import Common.PDU;
import Common.PDU_APP;
import Common.PDU_APP_DATA;
import Common.PDU_APP_PROB_RESPONSE;
import Common.PDU_Buider;
import Common.PDU_Reader;
import Connection.Reciver;
import Connection.Sender;

/*
 * Classe para ser utilizada pelo o cliente que pediu a musica e a vai receber
 */
public class ReceiveConectionClient implements Runnable{
	
	public static final int trys = 20;
	public static final int timeWait = 6000; 
	public static final int maxSize = 48*1029;
	
	
	private Map<String,String> resultsRequest; //resultados do request
	private DatagramSocket mySocketUDP; //o meu socket (sou eu a criar )
	private DatagramPacket myPairTransfFile;
	private PDU_APP_PROB_RESPONSE pduProbeRespChoose;
	private Client cliente; //a minha informação
	
	
	public ReceiveConectionClient(Map<String,String> results, Client cliente) throws SocketException{
		this.resultsRequest=results;
		this.mySocketUDP = new DatagramSocket(0);
		mySocketUDP.setReceiveBufferSize(PDU.MAXDATASIZE*51);
		this.pduProbeRespChoose = null;
		byte[] buf = new byte[maxSize];
		this.myPairTransfFile = new DatagramPacket(buf, buf.length);
		this.cliente = cliente;
	}

	private void sendProbeToProviders(){
		byte[] buf = new byte[maxSize];
		DatagramPacket dataSend;
		
		PDU probeRequest = PDU_Buider.PROB_REQUEST_PDU(1, this.cliente.getUser(), this.cliente.getIp(), this.mySocketUDP.getLocalPort());
		ArrayList<PDU> toSend = new ArrayList<>();
		toSend.add(probeRequest);
		
		//enviar a todos os clientes vieram nos results o probe
		for(String userANDip : resultsRequest.keySet()){
			try {
				System.out.println("Teste:"+ userANDip );
				String ip = userANDip.split(":")[1];
				System.out.println("Vou enviar probe para o ip -> " + ip );
				
				dataSend = new DatagramPacket(buf, buf.length,InetAddress.getByName(ip) , Integer.parseInt(resultsRequest.get(userANDip)));
				Sender send = new Sender(toSend, trys, timeWait, dataSend, mySocketUDP);
				try {
					send.send();
				} catch (IOException e) {
					this.resultsRequest.remove(userANDip);
					System.out.println("Não foi possivel enviar o probe");
				}
			} catch (NumberFormatException | UnknownHostException | SocketException e) {
				this.resultsRequest.remove(userANDip);
				System.out.println("Não foi possivel abrir a ligação com o cliente com a identificação " + userANDip + " para a porta " + this.resultsRequest.get(userANDip)+".");
			}
		}
	}
	
	
	
	private ArrayList<PDU> waitForAllResponses(){
		byte[] buf = new byte[maxSize];
		DatagramPacket dataPair;
		
		ArrayList<PDU> probesRecevidos = new ArrayList<>();
	
		for(String userANDip : resultsRequest.keySet()){
				String ip = userANDip.split(":")[1];
				System.out.println("Vou receber probe do ip -> " + ip );
				try {
					dataPair = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip) , Integer.parseInt(resultsRequest.get(userANDip)));
					Reciver recive;
					recive = new Reciver(trys, timeWait,dataPair, mySocketUDP);
					recive.recive();
					probesRecevidos.addAll(recive.getRecived());
				} catch (NumberFormatException | IOException e) {
					resultsRequest.remove(userANDip);
					e.printStackTrace();
				}		
		}
		return probesRecevidos;
	}
	
	
	
	private void chooseProvider(ArrayList<PDU> probeResponse) throws IOException{
		PDU_APP aux;
		for(PDU pdu : probeResponse){
			aux = PDU_Reader.read(pdu);
			
			if(aux.getClass().getSimpleName().equals("PDU_APP_PROB_RESPONSE")){
				PDU_APP_PROB_RESPONSE pduR = (PDU_APP_PROB_RESPONSE) aux;
				if(this.pduProbeRespChoose!=null){
					if(pduR.getTimestamp() > this.pduProbeRespChoose.getTimestamp()){
						this.pduProbeRespChoose = pduR;
					}
				}else{
					this.pduProbeRespChoose = pduR;
				}
			}
		}
		
		
		myPairTransfFile.setAddress(InetAddress.getByName(this.pduProbeRespChoose.getIp()));
		myPairTransfFile.setPort(this.pduProbeRespChoose.getPort());
		
		ArrayList<PDU> toSend = new ArrayList<>();
		toSend.add(PDU_Buider.REQUEST_FILE_PDU(1, this.cliente.getUser(), this.cliente.getIp(), this.mySocketUDP.getLocalPort(), "Ver o que se vai enviar aqui"));
		
		Sender sendRequestFile = new Sender(toSend, trys, timeWait, myPairTransfFile, mySocketUDP);
		sendRequestFile.send();
		
		/*foi enviado ao cliente que tem a musica e que foi escolhido 
		 *o pedido para enviar o ficheiro
		 */
	}
	
	private void receiveFile() throws IOException{
		Reciver recive = new Reciver(trys, timeWait,myPairTransfFile, mySocketUDP);
		recive.recive();
		ArrayList<PDU> rec = recive.getRecived();
		if(rec.size()!=0){
			PDU_APP_DATA pduDATA = PDU_Reader.read(rec);
			//criar ficlherio de audio
			File f = new File(this.cliente.getFolderMusic(), pduDATA.getNome());
			Files.write(f.toPath(), pduDATA.getSong(), StandardOpenOption.CREATE);
			//dados guradados no ficheiro
		}
	}
		
	@Override
	public void run() {
		Thread.currentThread().setName("ReceiveConectionClient");
		System.out.println("Vou enviar os probes ao cliente que me responderam");
		sendProbeToProviders();
		System.out.println("Vou ficar a escuta pelas respostas");
		ArrayList<PDU> response = waitForAllResponses();
		for(PDU p : response){
			System.out.println("Probe Resposnse recebido: "+p.toString());
		}
		if(response.size()!=0){
			try {
				chooseProvider(response);
				receiveFile();
			} catch (IOException e) {
				System.out.println("Não foi possivel realizar o pedido de musica a nenhum dos clientes que erespondeu afirmativamente");
			}
		}
		mySocketUDP.close();
	}

}
