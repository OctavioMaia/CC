package Common;

import java.util.Map;

public class PDU_APP_CONS_RESP extends PDU_APP {

	private int fonte;
	private String userID;
	private String ip;
	private int port;
	private boolean found;
	private Map<String,String> result;
	
	public PDU_APP_CONS_RESP(int version) {
		super(version);
	}

	//para servers usarem
	public PDU_APP_CONS_RESP(int version, int fonte, String ip, int port, Map<String, String> result) {
		super(version);
		this.fonte = fonte;
		this.ip = ip;
		this.port = port;
		this.result = result;
		this.found=false;
		this.userID=null;
	}
	
	
	//para clientes usarem
	public PDU_APP_CONS_RESP(int version, int fonte, String userID, String ip, int port, boolean found) {
		super(version);
		this.fonte = fonte;
		this.userID = userID;
		this.ip = ip;
		this.port = port;
		this.found = found;
		this.result=null;
	}


	public int getFonte() {
		return fonte;
	}

	public void setFonte(int fonte) {
		this.fonte = fonte;
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

	public boolean isFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}

	public Map<String, String> getResult() {
		return result;
	}

	public void setResult(Map<String, String> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "PDU_APP_CONS_RESP [fonte=" + fonte + ", userID=" + userID + ", ip=" + ip + ", port=" + port + ", found="
				+ found + ", result=" + result + ", toString()=" + super.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + fonte;
		result = prime * result + (found ? 1231 : 1237);
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + port;
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
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
		PDU_APP_CONS_RESP other = (PDU_APP_CONS_RESP) obj;
		if (fonte != other.fonte)
			return false;
		if (found != other.found)
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (port != other.port)
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}

}
