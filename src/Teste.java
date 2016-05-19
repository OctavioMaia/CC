import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
		jms();
	}
	
	private static void freitas(){
		System.out.println(0x01);
	}
	
	
	static private void jms(){
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
		PDU req = PDU_Buider.CONSULT_REQUEST_PDU(1, "192.168.1.1", 4444, "Coldplay", "Fix You", "mp3", "jms");
		byte[] infoReq = PDU.toBytes(req);
		PDU_APP reqAp = PDU_Reader.read(infoReq);
		System.out.println(reqAp.toString());
		System.out.println("NEXT\n\n");
		PDU respCliente0 = PDU_Buider.CONSULT_RESPONSE_PDU(1, "jsm2", "192.168.1.2", 6969, false, new HashMap<String,String>());
		byte[] infoRespC0 = PDU.toBytes(respCliente0);
		PDU_APP respC0AP = PDU_Reader.read(infoRespC0);
		System.out.println(respC0AP.toString());
		System.out.println("NEXT\n\n");
		PDU respCliente1 = PDU_Buider.CONSULT_RESPONSE_PDU(1, "jsm2", "192.168.1.2", 6969, true, new HashMap<String,String>());
		byte[] infoRespC1 = PDU.toBytes(respCliente1);
		PDU_APP respC1AP = PDU_Reader.read(infoRespC1);
		System.out.println(respC1AP.toString());
		
		System.out.println("NEXT\n\n");
		PDU respServer1 = PDU_Buider.CONSULT_RESPONSE_PDU(0, "Server1", "192.168.1.69", 6969, true, new HashMap<String,String>());
		byte[] infoRespS1 = PDU.toBytes(respServer1);
		PDU_APP respS1AP = PDU_Reader.read(infoRespS1);
		System.out.println(respS1AP.toString());
		
		System.out.println("NEXT\n\n");
		PDU respServer0 = PDU_Buider.CONSULT_RESPONSE_PDU(0, "Server1", "192.168.1.69", 6969, false, new HashMap<String,String>());
		byte[] infoRespS0 = PDU.toBytes(respServer0);
		PDU_APP respS0AP = PDU_Reader.read(infoRespS0);
		System.out.println(respS0AP.toString());
		
		System.out.println("NEXT\n\n");
		PDU probReq = PDU_Buider.PROB_REQUEST_PDU(1, "jms", "192.168.1.1", 6969);
		byte[] infoprobReq = PDU.toBytes(probReq);
		PDU_APP probReqAP = PDU_Reader.read(infoprobReq);
		System.out.println(probReqAP.toString());
		
		System.out.println("NEXT\n\n");
		long millis = System.currentTimeMillis();
		System.out.println("Time:" + millis);
		PDU probResp = PDU_Buider.PROB_RESPONSE_PDU(1, "jms", "192.168.1.1", 6969,millis);
		byte[] infoprobResp = PDU.toBytes(probResp);
		PDU_APP probRespAP = PDU_Reader.read(infoprobResp);
		System.out.println(probRespAP.toString());
	}
}
