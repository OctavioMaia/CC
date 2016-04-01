package Client;
public interface TypeRequest {
	 public static byte REGISTER =0x00;
	 public static byte CONSULT_REQUEST =0x01;
	 public static byte CONSULT_RESPONSE =0x02;
	 public static byte PROBE_REQUEST =0x03;
	 public static byte PROBE_RESPONSE =0x04;
	 public static byte REQUEST =0x05;
	 public static byte DATA =0x06;
	 
	 //Nossos
	 public static byte ARE_YOU_THERE =0x07;
	 public static byte I_AM_HERE =0x08;
	 public static byte CONFIRM =0x09;
	 public static byte ACK =0x0A;
	 public static byte REGISTER_RESPONSE =0x0B;
}
