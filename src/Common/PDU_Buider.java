package Common;



public final class PDU_Buider {

	private static byte LOGIN = 0x01;
	private static byte LOGOUT = 0x00;
	private static byte REGISTO = 0x02;
	
	static public PDU LOGIN_PDU(int tipo/*0 server 1 cliente*/,String username,String pass,String ip , int port){
		//PDU p = new PDU(version, security, tipo, op1, op2, op3, op4, data)
		String info = "UN_" + username + ";PW_"+pass+";IP_"+ip+";PT_"+port;
		byte[] data  = info.getBytes();	
		byte tam =  (byte) data.length;
        return new PDU((byte)0x01,(byte)tipo,PDU.REGISTER,LOGIN,tam,(byte)0x01,(byte)0x01,data);
		
	}
	static public PDU LOGOUT_PDU(int tipo/*0 server 1 cliente*/,String username,String pass,String ip , int port){
		//PDU p = new PDU(version, security, tipo, op1, op2, op3, op4, data)
		String info = "UN_" + username + ";PW_"+pass+";IP_"+ip+";PT_"+port;
		byte[] data  = info.getBytes();	
		byte tam =  (byte) data.length;
        return new PDU((byte)0x01,(byte)tipo,PDU.REGISTER,LOGOUT,tam,(byte)0x01,(byte)0x01,data);
		
	}

	static public PDU REGISTER_PDU(int tipo/*0 server 1 cliente*/,String username,String pass,String ip , int port){
		//PDU p = new PDU(version, security, tipo, op1, op2, op3, op4, data)
		String info = "UN_" + username + ";PW_"+pass+";IP_"+ip+";PT_"+port;
		byte[] data  = info.getBytes();	
		byte tam =  (byte) data.length;
        return new PDU((byte)0x01,(byte)tipo,PDU.REGISTER,REGISTO,tam,(byte)0x01,(byte)0x01,data);
		
	}
	static public PDU REGISTER_PDU_RESPONSE( int mensagem){
		PDU ret = new PDU((byte)0x01,(byte)mensagem,PDU.REGISTER_RESPONSE,REGISTO,(byte)0x00,(byte)0x01,(byte)0x01,new byte[0]);
		return ret;
	}
	
	static public PDU LOGIN_PDU_RESPONSE( int mensagem){
		PDU ret = new PDU((byte)0x01,(byte)mensagem,PDU.REGISTER_RESPONSE,LOGIN,(byte)0x00,(byte)0x01,(byte)0x01,new byte[0]);
		return ret;
	}
	static public PDU LOGOUT_PDU_RESPONSE( int mensagem){
		PDU ret = new PDU((byte)0x01,(byte)mensagem,PDU.REGISTER_RESPONSE,LOGOUT,(byte)0x00,(byte)0x01,(byte)0x01,new byte[0]);
		return ret;
	}
	
	static public  PDU ARE_YOU_THERE_PDU(String ip, int port){ //ver se tem de mandar o ip e porta de respsota
		String mensagem = "IP_"+ip+";PT_"+port;
		PDU ret = new PDU((byte)0x01,(byte)0x00,PDU.ARE_YOU_THERE,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x01,mensagem.getBytes());
		return ret;
	}
	
	static public  PDU I_AM_HERE_PDU(String ip, int port){ //ver se tem de mandar o ip e porta de respsota
		String mensagem = "IP_"+ip+";PT_"+port;
		PDU ret = new PDU((byte)0x01,(byte)0x00,PDU.I_AM_HERE,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x01,mensagem.getBytes());
		return ret;
	}

}
