package usuario;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class Monitor {
	private int nr, nw;
	private final ReentrantLock cerrojo = new ReentrantLock(true);
    private final Condition condicionLectores = cerrojo.newCondition();
    private final Condition condicionEscritores = cerrojo.newCondition();
    
    protected Monitor() {
    	this.nr = 0;
    	this.nw = 0;
    }
    
    protected void requestRead() throws InterruptedException{
    	cerrojo.lock();
    	while(nw > 0) {
    		condicionLectores.wait();
    	}
    	nr+=1;
    	cerrojo.unlock();
    }
    
    protected void releaseRead() {
    	cerrojo.lock();
    	nr--;
    	if(nr == 0) {
    		condicionEscritores.signal();
    	}
    	cerrojo.unlock();
    }
    
    protected void requestWrite() throws InterruptedException {
    	cerrojo.lock();
    	while(nr > 0 || nw > 0) {
    		condicionEscritores.wait();
    	}
    	nw++;
    	cerrojo.unlock();
    }
    
    protected void releaseWrite() {
    	cerrojo.lock();
    	nw--;
		condicionLectores.signal();
		condicionEscritores.signal();
		cerrojo.unlock();
    }
    
    
    
    
}
