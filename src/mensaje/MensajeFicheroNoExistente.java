package mensaje;

import java.io.Serializable;

public class MensajeFicheroNoExistente extends Mensaje implements Serializable{

    private static final long serialVersionUID = 1L;
    
	public MensajeFicheroNoExistente(String origen, String destino) {
		super(95, origen, destino);
	}

}
