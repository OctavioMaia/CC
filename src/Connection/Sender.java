package Connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeMap;

import Common.PDU;
import Common.PDU_APP_SREJ;
import Common.PDU_Reader;

public class Sender {

	private ArrayList<PDU> tosend;
	//private TreeMap<PDU,Integer> times;
	private boolean[] rec;
	private int trys;
	private int timeWait;
	private DatagramPacket pair;
	private DatagramSocket my;
	private static final int BuffSize=49*1024;
	public Sender(ArrayList<PDU> tosend,  int trys, int timeWait,DatagramPacket pair,DatagramSocket my) {
		super();
		this.tosend = tosend;
		this.trys = trys;
		this.timeWait = timeWait;
		this.pair=pair;
		this.my=my;
		
		this.rec = new boolean[tosend.size()];
		//assumo que nada foi reconhecido;
		for (int i = 0; i < rec.length; i++) {
			rec[i]=false;
		}		
		//this.times = new TreeMap<>();
		
	}
	
	
	public void send() throws IOException{
		int time=0;
		boolean fim = false;
		byte[] data;
		
		while(time<trys && !fim){
			System.out.println("Madar vez " +  time +" de "+ trys);
			for (PDU pdu : tosend) {
				data =PDU.toBytes(pdu);
				int index = pdu.getOptions()[2]-1;
				//System.out.println("indice " + index);
				if(rec[index]==false){
					System.out.println("vou mandar " + index + " nume " + pdu.getNUM());
					pair.setData(data);
					my.send(this.pair);
					System.out.println("vou mandei " + index + " nume " + pdu.getNUM());
					rec[index]=true; //assumo que ofi direito
				}
			}
			time++;
			fim=true; //assumo o fim que tudo foi mandado bem
			//ja foi tudo enviado
			//limpara o que manda
			data =new byte[0];
			pair.setData(data);
			//esperar por erros
			try{
				int erroR=0;
				byte[] buf = new byte[BuffSize];
				DatagramPacket erroRecebido=new DatagramPacket(buf, BuffSize);
				PDU erro;
				//Integer indixErro;
				//while(erroR<tosend.size()){
					System.out.println("ESpera de recebetr para mandar de novo");
					this.my.receive(erroRecebido);
					erro=PDU.fromBytes(erroRecebido.getData());
					//ver o ack
					PDU_APP_SREJ srej = (PDU_APP_SREJ)PDU_Reader.read(erro);
					//indixErro=erro.getNUM()-1;
					ArrayList<Integer> erronum = srej.getRetransmit();
					for (Integer indixErro : erronum) {
						fim=false;
						System.out.println("Mandar de novo "+indixErro);
						rec[indixErro]=false;
					}
					
				//}
			}catch (SocketTimeoutException  e){
					System.out.println("nao recebi mais nenhuma rejeiçao");
					fim=true;
			}
			
			for (int i = 0; i < rec.length && !fim; i++) {
				fim=fim && rec[i]; 
			}
			
		}
		if(time>=trys){
			throw new RuntimeException("Erro ao enviar nao foram mandados alguns pacotes");
		}
		System.out.println("FIM");
		
	}


	public ArrayList<PDU> getTosend() {
		return tosend;
	}


	public void setTosend(ArrayList<PDU> tosend) {
		this.tosend = tosend;
	}


	public boolean[] getRec() {
		return rec;
	}


	public void setRec(boolean[] rec) {
		this.rec = rec;
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


	public static int getBuffsize() {
		return BuffSize;
	}


	@Override
	public String toString() {
		return "Sender [tosend=" + tosend + ", rec=" + Arrays.toString(rec) + ", trys=" + trys + ", timeWait="
				+ timeWait + ", pair=" + pair + ", my=" + my + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((my == null) ? 0 : my.hashCode());
		result = prime * result + ((pair == null) ? 0 : pair.hashCode());
		result = prime * result + Arrays.hashCode(rec);
		result = prime * result + timeWait;
		result = prime * result + ((tosend == null) ? 0 : tosend.hashCode());
		result = prime * result + trys;
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
		Sender other = (Sender) obj;
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
		if (!Arrays.equals(rec, other.rec))
			return false;
		if (timeWait != other.timeWait)
			return false;
		if (tosend == null) {
			if (other.tosend != null)
				return false;
		} else if (!tosend.equals(other.tosend))
			return false;
		if (trys != other.trys)
			return false;
		return true;
	}
	
	

}
