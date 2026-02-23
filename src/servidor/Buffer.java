package servidor;

import java.util.concurrent.Semaphore;

public class Buffer implements Almacen{
	
	private int buf[];
	private int fin;
	private Semaphore[] vacio;
	private Semaphore[] lleno;
	private int tam;
	
	public Buffer(int n) {
		buf= new int[n];
		for(int i=0;i<n;++i) buf[i]=-1;
		tam=n;
		vacio=new Semaphore[tam];
		lleno=new Semaphore[tam];
		for(int i=0;i<tam;++i) {
			vacio[i]=new Semaphore(1);
			lleno[i]=new Semaphore(0);
		}
		fin=0;
	}
	
	@Override
	public int almacenar(int producto) {
		try {
			vacio[fin].acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int pos = fin;
		buf[pos]=producto;
		fin= (fin+1)%tam;
		lleno[pos].release();
		return pos;
	}

	@Override
	public int extraer(int pos) {
		try {
			lleno[pos].acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int resultado = buf[pos];
		vacio[pos].release();
		return resultado;
	}

}
