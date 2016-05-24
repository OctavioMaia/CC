package Connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;

import Common.PDU;

class udpSenderContr implements Runnable{ //sotrata dos ACK

	private udpSender sender;
	private DatagramPacket dp;
	private ControlTCP control;
	private final static int maxSize=100;
	
	public udpSenderContr(udpSender sender,ControlTCP control) {
		super();
		this.sender = sender;
		this.control=control;
		byte[] buf = new byte[maxSize];
		this.dp=new DatagramPacket(buf, buf.length);
	}

	private void control() throws InterruptedException{
		//ArrayList<Integer> ackRecivedList = new ArrayList<>();
		//boolean goBack=false;
		try{
			//criara a trhead para controlo
			
			while(control.getLastACK()!= control.getParaEnvioNUM()){
				System.out.println("Controlo quero lock");
				this.control.getLock().lock(); //vou macher nos controlos nesta iteraçao
				System.out.println("Controlo tenho lock, vou esperar");
				this.control.getPossivelEnviar().wait();
				System.out.println("Controlo acordei");
				if(/*!goBack && */this.control.getWindowActualSize()>0){
					this.control.getEsperaACK().signal(); //aviso que ja posso enviar mas vou recber a mesma ack
					//this.sender.getPossivelEnviar().wait();
				}
				//tenho de receber um ack
				int timeouts=0;
				boolean recive=false;
				while(timeouts*this.control.getTimeOutTry()<this.control.getTimeOutDesistir() && !recive){
					try {
						this.sender.getAckRecive().receive(dp);
						timeouts=0;
						recive=true;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						if(timeouts*this.control.getTimeOutTry()>=this.control.getTimeOutDesistir()){
							this.control.getLock().unlock(); //nao sei seé preciso
							throw new RuntimeException("TimeOUT");
						}
						timeouts++;
					}
				}
				//recebi o ack
				int ackRecived = PDU.fromBytes(this.dp.getData()).getOptions()[3];
				System.out.println("Recebi ACK" + ackRecived+ " Espera "+ this.control.getLastACK());
				//ESTOU TODO COMIDO AQUI 
				if(ackRecived>=this.control.getLastACK()){ //correu bem recebi um ack que estava a espera
					//mandei o 6 ultima confirmaçao do 3 recebi ack de 5
					int windowaumeto = ackRecived-this.control.getLastACK();
					this.control.setLastACK(ackRecived);
					this.control.aumentaWindowAtual(windowaumeto);
					System.out.println("Aumentar Janela: " + this.control.getWindowActualSize());
					//ackRecivedList.add(ackRecived); //digo que ja recebi ate
					
				}/*else{ 
				  int windowaumeto = this.control.getLastDataNumSent()-ackRecived;
					this.control.setLastACK(ackRecived);
					goBack=true;
				}*/
			}
		}
		finally{
			this.control.getLock().unlock();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			this.control();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
