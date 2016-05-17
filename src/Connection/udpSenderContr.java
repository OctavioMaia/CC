package Connection;

import java.io.IOException;
import java.net.DatagramPacket;

import Common.PDU;

class udpSenderContr implements Runnable{

	private udpSender sender;
	private DatagramPacket dp;
	private final static int maxSize=100;
	public udpSenderContr(udpSender sender) {
		super();
		this.sender = sender;
		byte[] buf = new byte[maxSize];
		this.dp=new DatagramPacket(buf, buf.length);
	}

	private void control() throws InterruptedException{
		boolean goBack=false;
		try{
			//criara a trhead para controlo
			this.sender.getLock().lock();
			while(sender.getLastACK()!= sender.getParaEnvio().size()){
				while(!goBack && this.sender.getWindowActualSize()>0){
					this.sender.getEsperaACK().signal(); //aviso que ja posso enviar
					this.sender.getPossivelEnviar().wait();
				}
				//tenho de receber um ack
				int timeouts=0;
				boolean recive=false;
				while(timeouts*this.sender.getTimeOutTry()<this.sender.getTimeOutDesistir() && !recive){
					try {
						this.sender.getAckRecive().receive(dp);
						timeouts=0;
						recive=true;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						if(timeouts*this.sender.getTimeOutTry()>=this.sender.getTimeOutDesistir()){
							throw new RuntimeException("TimeOUT");
						}
						timeouts++;
					}
				}
				//recebi o ack
				int ackRecived = PDU.fromBytes(this.dp.getData()).getOptions()[3];
				
				if(ackRecived>=this.sender.getLastACK()){ //correu bem recebi um ack que estava a espera
					int windowaumeto = ackRecived-this.sender.getLastACK();
					this.sender.setLastACK(ackRecived);
				}else{
					int windowaumeto = this.sender.getLastACK()-ackRecived;
					this.sender.setLastACK(ackRecived);
					goBack=true;
				}
			}
		}
		finally{
			this.sender.getLock().unlock();
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
