package Server;

import java.io.IOException;
import Common.PDU;
import Common.PDU_Buider;


public class SendPingMaster implements Runnable{

	private ServerInfo server;
	private Thread main;
	
	public SendPingMaster(ServerInfo s,Thread main) {
		this.server=s;
		this.main=main;
	}
	
	@Override
	public void run() {
		while(main.getState()!=Thread.State.TERMINATED){
			PDU pdu = PDU_Buider.I_AM_HERE_PDU(server.getLocalIP(), server.getPort(), server.getId()); 
			try {
				server.getOsMasterSocket().write(PDU.toBytes(pdu));
			} catch (IOException e) {
				System.out.println("Não é possivel enviar");
				e.printStackTrace();
			}
		}
	}

}
