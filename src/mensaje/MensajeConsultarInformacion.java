package mensaje;

import java.io.Serializable;

public class MensajeConsultarInformacion extends Mensaje implements Serializable {
	
    private static final long serialVersionUID = 1L;


	public MensajeConsultarInformacion(String origen, String destino) {
		super(1,origen,destino);
	}

}
