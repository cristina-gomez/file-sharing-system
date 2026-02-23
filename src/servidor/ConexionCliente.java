package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ConexionCliente {
	private final ObjectInputStream entrada;
	private final ObjectOutputStream salida;

	public ConexionCliente(ObjectOutputStream salida, ObjectInputStream entrada) {
		this.salida = salida;
		this.entrada = entrada;
	}

	public ObjectInputStream getEntrada() {
		return entrada;
	}

	public ObjectOutputStream getSalida() {
		return salida;
	}

}
