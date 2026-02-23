package mensaje;

import java.io.Serializable;

public class MensajeServidorListo extends Mensaje implements Serializable{
	
    private static final long serialVersionUID = 1L;
    
    private int puerto;

	public MensajeServidorListo(String origen, String destino, int puerto) {
		super(93, origen, destino);
		this.puerto=puerto;
	}

	public int getPuerto() {
		return puerto;
	}

}
