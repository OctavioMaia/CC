import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import Common.PDU;
import Common.PDU_APP_DATA;
import Common.PDU_Buider;
import Common.PDU_Reader;
import Connection.Reciver;
import Connection.Sender;
import Connection.udpReciver;
import Connection.udpSender;

public class TesteCliente {

	public TesteCliente() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			jms();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void jms() throws NumberFormatException, InterruptedException, IOException{
		DatagramSocket cliente = new DatagramSocket(6969);
		Thread.sleep(7000);
		int portacliente = cliente.getLocalPort();
		
		ArrayList<PDU> tosend = new ArrayList<>();
		PDU prequest = PDU_Buider.PROB_REQUEST_PDU(0, "jms", "127.0.0.1",portacliente ); // é o cliente qu emand
		
		tosend.add(prequest);
		
		/*System.out.println("Diga a porta do server");
		Scanner in = new Scanner(System.in);
		String p = in.nextLine();*/
		//DatagramSocket servidor = new DatagramSocket(Integer.parseInt(p), InetAddress.getByName("127.0.0.1"));
		byte[] buf = new byte[99999];
		DatagramPacket destino =  new DatagramPacket(buf, 99999,0, InetAddress.getByName("127.0.0.1"), 7070); 
		//Sender send = new Sender(tosend, 1, 100000, destino, cliente);
		/*System.out.println("Vou mandar prob musica");
		send.send();
		System.out.println("prob enviado");
		*/
		Reciver rec = new Reciver(3, 10000, destino, cliente);
		System.out.println("Vou receber musica");
		rec.recive();
		System.out.println("musica Recebida");
		ArrayList<PDU> papp =rec.getRecived();
		PDU_APP_DATA pdata = PDU_Reader.read(papp);
		System.out.println(pdata.toString());
		
		
		/*udpSender enviarCleiente = new udpSender(destino,cliente, 10000, 20000, 5,tosend);
		enviarCleiente.sendData();
		System.out.println("ja enviei");
		udpReciver recebemusia = new udpReciver(destino, cliente, 10000, 200000);
		System.out.println("Vou receber musica");
		recebemusia.recive();
		System.out.println("musica Recebida");
		ArrayList<PDU> papp =recebemusia.getaReceber();
		PDU_APP_DATA pdata = PDU_Reader.read(papp);
		pdata.toString();*/
	}
}
