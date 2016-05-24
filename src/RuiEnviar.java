import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import Common.PDU;
import Common.PDU_Buider;
import UDP.UDPsender;

public class RuiEnviar {

	
	public static void main(String[] args) {
		String hostToSend="192.168.1.83";
		int portToSend=51350;
		DatagramSocket dataSend;
		try {
			dataSend = new DatagramSocket(0);
			ArrayList<PDU> paraEnvio = new ArrayList<>();
			paraEnvio.add(PDU_Buider.PROB_REQUEST_PDU(1, "rui", hostToSend, dataSend.getLocalPort()));
			
			try {
				UDPsender sender = new UDPsender(dataSend,InetAddress.getByName(hostToSend),portToSend, 5, 50, 5, paraEnvio);
				try {
					sender.sendData();
				} catch (InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
