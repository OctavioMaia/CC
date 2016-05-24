import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Common.*;
import Connection.Reciver;
import Connection.Sender;
import Connection.udpReciver;
import Connection.udpSender;

public class TesteServer {
	
	
	static private int intfromByte(byte[] sizebytes){
		 ByteBuffer wrapped = ByteBuffer.wrap(sizebytes); 
		 return wrapped.getInt(); 
	 }
	 
	static private byte[] bytefromInt(int integer){

		 ByteBuffer dbuf = ByteBuffer.allocate(4);
		 dbuf.putInt(integer);
		 return  dbuf.array();
	 }
	 
	public static void main(String[] argv){
		//jms();
		/*PDU info = PDU_Buider.REGISTER_PDU(1, "JMS", "123", "192.168.1.1", 6970);
		byte[] infoA = PDU.toBytes(info);
		PDU info2 = PDU.fromBytes(infoA);
		System.out.println(info2.getData().toString());
		//System.out.println(Integer.toBinaryString(48).length());
		byte[] a = bytefromInt(48*1024);
		for (int i = 0; i < a.length; i++) {
			System.out.println(String.format("0x%02X", a[i]));
		}
		System.out.println(intfromByte(a)+ "=" + 48*1024);
		Integer.toBinaryString(48*1024).getBytes();
		/*
		String l = "pedro;";
		
		byte[] bs = l.getBytes();
		System.out.println("NUMERO DE BYTES:" + bs.length);

		System.out.println(new String(bs));
		System.out.println(Arrays.toString(bs));
		
		byte b = (byte) Integer.parseInt("11111",2);
		System.out.print(Byte.toString(b)+"\n");
		
		
		try {
			System.out.println(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//freitas();
		//System.out.println(argv[0]);
		try {
			jms();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void freitas(){
		System.out.println(0x01);
	}
	
	
	static private void jms() throws IOException, InterruptedException{
		/*PDU info = PDU_Buider.REGISTER_PDU(1, "JMS", "123", "192.168.1.1", 6970);
		byte[] infoA = PDU.toBytes(info);
		PDU info2 = PDU.fromBytes(infoA);
		PDU_APP pa = PDU_Reader.read(info2);
		System.out.println(pa.toString());
		
		PDU resp = PDU_Buider.REGISTER_PDU_RESPONSE(1);
		//PDU_Reader.read(PDU.toBytes(resp)).toString();
		System.out.println(PDU_Reader.read(PDU.toBytes(resp)).toString());
		
		PDU arethere = PDU_Buider.ARE_YOU_THERE_PDU("192.168.1.1", 9685,"jms");
		System.out.println(PDU_Reader.read(PDU.toBytes(arethere)).toString());
		PDU amhere = PDU_Buider.I_AM_HERE_PDU("192.168.1.2", 21,"jms");
		System.out.println(PDU_Reader.read(PDU.toBytes(amhere)).toString());
		long millis = System.currentTimeMillis();
		System.out.println("Time:" + millis);*/
		/*try {
			ArrayList<PDU> papp = PDU_Buider.DATA_PDU("/Users/joaosilva/Desktop/merda/Stromae - Alors on danse - Radio Edit.mp3", "Stromae - Alors on danse - Radio Edit.mp3");
			//PDU_APP_DATA pdata = PDU_Reader.read(papp);
			
			
			//System.out.println("Blocos:" + pdata.getBlocos() + "___" +papp.size());
			//System.out.println("Nome:" + pdata.getNome());
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		DatagramSocket servidor = new DatagramSocket(7070);
		Thread.sleep(7000);
		int portaservidor = servidor.getLocalPort();
		
		System.out.println("Porta: " +portaservidor);
		/*Scanner in = new Scanner(System.in);
		in.nextLine();*/
		/*System.out.println("Vou recever prob");
		Reciver rec = new Reciver(0, 100000, null, servidor);
		rec.recive();
		System.out.println("RECEBIDO prob");
		PDU p = rec.getRecived().get(0);
		PDU_APP_PROB_REQUEST pr = (PDU_APP_PROB_REQUEST) PDU_Reader.read(p);
		String ipPair = pr.getIp();*/
		int port = 6969;
		InetAddress address = InetAddress.getByName("127.0.0.1");
		DatagramPacket cliente = new DatagramPacket(new byte[Reciver.getBuffsize()], 0, Reciver.getBuffsize(), address, port);
		
		ArrayList<PDU> papp = PDU_Buider.DATA_PDU("/Users/joaosilva/Desktop/a.mp3", "a.mp3");
		System.out.println("Li Musica");
		Sender sender = new Sender(papp, 10, 10000, cliente, servidor);
		sender.send();
		System.out.println("Enviado");
		
		
	}
}
