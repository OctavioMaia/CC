import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import Common.*;

public class Teste {
	
	
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
		
	}
	
	private static void freitas(){
		System.out.println(0x01);
	}
	
	
	static private void jms(){
		PDU info = PDU_Buider.REGISTER_PDU(1, "JMS", "123", "192.168.1.1", 6970);
		byte[] infoA = PDU.toBytes(info);
		PDU info2 = PDU.fromBytes(infoA);
		PDU_APP pa = PDU_Reader.read(info2);
		System.out.println(pa.toString());
		
		PDU resp = PDU_Buider.REGISTER_PDU_RESPONSE(1);
		//PDU_Reader.read(PDU.toBytes(resp)).toString();
		System.out.println(PDU_Reader.read(PDU.toBytes(resp)).toString());
		
		PDU arethere = PDU_Buider.ARE_YOU_THERE_PDU("192.168.1.1", 9685);
		System.out.println(PDU_Reader.read(PDU.toBytes(arethere)).toString());
		PDU amhere = PDU_Buider.I_AM_HERE_PDU("192.168.1.2", 21);
		System.out.println(PDU_Reader.read(PDU.toBytes(amhere)).toString());
		
	}
}
