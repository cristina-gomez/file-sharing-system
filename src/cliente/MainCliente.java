package cliente;

import java.io.IOException;
import java.net.UnknownHostException;

public class MainCliente {

	public static void main(String[] args) throws UnknownHostException, IOException {
		try {
			Cliente c = new Cliente();
			c.run();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
