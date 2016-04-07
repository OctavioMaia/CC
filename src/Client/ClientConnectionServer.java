package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Common.PDU;
import Common.PDU_APP;
import Common.PDU_APP_REG;
import Common.PDU_APP_STATE;
import Common.PDU_Buider;
import Server.ReceiverClientThread;
import Versions.PDUVersion1;

/*
 * Apenas é utilizada uma thread para este serviço pois o cliente so vai receber mensagens de um server apenas
 */
public class ClientConnectionServer implements Runnable{

	private Thread main;
	private ServerSocket serverS;
	private Client cliente;
	private Socket sock;
	private InputStream is;
	private OutputStream os;
	
	public ClientConnectionServer(Thread m,Client c) {
		this.main = m;
		this.cliente = c;
		try {
			this.serverS = new ServerSocket(this.cliente.getPort());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void startServer(){
		try {
			this.sock = this.serverS.accept();
			System.out.println(Thread.currentThread().getName() + "O server ligou-se ao cliente");
			this.is = sock.getInputStream();
			this.os = sock.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void readFromSock(){
		byte[] version = new byte[1];
		while(sock.isConnected()){
    		try {
				while(is.read(version,0,1)!=1);
				switch (version[0]) {
					case 0x01:
						PDU_APP app_pdu = PDUVersion1.readPDU(version[0], this.is);
						System.out.println(Thread.currentThread().getName()+app_pdu);
						execPDU(app_pdu);
						break;
					default:
						System.out.println(Thread.currentThread().getName() + "A versão " + version[0] + "não se encontra disponovel no sistema.");
						break;
				}
			} catch (IOException e) {
				System.out.println(Thread.currentThread().getName() + "Não foi possivel realizar a leitura do campo da versão.");
				e.printStackTrace();
				break;//ver nmelhor este passo nao sei se isto é correto
			}
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
					responseARE_YOU_THERE();
					break;
				}
    			case 2: //recevi um PDU_I_AM_HERE
    			{
    				makeSomething(p);
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
	/*
	 * Função que responde a um ping por parte do servidor
	 * ao cliente. Envia um I_AM_HERE
	 */
	private void responseARE_YOU_THERE(){
		PDU iamhere = PDU_Buider.I_AM_HERE_PDU(cliente.getIp(), cliente.getPort());
		try {
			os.write(PDU.toBytes(iamhere));
		} catch (IOException e) {
			System.out.println(Thread.currentThread().getName() + "Não foi possivel criar o pack para envio para o servidor");
			e.printStackTrace();
		}
	}
	/*
	 * Função para realizar o que for necessario a quando
	 * do um pedido de ping afirmativo
	 */
	private void makeSomething(PDU_APP_STATE p){
		//ainda nao sei o que fazer aqui
	}
	
	@Override
	public void run() {
		while(main.getState()!=Thread.State.TERMINATED){
			startServer();
			readFromSock();
			
        }
	}
}
