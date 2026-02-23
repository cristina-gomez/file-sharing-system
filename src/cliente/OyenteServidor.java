package cliente;

import mensaje.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cerrojos.Cerrojo;
import mensaje.Mensaje;

public class OyenteServidor extends Thread {
    	
	private Cliente c;
	private Cerrojo cerrojo;
	private ObjectOutputStream salida;
	private ObjectInputStream entrada;
	
	public OyenteServidor(Cliente c, Cerrojo cerrojo) {
		this.c=c;
		this.cerrojo=cerrojo;
		this.salida = c.getOutputStream();
		this.entrada = c.getInputStream();
	}

	@Override
	public void run(){
		try {
			boolean end = false;
			while(!end) {
				Mensaje m = (Mensaje) entrada.readObject();
				switch(m.getTipo()) {
				  	case 99: //confirmacion de inicio
				  		cerrojo.takeLock(1);		
				  		System.out.println(c.getNombre()+" conectado");
				  		c.mostrarElegirOpcionDesdeInicio();
				  		cerrojo.releaseLock(1);
						break;
				  	
				  	case 98: //confirmacion consultar informacion
				  		cerrojo.takeLock(1);		
				  		MensajeConsultarInformacionConfirmado mInfo = (MensajeConsultarInformacionConfirmado) m;
				  		c.mostrarConsultarInformacion(mInfo.getUsuarios(), mInfo.getContenido());
				  		cerrojo.releaseLock(1);
				  		break;
				  		
				  	case 97: //mensaje mandar fichero usuario
				  		MensajeMandarFicheroUsuario mFicheroU = (MensajeMandarFicheroUsuario) m;
				  		Thread emisor = new Emisor(c, mFicheroU.getNombreArchivo(), mFicheroU.getPuerto());
				  		emisor.start();
				  		cerrojo.takeLock(1);
				  		salida.writeObject(new MensajeEmisorListo(c.getNombre(), mFicheroU.getOrigen(), mFicheroU.getPosicion()));
				  		salida.flush();
					  	cerrojo.releaseLock(1);
				  		break;
				  		
				  	case 96: //mensaje usuario ya posee archivo
				  		c.usuarioYaPoseeArchivo();
				  		break;
				  		
				  	case 95: //mensaje fichero no existente
				  		c.ficheroNoExistente();				  		
				  		break; 
				  		
				  	case 94: //mensaje salida confirmado
				  		end = true;
				  		break;
	
				  	case 93: //mensaje servidor listo
				  		MensajeServidorListo mServ = (MensajeServidorListo) m;
				  	    Thread receptor = new Receptor(c, mServ.getPuerto());
				  	    receptor.start();
				  	    break;
				default:
					break;
				  
			  }	
			}
		}
		catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
