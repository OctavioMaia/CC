package Server;

import java.io.IOException;
import Common.PDU;
import Common.PDU_Buider;


public class SendPingMaster implements Runnable{

	private ServerInfo server;
	private Thread main;
	private static int maxTimeSend = 40000;
	
	public SendPingMaster(ServerInfo s,Thread main) {
		this.server=s;
		this.main=main;
	}
	
	@Override
	public void run() {
		while(main.getState()!=Thread.State.TERMINATED){
			try {
				Thread.sleep(maxTimeSend);
				PDU pdu = PDU_Buider.I_AM_HERE_PDU(server.getLocalIP(), server.getPort(), server.getId()); 
				server.getOsMasterSocket().write(PDU.toBytes(pdu));
			} catch (IOException | InterruptedException e1) {
				System.out.println(Thread.currentThread() + "Não é possivel enviar ping para Master");
				break;
			}
		}
	}

}
