package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import Common.PDU;
import Common.PDU_APP_REG_RESP;
import Common.PDU_Buider;
import Common.PDU_Reader;

public class Client{
	private String user;
	private String ip;
	private String pass;
	private int port;
	private ClientConnectionServer conectServer;
	private SendPingServer pingServer;
	private ServerSocket serverSocket;
	private Socket sock;
	private InputStream is;
	private OutputStream os;
	
	
	
	public Client(String hostServer, int portServer){
		this.user = new String();
		try {
			this.ip= Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.pass= new String();
		try {
			this.sock = new Socket(hostServer,portServer);
		} catch (UnknownHostException e) {
			System.out.println("Host desconhecido");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Não foi possivel abrir o socket");
			e.printStackTrace();
		} 
		try {
			this.is = sock.getInputStream();
			this.os = sock.getOutputStream();
		} catch (IOException e) {
			System.out.println("Não foi possivel criar as streams de bytes");
			e.printStackTrace();
		}
		
		try {
			this.serverSocket = new ServerSocket(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.port = this.serverSocket.getLocalPort();
		
		this.conectServer = null;
		this.pingServer=null;
		
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
	public synchronized String getPass() {
		return pass;
	}
	public synchronized void setPass(String pass) {
		this.pass = pass;
	}
	public synchronized int getPort() {
		return port;
	}
	public synchronized void setPort(int port) {
		this.port = port;
	}
	public synchronized Socket getSock() {
		return sock;
	}
	public synchronized void setSock(Socket sock) {
		this.sock = sock;
	}
	public synchronized InputStream getIs() {
		return is;
	}
	public synchronized void setIsr(InputStream is) {
		this.is = is;
	}
	public synchronized OutputStream getOs() {
		return os;
	}
	public synchronized void setOsw(OutputStream os) {
		this.os = os;
	}
	public synchronized ClientConnectionServer getConectServer() {
		return conectServer;
	}
	public synchronized void setConectServer(ClientConnectionServer conectServer) {
		this.conectServer = conectServer;
	}
	public synchronized ServerSocket getServerSocket() {
		return serverSocket;
	}
	public synchronized void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	public synchronized SendPingServer getPingServer() {
		return pingServer;
	}
	public synchronized void setPingServer(SendPingServer pingServer) {
		this.pingServer = pingServer;
	}

	public synchronized int register(String username, String password, int p){
		PDU register = PDU_Buider.REGISTER_PDU(1, username, password, this.ip, p);
		try {
			os.write(PDU.toBytes(register));
		} catch (IOException e) {
			System.out.println("Não foi possivel criar o pack para envio para o servidor");
			e.printStackTrace();
		}
		//partilhar duvida se esta parte devia de estar aqui ou devia ser uma leitura como o de registo fora desta função
		byte[] response = new byte[11];
		try {
			is.read(response, 0, 11);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PDU_APP_REG_RESP resp = (PDU_APP_REG_RESP) PDU_Reader.read(response);;
		
		
		return resp.getMensagem();
	}
	public synchronized int login(String username, String password, int p){
		PDU login = PDU_Buider.LOGIN_PDU(1, username, password, this.ip, p);
		try {
			os.write(PDU.toBytes(login));
		} catch (IOException e) {
			System.out.println("Não foi possivel criar o pack para envio para o servidor");
			e.printStackTrace();
		}
		
		byte[] response = new byte[11];
		try {
			is.read(response, 0, 11);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		PDU_APP_REG_RESP resp = (PDU_APP_REG_RESP) PDU_Reader.read(response);
		System.out.println("Cliente:"+resp);
		int m = resp.getMensagem();
		
		if(m==1){
			setUser(username);
			setPass(password);
			setPort(p);
			this.conectServer = new ClientConnectionServer(Thread.currentThread(), this);
			this.pingServer = new SendPingServer(this,Thread.currentThread());
			Thread cs = new Thread(this.conectServer);
			Thread ps = new Thread(this.pingServer);
			cs.start();
			ps.start();
		}
		return m;
	}
	public synchronized void setIs(InputStream is) {
		this.is = is;
	}

	public synchronized void setOs(OutputStream os) {
		this.os = os;
	}

	public synchronized void logout(){
		PDU logout = PDU_Buider.LOGOUT_PDU(1, user, pass, this.ip, port);
		try {
			os.write(PDU.toBytes(logout));
		} catch (IOException e) {
			System.out.println("Não foi possivel criar o pack para envio para o servidor");
			e.printStackTrace();
		}
	}

}
