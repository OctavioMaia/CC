import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import Common.PDU;
import Common.PDU_Buider;
import UDP.UDPreciver;
import UDP.UDPsender;

public class RuiReceber {
	public static void main(String[] args) throws UnknownHostException {
		DatagramSocket receber;
		try {
			receber = new DatagramSocket(0);
			System.out.println("IP:"+InetAddress.getLocalHost().getHostAddress());
			System.out.print("PortaUDP:"+receber.getLocalPort());
			
			UDPreciver rec = new UDPreciver(receber, 5, 50);
			
			rec.recive();
			
			ArrayList<PDU> pdus = rec.getaReceber();
			System.out.println("SIZE" + pdus.size());
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
