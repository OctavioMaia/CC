package Common;

public class PDU {
	 //private int inc = 0;
	 private byte version;
	 private byte security;
	 private byte tipo;
	 private byte op1;
	 private byte op2;
	 private byte op3;
	 private byte op4;
	 private byte[] data;
	 public static byte REGISTER =0x00;
	 public static byte CONSULT_REQUEST =0x01;
	 public static byte CONSULT_RESPONSE =0x02;
	 public static byte PROBE_REQUEST =0x03;
	 public static byte PROBE_RESPONSE =0x04;
	 public static byte REQUEST =0x05;
	 public static byte DATA =0x06;
	 
	 //Nossos
	 public static byte ARE_YOU_THERE =0x07;
	 public static byte I_AM_HERE =0x08;
	 public static byte CONFIRM =0x09;
	 public static byte ACK =0x0A;
	 public static byte REGISTER_RESPONSE =0x0B;
	 

	 static public byte[] toBytes(PDU pdu){
		 byte[] obj=new byte[49152];
		 obj[0]=pdu.getVersion();
		 obj[1]=pdu.getSecurity();
		 obj[2]=pdu.getTipo();
		 byte[] tmp = pdu.getOptions();
		 for (int i = 0; i < tmp.length; i++) { //4
			obj[3+i]=tmp[i];
		 }
		 //comecar no 7
		 tmp=pdu.getData();
		 for (int i = 0; i < tmp.length; i++) { //49145
			obj[7+i]=tmp[i];
		 }
		 return obj;
	 }
	 
	 static public PDU fromBytes(byte[] data){
		 
		 byte ver = data[0];
		 byte sec = data[1];
		 byte tip = data[2];
		 //obj[1]=pdu.getSecurity();
		 //obj[2]=pdu.getTipo();
		 //byte[] tmp = pdu.getOptions();
		 byte[] ops = new byte[4];
		 
		 for (int i = 0; i < 4; i++) { //4
			ops[i]=data[i+3];
		 }
		 //comecar no 7
		 byte[] datai = new byte[49145];
		 for (int i = 0; i < 49145; i++) { //49145
			 //System.out.println(7+i);
			datai[i]=data[7+i];
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
		 this.data = new byte[49145];
		 int i=0;
		 for (i = 0;  i< data.length; i++) {
			 this.data[i]=data[i];
			
		}
		while (i<49145) {
			this.data[i]=(byte) 0xFF;
			i++;
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
