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
import Versions.PDUVersion;

/**
 *
 * @author ruifreitas
 */
public class ReceiverClientThread implements Runnable{
    
	private ClientInfo user;
	private ServerInfo server;
	
	//socket criado pelo servidor
    private Socket sockRegisto;
    private OutputStream osRegisto;
    private InputStream isRegisto;
    
    //socket criado pelo cliente
    private Socket sockConsulta;
    private OutputStream osConsulta;
    private InputStream isConsulta;
    
    
    public ReceiverClientThread(Socket sock, ServerInfo info ){
    	this.user=null;
        this.sockRegisto=sock;
        this.sockConsulta=null;
        this.server = info;
        try {
			this.osRegisto = sock.getOutputStream();
	        this.isRegisto = sock.getInputStream();
		} catch (IOException e) {
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
				PDU_APP_STATE p = (PDU_APP_STATE) pdu;
				switch (p.getTipo()) {
					case PDU_APP_STATE.I_AM_HERE_PDU: {
						receiveI_AM_HERE(p);
						break;
					}
				default:
					break;
				}
			}else{
				System.out.println("ERRO");
			}
		}
    }
    
    private void app_logout(PDU_APP_REG pdu){
    	if(this.server.logout(pdu.getUname())){
    		//pensar em fazer close aos sockets
    		System.out.println("Logout realizado com sucesso: " + pdu.getUname() + " -> " + pdu.getIp() +":"+pdu.getPort());
    	}
    }
    
    private void app_login(PDU_APP_REG pdu){
    	int mensagem = server.login(pdu.getUname(), pdu.getPass(), pdu.getIp(), pdu.getPort());
    	
    	if(mensagem==1){
    		try {
    			this.user = this.server.getUser(pdu.getUname());
        		this.sockConsulta = new Socket(pdu.getIp(),pdu.getPort());
        		this.isConsulta = sockConsulta.getInputStream();
        		this.osConsulta = sockConsulta.getOutputStream();
        		System.out.println("Login realizado com sucesso: " + pdu.getUname() + " -> " + pdu.getIp() +":"+pdu.getPort());
        	} catch (IOException e) {
    			System.out.println("Não foi possivel abrir o socktConsulta");
    			try {
					sockConsulta.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    			mensagem=4;
    			e.printStackTrace();
    		}
    	}
    	
    	PDU respPDU = PDU_Buider.LOGIN_PDU_RESPONSE(mensagem);
    	System.out.println("LOGIN_RESPONSE-"+respPDU.toString());
    	
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
    		System.out.println("Registo realizado com sucesso: " + pdu.getUname() + " -> " + pdu.getIp() +":"+pdu.getPort());
		} catch (IOException e) {
			System.out.println("Não foi possivel enviar a resposta do registo");
			e.printStackTrace();
		}
    }
    
    private void receiveI_AM_HERE(PDU_APP_STATE pdu){
    	this.user.setTimeStanp(System.currentTimeMillis());
    }
    
    public void run() {
    	while(sockRegisto.isConnected()){
    		PDU_APP pdu = PDUVersion.readPDU(isRegisto);
    		if(pdu!=null) { execPDU(pdu); }
    	}	
    	try {
			sockRegisto.close();
			osRegisto.close();
			isRegisto.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			sockConsulta.close();
			osConsulta.close();
			isConsulta.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
    }   
}