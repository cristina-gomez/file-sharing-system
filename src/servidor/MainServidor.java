package servidor;

import java.io.IOException;

public class MainServidor {
	public static void main(String[] args) throws IOException, InterruptedException {
		Servidor s = new Servidor();
		s.run();
	}
}