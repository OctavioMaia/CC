package Server;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.Thread.State;

import Common.PDU;

public class SendPingMaster implements Runnable{

	private Thread main;
	private PDU pdu_ping;
	private OutputStream osMaster;
	
	public SendPingMaster(Thread m, PDU pdu, OutputStream os) {
		this.pdu_ping=pdu;
		this.osMaster=os;
	}
	
	@Override
	public void run() {
		while(main.getState()!=State.TERMINATED){
			try {
				osMaster.write(PDU.toBytes(pdu_ping));
				System.out.println("Enviei ping para o master");
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(40000);
				//enviar ping para o master de 40 em 40 segundos
				// este ping vai fazer login no server
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}	
	}
}
