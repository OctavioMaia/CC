/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author ruifreitas
 */
public class ServerConnection implements Runnable{
    
    private Socket sockCliente;
    private OutputStreamWriter osw;
    private InputStreamReader isr;
    
    public ServerConnection(Socket sock) throws IOException{
        this.sockCliente=sock;
        this.osw = new OutputStreamWriter(sock.getOutputStream());
        this.isr = new InputStreamReader(sock.getInputStream());
    }
    
	
    public void run() {
    	while(sockCliente.isConnected()){
    			
    	}
    	
    }   
}