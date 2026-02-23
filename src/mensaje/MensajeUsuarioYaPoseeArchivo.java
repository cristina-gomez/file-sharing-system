package mensaje;

import java.io.Serializable;

public class MensajeUsuarioYaPoseeArchivo extends Mensaje implements Serializable {
	
    private static final long serialVersionUID = 1L;

	public MensajeUsuarioYaPoseeArchivo(String origen, String destino) {
		super(96, origen, destino);
	}
}
