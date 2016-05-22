package Client;

import Connection.udpReciver;

public class SendConectionClient implements Runnable{

	private boolean probeReceive;
	private boolean requestToSend;
	
	public SendConectionClient() {
		probeReceive = false;
		requestToSend = true;
	}
	
	@Override
	public void run() {
		while(!probeReceive && requestToSend){
			udpReciver udpRec = new udpReciver(sendACK, reciveData, timeOutTry, timeOutDesistir);
		
		}
	}

}
