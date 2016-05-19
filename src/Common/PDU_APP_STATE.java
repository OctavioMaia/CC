package Common;

public class PDU_APP_STATE extends PDU_APP{

	public static final int ARE_YOU_THERE = 1; 
	public static final int I_AM_HERE_PDU = 0;
	
	private String ip;
	private int port;
	private int tipo ; //pegunta ou resposta
	private String idUser;
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + port;
		result = prime * result + tipo;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PDU_APP_STATE other = (PDU_APP_STATE) obj;
		if (idUser == null) {
			if (other.idUser != null)
				return false;
		} else if (!idUser.equals(other.idUser))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (port != other.port)
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	@Override
	public String toString() {
		return "PDU_APP_STATE [ip=" + ip + ", port=" + port + ", tipo=" + tipo + ", idUser=" + idUser + ", toString()="
				+ super.toString() + "]";
	}
	public PDU_APP_STATE(int version, String ip, int port, String idUser, int tipo) {
		super(version);
		this.ip = ip;
		this.port = port;
		this.tipo = tipo;
		this.idUser = idUser;
	}
	
	

}
