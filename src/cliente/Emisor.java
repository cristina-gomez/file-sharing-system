package cliente;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public class Emisor extends Thread{
	
	Cliente c;
	private int puerto;
	private String nombreArchivo;
	
	protected Emisor(Cliente c,String nombreArchivo, int puerto) {
		this.c = c;
		this.nombreArchivo = nombreArchivo;
		this.puerto=puerto;
	}
	
	public void run() {
		ServerSocket server = null;
		Socket s = null;
		ObjectOutputStream salida = null;
		try {
			server = new ServerSocket(puerto); 
			s = server.accept();
            String rutaArchivo = System.getProperty("user.dir") + "/datos/" + c.getNombre() + "/" + nombreArchivo;
            File archivo = new File(rutaArchivo);
			byte[] datos = Files.readAllBytes(archivo.toPath());
			salida = new ObjectOutputStream(s.getOutputStream());
			salida.writeObject(archivo.getName());//nombre
			salida.writeObject(datos);//contenido
			salida.flush();
			System.out.println("Archivo enviado: " + archivo.getName());
			salida.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
		    try {
		        if (salida != null) salida.close();
		        if (s != null) s.close();
		        if (server != null) server.close(); 
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	}
	
	public int getPuerto() {
		return puerto;
	}
	
	
}
