package Client;

import java.net.DatagramSocket;
import java.net.SocketException;

public class ClientConectionClient implements Runnable{
	
	private String userRequest;
	private String ipUserRequest;
	private DatagramSocket socketUDP;
	private int localPortUDP;
	private Client cliente;
	private Thread mainCliente;
	
	public ClientConectionClient(Thread main, Client c , DatagramSocket dt, String user, String ip){
		this.userRequest=user;
		this.ipUserRequest=ip;
		this.mainCliente = main;
		this.setSocketUDP(dt);
		this.localPortUDP = dt.getLocalPort();
		this.cliente = c;
	}
	
	@Override
	public void run() {
		System.out.println("O utilizador " + this.cliente.getUser() + " está ativo para receber trafico UDP na porta " + this.localPortUDP + " para o utilizador " + this.userRequest + " com ip " + this.ipUserRequest+".");
		while (mainCliente.getState()!=Thread.State.TERMINATED) {
			//System.out.println("Ainda falta fazer a parte de comunicação com os outros clientes");
		}
	}

	public DatagramSocket getSocketUDP() {
		return socketUDP;
	}
	public void setSocketUDP(DatagramSocket socketUDP) {
		this.socketUDP = socketUDP;
	}
}
