package servidor;

import java.util.HashMap;

import cerrojos.*;

public class GestorConexiones {
    
    private HashMap<String, ConexionCliente> conexiones;
    private LockBakery cerrojo;

    public GestorConexiones(int maxHilos) {
        conexiones = new HashMap<>();
        cerrojo = new LockBakery(maxHilos); 
    }

    public ConexionCliente getConexion(String nombre, int id) {
        cerrojo.takeLock(id);  
        try {
            return conexiones.get(nombre);
        } finally {
            cerrojo.releaseLock(id);
        }
    }
    
    public void registrar(String nombre, ConexionCliente conexion, int id) {
    	cerrojo.takeLock(id);
        conexiones.put(nombre, conexion);
        cerrojo.releaseLock(id);
    }


    public void eliminar(String nombre, int id) {
        cerrojo.takeLock(id); 
        try {
            conexiones.remove(nombre);
        } finally {
            cerrojo.releaseLock(id);  
        }
    }
}
