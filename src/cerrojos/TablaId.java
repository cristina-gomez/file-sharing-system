package cerrojos;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class TablaId{
	
	private Set<Integer> ids; //para guardar los ids que se están utilizando
	private Semaphore semaforo;
	private static final int maxConcurrente = 50;
	
	public TablaId() {
		semaforo = new Semaphore(maxConcurrente);
		ids = new HashSet<Integer>();
	}
	
	public int introducirId() throws InterruptedException {
		semaforo.acquire();
		int id=0;
		while(ids.contains(id)) id++;
		return id;
	}
	
	public void eliminarId(int id) {
		if(ids.contains(id)) {
			ids.remove(id);
			semaforo.release();
		}
	}
	
}
