package Common;

public class PDU_APP_STATE extends PDU_APP{

	public static final int ARE_YOU_THERE = 1; 
	public static final int I_AM_HERE_PDU = 0;
	
	private String ip;
	private int port;
	private int tipo ; //pegunta ou resposta
	public PDU_APP_STATE(int version) {
		super(version);
		// TODO Auto-generated constructor stub
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	@Override
	public String toString() {
		return "PDU_APP_STATE [ip=" + ip + ", port=" + port + ", tipo=" + tipo + ", toString()=" + super.toString()
				+ "]";
	}
	public PDU_APP_STATE(int version, String ip, int port, int tipo) {
		super(version);
		this.ip = ip;
		this.port = port;
		this.tipo = tipo;
	}
	

}
