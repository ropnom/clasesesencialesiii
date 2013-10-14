package server15;

import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.TCPconection;

public class ProtocolochinosServer {

	protected TCPconection tcp = null;
	protected int etapa = 0;
	protected int puerto = 0;

	public ProtocolochinosServer(int puerto) {

		this.puerto = puerto;
	}

	public int IniciarJuego() {
		// modelamos el protocolos en 2 estados
		// estado 0 arranque y espera de cliente
		// estado 1 procesado de clientes 2º, 3º ...		
		// Los clientes son procesados dentro de partidas por la clase threadpartidas

		switch (etapa) {

		case 0:
			System.out.println("-- Iniciando Server Juego chinos --");
			tcp = new TCPconection(puerto);
			System.out.println(" Esperando jugadores ... ");
			tcp.ArrancarServer();
			TCPconection copia1 = new TCPconection(tcp);
			//(new Thread((new ThreadPartidas(copia1)))).start();	
			(new ThreadPartidas(copia1)).start();
			ThreadPartidas.Inicializar();
			etapa = 1;
			break;

		case 1:

			System.out.println(" Esperando jugadores ... ");
			try {
				tcp.contestarcliente();
			} catch (Exception e) {
				// printamos posibles excepciones
				e.printStackTrace();
			}

			TCPconection copia2 = new TCPconection(tcp);
			(new Thread((new ThreadPartidas(copia2)))).start();
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
