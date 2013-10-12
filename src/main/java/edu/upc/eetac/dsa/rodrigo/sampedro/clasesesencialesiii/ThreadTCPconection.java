package edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii;

import servidor.fechas;

public class ThreadTCPconection implements Runnable {

	TCPconection tcp = null;
	int etapa = 1;

	public ThreadTCPconection(TCPconection s) {
		this.tcp = s;
	}

	public void run() {
		// modelamos el protocolos en 3 estados
		// estado 0 arranque y espera
		// estado 1 contestacion
		// estado 2 cerrar sockets y cerrar

		switch (etapa) {

		case 1:
			String mensaje = fechas.EnviarFecha12();
			tcp.Write(mensaje);
			etapa = 2;
			break;

		case 2:
			System.out.println("Cerrar socket server");
			tcp.close();
			etapa = 1;

			// break;

		default:
			System.out.println("Error en el proceso, Protocolo reiniciado.");
			etapa = 1;
			break;
		}

	}

}
