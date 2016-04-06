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

public class ClientMain{
	private String user;
	private String ip;
	private String pass;
	private int port;
	 
	private Socket sock;
	private InputStream is;
	private OutputStream os;
	
	public ClientMain(String hostServer, int portServer){
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
	
	protected String getUser() {
		return user;
	}
	
	protected void setUser(String user) {
		this.user = user;
	}
	
	protected String getIp() {
		return ip;
	}

	protected void setIp(String ip) {
		this.ip = ip;
	}

	protected String getPass() {
		return pass;
	}

	protected void setPass(String pass) {
		this.pass = pass;
	}

	protected int getPort() {
		return port;
	}

	protected void setPort(int port) {
		this.port = port;
	}

	protected Socket getSock() {
		return sock;
	}

	protected void setSock(Socket sock) {
		this.sock = sock;
	}

	protected InputStream getIs() {
		return is;
	}

	protected void setIsr(InputStream is) {
		this.is = is;
	}

	protected OutputStream getOs() {
		return os;
	}

	protected void setOsw(OutputStream os) {
		this.os = os;
	}

	private void register(){
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
		
		System.out.println("Mensagem: " + resp.getMensagem());
		
	}
	
	private void login(){
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
	
	
	private void logout(){
		
	}
	
	
	public static void main(String argv[]){
		int op;
		int port = Integer.parseInt(argv[0]);
		
		ClientMain c1 = new ClientMain("localhost", port);
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
