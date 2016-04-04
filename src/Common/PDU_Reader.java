package Common;

public final class PDU_Reader {

	static public PDU_APP read(PDU p){
		String data;
		PDU_APP pa=null;
		String[] campos;
		byte version = p.getVersion();
		byte secu = p.getSecurity();
		byte tipo = p.getTipo();
		
		System.out.println("TIPOCLASS:"+tipo);
		
		switch (tipo) {
		case (PDU.REGISTER):
			int origem = (int)secu;
			int tipoR = (int)p.getOptions()[0];
			 data = new String(p.getData());
			
			 campos= data.split(";");
			
			pa = new PDU_APP_REG(version, origem, tipoR, campos[0].split("_")[1],
					campos[1].split("_")[1], campos[2].split("_")[1], Integer.parseInt(campos[3].split("_")[1]));
			break;

		case (PDU.REGISTER_RESPONSE):
			int tipoR2 = (int)p.getOptions()[0];
			pa = new PDU_APP_REG_RESP((int)version, (int)secu,tipoR2);
			break;
		case (PDU.ARE_YOU_THERE):
			data = new String(p.getData());
			campos= data.split(";");
			pa = new PDU_APP_STATE((int)version,campos[0].split("_")[1] , Integer.parseInt(campos[1].split("_")[1]), 1);
			break;
		case (PDU.I_AM_HERE):
			data = new String(p.getData());
			campos= data.split(";");
			pa = new PDU_APP_STATE((int)version,campos[0].split("_")[1] , Integer.parseInt(campos[1].split("_")[1]), 0);
			break;
		default:
			break;
		}
		System.out.println("ola");
		return pa;
		
	}
	
	static public PDU_APP read(byte[] data){
		return PDU_Reader.read(PDU.fromBytes(data));
	}
}
