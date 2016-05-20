package Common;

public class PDU_APP_REG extends PDU_APP{
	private int origem;
	private int tipo;
	private String uname;
	private String pass;
	private String ip;
	private int port;
	public PDU_APP_REG(int version, int origem, int tipo, String uname, String pass, String ip, int port) {
		super(version);
		this.origem = origem;
		this.tipo = tipo;
		this.uname = uname;
		this.pass = pass;
		this.ip = ip;
		this.port = port;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + origem;
		result = prime * result + ((pass == null) ? 0 : pass.hashCode());
		result = prime * result + port;
		result = prime * result + tipo;
		result = prime * result + ((uname == null) ? 0 : uname.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return "PDU_APP_REG [origem=" + origem + ", tipo=" + tipo + ", uname=" + uname + ", pass=" + pass + ", ip=" + ip
				+ ", port=" + port + ", toString()=" + super.toString() + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PDU_APP_REG other = (PDU_APP_REG) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (origem != other.origem)
			return false;
		if (pass == null) {
			if (other.pass != null)
				return false;
		} else if (!pass.equals(other.pass))
			return false;
		if (port != other.port)
			return false;
		if (tipo != other.tipo)
			return false;
		if (uname == null) {
			if (other.uname != null)
				return false;
		} else if (!uname.equals(other.uname))
			return false;
		return true;
	}
	public int getOrigem() {
		return origem;
	}
	public void setOrigem(int origem) {
		this.origem = origem;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
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