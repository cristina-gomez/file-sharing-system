package mensaje;

import java.io.Serializable;

public class MensajeSalida extends Mensaje implements Serializable{
	
    private static final long serialVersionUID = 1L;
	
	public MensajeSalida(String origen, String destino) {
		super(3,origen,destino);
	}

}
