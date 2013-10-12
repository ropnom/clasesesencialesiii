package server14;

import servidor13.ThreadTCPconection13;
import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.TCPconection;

public class ProtocoloHoraServer14 {

	TCPconection tcp = null;
	int etapa = 0;
	int puerto = 0;

	public ProtocoloHoraServer14(int puerto) {

		this.puerto = puerto;
	}

	protected int IniciarTCP() {
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
			TCPconection copia1 = new TCPconection(tcp);
			(new Thread((new ThreadTCPconection14(copia1)))).start();
			etapa = 1;
			break;

		case 1:
			System.out.println(" Esperando clientes ... ");
			try {
				tcp.contestarcliente();
			} catch (Exception e) {
				// printamos posibles excepciones
				e.printStackTrace();
			}
			TCPconection copia2 = new TCPconection(tcp);
			(new Thread((new ThreadTCPconection13(copia2)))).start();
			etapa = 1;
			break;

		default:
			System.out.println("Error en el proceso, Protocolo reiniciado.");
			etapa = 1;
			break;
		}

		return (0);
	}

}
