package mensaje;

import java.io.Serializable;
import java.util.List;

public class MensajeConsultarInformacionConfirmado extends Mensaje implements Serializable {
	
    private static final long serialVersionUID = 1L;

	private List<String> usuarios;
	private List<List<String>> contenido;

	public MensajeConsultarInformacionConfirmado(String origen, String destino, List<String> usuarios, List<List<String>> contenido) {
		super(98,origen,destino);
		this.usuarios=usuarios;
		this.contenido=contenido;
	}
	
	public List<String> getUsuarios(){
		return usuarios;
	}
	
	public List<List<String>> getContenido(){
		return contenido;
	}
}
