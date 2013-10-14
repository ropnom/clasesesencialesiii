package cliente15;

import java.net.InetAddress;
import java.util.Scanner;

import server15.gamer;
import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.TCPconection;

public class Jugador extends gamer {

	protected TCPconection tcp;
	protected int etapa = 0;
	protected int puerto = 52000;
	protected InetAddress ip;
	protected int intentos = 0;

	public Jugador(String identificador) {
		super(identificador);
		try {
			ip = InetAddress.getByName("localhost");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void PLAY() {
		String message = "PLAY:" + identificador;
		this.Write(message);
	}

	protected void Mybet(int bajomano, int total) {
		String message = "MY BET:" + bajomano + ":" + total;
		this.Write(message);
	}

	protected void Write(String message) {
		System.out.println("Cliente --> " + message);
		tcp.Write(message);
	}

	public int Jugar() {
		// modelamos el protocolos en 3 estados
		// estado 0 arranque y espera
		// estado 1 contestacion
		// estado 2 cerrar sockets y cerrar

		switch (etapa) {

		case 0:
			System.out.println("-- Iniciando Cliente Juego de Chinos --");
			try {
				tcp = new TCPconection(ip, puerto);
				System.out.println("Realizamos peticion de Cliente:");
				this.PLAY();
				etapa = 1;
			} catch (Exception e) {
				// printamos lasposibles excepciones
				e.printStackTrace();
				System.out.println(" Error en la conexion -- FAIL ");
				intentos++;
				if (intentos == 2) {
					System.out.println("Cancelacion de conexion");
					return (-1);
				}
			}
			break;

		case 1:
			System.out.println("--Contestacion--");
			System.out.println("Aqui llega cliente 2");
			String recibido = tcp.Read();
			try {
				int b = Integer.parseInt(recibido);
				if (b == -1) {
					System.out
							.println("Error de protocolo actulice la version del cliente");
					etapa = 6;
				} else if (b == -2) {
					System.out
							.println("El servidor esta lleno intentalo mas tarde");
					etapa = 6;
				} else if (b == -3) {
					System.out.println("Error de servidor reenviamos paquete");
					this.PLAY();
					etapa = 1;
				}
			} catch (Exception e) {
				System.out.println("Servidor:" + recibido);

				if (recibido.equals("WAIT")) {
					etapa = 2;
				} else {
					recibido = tcp.Read();
					System.out.println("Servidor:" + recibido);
					etapa = 4;
				}
			}

			break;

		case 2:
			String mensaje = tcp.Read();
			if (mensaje!=null) {
				System.out.println("Servidor:" + mensaje);
				String yourbet = "YOUR BET";
				if (mensaje.equals(yourbet)) {
					etapa = 3;
				} else {
					etapa = 2;
				}
			}

			break;

		case 3:
			System.out.println("-- Elige tu estrategia --");
			System.out.println("Introduce el numero de monas de tu mano:");
			int bajomano = -1;
			while (bajomano == -1)
				try {
					Scanner k = new Scanner(System.in);
					bajomano = k.nextInt();
					if (bajomano < 0 || bajomano > 30) {
						throw new Exception(
								"El valor introducido no es valido [0-30]");
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(" Vuelva a introducir el valor elegido");
					bajomano = -1;
				}
			int total = -1;
			while (total == -1)
				try {
					Scanner n = new Scanner(System.in);
					bajomano = n.nextInt();
					if (total < 0 || total > 60) {
						throw new Exception(
								"El valor introducido no es valido [0-60]");
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(" Vuelva a introducir el valor elegido");
					total = -1;
				}
			Mybet(bajomano, total);
			String mensaje2 = tcp.Read();
			System.out.println("Servidor:" + mensaje2);
			etapa = 5;
			break;

		case 4:
			String mensaje3 = tcp.Read();
			if (mensaje3 !=null) {
				System.out.println("Servidor:" + mensaje3);
				if (mensaje3.equals("YOUR BET")) {
					etapa = 3;
				} else {
					etapa = 4;
				}
			}

			break;

		case 5:
			String mensaje4 = tcp.Read();
			if (mensaje4 != null) {
				System.out.println("Servidor:" + mensaje4);
				if (mensaje4.contains("WINNER") || mensaje4.contains("NONE")) {
					etapa = 6;
				} else {
					etapa = 5;
				}
			}
			break;

		case 6:
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
