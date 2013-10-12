package cliente14;

import java.util.Scanner;

import cliente12.ProtocoloHoraCliente12;
import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.TCPconection;

public class ProtocoloHoraCliente14 extends ProtocoloHoraCliente12{

	public ProtocoloHoraCliente14(String sip, int puerto) {
		super(sip, puerto);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int IniciarTCP() {
		// modelamos el protocolos en 3 estados
		// estado 0 arranque y espera
		// estado 1 contestacion
		// estado 2 cerrar sockets y cerrar

		switch (etapa) {

		case 0:
			System.out.println("Iniciando Cliente TCP");
			try {
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
			//enviamos el codigo elegido
			System.out.println(t);
			tcp.Write(Integer.toString(t));
			etapa = 1;
			} catch (Exception e) {
				// printamos lasposibles excepciones
				e.printStackTrace();
				System.out.println(" Error en arranque del Socket -- FAIL ");
				intentos++;
				if (intentos == 2) {
					System.out.println("Cancelacion de conexion");
					return (-1);
				}				
			}
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
			//break;

		default:
			System.out.println("Error en el proceso, Protocolo reiniciado.");
			etapa = 1;
			break;
		}

		return (0);
	}

}
