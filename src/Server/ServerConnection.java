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

/**
 *
 * @author ruifreitas
 */
public class ServerConnection implements Runnable{
    
    private Socket sockCliente;
    private ServerInfo server;
    private OutputStream os;
    private InputStream is;
    
    public ServerConnection(Socket sock) throws IOException{
        this.sockCliente=sock;
        this.os = sock.getOutputStream();
        this.is = sock.getInputStream();
    
    }
    
	
    public void run() {
    	byte[] pdu = new byte[49152];
    	byte aux[] = new byte[1];
    	
    	while(sockCliente.isConnected()){
    		try {
				is.read(aux, 0, 8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		int j=0;
    		pdu[]
    		
    		System.out.println(register.getVersion());
    		
    		
    		
    		System.out.println(register.getSecurity());
    		register.getTipo();
    		
    		
    		
    	}
    	
    }   
}