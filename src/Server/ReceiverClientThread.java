/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import Common.PDU;
import Common.PDU_APP;
import Common.PDU_APP_REG;
import Common.PDU_APP_REG_RESP;
import Common.PDU_APP_STATE;
import Common.PDU_Buider;
import Versions.PDUVersion1;

/**
 *
 * @author ruifreitas
 */
public class ReceiverClientThread implements Runnable{
    
    private Socket sockCliente;
    private ServerInfo server;
    private OutputStream os;
    private InputStream is;
    
    
    public ReceiverClientThread(Socket sock, ServerInfo info ) throws IOException{
        this.sockCliente=sock;
        this.server = info;
        this.os = sock.getOutputStream();
        this.is = sock.getInputStream();
    }
    
	
    private void execPDU(PDU_APP pdu){
    	System.out.println(pdu.getClass().getSimpleName());
    	if(pdu.getClass().getSimpleName().equals("PDU_APP_REG")) {
			app_registo((PDU_APP_REG)pdu);
		}else{
			System.out.println("ERRO");
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
				while(is.read(version,0,1)!=1){System.out.println("AINDA ESTOU AQUI");};
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