package Common;

public class PDU_APP_LOGIN extends PDU_APP{

	private String uname;
	private String pass;
	private String ip;
	private int port;
	
	public PDU_APP_LOGIN(int version, String uname, String pass, String ip, int port) {
		super(version);
		this.uname = uname;
		this.pass = pass;
		this.ip = ip;
		this.port = port;
	}

	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
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
	
	
	
	
	
	
	
	
}
