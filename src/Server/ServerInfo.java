package Server;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class ServerInfo {
	private String localIP;
	private int port;
	private HashMap<String,ClientInfo> clients; //user->ClienteInfo
	private HashMap<String,ServerDomain> othersServers; 

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
	}
	
	

	public ServerInfo(String ipServer, int portServer){
		this.localIP = ipServer;
		this.port = portServer;
		this.clients = new HashMap<>();
	}

	private synchronized String getLocalIP() {
		return localIP;
	}
	private synchronized void setLocalIP(String localIP) {
		this.localIP = localIP;
	}
	private synchronized int getPort() {
		return port;
	}
	private synchronized void setPort(int port) {
		this.port = port;
	}
	private synchronized HashMap<String, ClientInfo> getClientes() {
		return clients;
	}
	private synchronized void setClients(HashMap<String, ClientInfo> clientes) {
		this.clients = clientes;
	}
	protected synchronized HashMap<String, ServerDomain> getOthersServers() {
		return othersServers;
	}

	protected synchronized void setOthersServers(HashMap<String, ServerDomain> othersServers) {
		this.othersServers = othersServers;
	}

	protected synchronized int addRegisto(int origem, String uname, String pass, String ip, int port){
		System.out.println("Origem: " + origem);
		System.out.println("USER: " + uname);
		System.out.println("PASS: " + pass);
		System.out.println("IP: " + ip);
		System.out.println("PORT: " + port);
		//futuramente verificar a origem pois pode ser o registo de um servidor
		if(clients.containsKey(uname)){
			return 2; // Username já existente
		}
		if(clients.put(uname, new ClientInfo(uname, pass, ip, port))==null){
			return 1; // registo com sucesso
		};
		return 0;
	}

}
