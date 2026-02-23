package mensaje;

import java.io.Serializable;

public class MensajeDescargarFichero extends Mensaje implements Serializable{
	
    private static final long serialVersionUID = 1L;

	private String infoDescargar;

	public MensajeDescargarFichero(String origen, String destino, String infoDescargar) {
		super(2,origen,destino);
		this.infoDescargar=infoDescargar;
	}
	
	public String getInfoDescargar() {
		return infoDescargar;
	}
}
