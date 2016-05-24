package Connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import Common.PDU;

public class udpSender { //manda musicas
	
	private DatagramPacket dataSend; // por onde mando cenas Cenas do sevidor
	
	private DatagramSocket ackRecive; // por onde recevo ACK Sou eu que crio
	
	
	private ControlTCP contolACK; //ver melhor
	
	
	
	//private int timeOutTry;
	//private int timeOutDesistir;
	
	
	//private int windowMax;
	//private int windowActualSize; //diminuir quando mando aumentar quando recebo ack
	
	
	//private int lastDataNumSent; //ultimo pacote enviado
	
	
	//private int lastACK; //ultimo ack recebido pois pode vir o 5 e depois o 4

	private ArrayList<PDU> paraEnvio; // onde tenho as cenas para enviar tem de estar por ordem
	
	//private ReentrantLock lock;
	//private Condition esperaACK;
	//private Condition possivelEnviar;
	
	private ControlTCP controlo;
	
	public udpSender(DatagramPacket dataSend, DatagramSocket ackRecive, int timeOutTry,
			int timeOutDesistir, int windowMax, ArrayList<PDU> paraEnvio) {
		super();
		this.dataSend = dataSend;
		this.ackRecive = ackRecive;
		this.contolACK = null;
		//this.timeOutTry = timeOutTry;
		//this.timeOutDesistir = timeOutDesistir;
		//this.windowMax = windowMax;
		//this.windowActualSize = windowMax;
		//this.lastDataNumSent = 0;
		//this.lastACK = 0;
		this.paraEnvio = paraEnvio;
		//this.lock = new ReentrantLock();
		//this.esperaACK = this.lock.newCondition();
		//this.possivelEnviar = this.lock.newCondition();
		int maxWin = Math.max(windowMax, paraEnvio.size());
		this.controlo=new ControlTCP(timeOutTry, timeOutDesistir, windowMax, paraEnvio.size());
	}



	public void sendData() throws InterruptedException, IOException{
			//criara a trhead para controlo
			Thread t = new Thread(new udpSenderContr (this,this.controlo));
			t.start();
			while(controlo.getLastACK()!= controlo.getParaEnvioNUM()){
				try{
					System.out.println("espera entrar");
					this.controlo.getLock().lock(); //ver se o lock esta bem aqui
					this.controlo.getPossivelEnviar().signalAll();
					System.out.println("sai");
					while(this.controlo.getWindowActualSize()<=0){
						//	possivelEnviar.signalAll(); //nao posso enviar mais vou dizer ao rector de ack para verificar as receçoes
						System.out.println("espera enviar");
						this.controlo.getEsperaACK().wait();
					}
					//lastDataNumSent++; // tenho de enviar o proximo
					int lastDataNumSent =this.controlo.getLastDataNumSent();
					if(lastDataNumSent<paraEnvio.size()){
						
						PDU p =paraEnvio.get(lastDataNumSent);
						/*System.out.println("Numro enviado: "+ p.getOptions()[2]);
						System.out.println("Numro dito enviado: "+ lastDataNumSent+1);*/
						byte[] buf = PDU.toBytes(p); //envio o seginte ao ultimo ack
						
						this.controlo.setLastDataNumSent(lastDataNumSent+1);
						dataSend.setData(buf);
						System.out.println("Numro enviado: "+ p.getOptions()[2]);
						System.out.println("Numro dito enviado: "+ lastDataNumSent+1);
						ackRecive.send(dataSend);
						this.controlo.diminuiWindowAtual();
					}
				}finally{
					this.controlo.getLock().unlock();
				}
			}
	}



	public DatagramPacket getDataSend() {
		return dataSend;
	}



	public void setDataSend(DatagramPacket dataSend) {
		this.dataSend = dataSend;
	}



	public DatagramSocket getAckRecive() {
		return ackRecive;
	}



	public void setAckRecive(DatagramSocket ackRecive) {
		this.ackRecive = ackRecive;
	}


/*
	public Thread getContolACK() {
		return contolACK;
	}



	public void setContolACK(Thread contolACK) {
		this.contolACK = contolACK;
	}
*/


	public ArrayList<PDU> getParaEnvio() {
		return paraEnvio;
	}



	public void setParaEnvio(ArrayList<PDU> paraEnvio) {
		this.paraEnvio = paraEnvio;
	}
}


