package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import Common.PDU;
import Common.PDU_APP;
import Common.PDU_APP_DATA;
import Common.PDU_Buider;
import Common.PDU_Reader;

public class UDPreciver { //receve musicas e envia ACK
	private  final static int sizeBuffer=48*1024;
	//private DatagramSocket sendACK; // por onde mando cenas
	private DatagramSocket reciveData; // por onde recevo
	private int timeOutTry; // se passar este temoo mandar ultimo ack
	private int timeOutDesistir; //mandar cominicaçao para as couves
	private int recived;
	private ArrayList<PDU> aReceber; //onde vou meter as coisas que recebo

	public UDPreciver(DatagramSocket reciveData, int timeOutTry, int timeOutDesistir) throws SocketException {
		super();
		this.reciveData = reciveData;
		this.timeOutTry = timeOutTry;
		this.timeOutDesistir = timeOutDesistir;
		this.aReceber = new ArrayList<>();
		//this.reciveData.setSoTimeout(timeOutTry);
		this.recived=0;

	}

	private void sendACK(int num, InetAddress adress, int port) throws IOException{
		PDU ack = PDU_Buider.ACK_PDU(num);
		byte[] bytes = PDU.toBytes(ack);
		reciveData.send(new DatagramPacket(bytes, bytes.length,adress,port));
	}

	
	public void recive() throws IOException{
		byte[] buf = new byte[UDPreciver.sizeBuffer];
		DatagramPacket p = new DatagramPacket(buf, sizeBuffer);
		int timeouts=0;
		
		boolean recive=false;
		while(timeouts*this.timeOutTry<this.timeOutDesistir && !recive){
			try {
				reciveData.receive(p);
				timeouts=0;
				recive=true;
			} catch (IOException e) {
				e.printStackTrace();
				if(timeouts*this.timeOutTry>=this.timeOutDesistir){
					throw new RuntimeException("TimeOUT");
				}
				timeouts++;
				sendACK(recived,p.getAddress(),p.getPort());
			}
		}

		PDU aux = PDU.fromBytes(p.getData());
		PDU_APP_DATA pdata = (PDU_APP_DATA) PDU_Reader.read(aux); //ver para nao ser so data
		int numAux = pdata.getNumeration();
		int totalSegments = pdata.getBlocos();
		if(numAux==1){
			recived=1;
			this.aReceber.add(aux);
			//Fazar AcK de 1
		//}
		sendACK(recived,p.getAddress(),p.getPort());
		//recebi um datagram sei quantos quero receber
		
		while(recived<totalSegments){
			timeouts=0;
			recive=false;
			while(timeouts*this.timeOutTry<this.timeOutDesistir && !recive){
				try {
					reciveData.receive(p);
					timeouts=0;
					recive=true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if(timeouts*this.timeOutTry>=this.timeOutDesistir){
						throw new RuntimeException("TimeOUT");
					}
					sendACK(recived,p.getAddress(),p.getPort());
					timeouts++;
				}
			}

			aux = PDU.fromBytes(p.getData());
			pdata = (PDU_APP_DATA)PDU_Reader.read(aux); //ver para nao ser so data
			numAux = pdata.getNumeration();
			if(numAux==recived+1){
				recived=numAux;
				this.aReceber.add(aux);
				//Fazar AcK de recived
				
			}
			sendACK(recived,p.getAddress(),p.getPort());
		}
		}	
	}
	
	public void closeConnections(){
		this.reciveData.close();
		//this.sendACK.close();
	}

	public DatagramSocket getReciveData() {
		return reciveData;
	}
	public void setReciveData(DatagramSocket reciveData) {
		this.reciveData = reciveData;
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
	public ArrayList<PDU> getaReceber() {
		return aReceber;
	}
	public void setaReceber(ArrayList<PDU> aReceber) {
		this.aReceber = aReceber;
	}
	@Override
	public String toString() {
		return "udpReciver [reciveData=" + reciveData + ", timeOutTry=" + timeOutTry
				+ ", timeOutDesistir=" + timeOutDesistir + ", aReceber=" + aReceber + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aReceber == null) ? 0 : aReceber.hashCode());
		result = prime * result + ((reciveData == null) ? 0 : reciveData.hashCode());
		result = prime * result + timeOutDesistir;
		result = prime * result + timeOutTry;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UDPreciver other = (UDPreciver) obj;
		if (aReceber == null) {
			if (other.aReceber != null)
				return false;
		} else if (!aReceber.equals(other.aReceber))
			return false;
		if (reciveData == null) {
			if (other.reciveData != null)
				return false;
		} else if (!reciveData.equals(other.reciveData))
			return false;
		if (timeOutDesistir != other.timeOutDesistir)
			return false;
		if (timeOutTry != other.timeOutTry)
			return false;
		return true;
	}
	

}
