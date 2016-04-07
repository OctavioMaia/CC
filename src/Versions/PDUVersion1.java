package Versions;

import java.io.IOException;
import java.io.InputStream;

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
		} catch (IOException e) {
			System.out.println("Não foi possivel realizar a leitura dos bytes desejados.");
			e.printStackTrace();
		}
		byte sizebyte[] = new byte[4];
		
		
		
		sizebyte[0]=pdu[7];
		sizebyte[1]=pdu[8];
		sizebyte[2]=pdu[9];
		sizebyte[3]=pdu[10];
		
		int tam = PDU.intfromByte(sizebyte);
				
		try {
			System.out.println(is.read(pdu,nReadBytes,tam));
		} catch (IOException e) {
			System.out.println("Não foi possivel realizar a leitura dos bytes do campo data.");
			e.printStackTrace();
		}
		
		return PDU_Reader.read(pdu);	
	}
	
	
	
	
}
