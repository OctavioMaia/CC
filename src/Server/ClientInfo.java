package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import Common.PDU;
import Common.PDU_APP;
import Common.PDU_APP_CONS_REQ;
import Common.PDU_APP_CONS_RESP;
import Common.PDU_Buider;
import Versions.PDUVersion;

public class ClientInfo {
	private int tipoCliente; /*0 server 1 cliente*/
	private String user;
	private String pass;
	private String ip;
	private int port;
	private ReentrantLock lock; //futuramente usar para quando apensar precisarmos mudificar o cliente.
	private Thread runRequest; //Thread que foi aberta apos a abertura do socket no server para um cliente
	private long timeStanp;
	private boolean flagStopThread;
	private Socket sockConsulta;
    private OutputStream osConsulta;
    private InputStream isConsulta;
	
 	public ClientInfo(int TipoClienteOrigem,String user, String pass, String ip, int port, Thread currentThread) {
 		this.tipoCliente = TipoClienteOrigem;
		this.user = user;
		this.pass = pass;
		this.ip = ip;
		this.port = port;
		this.runRequest = currentThread;
		this.timeStanp = System.currentTimeMillis();
		this.flagStopThread = false;
		this.sockConsulta = null;
		this.isConsulta = null;
		this.osConsulta = null;
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
	protected synchronized String getIp() {
		return ip;
	}
	protected synchronized void setIp(String ip) {
		this.ip = ip;
	}
	protected synchronized int getPort() {
		return port;
	}
	protected synchronized void setPort(int port) {
		this.port = port;
	}
	protected synchronized Thread getRunRequest() {
		return runRequest;
	}
	protected synchronized void setRunRequest(Thread runRequest) {
		this.runRequest = runRequest;
	}
	protected synchronized boolean getFlagStopThread() {
		return flagStopThread;
	}
	protected synchronized void setFlagStopThread(boolean flagStopThread) {
		this.flagStopThread = flagStopThread;
	}
	protected synchronized long getTimeStanp() {
			return timeStanp;
		}
	protected synchronized void setTimeStanp(long timeStanp) {
		this.timeStanp = timeStanp;
	}
	protected synchronized Socket getSockConsulta() {
		return sockConsulta;
	}
	protected synchronized void setSockConsulta(Socket sockConsulta) {
		this.sockConsulta = sockConsulta;
	}
	protected synchronized int getTipoCliente() {
		return tipoCliente;
	}
	protected synchronized void setTipoCliente(int tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	protected synchronized OutputStream getOsConsulta() {
		return osConsulta;
	}
	protected synchronized void setOsConsulta(OutputStream osConsulta) {
		this.osConsulta = osConsulta;
	}
	protected synchronized InputStream getIsConsulta() {
		return isConsulta;
	}
	protected synchronized void setIsConsulta(InputStream isConsulta) {
		this.isConsulta = isConsulta;
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
		System.out.println("O utilizador " + this.user + " não envia ping á: " + (System.currentTimeMillis()-this.timeStanp) + " segundos. Fazer logout automático? " + ((System.currentTimeMillis()-this.timeStanp)>maxTime));
		if((System.currentTimeMillis()-this.timeStanp)>maxTime){
			flagStopThread=true;
			return false;
		}
		return true;
	}
	
	public Map<String,String> consultRequestUser(String banda,String musica, String ext){
		//Verificar melhor quais os ips e portas a enviar
		Map<String,String> resp = new HashMap<>();
		PDU pdurequest = PDU_Buider.CONSULT_REQUEST_PDU(0, this.ip, this.port, banda, musica, ext, this.user);
		try {
			osConsulta.write(PDU.toBytes(pdurequest));
			try {
				PDU_APP pdu = PDUVersion.readPDU(isConsulta);
				if(pdu.getClass().getSimpleName().equals("PDU_APP_CONS_RESP")){
					PDU_APP_CONS_RESP pduResponse = ((PDU_APP_CONS_RESP)pdu);
					if(pduResponse.getFonte()==1){
						resp.put(pduResponse.getIp(), ""+pduResponse.getPort());
					}else{
						resp.putAll(pduResponse.getResult());
					}
				}
			} catch (IOException e) {
				System.out.println("Não foi possivel receber a resposta ao Consult Request por parte do user " + this.user + "( "+banda+","+ musica+ ext + " )");
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("Não foi possivel enviar consult Request para o user" + this.user + "( "+banda+","+ musica+ ext + " )");
			e.printStackTrace();
		}
		return resp;
	}
	
}
