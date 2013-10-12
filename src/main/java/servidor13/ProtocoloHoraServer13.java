package servidor13;

import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.TCPconection;

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
			//arancamos el puerto de escucha y a la 1º llegada lo enviamos al thread
			System.out.println("Iniciando Servidor TCP");
			tcp = new TCPconection(puerto);
			System.out.println(" Esperando clientes ... ");
			tcp.ArrancarServer();
			//utilizamos un contructor copia para el tcp.
			TCPconection copia1 = new TCPconection(tcp);	
			//TCPconection copia1 = tcp;
			(new Thread((new ThreadTCPconection13(copia1)))).start();
			etapa = 1;			
			break;		

		case 1:
			//seguimos esperando los clientes posibles
			System.out.println(" Esperando clientes ... ");
			try{
				tcp.contestarcliente();
			}catch (Exception e) {
				// printamos posibles excepciones
				e.printStackTrace();
			}
			TCPconection copia2 = new TCPconection(tcp);			
			//TCPconection copia2 = tcp;
			(new Thread((new ThreadTCPconection13(copia2)))).start();
			etapa = 1;
			break;

		default:
			System.out.println("Error en el proceso, Protocolo reiniciado.");
			etapa = 0;
			break;
		}

		return (0);
	}

}
