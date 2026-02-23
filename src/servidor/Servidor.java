package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cerrojos.*;
import cerrojos.TablaId;
import usuario.*;

public class Servidor {
	
	private static final int maxConcurrentes = 50;
	
    private final int puerto = 4000;
        
    private Buffer productorConsumidor;

	private ServerSocket server;
    private TablaUsuarios u;
	private Cerrojo lock;

	private static TablaId tablaIds;
	private static GestorConexiones gestorEntrada;
    protected Servidor() throws IOException {
		server = new ServerSocket(puerto);
		u = new TablaUsuarios();
		lock = new LockTicket(maxConcurrentes);
		productorConsumidor = new Buffer(maxConcurrentes);
		tablaIds = new TablaId();
	}
    
	protected void run() throws IOException, InterruptedException {  	
		System.out.println("Servidor creado");
		gestorEntrada = new GestorConexiones(maxConcurrentes);
		try {
			while(true) {
				Socket s = server.accept();
				int id = tablaIds.introducirId();
				Thread t = new OyenteCliente(s,u,gestorEntrada, productorConsumidor, id, lock);
				t.start();
	    	}
		}catch(IOException e) {
			server.close();
			System.out.println("Servidor cerrado");
		}
    	
    }

}
