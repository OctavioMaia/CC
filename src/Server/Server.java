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
				System.out.println("Não foi possivel inicializar o servidor");
				server.close();
			} catch (IOException e1) {
				System.out.println("Não foi possive criar o servidor Socket");
			}
		}
	}
	
	
	protected synchronized ServerInfo getInfo() {
		return info;
	}
	protected synchronized void setInfo(ServerInfo info) {
		this.info = info;
	}
	
	public void startServer(){
		System.out.println(this.info.getId());
		Thread cp = new Thread( new CheckPingClients(info, Thread.currentThread()));
		cp.start();
		while(true){
            try {
            	Socket sockCliente = server.accept();
            	Thread t = new Thread( new ReceiverClientThread(sockCliente,this.info));
    			t.start();
            } catch (IOException ex) {
                System.out.println("Erro ao criar socket para cliente.");
            }
        }
	}
	private void connectToMaster(String ip, int port) throws IOException{
		this.info.connectToMaster(ip, port);
	}
	
	public static void main(String argv[]){
		Server s = new Server(Integer.parseInt(argv[0]));
		if(argv.length==3){
			try {
				s.connectToMaster(argv[1], Integer.parseInt(argv[2]));
				System.out.println("Connect to master server");
				SendPingMaster spm = new SendPingMaster(s.getInfo(), Thread.currentThread());
				Thread tSPM = new Thread(spm);
				tSPM.start();
			} catch (IOException e) {
				System.out.println("Não foi possivel estabelecer conecção com o server master ("+argv[1]+":"+Integer.parseInt(argv[2]) +")");
			}
		}
		s.startServer();
	}
}