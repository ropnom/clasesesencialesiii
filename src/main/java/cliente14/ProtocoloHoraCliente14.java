package cliente14;

import java.net.InetAddress;
import java.util.Scanner;

import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.TCPconection;
import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.UDPconection;

public class ProtocoloHoraCliente14 {

	TCPconection tcp = null;
	UDPconection udp = null;
	// udp conecction
	int etapa = 0;
	int puerto = 0;
	InetAddress ip = null;

	public ProtocoloHoraCliente14(String sip, int puerto) {

		try {
			this.ip = InetAddress.getByName(sip);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.puerto = puerto;
	}

	public int IniciarTCP() {
		// modelamos el protocolos en 3 estados
		// estado 0 arranque y espera
		// estado 1 contestacion
		// estado 2 cerrar sockets y cerrar

		switch (etapa) {

		case 0:
			System.out.println("Iniciando Cliente TCP");
			tcp = new TCPconection(ip, puerto);
			tcp.ArrancarServer();
			System.out.println("Realizamos peticion de Cliente:");

			boolean encontrado = true;
			int t = -1;
			while (encontrado) {
				System.out.println("--¿Que hora es?---");
				System.out.println("  Pulse 0 para formato:");
				System.out
						.println("  [dia]/[mes]/[año] [hora]:[minutos]:[segundos]");
				System.out.println("  Pulse 1 para formato:");
				System.out
						.println("  [diade la semana] [día del mes] de [mes], [hora]: [minutos]:[segundos].");

				t = -1;
				Scanner sc = new Scanner(System.in);
				try {
					t = sc.nextInt();
				} catch (Exception e) {
					// printamos posibles excepciones
					e.printStackTrace();
				}
				if (t == 0 || t == 1) {
					encontrado = false;
				} else
					encontrado = true;
			}

			System.out.println(t);
			tcp.Write(Integer.toString(t));
			etapa = 1;
			break;

		case 1:
			System.out.println("--Contestacion--");
			System.out.println("Servidor:" + tcp.Read());
			etapa = 2;

			break;

		case 2:
			System.out.println("Cerrar socket cliente");
			tcp.close();
			etapa = 1;
			return (-1);
			// break;

		default:
			System.out.println("Error en el proceso, Protocolo reiniciado.");
			etapa = 1;
			break;
		}

		return (0);
	}

}
