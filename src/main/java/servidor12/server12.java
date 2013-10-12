package servidor12;

import java.util.Scanner;

public class server12 {

	public static void main(String[] args) {

		System.out.println("*******************");
		System.out.println(" Servidor iniciado ");
		System.out.println("*******************");
		ProtocoloHoraServer12 p = new ProtocoloHoraServer12(52000);

		System.out.println("Introduzca el protocolo que desea:");
		System.out.println("--- TCP pulse '0' ");
		System.out.println("--- UDP pulse '1' ");
		Scanner scan = new Scanner(System.in);

		int protocolo = 0;
		try {
			protocolo = scan.nextInt();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (protocolo == 0) {
			System.out.println("-------------------");
			System.out.println(" PROTOCOLO TPC  ");
			System.out.println("-------------------");

			// arrancamos el modo escucha del servidor
			while (p.IniciarTCP() == 0) {
				// esto es unbucle infinito
				// cuando acaba el protocolo
				// el valor retornado cambia a -1
			}
		} else {
			System.out.println("-------------------");
			System.out.println(" PROTOCOLO UDP  ");
			System.out.println("-------------------");
			while (p.IniciarUDP() == 0) {
				// esto es unbucle infinito
				// cuando acaba el protocolo
				// el valor retornado cambia a -1
			}
		}
		System.out.println("*******************");
		System.out.println(" Servidor cerrado ");
		System.out.println("*******************");

	}

}
