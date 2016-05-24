package Connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;

import Common.PDU;
import Common.PDU_Buider;

public class Reciver {

	private Boolean[] rec;
	private PDU[] pdus;
	private int trys;
	private int timeWait;
	private DatagramPacket pair;
	private DatagramSocket my;
	//private DatagramPacket toRecive;
	private ArrayList<PDU> recived;
	
	private static final int BuffSize=49*1024;
	
	public Reciver(int trys, int timeWait, DatagramPacket pair, DatagramSocket my) throws SocketException {
		super();
		this.trys = trys;
		this.timeWait = timeWait;
		this.pair = pair;
		this.my = my;
		this.my.setSoTimeout(this.timeWait);
		this.recived= new ArrayList<>();
		//this.recived=null;
		//byte[] data = new byte[BuffSize];
		//this.toRecive=new DatagramPacket(data, BuffSize);
	}
	private PDU reciveOne() throws IOException, SocketTimeoutException{
		byte[] data = new byte[BuffSize];
		DatagramPacket toRecive = new DatagramPacket(data, BuffSize);
		 my.receive(toRecive);
		 PDU rece= PDU.fromBytes(toRecive.getData());
		 System.out.println(rece.toString());
		 return rece;
	}
	

	private boolean done(){
		boolean ret =true;
		for (int i = 0; i < rec.length; i++) {
			System.out.println("Done " + i + "? =" + rec[i]);
			if(rec[i]==false) ret=false;
		}
		return ret;
	}
	
	private int quantosFaltamReceber(){
		int ret=0;
		for (int i = 0; i < rec.length; i++) {
			if(rec[i]==false) ret++;
		}
		return ret;
	}
	
	private void sendRetry() throws IOException{
		//byte[] data=null;
		 
		PDU p  = PDU_Buider.SREJ_PDU(this.rec);
		pair.setData(PDU.toBytes(p));
		my.send(this.pair);
	}
	
	public void recive() throws IOException{
		//recebo o 1;
		PDU first = reciveOne();
		int total = first.getTotal();
		System.out.println("Tamanho RECEBIDO "+ total+ "   " + first.getData()[3]);
		int num = first.getNUM();
		System.out.println("NUM: " +num);
		num--;
		System.out.println("Totl: " +total);
		this.rec = new Boolean[total];
		this.pdus= new PDU[total];
		for (int i = 0; i < pdus.length; i++) {
			pdus[i]=null;
			rec[i]=false;
		}
		this.rec[num]=true;
		this.pdus[num]=first;
		
		int time=0;
		//ciclo para tentativas
		while(this.done()==false && time<this.trys){
			System.out.println("EStou no ciclo");
			//ciclo para cada uma
			int i = 0;
			int faltamRecever= this.quantosFaltamReceber();
			PDU recived=null;
			int index;
			try{
				System.out.println("i="+ i+" f="+faltamRecever);
				while(i<faltamRecever){
					recived = reciveOne();
					index = recived.getNUM()-1;
					System.out.println("IDEX: " + index);
					this.rec[index]=true;
					this.pdus[index]=recived;
				}
				//recebi todos que estava
			}catch(SocketTimeoutException e){
				//nao recebi todos
				if(time<this.trys){
					System.out.println("Vou mandar Retry");
					this.sendRetry();
					System.out.println("Recebi Retry");
				}
			}
			time++;
		}
		if(this.done()==false){
			throw new RuntimeException("Segmentos em falta");
		}
		System.out.println("PdDUS " +pdus);
		System.out.println("Array " +this.recived);
		for (int i = 0; i < total; i++) {
			this.recived.add(pdus[i]);
		}
	}
	public Boolean[] getRec() {
		return rec;
	}
	public void setRec(Boolean[] rec) {
		this.rec = rec;
	}
	public PDU[] getPdus() {
		return pdus;
	}
	public void setPdus(PDU[] pdus) {
		this.pdus = pdus;
	}
	public int getTrys() {
		return trys;
	}
	public void setTrys(int trys) {
		this.trys = trys;
	}
	public int getTimeWait() {
		return timeWait;
	}
	public void setTimeWait(int timeWait) {
		this.timeWait = timeWait;
	}
	public DatagramPacket getPair() {
		return pair;
	}
	public void setPair(DatagramPacket pair) {
		this.pair = pair;
	}
	public DatagramSocket getMy() {
		return my;
	}
	public void setMy(DatagramSocket my) {
		this.my = my;
	}
	/*public DatagramPacket getToRecive() {
		return toRecive;
	}
	public void setToRecive(DatagramPacket toRecive) {
		this.toRecive = toRecive;
	}*/
	public ArrayList<PDU> getRecived() {
		return recived;
	}
	public void setRecived(ArrayList<PDU> recived) {
		this.recived = recived;
	}
	public static int getBuffsize() {
		return BuffSize;
	}
	@Override
	public String toString() {
		return "Reciver [rec=" + Arrays.toString(rec) + ", pdus=" + Arrays.toString(pdus) + ", trys=" + trys
				+ ", timeWait=" + timeWait + ", pair=" + pair + ", my=" + my + /*", toRecive=" + toRecive + */", recived="
				+ recived + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reciver other = (Reciver) obj;
		if (my == null) {
			if (other.my != null)
				return false;
		} else if (!my.equals(other.my))
			return false;
		if (pair == null) {
			if (other.pair != null)
				return false;
		} else if (!pair.equals(other.pair))
			return false;
		if (!Arrays.equals(pdus, other.pdus))
			return false;
		if (!Arrays.equals(rec, other.rec))
			return false;
		if (recived == null) {
			if (other.recived != null)
				return false;
		} else if (!recived.equals(other.recived))
			return false;
		if (timeWait != other.timeWait)
			return false;
		/*if (toRecive == null) {
			if (other.toRecive != null)
				return false;
		} else if (!toRecive.equals(other.toRecive))
			return false;*/
		if (trys != other.trys)
			return false;
		return true;
	}
	
}
