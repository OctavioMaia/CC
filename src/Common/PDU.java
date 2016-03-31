package Common;

import java.nio.ByteBuffer;

public class PDU {
	 //private int inc = 0;
	 private byte version;
	 private byte security;
	 private byte tipo;
	 private byte op1;
	 private byte op2;
	 private byte op3;
	 private byte op4;
	 private byte nbit;
	 private byte[] sizebytes;
	 private byte[] data;
	 
	public byte[] getSizebytes() {
		return sizebytes;
	}

	public void setSizebytes(byte[] sizebytes) {
		this.sizebytes = sizebytes;
	}



	
	 
	 
	 public static byte REGISTER =0x00;
	 public static byte CONSULT_REQUEST =0x01;
	 public static byte CONSULT_RESPONSE =0x02;
	 public static byte PROBE_REQUEST =0x03;
	 public static byte PROBE_RESPONSE =0x04;
	 public static byte REQUEST =0x05;
	 public static byte DATA =0x06;
	 
	 public byte getNbit() {
		return nbit;
	}

	public void setNbit(byte nbit) {
		this.nbit = nbit;
	}



	//Nossos
	 public static byte ARE_YOU_THERE =0x07;
	 public static byte I_AM_HERE =0x08;
	 public static byte CONFIRM =0x09;
	 public static byte ACK =0x0A;
	 public static byte REGISTER_RESPONSE =0x0B;
	 
	 private static double mlog(int base, double n){
		 return (Math.log(n) / Math.log(base));
	 }
	 
	 static public byte[] toBytes(PDU pdu){
		 byte[] obj=new byte[49152];
		 obj[0]=pdu.getVersion();
		 obj[1]=pdu.getSecurity();
		 obj[2]=pdu.getTipo();
		 byte[] tmp = pdu.getOptions();
		 for (int i = 0; i < tmp.length; i++) { //4
			obj[3+i]=tmp[i];
		 }
		 obj[7]=pdu.getNbit();
		 //comecar no 8
		 int objpos = 8;
		 tmp=pdu.getSizebytes();
		 for(int i =0;i<(int)obj[7];i++){
			 obj[objpos]=tmp[i];
			 objpos++;
		 }
		 //calcular tamanho do  dados a partir do byre[]
		 int tamanho  = intfromByte(pdu.getSizebytes());
		 tmp=pdu.getData();
		 for (int i = 0; i < tmp.length; i++) { //49145
			obj[objpos]=tmp[i];
			objpos++;
		 }
		 return obj;
	 }
	 
	 static private int round(double i, int v){
		    return (int)Math.round(i/v) * v;
	}
	 
	 static private int intfromByte(byte[] sizebytes){
		 ByteBuffer wrapped = ByteBuffer.wrap(sizebytes); 
		 return wrapped.getInt(); 
	 }
	 
	 static private byte[] bytefromInt(int integer){
		 int nbit = round(mlog(2,integer*1.0),8);
		 ByteBuffer dbuf = ByteBuffer.allocate(nbit/8);
		 dbuf.putInt(integer);
		 return  dbuf.array();
	 }
	 
	 static public PDU fromBytes(byte[] data){
		 int objpos = 0;
		 byte ver = data[objpos++];
		 byte sec = data[objpos++];
		 byte tip = data[objpos++];
		 //obj[1]=pdu.getSecurity();
		 //obj[2]=pdu.getTipo();
		 //byte[] tmp = pdu.getOptions();
		 byte[] ops = new byte[4];
		 
		 for (int i = 0; i < 4; i++) { //4
			ops[i]=data[objpos++];
		 }
		 byte nbit = data[objpos++];
		 
		 byte[] sizebytes = new byte[(int)nbit];
		 for(int i =0 ; i<(int)nbit;i++){
			 sizebytes[i]=data[objpos++];
		 }
		 // apartirn do byte[ calcular  o tamanho
		 int tamanho  = intfromByte(sizebytes);
		 byte[] datai = new byte[tamanho];
		 for (int i = 0; i < tamanho; i++) {
			datai[i]=data[objpos++];
		 }
		 PDU p = new PDU(ver, sec, tip, ops[0], ops[1], ops[2], ops[3], datai);
		 return p;
	 }
	 

		
	 public PDU (byte version, byte security,byte tipo, byte op1, byte op2, byte op3, byte op4, byte[] data){
		 this.version=version;
		 this.security=security;
		 this.tipo=tipo;
		 this.op1=op1; 
		 this.op2=op2; // tamanho dados
		 this.op3=op3; // numeracao atual
		 this.op4=op4; // numeraçao total
		 int tamanhodados = data.length;
		 byte[] sizebytes = bytefromInt(data.length);
		 this.nbit = (byte)sizebytes.length;
		 this.sizebytes=sizebytes;
		 
		 this.data = new byte[tamanhodados];
		 int i=0;
		 for (i = 0;  i< tamanhodados; i++) {
			 this.data[i]=data[i];
			
		}
	 }


	public byte[] getOptions() {
		byte[] ops = new byte[4];
		ops[0]=this.op1;
		ops[1]=this.op2;
		ops[2]=this.op3;
		ops[3]=this.op4;
		return ops;
	}
	public void setOptions(byte[] ops) {
		this.op1=ops[0];
		this.op2=ops[1];
		this.op3=ops[2];
		this.op4=ops[3];
	}
	 
	public byte getVersion() {
		return version;
	}



	public void setVersion(byte version) {
		this.version = version;
	}



	public byte getSecurity() {
		return security;
	}



	public void setSecurity(byte security) {
		this.security = security;
	}



	public byte getTipo() {
		return tipo;
	}



	public void setTipo(byte tipo) {
		this.tipo = tipo;
	}



	public byte[] getData() {
		return data;
	}



	public void setData(byte[] data) {
		this.data = data;
	}
	 
	
	 
}
