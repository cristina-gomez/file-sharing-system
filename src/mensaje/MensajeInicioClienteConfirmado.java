package mensaje;

import java.io.Serializable;

public class MensajeInicioClienteConfirmado extends Mensaje implements Serializable{
	
    private static final long serialVersionUID = 1L;

	public MensajeInicioClienteConfirmado(String origen, String destino) {
		super(99,origen,destino);
	}

}
