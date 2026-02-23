package mensaje;

import java.io.Serializable;
import java.util.List;

public class MensajeInicioCliente extends Mensaje implements Serializable {
	
    private static final long serialVersionUID = 1L;
	
	private List<String> archivos;
	
	public MensajeInicioCliente(String origen, String destino, List<String> archivos) {
		super(0,origen,destino);
		this.archivos = archivos;
	}
	
	public List<String> getArchivos() {
		return archivos;
	}

}
