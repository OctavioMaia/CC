package Server;

import java.util.concurrent.locks.ReentrantLock;

public class ClientInfo {
	private String user;
	private String pass;
	private String ip;
	private int port;
	private ReentrantLock lock; //futuramente usar para quando apensar precisarmos mudificar o cliente.

	public ClientInfo(String user, String pass, String ip, int port) {
		this.user = user;
		this.pass = pass;
		this.ip = ip;
		this.port = port;
	}
	
	protected synchronized String getUser() {
		return user;
	}
	protected synchronized void setUser(String user) {
		this.user = user;
	}
	protected synchronized String getPass() {
		return pass;
	}
	protected synchronized void setPass(String pass) {
		this.pass = pass;
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
