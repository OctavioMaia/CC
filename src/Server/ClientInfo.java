package Server;

import java.util.concurrent.locks.ReentrantLock;

public class ClientInfo {
	private String user;
	private String pass;
	private String ip;
	private int port;
	private ReentrantLock lock; //futuramente usar para quando apensar precisarmos mudificar o cliente.
	private Thread runRequest; //Thread que foi aberta apos a abertura do socket no server para um cliente
	private long timeStanp;
	private boolean flagStopThread;
	
 	public ClientInfo(String user, String pass, String ip, int port, Thread currentThread) {
		this.user = user;
		this.pass = pass;
		this.ip = ip;
		this.port = port;
		this.runRequest = currentThread;
		this.timeStanp = System.currentTimeMillis();
		this.flagStopThread = false;
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
	public synchronized Thread getRunRequest() {
		return runRequest;
	}
	public synchronized void setRunRequest(Thread runRequest) {
		this.runRequest = runRequest;
	}
	public boolean getFlagStopThread() {
		return flagStopThread;
	}
	public void setFlagStopThread(boolean flagStopThread) {
		this.flagStopThread = flagStopThread;
	}


	public synchronized void logout(){
		this.flagStopThread = true ;
		this.runRequest=null;
	}
	
	/*
	 * return true caso esteja ativo
	 * return falso caso contrario
	 */
	public boolean checkTimeStamp(int maxTime){
		System.out.println("Tempo:" + (System.currentTimeMillis()-this.timeStanp) + " " + ((System.currentTimeMillis()-this.timeStanp)>maxTime));
		if((System.currentTimeMillis()-this.timeStanp)>maxTime){
			flagStopThread=true;
			return false;
		}
		return true;
	}


	public synchronized long getTimeStanp() {
		return timeStanp;
	}


	public synchronized void setTimeStanp(long timeStanp) {
		this.timeStanp = timeStanp;
	}
}
