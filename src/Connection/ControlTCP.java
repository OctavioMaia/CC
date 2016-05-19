package Connection;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import Common.PDU;

public class ControlTCP {

	private ReentrantLock lock;
	private Condition esperaACK;
	private Condition possivelEnviar;
	
	
	
	private int timeOutTry;
	private int timeOutDesistir;
	
	
	private int windowMax;
	private int windowActualSize; //diminuir quando mando aumentar quando recebo ack
	
	
	private int lastDataNumSent; //ultimo pacote enviado
	
	
	private int lastACK; //ultimo ack recebido pois pode vir o 5 e depois o 4

	private int paraEnvioNUM; //total de pacotes a enviar
	
	

	public ControlTCP( int timeOutTry, int timeOutDesistir, int windowMax, int paraEnvioNUM) {
		super();
		this.lock = new ReentrantLock();
		this.esperaACK = this.lock.newCondition();
		this.possivelEnviar = this.lock.newCondition();
		
		this.timeOutTry = timeOutTry;
		this.timeOutDesistir = timeOutDesistir;
		
		this.windowMax = windowMax;
		
		this.windowActualSize = this.windowMax;
		
		this.lastDataNumSent = 0;
		this.lastACK = 0;
		this.paraEnvioNUM = paraEnvioNUM;
	}

	public void diminuiWindowAtual(){
		this.windowActualSize--;
	}
	
	public void aumentaWindowAtual(int size){
		this.windowActualSize+=size;
	}
	
	public ReentrantLock getLock() {
		return lock;
	}

	public void setLock(ReentrantLock lock) {
		this.lock = lock;
	}

	public Condition getEsperaACK() {
		return esperaACK;
	}

	public void setEsperaACK(Condition esperaACK) {
		this.esperaACK = esperaACK;
	}

	public Condition getPossivelEnviar() {
		return possivelEnviar;
	}

	public void setPossivelEnviar(Condition possivelEnviar) {
		this.possivelEnviar = possivelEnviar;
	}

	public int getTimeOutTry() {
		return timeOutTry;
	}

	public void setTimeOutTry(int timeOutTry) {
		this.timeOutTry = timeOutTry;
	}

	public int getTimeOutDesistir() {
		return timeOutDesistir;
	}

	public void setTimeOutDesistir(int timeOutDesistir) {
		this.timeOutDesistir = timeOutDesistir;
	}

	public int getWindowMax() {
		return windowMax;
	}

	public void setWindowMax(int windowMax) {
		this.windowMax = windowMax;
	}

	public int getWindowActualSize() {
		return windowActualSize;
	}

	public void setWindowActualSize(int windowActualSize) {
		this.windowActualSize = windowActualSize;
	}

	public int getLastDataNumSent() {
		return lastDataNumSent;
	}

	public void setLastDataNumSent(int lastDataNumSent) {
		this.lastDataNumSent = lastDataNumSent;
	}

	public int getLastACK() {
		return lastACK;
	}

	public void setLastACK(int lastACK) {
		this.lastACK = lastACK;
	}

	public int getParaEnvioNUM() {
		return paraEnvioNUM;
	}

	public void setParaEnvioNUM(int paraEnvioNUM) {
		this.paraEnvioNUM = paraEnvioNUM;
	}
	
	

}
