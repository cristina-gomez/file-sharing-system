package mensaje;

import java.io.Serializable;


public class MensajeMandarFicheroUsuario extends Mensaje implements Serializable{
	
    private static final long serialVersionUID = 1L;
	
	private String nombreArchivo;
	private int puerto;
	private int posicion;
	
	public MensajeMandarFicheroUsuario(String origen, String destino, String nombreArchivo, int puerto, int posicion) {
		super(97,origen,destino);
		this.nombreArchivo = nombreArchivo;
		this.puerto=puerto;
		this.posicion=posicion;
	}
	
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	
	public int getPuerto() {
		return puerto;
	}
	
	public int getPosicion() {
		return posicion;
	}

}
