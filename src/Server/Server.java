package Server;


import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    
	private ServerSocket Server;
	private ServerInfo info;
	
	public Server(int port) throws IOException {
		this.Server = new ServerSocket(port);
		this.info = new ServerInfo(Inet4Address.getLocalHost().getHostAddress(),port);
	}
	
	public void startServer() throws IOException{
		while(true){
            Socket sockCliente = null;
            try {
            	sockCliente = Server.accept();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Erro ao criar socket para cliente.");
            }
            Thread t = new Thread(new ReceiverClientThread(sockCliente,this.info));
			t.start();            
        }
	}
	
	public static void main(String argv[]){
		try {
			Server s = new Server(Integer.parseInt(argv[0]));
			s.startServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}