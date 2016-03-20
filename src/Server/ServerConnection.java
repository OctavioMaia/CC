/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author ruifreitas
 */
public class ServerConnection implements Runnable{
    
    private Socket sock;
    private BufferedWriter out;
    private BufferedReader in;
    
    public ServerConnection(Socket sock,UMinhoBoleias umb) throws IOException{
        this.sock=sock;
        this.umb=umb;
        this.out=new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		this.in=new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }
    
    private void regista() throws IOException{
	
    }
    
    private void login() throws IOException{
		
    }
        
	private void solicita() throws IOException{
		
	}
	
	private void disponivel() throws IOException{
		
	}
	
	
	byte = 0x01;
    public void run() {
    	String op="a";
    	boolean lout=false;
    	try{
    		while(sock.isConnected() && op !=null){
    			op = in.readLine();
    			if(op!=null){
    				switch(op){
    					default:
    						logout();
    						lout=true;
    						break;
    				}
    			}
    		}
    	}catch(IOException ex){
    		
    	}finally {
    		if(!lout){
    			logout();
    		}
		}
    }
    
}
*/