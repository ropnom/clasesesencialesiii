package servidor12;

import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.TCPconection;
import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.UDPconection;

public class ProtocoloHoraServer12 {

	TCPconection tcp = null;
	UDPconection udp = null;
	// udp conecction
	int etapa = 0;
	int puerto = 0;

	public ProtocoloHoraServer12(int puerto) {

		this.puerto = puerto;
	}

	public int IniciarTCP() {
		// modelamos el protocolos en 3 estados
		// estado 0 arranque y espera
		// estado 1 contestacion
		// estado 2 cerrar sockets y cerrar

		switch (etapa) {

		case 0:
			System.out.println("Iniciando Servidor TCP");
			tcp = new TCPconection(puerto);
			System.out.println(" Esperando clientes ... ");
			tcp.ArrancarServer();
			etapa = 1;
			break;

		case 1:			
			String mensaje = fechas.EnviarFecha12();
			tcp.Write(mensaje);			
			etapa = 2;
			break;

		case 2:
			System.out.println("Cerrar socket server");
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
	
	public int IniciarUDP() {
		// modelamos el protocolos en 3 estados
		// estado 0 arranque y espera
		// estado 1 contestacion
		// estado 2 cerrar sockets y cerrar

		switch (etapa) {

		case 0:
			System.out.println("Iniciando Servidor UDP");
			udp = new UDPconection(puerto+1);
			System.out.println(" Esperando clientes ... ");
			udp.ArrancarServer();
			etapa = 1;
			break;

		case 1:			
			String mensaje = fechas.EnviarFecha12();
			udp.writeLineServer(mensaje);			
			etapa = 2;
			break;

		case 2:
			System.out.println("Cerrar socket server");
			udp.close();
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
