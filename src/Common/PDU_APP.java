package Common;

public class PDU_APP {

	@Override
	public String toString() {
		return "PDU_APP [version=" + version + "]";
	}

	private int version;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PDU_APP other = (PDU_APP) obj;
		if (version != other.version)
			return false;
		return true;
	}

	public PDU_APP(int version) {
		super();
		this.version = version;
	}

	
}

