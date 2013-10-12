package server14;

import servidor13.ThreadTCPconection13;
import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.TCPconection;
import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.fechas;

public class ThreadTCPconection14 extends ThreadTCPconection13 {

	protected int a = -1;

	public ThreadTCPconection14(TCPconection s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// modelamos el protocolos en 3 estados
		// estado 0 arranque y espera
		// estado 1 contestacion
		// estado 2 cerrar sockets y cerrar
		boolean encontrado = true;

		while (encontrado) {
			switch (etapa) {

			case 1:
				a = -1;
				String mensaje = tcp.Read();
				System.out.println("Cliente envia: " + mensaje);
				try {
					a = Integer.parseInt(mensaje);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error en la recepcion del mensaje");
					System.out.println("Contestacion Default '-1' ");
				} finally {
					String linea = fechas.EnviarFechas14(a);
					System.out.println("server envia: " + linea);
					tcp.Write(linea);
					etapa = 2;
				}				
				break;

			case 2:
				System.out.println("Cerrar socket cliente");
				tcp.closecliente();
				encontrado = false;
				etapa = 1;
				break;

			default:
				System.out
						.println("Error en el proceso, Protocolo reiniciado.");
				etapa = 1;
				encontrado = false;
				break;
			}
		}
		
		//return;
	}

}
