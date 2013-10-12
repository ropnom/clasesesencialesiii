package cliente13;

public class cliente13 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

				System.out.println("*******************");
				System.out.println(" Programa cliente ");
				System.out.println("*******************");
				ProtocoloHoraCliente13 a = new ProtocoloHoraCliente13("localhost",
						52000);

				System.out.println("Iniciando Cliente a Server concurrente TCP");

				System.out.println("-------------------");
				System.out.println(" PROTOCOLO TPC  ");
				System.out.println("-------------------");
				while (a.IniciarTCP() == 0) {
					// While infito realiza peticion e imprime resolucion
					// finalizado salta y finaliza
				}

				System.out.println("*******************");
				System.out.println(" Cliente cerrado ");
				System.out.println("*******************");


	}

}
