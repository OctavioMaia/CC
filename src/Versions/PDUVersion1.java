package Versions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import Common.PDU;
import Common.PDU_APP;
import Common.PDU_Reader;

public class PDUVersion1 {

	public static PDU_APP readPDU(byte version, InputStream is){
		byte pdu[] = new byte[49152];
		int nReadBytes=1;
		
		pdu[0]=version;
		
		try {
			nReadBytes += is.read(pdu,nReadBytes,11);
			System.out.println("OLATUDO"+nReadBytes);
		} catch (IOException e) {
			System.out.println("Não foi possivel realizar a leitura dos bytes desejados.");
			e.printStackTrace();
		}
		byte sizebyte[] = new byte[4];
		
		
		
		sizebyte[0]=pdu[7];
		sizebyte[1]=pdu[8];
		sizebyte[2]=pdu[9];
		sizebyte[3]=pdu[10];
		
		System.out.println("TAM:" + new String(sizebyte)); //valor do tamanho esta correcto pelo que é escrito

		int tam = PDU.intfromByte(sizebyte);
		
		System.out.println("TAMversion:" + tam);
		
		try {
			System.out.println(is.read(pdu,nReadBytes,tam));
		} catch (IOException e) {
			System.out.println("Não foi possivel realizar a leitura dos bytes do campo data.");
			e.printStackTrace();
		}
		
		System.out.println("BYTESTAMAnho:"+ pdu.length);
		System.out.println("BYTES:"+ pdu);
		
		return PDU_Reader.read(pdu);
		
	}
	
	
	
	
}
