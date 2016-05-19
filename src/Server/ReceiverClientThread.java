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
import java.util.Map;

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
    
    
    
    public ReceiverClientThread(Socket sock, ServerInfo info ){
    	this.user=null;
        this.sockRegisto=sock;
        this.server = info;
        try {
			this.osRegisto = sock.getOutputStream();
	        this.isRegisto = sock.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private void execPDU_APP_CONS_REQ(PDU_APP_CONS_REQ pdu){
    	boolean found = false;
    	Map<String,String> results = null;
    	//vou fazer trafulha aqui
    	if(pdu.getFonte()==1){
    		results = this.server.consultRequestToUsersOnline(this.user.getUser(),pdu.getBanda(),pdu.getMusica(),pdu.getExt());
    	}else{
    		results = this.server.consultRequestToUsersOnline("",pdu.getBanda(),pdu.getMusica(),pdu.getExt());
    	}
    	if(results.size()!=0){ found = true;} 
    	PDU pduResponse = PDU_Buider.CONSULT_RESPONSE_PDU(0, this.server.getId(), this.server.getLocalIP(), this.server.getPort(), found , results);
    	try {
			osRegisto.write(PDU.toBytes(pduResponse));
		} catch (IOException e) {
			System.out.println("Não foi possivel enviar uma repsosta ao ConsulRequest");
			e.printStackTrace();
		}
    }
    
    private void execPDU_APP_REG(PDU_APP_REG pdu){
    	switch (pdu.getTipo()) {
			case PDU_Buider.REGISTO	:	{app_registo(pdu); break;}
			case PDU_Buider.LOGIN	: 	{app_login(pdu); break;}
			case PDU_Buider.LOGOUT	:	{app_logout(pdu); break;}
			default:
				break;
    	}
    }
    
    private void execPDU_APP_STATE(PDU_APP_STATE pdu){
    	switch (pdu.getTipo()) {
			case PDU_APP_STATE.I_AM_HERE_PDU : { receiveI_AM_HERE(pdu); break; }
			default:
				break;
		}
    }
    
    private void execPDU(PDU_APP pdu){
    	switch (pdu.getClass().getSimpleName()) {
			case "PDU_APP_REG"		: 	{ execPDU_APP_REG((PDU_APP_REG)pdu); break; }
			case "PDU_APP_STATE"	: 	{ execPDU_APP_STATE((PDU_APP_STATE)pdu); break; }
			case "PDU_APP_CONS_REQ"	:	{ execPDU_APP_CONS_REQ((PDU_APP_CONS_REQ)pdu); break; }
			default: { System.out.println("ERRO"); break; }
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
    			Socket sockConsulta = new Socket(pdu.getIp(),pdu.getPort());
        		this.user.setSockConsulta(sockConsulta);
        		this.user.setIsConsulta( sockConsulta.getInputStream() );
        		this.user.setOsConsulta( sockConsulta.getOutputStream() );
        		System.out.println("Login realizado com sucesso: " + pdu.getUname() + " -> " + pdu.getIp() +":"+pdu.getPort());
        	} catch (IOException e) {
    			System.out.println("Não foi possivel abrir o socktConsulta");
    			try {
					this.user.getSockConsulta().close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    			mensagem=4;
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
    	int mensagem = server.addRegisto( pdu.getOrigem(), pdu.getUname(), pdu.getPass(), pdu.getIp(), pdu.getPort());
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
    	if(!this.server.containsOnline(this.user.getUser())){
    		this.server.addOnline(this.user.getUser());
    	}
    }
    
    public void run() {
    	while(sockRegisto.isConnected()){
			try {
				PDU_APP pdu = PDUVersion.readPDU(isRegisto);
				if(pdu!=null) { execPDU(pdu); }
			} catch (IOException e) {
				if(sockRegisto.isInputShutdown()){
					System.out.println("Impossivel estabeler ligação com " + this.user.getUser()+" from " + this.user.getIp() + ":" + this.user.getPort());
					break;
				}
			}
    	}	
    	try {
			sockRegisto.close();
			osRegisto.close();
			isRegisto.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			this.user.getSockConsulta().close();
			this.user.getOsConsulta().close();
			this.user.getIsConsulta().close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
    }   
}
