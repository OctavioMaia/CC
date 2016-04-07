/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import Client.Client;
import Common.*;
import Versions.PDUVersion1;

/**
 *
 * @author ruifreitas
 */
public class ReceiverClientThread implements Runnable{
    
	private ClientInfo user;
	private ServerInfo server;
	
    private Socket sockRegisto;
    
    private OutputStream osRegisto;
    private InputStream isRegisto;
    
    private Socket sockConsulta;
    private OutputStream osConsulta;
    private InputStream isConsulta;
    
    
    public ReceiverClientThread(Socket sock, ServerInfo info ){
    	this.user=null;
        this.sockRegisto=sock;
        this.server = info;
        try {
			this.osRegisto = sock.getOutputStream();
	        this.isRegisto = sock.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        this.osConsulta = null;
        this.isConsulta = null;
    }
    
    private void execPDU(PDU_APP pdu){
    	if(pdu.getClass().getSimpleName().equals("PDU_APP_REG")) {
    		PDU_APP_REG p = (PDU_APP_REG)pdu;
    		switch (p.getTipo()) {
    			case PDU_Buider.REGISTO	:	{app_registo(p);break;}
				case PDU_Buider.LOGIN	: 	{app_login(p);	break;}
				case PDU_Buider.LOGOUT	:	{app_logout(p);	break;}
				default:
					break;
			}
		}else{
			if(pdu.getClass().getSimpleName().equals("PDU_APP_STATE")){
				this.receiveI_AM_HERE((PDU_APP_STATE)pdu);
			}else{
				System.out.println("ERRO");
			}
		}
    }
    
    private void app_logout(PDU_APP_REG pdu){
    	if(this.server.logout(pdu.getUname())){
    		System.out.println("O Cliente " + pdu.getUname() + " fez logout da sua conta.");
    	}
    }
    
    private void app_login(PDU_APP_REG pdu){
    	int mensagem = server.login(pdu.getUname(), pdu.getPass(), pdu.getIp(), pdu.getPort());
    	if(mensagem==1){
    		this.user = this.server.getUser(pdu.getUname());
    		try {
				this.sockConsulta = new Socket(this.user.getIp(), this.user.getPort());
				this.isConsulta = sockConsulta.getInputStream();
	    		this.osConsulta = sockConsulta.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	PDU respPDU = PDU_Buider.LOGIN_PDU_RESPONSE(mensagem);
    	try {
			osRegisto.write(PDU.toBytes(respPDU));
		} catch (IOException e) {
			System.out.println("Não foi possivel enviar a mensagem de registo");
			e.printStackTrace();
		}
    }
    
    private void app_registo(PDU_APP_REG pdu){
    	int mensagem = server.addRegisto(pdu.getOrigem(), pdu.getUname(), pdu.getPass(), pdu.getIp(), pdu.getPort());
    	PDU respPDU = PDU_Buider.REGISTER_PDU_RESPONSE(mensagem);
    	try {
			osRegisto.write(PDU.toBytes(respPDU));
		} catch (IOException e) {
			System.out.println("Não foi possivel enviar a mensagem de registo");
			e.printStackTrace();
		}
    }
    
    private void sendARE_YOU_THERE(){
    	PDU iamhere = PDU_Buider.I_AM_HERE_PDU(server.getClientes().get(user).getIp(), server.getClientes().get(user).getPort());
		try {
			osConsulta.write(PDU.toBytes(iamhere));
		} catch (IOException e) {
			System.out.println(Thread.currentThread().getName() + "Não foi possivel criar o pack para envio para o servidor");
			e.printStackTrace();
		}
    }
    private void receiveI_AM_HERE(PDU_APP_STATE pdu){
    	
    }
    
    /*
     * Esta função vai testar se o cliente esta on.
     * Se por algum motivo a conecção do socket for a vida vai ser lancada uma exeção
     * no while principal o que vai fazer com que seja enviado um ping ao cliente 
     * caso este nao responda num determinado tempo a flagPING vai passar a falso
     * o que vai fazer com que ocorra a saida do ciclo e assim acaba a conecção do cliente
     */
    private boolean connected(Socket sock, boolean flagPING){
		return sock.isConnected() && flagPING;
    }

    public void run() {
    	int nBytes;
    	boolean flagPING = false;
    	
    	byte[] version = new byte[1];
    	
    	while(sockRegisto.isConnected()){
    		try {
				while(isRegisto.read(version,0,1)!=1);
				switch (version[0]) {
					case 0x01:
						PDU_APP app_pdu = PDUVersion1.readPDU(version[0], this.isRegisto);
						System.out.println(app_pdu);
						execPDU(app_pdu);
						break;
					default:
						System.out.println("A versão " + version[0] + "não se encontra disponovel no sistema.");
						break;
				}
			} catch (IOException | NullPointerException e ) {
				
				System.out.println("Não foi possivel realizar a leitura do campo da versão.\n O cliente " + this.user.getUser() +" caiu.");
				/*
				//timeout 10 segundos
				long startTime = System.currentTimeMillis(); //fetch starting time
				try {
					while((nBytes = isConsulta.read(version,0,1))!=1 || (System.currentTimeMillis()-startTime)<10000)
					{
					    System.out.println("Estou a espera do I_AM_HERE do " + this.user.getUser());
					}
				} catch (IOException e1) {
					 //o cliente nao se encontra disponivel por nenhum dos canais
					 //de comunicação fazer logout e fechar a thread
					 
					//e1.printStackTrace();
					System.out.println("Cliente " + this.user.getUser() + "não se encontra disponivel");
					server.logout(this.user.getUser());
					break;
				}
				if(nBytes==1){
					PDU_APP pduIAMHERE = PDUVersion1.readPDU(version[0], isConsulta);
					flagPING=true;
				}else{
					//remover cliente de online fazer logout
					System.out.println("Cliente " + this.user.getUser() + "não se encontra disponivel");
					server.logout(this.user.getUser());
					break;
				}
				*/
			}
    	}	
    }   
}