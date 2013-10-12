package server15;

public class servidor15 {

	public static void main(String[] args) {
		
		// el protocolo de chinos atiende a los clientes entrantes
		ProtocolochinosServer server = new ProtocolochinosServer(52000);
		
		while(server.IniciarJuego() == 0)
		{
			//bucle infinito del server
			// se atienden a clientes entrantes
		}
		
	}

}
