package Common;

public class PDU_APP_CONS_REQ extends PDU_APP{

	private String banda;
	private String musica;
	private String ext;
	private int fonte; // o servidor 1 clienet
	private String idUser;
	private String ip;
	private int port;

	public PDU_APP_CONS_REQ(int version) {
		super(version);
		// TODO Auto-generated constructor stub
	}
	
	public PDU_APP_CONS_REQ(int version, String banda, String musica, String ext, int fonte, String idUser, String ip,
			int port) {
		super(version);
		this.banda = banda;
		this.musica = musica;
		this.ext = ext;
		this.fonte = fonte;
		this.idUser = idUser;
		this.ip = ip;
		this.port = port;
	}

	public String getBanda() {
		return banda;
	}
	public void setBanda(String banda) {
		this.banda = banda;
	}
	public String getMusica() {
		return musica;
	}
	public void setMusica(String musica) {
		this.musica = musica;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public int getFonte() {
		return fonte;
	}
	public void setFonte(int fonte) {
		this.fonte = fonte;
	}
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((banda == null) ? 0 : banda.hashCode());
		result = prime * result + ((ext == null) ? 0 : ext.hashCode());
		result = prime * result + fonte;
		result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((musica == null) ? 0 : musica.hashCode());
		result = prime * result + port;
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
		PDU_APP_CONS_REQ other = (PDU_APP_CONS_REQ) obj;
		if (banda == null) {
			if (other.banda != null)
				return false;
		} else if (!banda.equals(other.banda))
			return false;
		if (ext == null) {
			if (other.ext != null)
				return false;
		} else if (!ext.equals(other.ext))
			return false;
		if (fonte != other.fonte)
			return false;
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
		if (musica == null) {
			if (other.musica != null)
				return false;
		} else if (!musica.equals(other.musica))
			return false;
		if (port != other.port)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PDU_APP_CONS_REQ [banda=" + banda + ", musica=" + musica + ", ext=" + ext + ", fonte=" + fonte
				+ ", idUser=" + idUser + ", ip=" + ip + ", port=" + port + ", toString()=" + super.toString() + "]";
	}

	
	
}
