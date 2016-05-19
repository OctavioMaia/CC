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
	private ArrayList<PDU> paraEnvio; // onde tenho as cenas para enviar tem de estar por ordem	
	private ControlTCP controlo;
	
	public udpSender(DatagramSocket dataSend, DatagramSocket ackRecive, int timeOutTry,
			int timeOutDesistir, int windowMax, ArrayList<PDU> paraEnvio) {
		super();
		this.dataSend = dataSend;
		this.ackRecive = ackRecive;
		this.contolACK = null;
		this.paraEnvio = paraEnvio;
		this.controlo=new ControlTCP(timeOutTry, timeOutDesistir, windowMax, paraEnvio.size());
	}

	public void sendData() throws InterruptedException, IOException{
			//criara a trhead para controlo
			
			while(controlo.getLastACK()!= controlo.getParaEnvioNUM()){
				try{
					this.controlo.getLock().lock(); //ver se o lock esta bem aqui
					while(this.controlo.getWindowActualSize()<=0){
						//	possivelEnviar.signalAll(); //nao posso enviar mais vou dizer ao rector de ack para verificar as receçoes
						this.controlo.getEsperaACK().wait();
					}
					//lastDataNumSent++; // tenho de enviar o proximo
					int lastDataNumSent =this.controlo.getLastDataNumSent();
					byte[] buf = PDU.toBytes(paraEnvio.get(lastDataNumSent)); //envio o seginte ao ultimo ack
					this.controlo.setLastDataNumSent(lastDataNumSent+1);
					DatagramPacket p = new DatagramPacket(buf, buf.length);
					dataSend.send(p);
					this.controlo.diminuiWindowAtual();
				}finally{
					this.controlo.getLock().unlock();
				}
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
	public ArrayList<PDU> getParaEnvio() {
		return paraEnvio;
	}
	public void setParaEnvio(ArrayList<PDU> paraEnvio) {
		this.paraEnvio = paraEnvio;
	}
}


