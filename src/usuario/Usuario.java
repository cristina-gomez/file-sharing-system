package usuario;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Usuario {
	
	private String nombre;
	private List<String> fotos;
	
	public Usuario(String nombre, List<String> archivos) {
	    this.nombre = nombre;
	    this.fotos = archivos;
	}
		
	public String getNombre() {
		return nombre;
	}

	public List<String> getFotos() {
		return fotos;
	}
	
	public void actualizarFotos(String ruta) throws IOException {
	    fotos.clear();
	    File carpeta = new File(ruta);
	    if (carpeta.exists() && carpeta.isDirectory()) {
	        File[] archivos = carpeta.listFiles();
	        if (archivos != null) {
	            for (File archivo : archivos) {
	                if (archivo.isFile()) {
	                    fotos.add(archivo.getName());
	                }
	            }
	        }
	    }
	}
	
	public boolean tieneFoto(String nombreArchivo) {
	    return fotos.contains(nombreArchivo);
	}
	
	public void anadirFoto(String nombreArchivo) {
	    if (!fotos.contains(nombreArchivo)) {
	        fotos.add(nombreArchivo);
	    }
	}
	
	public void eliminarFoto(String nombreArchivo) {
	    fotos.remove(nombreArchivo);
	}
}