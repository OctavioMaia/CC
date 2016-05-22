package Client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

import Common.PDU;
import Common.PDU_Buider;
import Connection.udpReciver;
import Connection.udpSender;

/*
 * Classe para ser utilizada pelo o cliente que pediu a musica e a vai receber
 */
public class ReceiveConectionClient implements Runnable{
	
	public static final int timeOutTry = 2000;
	public static final int timeOutDesistir = 6000; 
	public static final int windowMax = 5;
	
	private Map<String,String> resultsRequest; //resultados do request
	private DatagramSocket mySocketUDP; //o meu socket (sou eu a criar )
	private DatagramSocket provideSocketUDP; // provide que foi escolhido da lista de respostas (crio com o que vem no map)
	private Client cliente; //a minha informação
	private String ipProbiderChooser;
	private Map<String,Integer> timesProbes;
	
	
	public ReceiveConectionClient(Map<String,String> results, Client cliente){
		this.resultsRequest=results;
		try {
			this.mySocketUDP = new DatagramSocket(0);
		} catch (SocketException e) {
			System.out.println("Não foi possivel criar o nosso DatagramSocket");
		}
		this.provideSocketUDP = null;
		this.cliente = cliente;
		this.ipProbiderChooser=null;
	}

	private void sendProbeToProviders(){
		PDU probeRequest = PDU_Buider.PROB_REQUEST_PDU(1, this.cliente.getUser(), this.cliente.getIp(), this.mySocketUDP.getLocalPort());
		ArrayList<PDU> toSend = new ArrayList<>();
		toSend.add(probeRequest);
		
		//enviar a todos os clientes vieram nos results o probe
		for(String userANDip : resultsRequest.keySet()){
			try {
				String ip = userANDip.split(":")[1];
				System.out.println("Vou enviar probe para o ip -> " + ip );
				DatagramSocket dataSend = new DatagramSocket(Integer.parseInt(resultsRequest.get(userANDip)), InetAddress.getByName(ip));
				udpSender udpSEND = new udpSender(dataSend, mySocketUDP, timeOutTry, timeOutDesistir, windowMax, toSend);
				try {
					udpSEND.sendData();
				} catch (InterruptedException | IOException e) {
					this.resultsRequest.remove(userANDip);
					System.out.println("Não foi possivel enviar o probe");
				}
			} catch (NumberFormatException | SocketException | UnknownHostException e) {
				System.out.println("Não foi possivel abrir a ligação com o cliente com a identificação " + userANDip + " para a porta " + this.resultsRequest.get(userANDip)+".");
			}
		}
	}
	
	
	
	private void waitForAllResponses(){
		int numeroProbesWaiting = resultsRequest.size();
		while (numeroProbesWaiting>0) {
			//udpReciver updrec = new udpReciver(sendACK, reciveData, numeroProbesWaiting, numeroProbesWaiting)
			numeroProbesWaiting--;
		}
	}
	
	private void chooseProvider(){
		
	}
	
	
	private void closeConnections(){
		
	}
	
	
	
	
	@Override
	public void run() {
		sendProbeToProviders();
		
		
		
	}

}
