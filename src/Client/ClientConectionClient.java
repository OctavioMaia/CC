package Client;

import java.net.DatagramSocket;
import java.net.SocketException;

public class ClientConectionClient implements Runnable{
	
	private DatagramSocket socketUDP;
	private Client cliente;
	private Thread mainCliente;
	
	public ClientConectionClient(Thread main, Client c) {
		try {
			this.socketUDP = new DatagramSocket(0);
			this.cliente = c;
			this.cliente.setPortUDP(this.socketUDP.getPort());
			System.out.println("O utilizador " + this.cliente.getUser() + " está ativo para receber trafico UDP na porta" + this.cliente.getPortUDP() );
		} catch (SocketException e) {
			System.out.println("O utilizador " + this.cliente.getUser() + " não consegui abrir Socket UDP para comunicações com outros utilizadores");
		}
	}
	
	@Override
	public void run() {
		while (mainCliente.getState()!=Thread.State.TERMINATED) {
			System.out.println("Ainda falta fazer a parte de comunicação com os outros clientes");
		}
	}
}
