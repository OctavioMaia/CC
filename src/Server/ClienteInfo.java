package Server;

public class ClienteInfo {
	private int id;
	private String ip;
	private int port;
	
	
	public ClienteInfo(int id, String ip, int port) {
		this.id = id;
		this.ip = ip;
		this.port = port;
	}


	public synchronized int getId() {
		return id;
	}


	public synchronized void setId(int id) {
		this.id = id;
	}


	public synchronized String getIp() {
		return ip;
	}


	public synchronized void setIp(String ip) {
		this.ip = ip;
	}


	public synchronized int getPort() {
		return port;
	}


	public synchronized void setPort(int port) {
		this.port = port;
	}

}
