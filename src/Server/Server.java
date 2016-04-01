package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    
	private ServerSocket Server;
	
	public Server(int port) throws IOException {
		this.Server = new ServerSocket(port);
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
            Thread t = new Thread(new ServerConnection(sockCliente));
			t.start();            
        }
	}
	
	public static void main(String argv[]){
		try {
			Server s = new Server(6969);
			s.startServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}