package cerrojos;

public class Entero {
	
	volatile int valor;
	
	public Entero(int n) {
		valor=n;
	}
	
	public void incrementar() {
		valor++;
	}
	
	public void decrementar() {
		valor--;
	}
	
	public void asignarValor(int n) {
		valor=n;
	}
	
	public int getValor() {
		return valor;
	}
	
	public void imprimirValor() {
		System.out.println(valor);
	}

}
