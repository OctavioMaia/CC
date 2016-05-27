package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Scanner;

import Common.PDU;
import Common.PDU_APP;
import Common.PDU_APP_CONS_RESP;
import Common.PDU_APP_REG_RESP;
import Common.PDU_Buider;
import Common.PDU_Reader;
import Versions.PDUVersion;

public class Client{
	private String user;
	private String ip;
	private String pass;
	private int portTCP; //porta a onde vou estar a escuta para o server
	private String folderMusic;
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
			System.out.println("Não foi possivel obter o IP Local");
		}
		this.pass= new String();
		this.setFolderMusic(null);
		try {
			this.sock = new Socket(hostServer,portServer);
			this.is = sock.getInputStream();
			this.os = sock.getOutputStream();
		} catch (UnknownHostException e) {
			System.out.println("Host desconhecido");
		} catch (IOException e) {
			System.out.println("Não foi possivel abrir o socket com o server");
		} 
		try {
			this.serverSocket = new ServerSocket(0);
		} catch (IOException e) {
			System.out.println("Não foi possivel criar o servidor TCP");
		}
		this.portTCP = this.serverSocket.getLocalPort();
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
	public synchronized int getPortTCP() {
		return portTCP;
	}
	public synchronized void setPortTCP(int portTCP) {
		this.portTCP = portTCP;
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
	public synchronized void setIs(InputStream is) {
		this.is = is;
	}
	public synchronized void setOs(OutputStream os) {
		this.os = os;
	}
	public synchronized String getFolderMusic() {
		return folderMusic;
	}
	public synchronized void setFolderMusic(String folderMusic) {
		this.folderMusic = folderMusic;
	}

	public synchronized int register(String username, String password, int p){
		int resp = 0;
		PDU register = PDU_Buider.REGISTER_PDU(1, username, password, this.ip, p);
		try {
			os.write(PDU.toBytes(register));
			try {
				PDU_APP pdu_resp = PDUVersion.readPDU(is);
				if(pdu_resp.getClass().getSimpleName().equals("PDU_APP_REG_RESP")){
					resp = ((PDU_APP_REG_RESP)pdu_resp).getMensagem();
				}
			} catch (IOException e1) {
				System.out.println("Não foi recebida a resposta do servidor ao pedido de registo RESP: " + resp);
			}
		} catch (IOException e) {
			System.out.println("Não foi possivel enviar para o servidor um pedido de registo para o servidor");
		}
		return resp;
	}
	
	public synchronized int login(String username, String password, int p){
		int respMess = 0;
		PDU login = PDU_Buider.LOGIN_PDU(1, username, password, this.ip, p);
		try {
			os.write(PDU.toBytes(login));
			try {
				PDU_APP pdu_resp = PDUVersion.readPDU(is);
				if(pdu_resp.getClass().getSimpleName().equals("PDU_APP_REG_RESP")){
					respMess = ((PDU_APP_REG_RESP)pdu_resp).getMensagem();
				}
				if(respMess==1){
					setUser(username);
					setPass(password);
					setPortTCP(p);
					this.conectServer = new ClientConnectionServer(Thread.currentThread(), this);
					this.pingServer = new SendPingServer(this,Thread.currentThread());
					Thread cs = new Thread(this.conectServer);
					Thread ps = new Thread(this.pingServer);
					cs.start();
					ps.start();
				}
			} catch (IOException e1) {
				System.out.println("Não foi recebida a resposta do servidor ao pedido de logn RESP: " + respMess);
			}
		} catch (IOException e) {
			System.out.println("Não foi possivel enviar o pedido de login para o servidor");
		}
		return respMess;
	}

	public synchronized void logout(){
		PDU logout = PDU_Buider.LOGOUT_PDU(1, this.user, this.pass, this.ip, this.portTCP);
		try {
			os.write(PDU.toBytes(logout));
		} catch (IOException e) {
			System.out.println("Não foi possivel enviar o pedido de logout para o servidor");
		}
	}

	public synchronized Map<String, String> consultRequest(String banda, String musica, String ext){
		Map<String, String> result = null;
		PDU pduRequest = PDU_Buider.CONSULT_REQUEST_PDU(1, this.ip, this.portTCP, banda, musica, ext, this.user);
		try {
			os.write(PDU.toBytes(pduRequest));
			try {
				PDU_APP pduResponse = PDUVersion.readPDU(is);
				if(pduResponse.getClass().getSimpleName().equals("PDU_APP_CONS_RESP")){
					result = ((PDU_APP_CONS_RESP) pduResponse).getResult();
					Thread trcc = new Thread( new ReceiveConectionClient(result, this) );
					trcc.start();
				}

			} catch (IOException e) {
				System.out.println("Não foi possivel receber a resposta da consulta por parte do servidor");
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("Não foi possivel enviar o pedido de consulta para o servidor");
			e.printStackTrace();
		}
		return result;
	}
}
