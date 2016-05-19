package Common;

public class PDU_APP_REQUEST extends PDU_APP {

	private String userID;
	private String ip;
	private int port;
	private int font;
	private String song;
	
	public PDU_APP_REQUEST(int version) {
		super(version);
		// TODO Auto-generated constructor stub
	}

	
	public PDU_APP_REQUEST(int version, String userID, String ip, int port, int font, String song) {
		super(version);
		this.userID = userID;
		this.ip = ip;
		this.port = port;
		this.font = font;
		this.song = song;
	}


	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
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

	public int getFont() {
		return font;
	}

	public void setFont(int font) {
		this.font = font;
	}

	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}

	@Override
	public String toString() {
		return "PDU_APP_REQUEST [userID=" + userID + ", ip=" + ip + ", port=" + port + ", font=" + font + ", song="
				+ song + ", toString()=" + super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + font;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + port;
		result = prime * result + ((song == null) ? 0 : song.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
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
		PDU_APP_REQUEST other = (PDU_APP_REQUEST) obj;
		if (font != other.font)
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (port != other.port)
			return false;
		if (song == null) {
			if (other.song != null)
				return false;
		} else if (!song.equals(other.song))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}
	

}
