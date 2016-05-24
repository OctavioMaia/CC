package Common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class PDU_Reader {

	
	static public PDU_APP_DATA read(ArrayList<PDU> ps){
		PDU_APP_DATA pa=null;
		byte version = ps.get(0).getVersion();
		PDU p1 = ps.get(0);
		int total =p1.getTotal();
		byte[][] bs = new byte[total][];
		pa=new PDU_APP_DATA(version);
		pa.setBlocos(total);
		if(ps.size()!=total){
			//mandar exeçao
		}
		for (PDU p  : ps) {
			//byte secu = p.getSecurity();
			//byte tipo = p.getTipo();
			int num = p.getNUM()-1;
			byte[] datapdu = p.getData();
			if(num==0){
				//ter atençao a estas manhosices
				String s = new String(p.getData());
				int sep = s.indexOf(';', 0);
				String nome = s.substring(0, sep).replaceFirst(";", "");
				datapdu = s.substring(sep+1).getBytes();
				pa.setNome(nome);
			}
			bs[num]=datapdu;

		}
		byte[] musica = new byte[0];
		for (int i = 0; i < total; i++) {
			musica = PDU_Buider.concatenarArray(musica, bs[i]);
		}
		pa.setSong(musica);
		pa.setComplete(true);
		return pa;
	}
	
	
	static public PDU_APP read(PDU p){
		String data;
		PDU_APP pa=null;
		String[] campos;
		byte version = p.getVersion();
		byte secu = p.getSecurity();
		byte tipo = p.getTipo();
		
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
			pa = new PDU_APP_STATE((int)version,campos[1].split("_")[1], Integer.parseInt(campos[2].split("_")[1]),campos[0].split("_")[1], 1);
			break;
		case (PDU.I_AM_HERE):
			data = new String(p.getData());
			campos= data.split(";");
			pa = new PDU_APP_STATE((int)version,campos[1].split("_")[1] , Integer.parseInt(campos[2].split("_")[1]), campos[0].split("_")[1],0);
			break;
		case (PDU.CONSULT_REQUEST):
			data = new String(p.getData());
			//int origem = (int)secu;
			campos = data.split(";");
			pa = new PDU_APP_CONS_REQ((int)version, campos[3].split("_")[1], campos[4].split("_")[1],
					campos[5].split("_")[1], (int)secu, campos[0].split("_")[1],
					campos[1].split("_")[1], Integer.parseInt(campos[2].split("_")[1]));
			break;
		case (PDU.CONSULT_RESPONSE):
			pa = leConsResp(p);
			break;
		case(PDU.PROBE_REQUEST):
			data = new String(p.getData());
			//int origem = (int)secu;
			campos = data.split(";");
			pa = new PDU_APP_PROB_REQUEST(version, campos[0].split("_")[1], campos[1].split("_")[1], Integer.parseInt(campos[2].split("_")[1]), (int)secu);
			break;
		case(PDU.PROBE_RESPONSE):
			data = new String(p.getData());
			//int origem = (int)secu;
			campos = data.split(";");
			pa = new PDU_APP_PROB_RESPONSE(version, campos[0].split("_")[1], campos[1].split("_")[1], 
					Integer.parseInt(campos[2].split("_")[1]), (int)secu, Long.parseLong(campos[3].split("_")[1]));
			break;
		case(PDU.DATA):
			Byte b = p.getOptions()[3];
			int tam = b.intValue();
			pa = new PDU_APP_DATA(version, null, null, tam, false,p.getOptions()[2]);
			break;
		case(PDU.REQUEST):
			data = new String(p.getData());
			//int origem = (int)secu;
			campos = data.split(";");
			pa = new PDU_APP_REQUEST(version, campos[0].split("_")[1], campos[1].split("_")[1], Integer.parseInt(campos[2].split("_")[1]), (int)secu,campos[3].split("_")[1]);
			break;
		case(PDU.SREJ):
			ArrayList<Integer> recived = new ArrayList<>();
			data = new String(p.getData());
			if(!data.isEmpty()){
				//int origem = (int)secu;
				campos = data.split(";");
				for (String string : campos) {
					recived.add(Integer.parseInt(string));
				}
			}
			
			pa= new PDU_APP_SREJ(version, recived);
			//pa = new PDU_APP_REQUEST(version, campos[0].split("_")[1], campos[1].split("_")[1], Integer.parseInt(campos[2].split("_")[1]), (int)secu,campos[3].split("_")[1]);
		break;
		default:
			break;
		}
		return pa;
		
	}
	
	static private PDU_APP leConsResp(PDU p){
		String data;
		PDU_APP pa=null;
		String[] campos;
		int versao = (int)p.getVersion();
		int fonte = (int)p.getSecurity();
		if(fonte==0){//server
			Map<String,String> map = new HashMap<>();
			data = new String(p.getData());
			campos = data.split(";");
			String ip = campos[0].split("_")[1];
			int porta = Integer.parseInt(campos[1].split("_")[1]);
			for (int i = 2; i < campos.length; i++) {
				String nome = campos[i].split("_")[0];
				String info = campos[i].split("_")[1];
				map.put(nome, info);
			}
			pa = new PDU_APP_CONS_RESP(versao, fonte, ip, porta, map);
		}else{//cliente
			data = new String(p.getData());
			campos = data.split(";");
			String uid = campos[0].split("_")[1];
			String ip = campos[1].split("_")[1];
			
			boolean found = ((int)p.getOptions()[0])==1;
			int porta=-1;
			if(found){
				porta = Integer.parseInt(campos[2].split("_")[1]);
			}
			pa = new PDU_APP_CONS_RESP(versao, fonte, uid, ip, porta, found);
		}
		
		
		return pa;
	}
	static public PDU_APP read(byte[] data){
		return PDU_Reader.read(PDU.fromBytes(data));
	}
}