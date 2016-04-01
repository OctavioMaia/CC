package Common;

public final class PDU_Buider {

	static public PDU LOGIN_PDU(int tipo/*0 server 1 cliente*/,String username,String pass,String ip , int port){
		//PDU p = new PDU(version, security, tipo, op1, op2, op3, op4, data)
		String info = "UN_" + username + ";PW_"+pass+";IP_"+ip+";PT_"+port;
		byte[] data  = info.getBytes();	
		byte tam =  (byte) data.length;
        return new PDU((byte)0x01,(byte)tipo,PDU.REGISTER,(byte)0x01/*é para login*/,tam,(byte)0x01,(byte)0x01,data);
		
	}
	static public PDU LOGOUT_PDU(int tipo/*0 server 1 cliente*/,String username,String pass,String ip , int port){
		//PDU p = new PDU(version, security, tipo, op1, op2, op3, op4, data)
		String info = "UN_" + username + ";PW_"+pass+";IP_"+ip+";PT_"+port;
		byte[] data  = info.getBytes();	
		byte tam =  (byte) data.length;
        return new PDU((byte)0x01,(byte)tipo,PDU.REGISTER,(byte)0x00/*é para logout*/,tam,(byte)0x01,(byte)0x01,data);
		
	}

	static public PDU REGISTER_PDU(int tipo/*0 server 1 cliente*/,String username,String pass,String ip , int port){
		//PDU p = new PDU(version, security, tipo, op1, op2, op3, op4, data)
		String info = "UN_" + username + ";PW_"+pass+";IP_"+ip+";PT_"+port;
		//System.out.println(info + " " +info.length() );
		byte[] data  = info.getBytes();	
		byte tam =  (byte) data.length;
        return new PDU((byte)0x01,(byte)tipo,PDU.REGISTER,(byte)0x02/*é para registo*/,tam,(byte)0x01,(byte)0x01,data);
		
	}
	static public PDU REGISTER_PDU_RESPONSE( int mensagem){
		PDU ret = new PDU((byte)0x01,(byte)mensagem,PDU.REGISTER_RESPONSE,(byte)0x02/*é de registo*/,(byte)0x00,(byte)0x01,(byte)0x01,new byte[0]);
		return ret;
	}
	
	static public PDU LOGIN_PDU_RESPONSE( int mensagem){
		PDU ret = new PDU((byte)0x01,(byte)mensagem,PDU.REGISTER_RESPONSE,(byte)0x01/*é de LOGIN*/,(byte)0x00,(byte)0x01,(byte)0x01,new byte[0]);
		return ret;
	}
	static public PDU LOGOUT_PDU_RESPONSE( int mensagem){
		PDU ret = new PDU((byte)0x01,(byte)mensagem,PDU.REGISTER_RESPONSE,(byte)0x00/*é de LOGOUT*/,(byte)0x00,(byte)0x01,(byte)0x01,new byte[0]);
		return ret;
	}
	
	static public  PDU ARE_YOU_THERE_PDU(){ //ver se tem de mandar o ip e porta de respsota
		PDU ret = new PDU((byte)0x01,(byte)0x00,PDU.ARE_YOU_THERE,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x01,new byte[0]);
		return ret;
	}
	
	static public  PDU I_AM_HEREE_PDU(){ //ver se tem de mandar o ip e porta de respsota
		PDU ret = new PDU((byte)0x01,(byte)0x00,PDU.I_AM_HERE,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x01,new byte[0]);
		return ret;
	}

}
