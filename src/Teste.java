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
		/*PDU info = PDU_Buider.REGISTER_PDU(1, "JMS", "123", "192.168.1.1", 6970);
		byte[] infoA = PDU.toBytes(info);
		PDU info2 = PDU.fromBytes(infoA);
		System.out.println(info2.getData().toString());*/
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
		System.out.print(Byte.toString(b)+"\n");*/
		
		
		try {
			System.out.println(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
