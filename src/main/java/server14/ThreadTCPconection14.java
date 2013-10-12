package server14;

import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.TCPconection;
import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.fechas;


public class ThreadTCPconection14 implements Runnable {

	TCPconection tcp = null;
	int etapa = 0;
	int a = -1;

	public ThreadTCPconection14(TCPconection s) {
		this.tcp = s;
	}

	public void run() {
		// modelamos el protocolos en 3 estados
		// estado 0 arranque y espera
		// estado 1 contestacion
		// estado 2 cerrar sockets y cerrar

		switch (etapa) {

		case 0:
			String mensaje = tcp.Read();
			System.out.println("Cliente envia: " + mensaje);
			try{
			a = Integer.parseInt(mensaje);
			}catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Error en la recepcion del mensaje");
				System.out.println("Contestacion Default '-1' ");
			}		
			etapa = 1;
			break;
			
		case 1:
			String linea = fechas.EnviarFechas14(a);
			tcp.Write(linea);
			etapa = 2;
			break;

		case 2:
			System.out.println("Cerrar socket server");
			tcp.closecliente();
			etapa = 1;

			// break;

		default:
			System.out.println("Error en el proceso, Protocolo reiniciado.");
			etapa = 1;
			break;
		}

	}

}
