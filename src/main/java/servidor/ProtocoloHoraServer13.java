package servidor;

import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.TCPconection;
import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.ThreadTCPconection;

public class ProtocoloHoraServer13 {

	TCPconection tcp = null;
	int etapa = 0;
	int puerto = 0;

	public ProtocoloHoraServer13(int puerto) {

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
			etapa = 1;			
			break;		

		case 1:
			System.out.println(" Esperando clientes ... ");
			tcp.ArrancarServer();
			TCPconection copia = tcp;			
			(new Thread((new ThreadTCPconection(copia)))).start();
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
