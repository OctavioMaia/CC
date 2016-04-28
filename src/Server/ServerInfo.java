package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;

import Common.PDU;
import Common.PDU_Buider;

public class ServerInfo {
	private String localIP;
	private int port;
	private HashMap<String,ClientInfo> clients; //user->ClienteInfo  clientes registados
	private HashSet<String> online;
	private HashMap<String,ServerDomain> othersServers; 
	private Socket sockMaster;
	private InputStream isMasterSocket;
	private OutputStream osMasterSocket;
	private String masterIP;
	private int masterPort;
	
	
	
	public ServerInfo(int port){
		try {
			localIP = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println("Não foi possivel obter o ip local do server");
			e.printStackTrace();
		}
		this.port = port;
		this.clients = new HashMap<>();
		this.othersServers = new HashMap<>();
		this.online = new HashSet<>();
		this.sockMaster=null;
		this.isMasterSocket=null;
		this.osMasterSocket=null;
		this.masterIP="";
		this.masterPort=-1;
	}
	
	public ServerInfo(String ipServer, int portServer){
		this.localIP = ipServer;
		this.port = portServer;
		this.clients = new HashMap<>();
		this.othersServers = new HashMap<>();
		this.online = new HashSet<>();
	}

	public HashSet<String> getOnline() {
		return online;
	}
	public void setOnline(HashSet<String> online) {
		this.online = online;
	}
	protected synchronized String getLocalIP() {
		return localIP;
	}
	public synchronized void setLocalIP(String localIP) {
		this.localIP = localIP;
	}
	protected synchronized int getPort() {
		return port;
	}
	protected synchronized void setPort(int port) {
		this.port = port;
	}
	protected synchronized HashMap<String, ClientInfo> getClientes() {
		return clients;
	}
	protected synchronized void setClients(HashMap<String, ClientInfo> clientes) {
		this.clients = clientes;
	}
	protected synchronized HashMap<String, ServerDomain> getOthersServers() {
		return othersServers;
	}
	protected synchronized void setOthersServers(HashMap<String, ServerDomain> othersServers) {
		this.othersServers = othersServers;
	}

	protected synchronized int addRegisto(int origem, String uname, String pass, String ip, int port){
		//futuramente verificar a origem pois pode ser o registo de um servidor
		if(clients.containsKey(uname)){
			return 2; // Username já existente
		}
		if(clients.put(uname, new ClientInfo(origem,uname, pass, ip, port, Thread.currentThread()))==null){
			return 1; // registo com sucesso
		};
		return 0;
	}
	protected synchronized boolean logout(String username){
		boolean flag = this.online.remove(username);
		System.out.println(online.toString() +"and " + flag);
		return flag;
		
	}
	/**
	 * Função que realiza o login de um cliente no server
	 * Isto so é possivel ser realizado se o cliente estiver
	 * registado(2) e nao tiver com login feito(3).
	 * E por fim a pass tem de corresponder
	 * @param uname
	 * @param pass
	 * @param ip
	 * @param port
	 * @return
	 */
	protected synchronized int login(String uname, String pass, String ip, int port){
		if(!clients.containsKey(uname)){
			return 2; // nao tem registo feito
		}else{
			System.out.println(this.online);
			if(this.online.contains(uname)){
				return 3; // já tem login feito
			}else{
				ClientInfo cl = clients.get(uname);
				if(cl.getPass().equals(pass)){
					cl.setIp(ip);
					cl.setPort(port);
					online.add(uname);
					return 1;
				}
			}
		}
		return 0;
	}

	protected synchronized ClientInfo getUser(String user){
		return this.clients.get(user);
	}

	/*
	 * 
	 */
	public synchronized void checkTimeStampClient(String user,int maxTime) {
		ClientInfo c = this.clients.get(user);
		if(c.checkTimeStamp(maxTime)==false){
			//caso em que nao foi verificada a permaneicia do cliente no serviço
			c.logout();
			this.online.remove(user);
		};
	}

	
	protected synchronized void connectToMaster(String ip, int port){
		this.masterIP=ip;
		this.masterPort=port;
		try {
			this.sockMaster = new Socket(this.masterIP, this.masterPort);
			this.isMasterSocket = sockMaster.getInputStream();
			this.osMasterSocket = sockMaster.getOutputStream();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//enviar um registo para o master
		PDU pduRegisterMaster = PDU_Buider.REGISTER_PDU(0, this.localIP+":"+this.port, "", this.localIP, this.port);
		try {
			this.osMasterSocket.write(PDU.toBytes(pduRegisterMaster));
		} catch (IOException e) {
			System.out.println("Impossivel enviar mensagem de registo para o master");
			e.printStackTrace();
		}
	}
}
