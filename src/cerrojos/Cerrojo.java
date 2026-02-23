package cerrojos;

public class Cerrojo extends Thread{
	
	//protected para que las clases hijas (LockRompeEmpate, LockBakery y LockTicket) puedan acceder al atributo. 
	protected int num;
	
	public Cerrojo(int num) {
		this.num=num;
	}
	
	public void takeLock(int id) {	
	}
	
	public void releaseLock(int id) {
	}
		
	
	public void run() {
	}

}
