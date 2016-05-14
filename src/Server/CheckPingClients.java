package Server;

public class CheckPingClients implements Runnable{
	
	private static int maxTime = 60000;
	private ServerInfo server;
	private Thread main;
	
	public CheckPingClients(ServerInfo s, Thread m) {
		this.server = s;
		this.main = m;
	}

	@Override
	public void run() {
		while(main.getState()!=Thread.State.TERMINATED){
			for(String user : this.server.getOnline()){
				this.server.checkTimeStampClient(user, CheckPingClients.maxTime);
			}
			try {
				System.out.println("Daqui a " + CheckPingClients.maxTime/1000.0 + " segundos volto a verificar os clientes");
				Thread.sleep(CheckPingClients.maxTime);
			} catch (InterruptedException e) {
				System.out.println("Não foi possivel esperar " + CheckPingClients.maxTime/1000 + " segundos");
				break;
			}
		}
	}

}
