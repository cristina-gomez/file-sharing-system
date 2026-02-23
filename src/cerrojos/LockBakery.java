package cerrojos;

public class LockBakery extends Cerrojo{
	
	private ArrayEntero turn;

	public LockBakery(int num) {
		super(num);
		turn = new ArrayEntero(num, 0);
	}
	
	public void takeLock(int numProceso) {
		turn.asignarValor(numProceso, 1);
		int maximo=0;
		for(int i=0;i<num;++i) {
			int valor=turn.getValor(i);
			if(valor>maximo) maximo=valor;
		}
		turn.asignarValor(numProceso, maximo+1);
		ParEnteros par1 = new ParEnteros(turn.getValor(numProceso), numProceso);
		for(int j=0; j<num;++j) {
			if(j!=numProceso) {
				ParEnteros par2 = new ParEnteros(turn.getValor(j),j);
				while(turn.getValor(j)!=0 && par1.mayorQue(par2)) Thread.yield();
			}
		}
	}
	
	public void releaseLock(int numProceso) {
		turn.asignarValor(numProceso, 0);
	}
	

}
