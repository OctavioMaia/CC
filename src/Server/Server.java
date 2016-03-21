package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    
	private ServerSocket Server;
	private String localIP;
	private String outsideIP;
	private String masterDom;
	private String masterPort;
	
	private HashMap<Integer,ClienteInfo> clientes;
	
	
	
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
}