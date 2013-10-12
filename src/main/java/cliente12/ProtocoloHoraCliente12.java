package cliente12;

import java.net.InetAddress;

import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.TCPconection;
import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.UDPconection;

public class ProtocoloHoraCliente12 {

	protected TCPconection tcp = null;
	protected UDPconection udp = null;
	// udp conecction
	protected int etapa = 0;
	protected int puerto = 0;
	protected InetAddress ip = null;
	protected int intentos = 0;

	public ProtocoloHoraCliente12(String sip, int puerto) {

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
			try {
				tcp = new TCPconection(ip, puerto);
				tcp.ArrancarServer();
				System.out.println("Realizamos peticion de Cliente:");
				String t = "--¿Que hora es?---";
				System.out.println(t);
				tcp.Write(t);
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
			// break;

		default:
			System.out.println("Error en el proceso, Protocolo reiniciado.");
			etapa = 0;			
			break;
		}

		return (0);
	}

	public int IniciarUDP() {
		// modelamos el protocolos en 3 estados
		// estado 0 arranque y espera
		// estado 1 contestacion
		// estado 2 cerrar sockets y cerrar

		switch (etapa) {

		case 0:
			System.out.println("Iniciando Cliente UDP");
			udp = new UDPconection(ip, puerto + 1);
			udp.ArrancarCliente();
			System.out.println("Realizamos peticion de Cliente:");
			String t = "--¿Que hora es?---";
			System.out.println(t);
			udp.writeLineClient(t);
			etapa = 1;
			break;

		case 1:
			System.out.println("--Contestacion--");
			System.out.println("Servidor:" + udp.readLine());
			etapa = 2;

			break;

		case 2:
			System.out.println("Cerrar socket cliente");
			udp.close();
			etapa = 1;
			return (-1);
			// break;

		default:
			System.out.println("Error en el proceso, intento :" + intentos
					+ " ...Protocolo reiniciado.");
			etapa = 0;
			intentos++;
			if (intentos == 2) {
				System.out.println("Cancelacion de conexion");
				return (-1);
			}
			break;
		}

		return (0);
	}

}
