package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

import Common.PDU;
import Common.PDU_Buider;
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
	private DatagramPacket provideSocketUDP; // provide que foi escolhido da lista de respostas (crio com o que vem no map)
	private Client cliente; //a minha informação
	private String ipProbiderChooser;
	private Map<String,Integer> timesProbes;
	
	
	public ReceiveConectionClient(Map<String,String> results, Client cliente) throws SocketException{
		this.resultsRequest=results;
		this.mySocketUDP = new DatagramSocket(0);
		this.provideSocketUDP = null;
		this.cliente = cliente;
		this.ipProbiderChooser=null;
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
			} catch (NumberFormatException | UnknownHostException e) {
				this.resultsRequest.remove(userANDip);
				System.out.println("Não foi possivel abrir a ligação com o cliente com a identificação " + userANDip + " para a porta " + this.resultsRequest.get(userANDip)+".");
			}
		}
	}
	
	
	
	private ArrayList<PDU> waitForAllResponses(){
		int numeroProbesWaiting = resultsRequest.size();
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
	
	
	
	private void chooseProvider(){
		
	}
	
	
	private void closeConnections(){
		
	}
	
	
	
	
	@Override
	public void run() {
		sendProbeToProviders();
		ArrayList<PDU> r = waitForAllResponses();
		for(PDU p : r){
			System.out.println(p.toString());
		}
	}

}
