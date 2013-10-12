package server14;


public class server14 {

	public static void main(String[] args) {

		System.out.println("*******************");
		System.out.println(" Servidor iniciado ");
		System.out.println("*******************");
		ProtocoloHoraServer14 p = new ProtocoloHoraServer14(52000);

		System.out.println("Introduzca el protocolo que desea:");

		System.out.println("----------------------------");
		System.out.println(" PROTOCOLO TPC CONCURRENTE ");
		System.out.println("----------------------------");

		// arrancamos el modo escucha del servidor
		while (p.IniciarTCP() == 0) {
			// esto es unbucle infinito
			// cuando acaba el protocolo
			// el valor retornado cambia a -1
		}

		System.out.println("*******************");
		System.out.println(" Servidor cerrado ");
		System.out.println("*******************");

	}

}
