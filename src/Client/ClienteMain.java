package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteMain {
	private String user;
	private String ip;
	 
	private Socket sock;
	public InputStream isr;
	public OutputStream osw;
	
	public ClienteMain(String user, String ip, String remotehost, int port){
		this.user = user;
		this.ip=ip;
		try {
			this.sock = new Socket(remotehost,port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		try {
			this.isr = sock.getInputStream();
			this.osw = sock.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public synchronized String getUser() {
		return user;
	}



	public synchronized void setUser(String user) {
		this.user = user;
	}



	public synchronized String getIp() {
		return ip;
	}



	public synchronized void setIp(String ip) {
		this.ip = ip;
	}



	public synchronized Socket getSock() {
		return sock;
	}



	public synchronized void setSock(Socket sock) {
		this.sock = sock;
	}



	public synchronized InputStreamReader getIsr() {
		return isr;
	}



	public synchronized void setIsr(InputStreamReader isr) {
		this.isr = isr;
	}



	public synchronized OutputStreamWriter getOsw() {
		return osw;
	}



	public synchronized void setOsw(OutputStreamWriter osw) {
		this.osw = osw;
	}



	public static void main(String argv[]){
		ClienteMain c1 = new ClienteMain("rui", "192.168.1.65",	"localhost", 6969);
		
		try {
			c1.isr.read(b, off, len);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
