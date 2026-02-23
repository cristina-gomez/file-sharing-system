package usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TablaUsuarios {
	private Monitor monitor;
	private HashMap<String,Usuario> tabla;
	
	public TablaUsuarios() {
		this.tabla = new HashMap<String,Usuario>();
		this.monitor = new Monitor();
	}
	
	public void nuevoUsuario(Usuario u) throws InterruptedException {
		monitor.requestWrite();
		tabla.put(u.getNombre(), u);
		monitor.releaseWrite();
	}
	
    public void eliminarUsuario(String u) throws InterruptedException {
    	monitor.requestWrite();
    	tabla.remove(u);
    	monitor.releaseWrite();
    }
    
    public List<String> getUsuarios() throws InterruptedException{
    	monitor.requestRead();
		List<String> userList = new ArrayList<String>();
		for (Usuario u : tabla.values()) {
			userList.add(u.getNombre());
		}
		monitor.releaseRead();
		return userList;
	}
    
    public List<List<String>> getFicheros() throws InterruptedException {
    	monitor.requestRead();
		List<List<String>> usuarios = new ArrayList<List<String>>();
		for (Usuario u : tabla.values()) {
			List<String> ficheros = new ArrayList<String>();
			for(String foto : u.getFotos()) {
				ficheros.add(foto);
			}
			usuarios.add(ficheros);
		}
		monitor.releaseRead();
		return usuarios;
	}
    
	public String getUsuarioDeFichero(String fichero) throws InterruptedException {
		monitor.requestRead();
		String usuario = null;
		for(Usuario u : tabla.values()) {
			for(String foto : u.getFotos()) {
				if(fichero.equals(foto)) {
					usuario = u.getNombre();
					break;
				}
			}
			if(usuario != null) {
				break;
			}
		}
		monitor.releaseRead();
		return usuario;
	}
	
	public void anadirArchivo(String nombreUsuario, String nombreArchivo) throws InterruptedException{
	    monitor.requestWrite();
	    try {
	        Usuario usuario = tabla.get(nombreUsuario);
	        if (usuario != null) {
	            usuario.anadirFoto(nombreArchivo);
	        }
	    } finally {
	        monitor.releaseWrite();
	    }
	}
	
	public Boolean tieneFichero(String nombre, String fichero) throws InterruptedException {
		monitor.requestRead();
		Usuario u = tabla.get(nombre);
		List<String> fotos = u.getFotos();
		for(String s : fotos) {
			if(s.equals(fichero)) {
				monitor.releaseRead();
				return true;
			}
		}
		monitor.releaseRead();
		return false;
	}

}
