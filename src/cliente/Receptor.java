package cliente;

import java.io.*;
import java.net.Socket;
import java.nio.file.*;

import cerrojos.*;

public class Receptor extends Thread {

    private Cliente c;
    private int puerto;

    public Receptor(Cliente c, int puerto) {
        this.c = c;
        this.puerto=puerto;
    }

    public void run() {
        Socket socket = null;
        ObjectInputStream entrada = null;
        FileOutputStream salida = null;
        String nombreCliente = c.getNombre();
        try {
			socket = new Socket("localhost", puerto);
            System.out.println("Receptor esperando imagen en puerto " + puerto);
            entrada = new ObjectInputStream(socket.getInputStream());

            String nombreArchivo = (String) entrada.readObject();
            byte[] contenidoImagen = (byte[]) entrada.readObject(); 

            Path rutaDestino = Paths.get(System.getProperty("user.dir"), "datos", nombreCliente, nombreArchivo);
            Files.createDirectories(rutaDestino.getParent()); 

            try {
                salida = new FileOutputStream(rutaDestino.toFile());
                salida.write(contenidoImagen);
                salida.flush();
                System.out.println("Imagen recibida y guardada en: " + rutaDestino);
                c.imagenDescargadaCorrectamente();
                c.mensajeAnadirArchivo(nombreArchivo);

            }
            catch (Exception e) {
            e.printStackTrace();
            } finally {
            try {
                if (salida != null) salida.close();
                if (entrada != null) entrada.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
      
        }
      } catch (IOException | ClassNotFoundException e1) {
	}finally {}
    }

}
