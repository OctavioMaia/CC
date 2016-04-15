package Common;

import java.util.Iterator;
import java.util.Map;

public final class PDU_Buider {

	public static final byte REGISTO = 0x01;
	public static final byte LOGIN = 0x02;
	public static final byte LOGOUT = 0x03;
	
	
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

	//Fase2
	/**
	 * Parametros
	 * fonte indica quem envia o consulyt request (0 é o servidor que consulta , 1 é o cliente)
	 * ip ip de quem consulta e e porta onde uqre receber a resposta
	 * banda, musica e extensao especificado no enunciado
	 * */
	static public PDU CONSULT_REQUEST(int fonte /*0 server 1 cliente*/,String ip, int port, String banda, String musica, String ext){
		String mensagem = "IP_"+ip+";PT_"+port+";BAND_"+banda+";MUSIC_"+musica+";EXT_"+ext;
		PDU ret = new PDU((byte)0x01, (byte)fonte, PDU.CONSULT_REQUEST,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x01, mensagem.getBytes());
		return ret;
	}
	
	/**
	 * Parametros
	 * fonte indica quem envia o consulyt request (0 é o servidor que consulta , 1 é o cliente)
	 * ip ip de quem consulta e e porta onde tem comunicaçao aberta para falar
	 * foud so é considerado quendo provem de um cliente, assim como o results so é considerado quando prevem de um server
	 * caso  a musica no cliente nao sega eencontrada o ip e porta sao ignorados
	 * a informaçao de ser encontrado ou nao vai no 1 campo da opçoes
	 * */
	static public PDU CONSULT_RESPONSE(int fonte /*0 server 1 cliente*/,String ip, int port, boolean found, Map<String,String> results ){
		PDU ret =null;
		if(fonte == 0){
			int total = results.size();
			String mensagem = "TOT_"+total;
			Iterator<String> it = results.keySet().iterator();
			while (it.hasNext()) {
				String string =it.next();
				mensagem = 
			}
		}else{
			if(found){
				String mensagem = "IP_"+ip+";PT_"+port;
				ret =new PDU((byte)0x01, (byte)fonte, PDU.CONSULT_RESPONSE, (byte)0x01, (byte)0x00,(byte)0x01,(byte)0x01, mensagem.getBytes());
			}else{
				ret =new PDU((byte)0x01, (byte)fonte, PDU.CONSULT_RESPONSE, (byte)0x00, (byte)0x00,(byte)0x01,(byte)0x01,new byte[0]);
			}
		}
		
		return ret;
	}
	
	
}
