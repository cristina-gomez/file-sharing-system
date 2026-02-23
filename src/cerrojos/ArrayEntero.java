package cerrojos;

public class ArrayEntero {
	
	Entero[] enteros;
	
	public ArrayEntero(int n, int valorInicial) {
		enteros=new Entero[n]; //Por defecto, el array de enteros se inicializa a 0.
		for(int i=0;i<n;++i) {
			enteros[i]=new Entero(valorInicial);
		}
	}
	
	public void incrementarPos(int pos) {
		enteros[pos].incrementar();
	}
	
	public void decrementarPos(int pos) {
		enteros[pos].decrementar();
	}
	
	public void asignarValor(int pos, int valor) {
		enteros[pos].asignarValor(valor);
	}
	
	public int getValor(int pos) {
		return enteros[pos].getValor();
	}

}
