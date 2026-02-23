package mensaje;

import java.io.Serializable;

public class MensajeAnadirArchivo extends Mensaje implements Serializable{
	
    private static final long serialVersionUID = 1L;
    private String nombreArchivo;

    public MensajeAnadirArchivo(String origen, String destino, String nombreArchivo) {
    	super(5,origen,destino);
    	this.nombreArchivo=nombreArchivo;
    }
    
    public String getNombreArchivo() {
    	return nombreArchivo;
    }
}
