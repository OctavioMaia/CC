package Connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

import Common.PDU;
import Common.PDU_APP;
import Common.PDU_APP_DATA;
import Common.PDU_APP_PROB_REQUEST;
import Common.PDU_APP_PROB_RESPONSE;
import Common.PDU_Buider;
import Common.PDU_Reader;

public class udpReciver { //receve musicas e envia ACK
	private  final static int sizeBuffer=48*1024;
	private DatagramPacket sendACK; // por onde mando cenas
	private DatagramSocket reciveData; // por onde recevo
	//private int dataWaitnumeration;
	private int timeOutTry; // se passar este temoo mandar ultimo ack
	private int timeOutDesistir; //mandar cominicaçao para as couves
	private int recived;
	private ArrayList<PDU> aReceber; //onde vou meter as coisas que recebo

	public udpReciver(DatagramPacket sendACK, DatagramSocket reciveData, int timeOutTry, int timeOutDesistir) throws SocketException {
		super();
		this.sendACK = sendACK;
		this.reciveData = reciveData;
		this.timeOutTry = timeOutTry;
		this.timeOutDesistir = timeOutDesistir;
		this.aReceber = new ArrayList<>();
		this.reciveData.setSoTimeout(timeOutTry);
		this.recived=0;

	}

	private void sendACK(int num) throws IOException{
		PDU ack = PDU_Buider.ACK_PDU(num);
		byte[] bytes = PDU.toBytes(ack);
		sendACK.setData(bytes);
		System.out.println("Vou enviar ACK " + num);
		reciveData.send(this.sendACK);
	}

	
	public void recive() throws IOException{
		byte[] buf = new byte[udpReciver.sizeBuffer];
		DatagramPacket receData = new DatagramPacket(buf, sizeBuffer);
		int timeouts=0;
		
		boolean recive=false;
		while(timeouts*this.timeOutTry<this.timeOutDesistir && !recive){
			try {
				reciveData.receive(receData);
				System.out.println("recebi");
				timeouts=0;
				recive=true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				if(timeouts*this.timeOutTry>=this.timeOutDesistir){
					System.out.println("TimeOUT Final");
					throw new RuntimeException("TimeOUT Final");
				}
				System.out.println("Timeout nao final vou enviar ack: " + recived);
				sendACK(recived);
				timeouts++;
			}
		}
		int numAux;
		int totalSegments;
		PDU aux = PDU.fromBytes(receData.getData());
		PDU_APP pdataaux = PDU_Reader.read(aux); //ver para nao ser so data
		if(pdataaux instanceof PDU_APP_PROB_REQUEST || pdataaux instanceof PDU_APP_PROB_RESPONSE){
			numAux=1;
			totalSegments=1;
			if (pdataaux instanceof PDU_APP_PROB_REQUEST){
				//tenho de me ligar ao outro lado atravez do ip e porta que vem nosocket
				PDU_APP_PROB_REQUEST pdata=(PDU_APP_PROB_REQUEST)pdataaux;
				InetAddress laddr=InetAddress.getByName(pdata.getIp());
				this.sendACK=new DatagramPacket(new byte[udpReciver.sizeBuffer],sizeBuffer,laddr,pdata.getPort());
				//this.sendACK=new DatagramPacket(pdata.getPort(),laddr);
			}else{ // nao sei se é preciso se for um probresponse so tenho de o mandar para fora que ja sei de onde vou ler e enviar
				PDU_APP_PROB_RESPONSE pdata=(PDU_APP_PROB_RESPONSE)pdataaux;
				InetAddress laddr=InetAddress.getByName(pdata.getIp());
				this.sendACK=new DatagramPacket(new byte[udpReciver.sizeBuffer],sizeBuffer,laddr,pdata.getPort());
			}
			
		}else{
			PDU_APP_DATA pdata=(PDU_APP_DATA)pdataaux;
			numAux = pdata.getNumeration();
			totalSegments = pdata.getBlocos();
			System.out.println("Recebi numero " +numAux );
			System.out.println("toral numero " +totalSegments );
		}
		
		if(numAux==1){
			recived=1;
			this.aReceber.add(aux);
			//Fazar AcK de 1
		}
		System.out.println("Ja recebi o 1º vou enviar ack " +recived );
		sendACK(recived);
		//recebi um datagram sei quantos quero receber
		
		PDU_APP_DATA pdata2;
		while(recived<totalSegments){
			
			timeouts=0;
			recive=false;
			while(timeouts*this.timeOutTry<this.timeOutDesistir && !recive){
				try {
					reciveData.receive(receData);
					recive=true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if(timeouts*this.timeOutTry>=this.timeOutDesistir){
						System.out.println("TimeOUT Final");
						throw new RuntimeException("TimeOUT Final");
					}
					sendACK(recived);
					System.out.println("TimeOUT " + timeouts*this.timeOutTry + " de " + this.timeOutDesistir );
					timeouts++;
				}
				
			}

			aux = PDU.fromBytes(receData.getData());
			pdata2 = (PDU_APP_DATA)PDU_Reader.read(aux); //ver para nao ser so data
			numAux = pdata2.getNumeration();
			System.out.println("Recebido "+ numAux +" espera: " + (recived+1));
			if(numAux==recived+1){
				recived=numAux;
				this.aReceber.add(aux);
				//Fazar AcK de recived
				
			}
			sendACK(recived);
		}
			
	}
	
	public void closeConnections(){
		this.reciveData.close();
	//	this.sendACK.close();
	}


	public DatagramPacket getSendACK() {
		return sendACK;
	}


	public void setSendACK(DatagramPacket sendACK) {
		this.sendACK = sendACK;
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
		return "udpReciver [sendACK=" + /*sendACK +*/ ", reciveData=" + reciveData + ", timeOutTry=" + timeOutTry
				+ ", timeOutDesistir=" + timeOutDesistir + ", aReceber=" + aReceber + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aReceber == null) ? 0 : aReceber.hashCode());
		result = prime * result + ((reciveData == null) ? 0 : reciveData.hashCode());
		result = prime * result + ((sendACK == null) ? 0 : sendACK.hashCode());
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
		udpReciver other = (udpReciver) obj;
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
		if (sendACK == null) {
			if (other.sendACK != null)
				return false;
		} else if (!sendACK.equals(other.sendACK))
			return false;
		if (timeOutDesistir != other.timeOutDesistir)
			return false;
		if (timeOutTry != other.timeOutTry)
			return false;
		return true;
	}
	

}
