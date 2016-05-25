package Client;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import Common.PDU;
import Common.PDU_APP_PROB_REQUEST;
import Common.PDU_Buider;
import Common.PDU_Reader;
import Connection.Reciver;
import Connection.Sender;


//classe a ser utilizada pelo utilizador que vai enviar a musica.
public class SendConectionClient implements Runnable{
	
	public static final int trys = 20;
	public static final int timeWait = 6000; 
	public static final int maxSize = 48*1029;
	

	private DatagramSocket mySocket;
	private DatagramPacket myPair;
	
	private Client user;
	private File ficheiroEnvio;
	
	public SendConectionClient(Client c, File file, DatagramSocket my) {
		this.user=c;
		this.ficheiroEnvio=file;
		mySocket = my;
		byte[] buf = new byte[maxSize];
		myPair = new DatagramPacket(buf, buf.length);
	}
	
	
	@Override
	public void run() {
		Thread.currentThread().setName("SendConectionClient");
		Reciver receive;
		Sender send;
		ArrayList<PDU> recivedPDUs;
		ArrayList<PDU> toSend = new ArrayList<>();
		try {
			//receber o probeRequest
			receive = new Reciver(trys, timeWait, myPair, mySocket);
			receive.recive();
			recivedPDUs = receive.getRecived();
			PDU_APP_PROB_REQUEST pduRequest = (PDU_APP_PROB_REQUEST) PDU_Reader.read(recivedPDUs.get(0));
		
			myPair.setAddress(InetAddress.getByName(pduRequest.getIp()));
			myPair.setPort(pduRequest.getPort());
			
			//enviar probeResponse
			PDU probeResponse = PDU_Buider.PROB_RESPONSE_PDU(1, user.getUser(), mySocket.getLocalAddress().getHostAddress(), mySocket.getLocalPort(), System.currentTimeMillis());
			toSend.add(probeResponse);
			send = new Sender(toSend, trys, timeWait, myPair, mySocket);
			send.send();
			
			//tentativa para receber o requestFile
			receive = new Reciver(trys, timeWait, myPair, mySocket);
			receive.recive();
			recivedPDUs = receive.getRecived();
			if(recivedPDUs.size()!=0 && PDU_Reader.read(recivedPDUs.get(0)).getClass().getSimpleName().equals("PDU_APP_REQUEST")){
				//confirmação de que o pdu que recebemos é o request do ficheiro(fomos o cliente escolhido)
				toSend = PDU_Buider.DATA_PDU(ficheiroEnvio.getAbsolutePath(), ficheiroEnvio.getName());
				send = new Sender(toSend, trys, timeWait, myPair, mySocket);
				//ver como posso confirmar que foram enviados todos os ficherios data
			};
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SendCliente:" + e.getMessage());
		}
		
	}
}
