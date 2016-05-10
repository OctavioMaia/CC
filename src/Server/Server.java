package Server;


import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Server {
    
	private ServerSocket server;
	private ServerInfo info;
	
	public Server(int port) {
		try {
			this.server = new ServerSocket(port);
			this.info = new ServerInfo(Inet4Address.getLocalHost().getHostAddress(),port);
		} catch (IOException e) {
			try {
				server.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	
	protected synchronized ServerInfo getInfo() {
		return info;
	}
	protected synchronized void setInfo(ServerInfo info) {
		this.info = info;
	}
	public void startServer(){
		System.out.println(this.info.getLocalIP());
		while(true){
            try {
            	Socket sockCliente = server.accept();
            	Thread t = new Thread( new ReceiverClientThread(sockCliente,this.info));
                Thread cp = new Thread( new CheckPingClients(info, Thread.currentThread()));
                cp.start();
    			t.start();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Erro ao criar socket para cliente.");
            }
        }
	}
	public void connectToMaster(String ip, int port){
		this.info.connectToMaster(ip, port);
	}
	
	public static void main(String argv[]){
		Server s = new Server(Integer.parseInt(argv[0]));
		if(argv.length==3){
			s.connectToMaster(argv[1], Integer.parseInt(argv[2]));
			System.out.println("Connect to master server");
			
			SendPingMaster spm = new SendPingMaster(s.getInfo(), Thread.currentThread());
			Thread tSPM = new Thread(spm);
			tSPM.start();
		}
		s.startServer();
	}
}