package Client;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.Thread.State;

import Common.PDU;
import Common.PDU_Buider;

public class SendPingServer implements Runnable{
	
	private Thread main;
	private OutputStream osConsulta;
	private Client user;
	private static int maxTimeSend = 40000;
	
	public SendPingServer(Client c, Thread main){
		this.user = c;
		this.osConsulta = c.getOs();
		this.main=main;
	}
	
	@Override
	public void run() {
		while(this.main.getState()!=State.TERMINATED){
			PDU pduIMH = PDU_Buider.I_AM_HERE_PDU(user.getIp(), user.getPort(), user.getUser());
			try {
				osConsulta.write(PDU.toBytes(pduIMH));
				System.out.println("Enviei uma presença para o server");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				System.out.println("Daqui a " + maxTimeSend + " volto a enviar uma presença para o server ");
				Thread.sleep(maxTimeSend);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	
}
