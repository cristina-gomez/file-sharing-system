package cliente;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import cerrojos.*;
import mensaje.*;
import gui.*;

public class Cliente{
	
	private final String nombreServidor = "localhost";
	private final int IPServidor = 4000;

    private final WindowTracker tracker;
	private String nombre;
    private VentanaInicio vInicio;
    private VentanaElegirOpcion vElegirOpcion;
    private VentanaDescargar vDescargar; 
    private VentanaConsultarInformacion vConsultar;
    private Socket socket;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private Thread oyenteServ;
    private Cerrojo cerrojo;
    
    public Cliente() throws UnknownHostException, IOException {
		socket = new Socket(nombreServidor, IPServidor);
		entrada = new ObjectInputStream(socket.getInputStream());
		salida = new ObjectOutputStream(socket.getOutputStream());
		cerrojo = new LockRompeEmpate(2);
		oyenteServ = new OyenteServidor(this,cerrojo);
		tracker = new WindowTracker();
    }
    
    public void run() {
    	mostrarInicio();
    	oyenteServ.start();    	
    }
	
	public void mostrarInicio() {
		nombre=null;
		vInicio = new VentanaInicio(this);
		vInicio.setVisible(true);
	}
	
	public void mostrarElegirOpcionDesdeInicio() throws IOException {
		vElegirOpcion = new VentanaElegirOpcion(this);
		vElegirOpcion.setLocation(tracker.getLastPosition("inicio"));
		vElegirOpcion.setVisible(true);
	}
	
	public void mostrarElegirOpcionDesdeConsultar() throws IOException {
		vElegirOpcion = new VentanaElegirOpcion(this);
		vElegirOpcion.setLocation(tracker.getLastPosition("consultar"));
		vElegirOpcion.setVisible(true);
	}
	
	public void mostrarElegirOpcionDesdeDescargar() throws IOException {
		vElegirOpcion = new VentanaElegirOpcion(this);
		vElegirOpcion.setLocation(tracker.getLastPosition("descargar"));
		vElegirOpcion.setVisible(true);
	}
	
	public void mostrarDescargar() {
		vDescargar = new VentanaDescargar(this);
		vDescargar.setLocation(tracker.getLastPosition("elegir"));
		vDescargar.setVisible(true);
	}
	
	public void mostrarConsultarInformacion(List<String> usuarios, List<List<String>> contenido) {
		vConsultar = new VentanaConsultarInformacion(this, usuarios, contenido);
		vConsultar.setLocation(tracker.getLastPosition("elegir"));
		vConsultar.setVisible(true);
	}
	
	
	public void comprobarNombreUsuario(String s) throws IOException {
    	String path = Paths.get(System.getProperty("user.dir"), "datos", s).toString();
    	File f = new File(path);
    	if(!f.exists())f.mkdirs();
	}
	public Boolean comprobarNombreFichero(String s) {
    	String path = Paths.get(System.getProperty("user.dir"), "datos", nombre, s).toString();
    	File f = new File(path);
    	if(!f.exists()) return false;
    	else return true;
	}
	
	public void setNombre(String s) throws IOException {
		this.nombre = s;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public ObjectOutputStream getOutputStream() {
		return salida;
	}
	
	public ObjectInputStream getInputStream() {
		return entrada;
	}
	
	public void mensajeConsultarInformacion() throws IOException {
		cerrojo.takeLock(0);
		salida.writeObject(new MensajeConsultarInformacion(nombre,"server"));
		salida.flush();
		cerrojo.releaseLock(0);
	}
	
	public void mensajeDescargarFichero(String nombreFichero) throws IOException {
		cerrojo.takeLock(0);
		salida.writeObject(new MensajeDescargarFichero(nombre, "server", nombreFichero));
		salida.flush();
		cerrojo.releaseLock(0);
	}
	
	public void mensajeCerrar() throws IOException, InterruptedException {
		cerrojo.takeLock(0);
		salida.writeObject(new MensajeSalida(nombre,"server"));
		salida.flush();
		cerrojo.releaseLock(0);
		oyenteServ.join();
		socket.close();
	}
	
	public void mensajeAnadirArchivo(String nombreArchivo) throws IOException {
		cerrojo.takeLock(0);
		salida.writeObject(new MensajeAnadirArchivo(nombre,"server",nombreArchivo));
		salida.flush();
		cerrojo.releaseLock(0);
	}
	
	public void mensajeInicioCliente() throws IOException {
		String carpetaRuta = Paths.get(System.getProperty("user.dir"), "datos", nombre).toString();
		List<String> nombresArchivos = new ArrayList<>();
		File carpeta = new File(carpetaRuta);
		File[] archivos = carpeta.listFiles();
		if (archivos != null) {
		    for (File archivo : archivos) {
		        if (archivo.isFile()&&!archivo.getName().equals(".DS_Store")) {
		            nombresArchivos.add(archivo.getName());
		        }
		    }
		}

		cerrojo.takeLock(0);
		salida.writeObject(new MensajeInicioCliente(nombre, "server", nombresArchivos));
		salida.flush();
		cerrojo.releaseLock(0);
	}
	
	public void usuarioYaPoseeArchivo() {
		vDescargar.usuarioYaPoseeArchivo();
	}
	
	public void ficheroNoExistente() {
		vDescargar.ficheroNoExistente();
	}
	
	public void imagenDescargadaCorrectamente() {
		vDescargar.imagenDescargadaCorrectamente();
	}
	
	public WindowTracker getTracker() {
		return tracker;
	}
	


}
