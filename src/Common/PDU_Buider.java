package Common;

public final class PDU_Buider {

	static public PDU LOGIN_PDU(int tipo/*0 server 1 cliente*/,String username,String pass,String ip , int port){
		//PDU p = new PDU(version, security, tipo, op1, op2, op3, op4, data)
		String info = "UN_" + username + ";PW_"+pass+";IP_"+ip+";PT_"+port;
		byte[] data  = info.getBytes();	
		byte tam =  (byte) data.length;
        return new PDU((byte)0x01,(byte)0x00,PDU.REGISTER,(byte)0x02,tam,(byte)0x01,(byte)0x01,data);
		
	}
	static public PDU LOGOUT_PDU(int tipo/*0 server 1 cliente*/,String username,String pass,String ip , int port){
		//PDU p = new PDU(version, security, tipo, op1, op2, op3, op4, data)
		String info = "UN_" + username + ";PW_"+pass+";IP_"+ip+";PT_"+port;
		byte[] data  = info.getBytes();	
		byte tam =  (byte) data.length;
        return new PDU((byte)0x01,(byte)0x00,PDU.REGISTER,(byte)0x02,tam,(byte)0x01,(byte)0x01,data);
		
	}

	static public PDU REGISTER_PDU(int tipo/*0 server 1 cliente*/,String username,String pass,String ip , int port){
		//PDU p = new PDU(version, security, tipo, op1, op2, op3, op4, data)
		String info = "UN_" + username + ";PW_"+pass+";IP_"+ip+";PT_"+port;
		byte[] data  = info.getBytes();	
		byte tam =  (byte) data.length;
        return new PDU((byte)0x01,(byte)0x00,PDU.REGISTER,(byte)0x02,tam,(byte)0x01,(byte)0x01,data);
		
	}

}
