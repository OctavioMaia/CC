package Server;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;

public class ServerInfo {
	private String localIP;
	private int port;
	private HashMap<String,ClienteInfo> clientes; //ip->ClienteInfo

	public ServerInfo(int port){
		try {
			localIP = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println("Não foi possivel obter o ip local do server");
			e.printStackTrace();
		}
		this.port = port;
		this.clientes = new HashMap<>();
	}
	
	public ServerInfo(String ip, int port){
		this.localIP = ip;
		this.port = port;
		this.clientes = new HashMap<>();
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
	protected synchronized HashMap<String, ClienteInfo> getClientes() {
		return clientes;
	}
	protected synchronized void setClientes(HashMap<String, ClienteInfo> clientes) {
		this.clientes = clientes;
	}
	protected synchronized void addCliente(String user, String pass, String ip, int port){		
		ClienteInfo cliente = new ClienteInfo(user, pass, ip, port);
		this.clientes.put(ip, cliente);
	}
}
