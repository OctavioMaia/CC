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

import Common.*;
import Versions.PDUVersion1;

/**
 *
 * @author ruifreitas
 */
public class ReceiverClientThread implements Runnable{
    
	private String user;
    private Socket sockCliente;
    private ServerInfo server;
    private OutputStream os;
    private InputStream is;
    
    
    public ReceiverClientThread(Socket sock, ServerInfo info ){
    	this.user=null;
        this.sockCliente=sock;
        this.server = info;
        try {
			this.os = sock.getOutputStream();
	        this.is = sock.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void execPDU(PDU_APP pdu){
    	if(pdu.getClass().getSimpleName().equals("PDU_APP_REG")) {
			app_registo((PDU_APP_REG)pdu);
		}else{
			if(pdu.getClass().getSimpleName().equals("PDU_APP_LOGOUT")){
				app_logout((PDU_APP_LOGOUT)pdu);
			}else{
				if(pdu.getClass().getSimpleName().equals("PDU_APP_LOGIN")){
					app_login((PDU_APP_LOGIN)pdu);
				}
			}
			System.out.println("ERRO");
		}
    }
    
    private void app_logout(PDU_APP_LOGOUT pdu){
    	server.logout(this.user);
    }
    
    private void app_login(PDU_APP_LOGIN pdu){
    	int mensagem = server.login(pdu.getUname(), pdu.getPass(), pdu.getIp(), pdu.getPort());
    	if(mensagem==1){
    		this.user = pdu.getUname();
    	}
    	PDU respPDU = PDU_Buider.LOGIN_PDU_RESPONSE(mensagem);
    	try {
			os.write(PDU.toBytes(respPDU));
		} catch (IOException e) {
			System.out.println("Não foi possivel enviar a mensagem de registo");
			e.printStackTrace();
		}
    }
    
    private void app_registo(PDU_APP_REG pdu){
    	int mensagem = server.addRegisto(pdu.getOrigem(), pdu.getUname(), pdu.getPass(), pdu.getIp(), pdu.getPort());
    	PDU respPDU = PDU_Buider.REGISTER_PDU_RESPONSE(mensagem);
    	try {
			os.write(PDU.toBytes(respPDU));
		} catch (IOException e) {
			System.out.println("Não foi possivel enviar a mensagem de registo");
			e.printStackTrace();
		}
    }
    
    public void run() {
    	
    	byte[] version = new byte[1];
    	
    	while(sockCliente.isConnected()){
    		try {
				while(is.read(version,0,1)!=1);
			} catch (IOException e) {
				System.out.println("Não foi possivel realizar a leitura do campo da versão.");
				e.printStackTrace();
			}
    		
    		switch (version[0]) {
			case 0x01:
				PDU_APP app_pdu = PDUVersion1.readPDU(version[0], this.is);
				System.out.println(app_pdu);
				execPDU(app_pdu);
				break;
			default:
				System.out.println("A versão " + version[0] + "não se encontra disponovel no sistema.");
				break;
			}
    	
    	}
    	
    	
    	
    }   
}