package Client;

import java.net.DatagramSocket;

/*
 * Classe para ser utilizada pelo o cliente que pediu a musica e a vai receber
 */
public class ReceiveConectionClient implements Runnable{

	private String userProvide; 
	private String ipUserProvide;
	private DatagramSocket socketUDP;
	private int localPortUDP;
	private Client cliente;
	private Thread mainCliente;
	
	public ReceiveConectionClient() {
	}
	
	@Override
	public void run() {
		//System.out.println("O utilizador " + this.cliente.getUser() + " está ativo para receber trafico UDP na porta " + this.localPortUDP + " para o utilizador " + this.userRequest + " com ip " + this.ipUserRequest+".");
		while (mainCliente.getState()!=Thread.State.TERMINATED) {
			
		}
	}

}
