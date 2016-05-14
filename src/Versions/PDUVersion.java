package Versions;

import java.io.IOException;
import java.io.InputStream;

import Common.PDU;
import Common.PDU_APP;
import Common.PDU_Reader;

public class PDUVersion {

	public static PDU_APP readPDU(InputStream is) throws IOException{
		byte[] version = new byte[1];
		PDU_APP pdu = null;
			while(is.read(version,0,1)!=1);
			switch (version[0]) {
				case 0x01:
					pdu = readPDU_V1(is);
					break;
				default:
					System.out.println(Thread.currentThread().getName() + " A versão " + version[0] + " não se encontra disponovel no sistema.");
					break;
			}
		System.out.println(Thread.currentThread() + " " +pdu);
		return pdu;
	}
	
	private static PDU_APP readPDU_V1(InputStream is){
		byte pdu[] = new byte[49152];
		int nReadBytes=1;
		
		pdu[0]=0x01;
		try {
			nReadBytes += is.read(pdu,nReadBytes,11);
			byte sizebyte[] = new byte[4];
			
			sizebyte[0]=pdu[7];
			sizebyte[1]=pdu[8];
			sizebyte[2]=pdu[9];
			sizebyte[3]=pdu[10];
			
			int tam = PDU.intfromByte(sizebyte);
					
			try {
				is.read(pdu,nReadBytes,tam);
			} catch (IOException e) {
				System.out.println(Thread.currentThread().getName() + " Não foi possivel realizar a leitura dos bytes do campo data.");
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println(Thread.currentThread().getName() + " Não foi possivel realizar a leitura dos bytes desejados.");
			e.printStackTrace();
		}
		return PDU_Reader.read(pdu);	
	}
	
	
	
	
}
