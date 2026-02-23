package mensaje;

import java.io.Serializable;

public class MensajeSalidaConfirmado extends Mensaje implements Serializable{
	
    private static final long serialVersionUID = 1L;

	public MensajeSalidaConfirmado(String origen, String destino) {
		super(94,origen,destino);
	}

}
