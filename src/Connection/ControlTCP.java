package Connection;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.sun.swing.internal.plaf.synth.resources.synth;

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

	public synchronized void diminuiWindowAtual(){
		this.windowActualSize--;
	}
	public synchronized void aumentaWindowAtual(int size){
		this.windowActualSize+=size;
	}
	
	public synchronized ReentrantLock getLock() {
		return lock;
	}
	public synchronized void setLock(ReentrantLock lock) {
		this.lock = lock;
	}
	public synchronized Condition getEsperaACK() {
		return esperaACK;
	}
	public synchronized void setEsperaACK(Condition esperaACK) {
		this.esperaACK = esperaACK;
	}
	public synchronized Condition getPossivelEnviar() {
		return possivelEnviar;
	}
	public synchronized void setPossivelEnviar(Condition possivelEnviar) {
		this.possivelEnviar = possivelEnviar;
	}
	public synchronized int getTimeOutTry() {
		return timeOutTry;
	}
	public synchronized void setTimeOutTry(int timeOutTry) {
		this.timeOutTry = timeOutTry;
	}
	public synchronized int getTimeOutDesistir() {
		return timeOutDesistir;
	}
	public synchronized void setTimeOutDesistir(int timeOutDesistir) {
		this.timeOutDesistir = timeOutDesistir;
	}
	public synchronized int getWindowMax() {
		return windowMax;
	}
	public synchronized void setWindowMax(int windowMax) {
		this.windowMax = windowMax;
	}
	public synchronized int getWindowActualSize() {
		return windowActualSize;
	}
	public synchronized void setWindowActualSize(int windowActualSize) {
		this.windowActualSize = windowActualSize;
	}
	public synchronized int getLastDataNumSent() {
		return lastDataNumSent;
	}
	public synchronized void setLastDataNumSent(int lastDataNumSent) {
		this.lastDataNumSent = lastDataNumSent;
	}
	public synchronized int getLastACK() {
		return lastACK;
	}
	public synchronized void setLastACK(int lastACK) {
		this.lastACK = lastACK;
	}
	public synchronized int getParaEnvioNUM() {
		return paraEnvioNUM;
	}
	public synchronized void setParaEnvioNUM(int paraEnvioNUM) {
		this.paraEnvioNUM = paraEnvioNUM;
	}
}
