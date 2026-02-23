package cerrojos;

import java.util.concurrent.atomic.AtomicInteger;

public class LockTicket extends Cerrojo{
	
	private AtomicInteger number;
	private int next;
	private ArrayEntero turn;
		
	public LockTicket(int num) {
		super(num);
		number = new AtomicInteger(1);
		next=1;
		turn = new ArrayEntero(num, -1);
	}
	
	public void takeLock(int numProceso) {
		turn.asignarValor(numProceso, number.getAndAdd(1));
		if(turn.getValor(numProceso)==num) number.getAndAdd(-num);
		else if(turn.getValor(numProceso)>num) turn.asignarValor(numProceso, turn.getValor(numProceso)%num);
        while (turn.getValor(numProceso) != next) Thread.yield();
	}
	
	public void releaseLock(int numProceso) {
		next = (next % num) +1;
	}

}
