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
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.info = new ServerInfo(Inet4Address.getLocalHost().getHostAddress(),port);
		} catch (UnknownHostException e) {
			try {
				server.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public void startServer(){
		while(true){
            Socket sockCliente = null;
            try {
            	sockCliente = server.accept();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Erro ao criar socket para cliente.");
            }
            Thread t = new Thread(new ReceiverClientThread(sockCliente,this.info));
			t.start();            
        }
	}
	
	public static void main(String argv[]){
		Server s = new Server(Integer.parseInt(argv[0]));
		s.startServer();
		
	}
	
	
}