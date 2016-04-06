package Server;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class ServerInfo {
	private String localIP;
	private int port;
	private HashMap<String,ClientInfo> clients; //user->ClienteInfo  clientes registados
	private HashSet<String> online;
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

	protected synchronized String getLocalIP() {
		return localIP;
	}
	protected synchronized void setLocalIP(String localIP) {
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

	protected synchronized void logout(String username){
		if(this.online.remove(username)){
			System.out.println("O Cliente " + username + "fez logout da sua conta.");
		};
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
			if(online.contains(uname)){
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
}
