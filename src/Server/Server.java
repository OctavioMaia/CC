package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    
	private ServerSocket Server;
	private HashMap<Integer,ClienteInfo> clientes;
	
	public Server(int port) throws IOException {
		this.Server = new ServerSocket(port);
	}
	
	public void startServer() throws IOException{
		while(true){
            Socket cliente = null;
            try {
                cliente = Server.accept();
                Thread t = new Thread(new ServerConnection(cliente,boleias));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
			t.start();            
        }
	}
}