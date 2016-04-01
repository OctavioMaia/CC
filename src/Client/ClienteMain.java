package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import Common.PDU;
import Common.PDU_Buider;

public class ClienteMain implements TypeRequest{
	private String user;
	private String ip;
	private String pass;
	private int port;
	 
	private Socket sock;
	public InputStream is;
	public OutputStream os;
	
	private static Scanner input = new Scanner(System.in);
	
	public ClienteMain(String user,String pass, String ip, String remotehost, int port){
		this.user = user;
		this.ip=ip;
		this.pass=pass;
		this.port=port;
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
			this.is = sock.getInputStream();
			this.os = sock.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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

	protected static int lerint() {
		Integer ret = 0;
		String inp = input.nextLine();
		try {
			ret = Integer.parseInt(inp);
		} catch (Exception e) {
			ret = lerint();
		}
		return ret;
	}
	
	private void register(){
		PDU register = PDU_Buider.REGISTER_PDU(1, user, pass, ip, port);
		try {
			os.write(PDU.toBytes(register));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String argv[]){
		int n;
		int port = 6969;
		
		ClienteMain c1 = new ClienteMain("rui", "123","192.168.1.65","localhost", 6969);
		
		System.out.println(ClienteMenus.menuInicio());
		
		while ((n = lerint()) != 0) {
			switch (n) {
			case 1: {
				System.out.println("Entrei no registo");
				c1.register();
				break;
			}
			default:
				System.out.println("Insira um numero do menu");
			}
		}
	}
}
