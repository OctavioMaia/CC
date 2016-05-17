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
	
	private DatagramSocket dataSend; // por onde mando cenas
	private DatagramSocket ackRecive; // por onde recevo
	
	
	private Thread contolACK; //ver melhor
	
	
	
	private int timeOutTry;
	private int timeOutDesistir;
	
	
	private int windowMax;
	private int windowActualSize; //diminuir quando mando aumentar quando recebo ack
	
	
	private int lastDataNumSent; //ultimo pacote enviado
	
	
	private int lastACK; //ultimo ack recebido pois pode vir o 5 e depois o 4

	private ArrayList<PDU> paraEnvio; // onde tenho as cenas para enviar tem de estar por ordem
	
	private ReentrantLock lock;
	private Condition esperaACK;
	private Condition possivelEnviar;
	
	
	public udpSender(DatagramSocket dataSend, DatagramSocket ackRecive, int timeOutTry,
			int timeOutDesistir, int windowMax, ArrayList<PDU> paraEnvio) {
		super();
		this.dataSend = dataSend;
		this.ackRecive = ackRecive;
		this.contolACK = null;
		this.timeOutTry = timeOutTry;
		this.timeOutDesistir = timeOutDesistir;
		this.windowMax = windowMax;
		this.windowActualSize = windowMax;
		this.lastDataNumSent = 0;
		this.lastACK = 0;
		this.paraEnvio = paraEnvio;
		this.lock = new ReentrantLock();
		this.esperaACK = this.lock.newCondition();
		this.possivelEnviar = this.lock.newCondition();
	}


	public Condition getPossivelEnviar(){
		return this.possivelEnviar;
	}

	public void sendData() throws InterruptedException, IOException{
		try{
			//criara a trhead para controlo
			this.lock.lock();
			while(lastACK!= paraEnvio.size()){
				while(this.windowActualSize<=0){
					possivelEnviar.signalAll(); //nao posso enviar mais vou dizer ao rector de ack para verificar as receçoes
					esperaACK.wait();
				}
				//lastDataNumSent++; // tenho de enviar o proximo
				byte[] buf = PDU.toBytes(paraEnvio.get(lastDataNumSent)); //envio o seginte ao ultimo ack
				lastDataNumSent++;
				DatagramPacket p = new DatagramPacket(buf, buf.length);
				dataSend.send(p);
				this.windowActualSize--;
			}
		}
		finally{
			this.lock.unlock();
		}
	}



	public DatagramSocket getDataSend() {
		return dataSend;
	}



	public void setDataSend(DatagramSocket dataSend) {
		this.dataSend = dataSend;
	}



	public DatagramSocket getAckRecive() {
		return ackRecive;
	}



	public void setAckRecive(DatagramSocket ackRecive) {
		this.ackRecive = ackRecive;
	}



	public Thread getContolACK() {
		return contolACK;
	}



	public void setContolACK(Thread contolACK) {
		this.contolACK = contolACK;
	}



	public int getTimeOutTry() {
		return timeOutTry;
	}



	public void setTimeOutTry(int timeOutTry) {
		this.timeOutTry = timeOutTry;
	}



	public int getTimeOutDesistir() {
		return timeOutDesistir;
	}



	public void setTimeOutDesistir(int timeOutDesistir) {
		this.timeOutDesistir = timeOutDesistir;
	}



	public int getWindowMax() {
		return windowMax;
	}



	public void setWindowMax(int windowMax) {
		this.windowMax = windowMax;
	}



	public int getWindowActualSize() {
		return windowActualSize;
	}



	public void setWindowActualSize(int windowActualSize) {
		this.windowActualSize = windowActualSize;
	}



	public int getLastDataNumSent() {
		return lastDataNumSent;
	}



	public void setLastDataNumSent(int lastDataNumSent) {
		this.lastDataNumSent = lastDataNumSent;
	}



	public int getLastACK() {
		return lastACK;
	}



	public void setLastACK(int lastACK) {
		this.lastACK = lastACK;
	}



	public ArrayList<PDU> getParaEnvio() {
		return paraEnvio;
	}



	public void setParaEnvio(ArrayList<PDU> paraEnvio) {
		this.paraEnvio = paraEnvio;
	}



	public ReentrantLock getLock() {
		return lock;
	}



	public void setLock(ReentrantLock lock) {
		this.lock = lock;
	}



	public Condition getEsperaACK() {
		return esperaACK;
	}



	public void setEsperaACK(Condition esperaACK) {
		this.esperaACK = esperaACK;
	}

	
}
