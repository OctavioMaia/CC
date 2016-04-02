package Common;

public class PDU_APP_STATE extends PDU_APP{

	private String ip;
	private int port;
	private int tipo ; //pegunta ou respsta
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
