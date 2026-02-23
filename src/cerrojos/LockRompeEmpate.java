package cerrojos;

public class LockRompeEmpate extends Cerrojo {
	
	private ArrayEntero in;
	private ArrayEntero last;
	
	public LockRompeEmpate(int num) {
		super(num);
		in=new ArrayEntero(num, -1);
		last=new ArrayEntero(num, -1);
	}

	public void takeLock(int numProceso) {
		for(int j=0;j<num;++j) {
			last.asignarValor(j, numProceso);
			in.asignarValor(numProceso, j);
			for(int k=0;k<num;++k) {
				if(numProceso!=k) {
					while(in.getValor(k)>=in.getValor(numProceso)&&last.getValor(j)==numProceso) Thread.yield();
				}
			}
		}
	}
	
	public void releaseLock(int numProceso) {
		in.asignarValor(numProceso, -1);
	}

}
