/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
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
        this.sockConsulta=null;
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
				PDU_APP_STATE p = (PDU_APP_STATE) pdu;
				switch (p.getTipo()) {
					case PDU_APP_STATE.I_AM_HERE_PDU: {
						receiveI_AM_HERE(p);
						break;
					}
					case PDU_APP_STATE.ARE_YOU_THERE: {
						break;
					}
				default:
					break;
				}
				this.receiveI_AM_HERE((PDU_APP_STATE)pdu);
			}else{
				System.out.println("ERRO");
			}
		}
    }
    
    private void app_logout(PDU_APP_REG pdu){
    	if(this.server.logout(pdu.getUname())){
    		//pensar em fazer close aos sockets
    		System.out.println("O Cliente " + pdu.getUname() + " fez logout da sua conta.");
    	}
    }
    
    private void app_login(PDU_APP_REG pdu){
    	int mensagem = server.login(pdu.getUname(), pdu.getPass(), pdu.getIp(), pdu.getPort());
    	
    	if(mensagem==1){
    		try {
    			System.out.println(pdu.getIp() +"--"+pdu.getPort());
        		this.sockConsulta = new Socket(pdu.getIp(),pdu.getPort());
        		this.isConsulta = sockConsulta.getInputStream();
        		this.osConsulta = sockConsulta.getOutputStream();
        		this.user = this.server.getUser(pdu.getUname());
        	} catch (IOException e) {
    			System.out.println("Não foi possivel abrir o socktConsulta");
    			try {
					sockConsulta.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
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
		} catch (IOException e) {
			System.out.println("Não foi possivel enviar a mensagem de registo");
			e.printStackTrace();
		}
    }
    
    private void receiveI_AM_HERE(PDU_APP_STATE pdu){
    	this.server.setTimeStampClient(this.user.getUser());
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
			} catch (IOException e ) {
				System.out.println("Não foi possivel realizar a leitura do campo da versão.");				
			}
    	}	
    	try {
			sockRegisto.close();
			osRegisto.close();
			isRegisto.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			sockConsulta.close();
			osConsulta.close();
			isConsulta.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }   
}