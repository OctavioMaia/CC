package Common;

public final class PDU_Reader {

	static public PDU_APP read(PDU p){
		PDU_APP pa=null;
		byte version = p.getVersion();
		byte secu = p.getSecurity();
		byte tipo = p.getTipo();
		switch (tipo) {
		case (PDU.REGISTER):
			int origem = (int)secu;
			int tipoR = (int)p.getOptions()[0];
			String data = new String(p.getData());
			
			String[] campos= data.split(";");
			
			pa = new PDU_APP_REG(version, origem, tipoR, campos[0].split("_")[1],
					campos[1].split("_")[1], campos[2].split("_")[1], Integer.parseInt(campos[3].split("_")[1]));
			break;

		default:
			break;
		}
		return pa;
		
	}
}
