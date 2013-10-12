package cliente13;

import java.net.InetAddress;

import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.TCPconection;
import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.UDPconection;

public class ProtocoloHoraCliente13 {

	TCPconection tcp = null;
	UDPconection udp = null;
	// udp conecction
	int etapa = 0;
	int puerto = 0;
	InetAddress ip = null;

	public ProtocoloHoraCliente13(String sip, int puerto) {

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
			String t = "--¿Que hora es?---";
			System.out.println(t);
			tcp.Write(t);
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
