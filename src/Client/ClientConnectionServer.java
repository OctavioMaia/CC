package Client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import Common.PDU_APP;
import Common.PDU_APP_CONS_REQ;
import Common.PDU_APP_STATE;
import Common.PDU_Buider;
import Versions.PDUVersion;

/*
 * Apenas é utilizada uma thread para este serviço pois o cliente so vai receber mensagens de um server apenas
 * Thread que vai correr todos os pedidos que tem iniciativa no servidor e chegam a um cliente.
 * Caso o servidor perca a conecção com o cliente pelo socketserver do cliente é necessario que cliente volte a realizar login para que a coneção se volte a erstabelecer
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
			e.printStackTrace();
		}
	}
	
	private void execPDU_APP_STATE(PDU_APP_STATE pdu){
		switch (pdu.getTipo()) {
			case 1: //recevi um PDU_ARE_YOU_THERE
			{
				break;
			}
			default:
				break;
		}
	}
	
	private void execPDU_APP_CONSULT_REQUEST(PDU_APP_CONS_REQ pdu){
		boolean found=false;
		File[] files = new File(this.cliente.getFolderMusic()).listFiles();
		for(int i=0; i<files.length && !found ;i++){
			if(files[i].isFile() && files[i].getName().contains(pdu.getBanda()) 
								 && files[i].getName().contains(pdu.getMusica()) 
								 && files[i].getName().contains(pdu.getExt()) ){
				found=true;
			}
		}
		//enviar a porta UDP que este cliente vai ter disponivel para comunicar com outros clientes.
		if(this.cliente.getPortUDP()!=-1){
			//significa que o cliente tem o DatagraSocket inicializado
			PDU_Buider.CONSULT_RESPONSE_PDU(1, this.cliente.getUser(), this.cliente.getIp(), this.cliente.getPortUDP(), found, null);
		}
	}
	
	
	/*
	 * Função que vai invocar outras funçoes de acordo com
	 * o tipo de pdu que receber
	 */
	private void execPDU(PDU_APP pdu){
		switch (pdu.getClass().getSimpleName()) {
			case "PDU_APP_STATE":{ execPDU_APP_STATE((PDU_APP_STATE)pdu); break; }
			case "PDU_APP_CONS_REQ":{ execPDU_APP_CONSULT_REQUEST((PDU_APP_CONS_REQ)pdu); break; }
			default:{ 
				System.out.println(Thread.currentThread().getName() + "Ainda nao esta disponivel esta função");
				break;
			}
		}
    }
	
	@Override
	public void run() {
		startServer();
		while(main.getState()!=Thread.State.TERMINATED && sock.isConnected() ){
			try {
				PDU_APP app_pdu = PDUVersion.readPDU(this.isConsult);
				if(app_pdu!=null) { execPDU(app_pdu); }
			} catch (IOException e) {
				if(sock.isInputShutdown()){
					System.out.println("Impossivel estabeler ligação com servidor");
					break;
				}
			}
        }
	}
}
