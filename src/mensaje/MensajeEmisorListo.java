package mensaje;

import java.io.Serializable;

public class MensajeEmisorListo extends Mensaje implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private int posicion;
	
	public MensajeEmisorListo(String origen, String destino, int posicion){
		super(4,origen,destino);
	}

	public int getPosicion() {
		return posicion;
	}
}
