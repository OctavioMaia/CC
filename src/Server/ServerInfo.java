package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import Client.SendPingServer;
import Common.PDU;
import Common.PDU_APP;
import Common.PDU_APP_CONS_RESP;
import Common.PDU_Buider;
import Versions.PDUVersion;

public class ServerInfo {
	private String id;
	private String localIP;
	private int port;
	private HashMap<String,ClientInfo> clients; //user->ClienteInfo  clientes registados
	private HashSet<String> online;
	private Socket sockMaster;
	//informações sobre o server master
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
		this.id=localIP+":"+port;
		this.port = port;
		this.clients = new HashMap<>();
		this.online = new HashSet<>();
		this.sockMaster=null;
		this.isMasterSocket=null;
		this.osMasterSocket=null;
		this.masterIP=null;
		this.masterPort=-1;
	}
	
	public ServerInfo(String ipServer, int portServer){
		this.id=ipServer+":"+portServer;
		this.localIP = ipServer;
		this.port = portServer;
		this.clients = new HashMap<>();
		this.online = new HashSet<>();
	}

	//Getters and Setters
	protected synchronized String getId() {
		return id;
	}
	protected synchronized void setId(String id) {
		this.id = id;
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
	protected synchronized HashMap<String, ClientInfo> getClients() {
		return clients;
	}
	protected synchronized void setClients(HashMap<String, ClientInfo> clients) {
		this.clients = clients;
	}
	protected synchronized HashSet<String> getOnline() {
		return online;
	}
	protected synchronized void setOnline(HashSet<String> online) {
		this.online = online;
	}
	protected synchronized Socket getSockMaster() {
		return sockMaster;
	}
	protected synchronized void setSockMaster(Socket sockMaster) {
		this.sockMaster = sockMaster;
	}
	protected synchronized InputStream getIsMasterSocket() {
		return isMasterSocket;
	}
	protected synchronized void setIsMasterSocket(InputStream isMasterSocket) {
		this.isMasterSocket = isMasterSocket;
	}
	protected synchronized OutputStream getOsMasterSocket() {
		return osMasterSocket;
	}
	protected synchronized void setOsMasterSocket(OutputStream osMasterSocket) {
		this.osMasterSocket = osMasterSocket;
	}
	protected synchronized String getMasterIP() {
		return masterIP;
	}
	protected synchronized void setMasterIP(String masterIP) {
		this.masterIP = masterIP;
	}
	protected synchronized int getMasterPort() {
		return masterPort;
	}
	protected synchronized void setMasterPort(int masterPort) {
		this.masterPort = masterPort;
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

	protected synchronized boolean containsOnline(String user){
		return this.online.contains(user);		
	} 
	
	protected synchronized void addOnline(String user){
		this.online.add(user);
	}
	
	protected synchronized void checkTimeStampClient(String user,int maxTime) {
		ClientInfo c = this.clients.get(user);
		if(c.checkTimeStamp(maxTime)==false){
			//caso em que nao foi verificada a permaneicia do cliente no serviço
			c.logout();
			this.online.remove(user);
		};
	}
	protected synchronized void connectToMaster(String ip, int port) throws IOException{
		this.masterIP=ip;
		this.masterPort=port;
		
		this.sockMaster = new Socket(this.masterIP, this.masterPort);
		this.isMasterSocket = sockMaster.getInputStream();
		this.osMasterSocket = sockMaster.getOutputStream();
		//enviar um registo para o master
		PDU pduRegisterMaster = PDU_Buider.REGISTER_PDU(0, this.id, ""+this.port, this.localIP, this.port);
		this.osMasterSocket.write(PDU.toBytes(pduRegisterMaster));
		
		//receber confirmação de registo
		PDUVersion.readPDU(isMasterSocket);
		
		
		PDU pduLoginMaster = PDU_Buider.LOGIN_PDU(0, this.id, ""+this.port, this.localIP, this.port);
		this.osMasterSocket.write(PDU.toBytes(pduLoginMaster));
		// receber confirmação de login
		PDUVersion.readPDU(isMasterSocket);
			
		
	}
	protected synchronized Map<String,String> consultRequestToUsersOnline(String userRequest, String banda, String musica, String ext){
		Map<String,String> result = new HashMap<>();
		for (String userOnline : this.online) {
			if(!userOnline.equals(userRequest)){
				Map<String,String> clientResult = this.clients.get(userOnline).consultRequestUser(banda, musica, ext);
				result.putAll(clientResult);
			}
		}
		if (result.size()==0 && masterPort!=-1) {
			Map<String,String> masterResult = consultMaster(banda, musica, ext);
			result.putAll(masterResult);
		}
		return result;
	}
	
	protected synchronized Map<String,String> consultMaster(String banda, String musica, String ext){
		Map<String,String> result = new HashMap<>();
		
		PDU pdurequest = PDU_Buider.CONSULT_REQUEST_PDU(0, this.masterIP, this.masterPort, banda, musica, ext, this.id);
		try {
			osMasterSocket.write(PDU.toBytes(pdurequest));
			try {
				PDU_APP pdu = PDUVersion.readPDU(isMasterSocket);
				if(pdu.getClass().getSimpleName().equals("PDU_APP_CONS_RESP")){
					PDU_APP_CONS_RESP pduResponse = ((PDU_APP_CONS_RESP)pdu);
					if(pduResponse.getFonte()==0){
						result.putAll(pduResponse.getResult());
					}
				}
			} catch (IOException e) {
				System.out.println("Não foi possivel receber a resposta ao Consult Request realizada ao Master( "+banda+","+ musica+ ext + " )");
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("Não foi possivel enviar consult Request para o Master( "+banda+","+ musica+ ext + " )");
			e.printStackTrace(); 
		}
		
		return result;
	}
	
}
