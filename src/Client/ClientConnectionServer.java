package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import Common.PDU_APP;
import Common.PDU_APP_STATE;
import Versions.PDUVersion;

/*
 * Apenas é utilizada uma thread para este serviço pois o cliente so vai receber mensagens de um server apenas
 */
public class ClientConnectionServer implements Runnable{

	private Thread main;
	private Client cliente;
	private Socket sock;
	private InputStream isConsult;
	private OutputStream osConsult;
	
	public ClientConnectionServer(Thread m,Client c) {
		this.main = m;
		this.cliente = c;
		this.sock = null;
		this.isConsult = null;
		this.osConsult = null;
	}
	
	private void startServer(){
		try {
			this.sock = this.cliente.getServerSocket().accept();
			System.out.println(Thread.currentThread().getName() + "O server ligou-se ao cliente");
			this.isConsult = sock.getInputStream();
			this.osConsult = sock.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void readFromSock(){
		byte[] version = new byte[1];
		while(sock.isConnected()){
			PDU_APP app_pdu = PDUVersion.readPDU(this.isConsult);
			execPDU(app_pdu);
    	}
	}
	
	/*
	 * Função que vai invocar outras funçoes de acordo com
	 * o tipo de pdu que receber
	 */
	private void execPDU(PDU_APP pdu){
    	if(pdu.getClass().getSimpleName().equals("PDU_APP_STATE")) {
    		PDU_APP_STATE p = (PDU_APP_STATE)pdu;
    		switch (p.getTipo()) {
    			case 1: //recevi um PDU_ARE_YOU_THERE
				{
					break;
				}
    			case 2: //recevi um PDU_I_AM_HERE
    			{
    				break;
    			}
				default:
					break;
			}
		}else{
			if(pdu.getClass().getSimpleName().equals("PDU_APP_CONSULT_REQUEST")){
				System.out.println(Thread.currentThread().getName() + "Ainda nao esta disponivel esta função");
			}
			System.out.println("ERRO");
		}
    }
	
	@Override
	public void run() {
		startServer();
		while(main.getState()!=Thread.State.TERMINATED){
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Estou aberto");
        }
	}
}
