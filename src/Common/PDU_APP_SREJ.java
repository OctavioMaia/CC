package Common;

import java.util.ArrayList;

public class PDU_APP_SREJ extends PDU_APP {
	private ArrayList<Integer> retransmit;

	public PDU_APP_SREJ(int version, ArrayList<Integer> retransmit) {
		super(version);
		this.retransmit = retransmit;
	}

	public ArrayList<Integer> getRetransmit() {
		return retransmit;
	}

	public void setRetransmit(ArrayList<Integer> retransmit) {
		this.retransmit = retransmit;
	}
	
	

}
