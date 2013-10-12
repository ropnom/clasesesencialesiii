package servidor13;

import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.TCPconection;
import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.fechas;

public class ThreadTCPconection13 implements Runnable {

	TCPconection tcp = null;
	int etapa = 1;

	public ThreadTCPconection13(TCPconection s) {
		this.tcp = s;
	}

	public void run() {
		// modelamos el protocolos en 3 estados
		// estado 0 arranque y espera
		// estado 1 contestacion
		// estado 2 cerrar sockets y cerrar
		boolean encontrado = true;

		while (encontrado) {
			switch (etapa) {

			case 1:
				String mensaje = fechas.EnviarFecha12();
				tcp.Write(mensaje);
				etapa = 2;
				break;

			case 2:
				System.out.println("Cerrar socket server");
				encontrado = false;
				tcp.closecliente();;				
				etapa = 1;				
				break;

			default:
				System.out.println("Error en el proceso, Protocolo reiniciado.");
				encontrado = false;
				etapa = 1;				
				break;
			}
		}
		System.out.println("Thread ha acabado");		

	}

}
