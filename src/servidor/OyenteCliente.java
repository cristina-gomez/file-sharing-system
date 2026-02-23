package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

import cerrojos.*;
import mensaje.*;
import usuario.*;
 

public class OyenteCliente extends Thread{
	
	private Socket s = null;
	
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	private TablaUsuarios tabla;
	private Cerrojo cerrojo;
	private GestorConexiones gestor;
	private Buffer productorConsumidor;
	private int id;


	protected OyenteCliente(Socket s, TablaUsuarios tabla, GestorConexiones gestor, Buffer productorConsumidor, int id, Cerrojo cerrojo) {
		this.s = s;
		this.tabla = tabla;
		this.cerrojo = cerrojo;
		this.gestor = gestor;
		this.productorConsumidor=productorConsumidor;
		this.id=id;

    }
	
	@Override
	public void run() {
		try {
			this.salida = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.entrada = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean end = false;
		while (!end) {
			Mensaje m;
			try {
				m = (Mensaje) entrada.readObject();
				switch(m.getTipo()) {
				case 0: //Mensaje de inicio de conexion de cliente.
					MensajeInicioCliente mInicio = (MensajeInicioCliente) m;
					Usuario u = new Usuario(mInicio.getOrigen(), mInicio.getArchivos());
					tabla.nuevoUsuario(u);
                    gestor.registrar(u.getNombre(), new ConexionCliente(salida, entrada), id);
		   			cerrojo.takeLock(id);
		   			salida.writeObject(new MensajeInicioClienteConfirmado("server", mInicio.getOrigen()));
					salida.flush();
					cerrojo.releaseLock(id);
					break;
		  	
		   		case 1: //Mensaje de consultar informacion
		   			cerrojo.takeLock(id); 
		   			salida.writeObject(new MensajeConsultarInformacionConfirmado("server", m.getOrigen(), tabla.getUsuarios(), tabla.getFicheros()));
		   			salida.flush();
		   			cerrojo.releaseLock(id); 
			  		break;
			  		
		   		case 2: // mensaje de descargar fichero
		   		    MensajeDescargarFichero mDescargar = (MensajeDescargarFichero) m;
		   		    String archivoDescargar = mDescargar.getInfoDescargar();
		   		    String nombrePropietario = tabla.getUsuarioDeFichero(archivoDescargar);
		   		    if (nombrePropietario != null) {
		   		        if (nombrePropietario.equals(mDescargar.getOrigen())||tabla.tieneFichero(mDescargar.getOrigen(), archivoDescargar)) {
		   		            cerrojo.takeLock(id);
		   		            salida.writeObject(new MensajeUsuarioYaPoseeArchivo("server", nombrePropietario));
		   		            salida.flush();
		   		            cerrojo.releaseLock(id);
		   		        } else {
		   		            ObjectOutputStream salida2 = gestor.getConexion(nombrePropietario, id).getSalida();
		   		            cerrojo.takeLock(id);
		   		            int numPuerto=generarPuertoAleatorio();
		   		            int posicion = productorConsumidor.almacenar(numPuerto);
		   		            salida2.writeObject(new MensajeMandarFicheroUsuario(mDescargar.getOrigen(), nombrePropietario, archivoDescargar, numPuerto, posicion));
		   		            salida2.flush();
		   		            cerrojo.releaseLock(id);
		   		        }
		   		    } else {
		   		        cerrojo.takeLock(id);
		   		        salida.writeObject(new MensajeFicheroNoExistente("server", mDescargar.getOrigen()));
		   		        salida.flush();
		   		        cerrojo.releaseLock(id);
		   		    }
		   		    break;

		   		case 3: //mensaje de salida
		   			MensajeSalida mSalida = (MensajeSalida) m;
		   			String nombreUsuario = mSalida.getOrigen();
		   			tabla.eliminarUsuario(nombreUsuario);
		   			gestor.eliminar(nombreUsuario,id );
		   			cerrojo.takeLock(id); 
		   			salida.writeObject(new MensajeSalidaConfirmado("server", mSalida.getOrigen()));
		   			salida.flush();
		   			cerrojo.releaseLock(id); 
		   			end = true;
			  		break;
		  		
		   		case 4: //mensaje emisor listo
		   			MensajeEmisorListo mEmisorListo = (MensajeEmisorListo) m;
		   			ObjectOutputStream salida2 = gestor.getConexion(mEmisorListo.getDestino(), id).getSalida();
		   			int puerto = productorConsumidor.extraer(mEmisorListo.getPosicion());
		   			cerrojo.takeLock(id);
		   			salida2.writeObject(new MensajeServidorListo(mEmisorListo.getOrigen(), mEmisorListo.getDestino(), puerto));
		   			salida2.flush();
		   			cerrojo.releaseLock(id);
		   			break;
		   		case 5: //mensaje anadir archivo
		   			MensajeAnadirArchivo mActualizar = (MensajeAnadirArchivo) m;
		   			tabla.anadirArchivo(mActualizar.getOrigen(), mActualizar.getNombreArchivo());	

		   		default:
		   			break;
				}
			} catch (ClassNotFoundException | IOException | InterruptedException e) {
				e.printStackTrace();
			}
	
		}
	}
	public static int generarPuertoAleatorio() {
        Random random = new Random();
        int puertoAleatorio = random.nextInt(65535 - 49152 + 1) + 49152;
        return puertoAleatorio;
    }
}