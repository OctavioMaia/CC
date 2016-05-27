

class tcpControl {

	private int windowSize;
	private int ackRecived;
	private int ackWait;
	private int toSend;
	private int maxWaitTime;
	private int waitTimeStep;
	private int lastWait;
	private boolean waitingRecive;
	private boolean fim;
	
	
	public tcpControl( int maxWaitTime, int waitTimeStep) {
		super();
		this.windowSize = 1;
		this.ackRecived = 0;
		this.ackWait = 1;
		this.toSend = 0;
		this.maxWaitTime = maxWaitTime;
		this.waitTimeStep = waitTimeStep;
		this.lastWait = 0;
		this.waitingRecive=false;
		this.fim=false;
	}
	
	public synchronized void registaRecep(int num){
		this.lastWait=0;
		if(this.ackWait==num){
			this.ackWait=num+1;
			this.ackRecived=num;
			this.waitingRecive=false;
		}
	}
	
	public synchronized void registaEnvio(int num){
		this.lastWait=0;
		this.ackWait=num;
		this.waitingRecive=true;
		this.notifyAll(); // registo que estou a espera de receber um ACK
	}
	
	public synchronized void termina(){
		this.fim=true;
	}
	
	public synchronized void control() throws InterruptedException{
		boolean timeout=false;
		int last = this.ackRecived;
		while(this.fim==false || timeout==true)
			this.lastWait = 0;
			while(this.waitingRecive==false && this.fim==false){
				this.wait(); //espera que algeum lhe diga que esta a espera de receber
				
			}
			while(this.lastWait<=this.maxWaitTime && this.fim==false){
				this.lastWait+=this.waitTimeStep;
				this.wait(this.waitTimeStep);
				if(this.ackRecived!=last){//recebi pacote que queria
					last=this.ackRecived;
					this.lastWait=0;
				}
				
			}
			if(this.lastWait>this.maxWaitTime){
				timeout=true;
		}
		if(timeout==true){
			//matar a therad que esta a receber dados 
			//matar esta com exepçao
		}
	}
	
	public synchronized int getWindowSize() {
		return windowSize;
	}
	public synchronized void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}
	public synchronized int getAckRecived() {
		return ackRecived;
	}
	public synchronized void setAckRecived(int ackRecived) {
		this.ackRecived = ackRecived;
	}
	public synchronized int getAckWait() {
		return ackWait;
	}
	public synchronized void setAckWait(int ackWait) {
		this.ackWait = ackWait;
	}
	public synchronized int getToSend() {
		return toSend;
	}
	public synchronized void setToSend(int toSend) {
		this.toSend = toSend;
	}
	public synchronized int getMaxWaitTime() {
		return maxWaitTime;
	}
	public synchronized void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}
	public synchronized int getWaitTimeStep() {
		return waitTimeStep;
	}
	public synchronized void setWaitTimeStep(int waitTimeStep) {
		this.waitTimeStep = waitTimeStep;
	}
	public synchronized int getLastWait() {
		return lastWait;
	}
	public synchronized void setLastWait(int lastWait) {
		this.lastWait = lastWait;
	}
	@Override
	public synchronized String toString() {
		return "tcpControl [windowSize=" + windowSize + ", ackRecived=" + ackRecived + ", ackWait=" + ackWait
				+ ", toSend=" + toSend + ", maxWaitTime=" + maxWaitTime + ", waitTimeStep=" + waitTimeStep
				+ ", lastWait=" + lastWait + "]";
	}
	@Override
	public synchronized int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ackRecived;
		result = prime * result + ackWait;
		result = prime * result + lastWait;
		result = prime * result + maxWaitTime;
		result = prime * result + toSend;
		result = prime * result + waitTimeStep;
		result = prime * result + windowSize;
		return result;
	}
	@Override
	public synchronized boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		tcpControl other = (tcpControl) obj;
		if (ackRecived != other.ackRecived)
			return false;
		if (ackWait != other.ackWait)
			return false;
		if (lastWait != other.lastWait)
			return false;
		if (maxWaitTime != other.maxWaitTime)
			return false;
		if (toSend != other.toSend)
			return false;
		if (waitTimeStep != other.waitTimeStep)
			return false;
		if (windowSize != other.windowSize)
			return false;
		return true;
	}
	
	

}
