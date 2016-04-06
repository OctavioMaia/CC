package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
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
		this.port=-1;
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
		
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Socket getSock() {
		return sock;
	}

	public void setSock(Socket sock) {
		this.sock = sock;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIsr(InputStream is) {
		this.is = is;
	}

	public OutputStream getOs() {
		return os;
	}

	public void setOsw(OutputStream os) {
		this.os = os;
	}

	public int register(){
		PDU register = PDU_Buider.REGISTER_PDU(1, user, pass, ip, port);
		try {
			os.write(PDU.toBytes(register));
		} catch (IOException e) {
			System.out.println("Não foi possivel criar o pack para envio para o servidor");
			e.printStackTrace();
		}
		
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
	
	public void login(){
		PDU register = PDU_Buider.LOGIN_PDU(1, user, pass, ip, port);
		try {
			os.write(PDU.toBytes(register));
		} catch (IOException e) {
			System.out.println("Não foi possivel criar o pack para envio para o servidor");
			e.printStackTrace();
		}
		
		byte[] response = new byte[11];
		try {
			is.read(response, 0, 11);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PDU_APP_REG_RESP resp = (PDU_APP_REG_RESP) PDU_Reader.read(response);
		
		System.out.println("Mensagem: " + resp.getMensagem());
	}
	
	
	public void logout(){
		
	}
	
	
	public static void main(String argv[]){
		int op;
		int port = Integer.parseInt(argv[0]);
		
		Client c1 = new Client("localhost", port);
		c1.setUser("RUI FREITAS");
		c1.setPass("OLATUDOBEM");
		c1.setPort(12346);
		
		System.out.println(ClientMenus.menuInicio());
		
		while ((op = ClientMenus.lerint()) != 0) {
			System.out.println("Opção:"+op);
			switch (op) {
			case 1: {
				c1.register();
				break;
			}
			case 2:{
				//login 
				c1.login();
				break;
			}
			case 3:{
				c1.logout();
			}
			default:
				System.out.println("Insira um numero do menu");
			}
			System.out.println(ClientMenus.menuInicio());
			
		}
	}
}
