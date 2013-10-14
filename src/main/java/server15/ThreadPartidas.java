package server15;

import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.TCPconection;

public class ThreadPartidas extends Thread{

	//implements Runnable
	protected static int numjugadores = 0;	
	//ponemso unmaximo de 5 partidas
	protected static Partida[] partidas = new Partida[5];
	protected TCPconection tcp = null;
	protected int etapa = 1;
	
	
	public ThreadPartidas(TCPconection s)
	{
		this.tcp = s;		
		
	}	
	
	public static void Inicializar()
	{
		for(int i=0;i<5;i++)
		{
			partidas[i] = new Partida(i);
		}
	}
	
		public synchronized boolean IsFullParties()
	{
		if(numjugadores==10)
			return(true);
		else
		{
			IncrementarJugadores();
			return(false);			
		}
	}
	
	protected synchronized int GetnumJugadores()
	{
		return(numjugadores);	
	}
	
	protected synchronized void IncrementarJugadores()
	{
		numjugadores++;	
	}
	protected synchronized void DecrementarJugadores()
	{
		numjugadores--;	
	}
	

	
	public void run() {
		
				// modelamos el protocolos en 3 estados
				// estado 1 identificacion del mensaje entrante
				//			en caso de correcto lo procesamos en la partida correspondiente
				//			la clase Partida gestiona el thread de cada partida
				// estado 2 cerrar sockets y cerrar
		
				boolean encontrado = true;
				while (encontrado) {
					switch (etapa) {

					case 1:	
						String mensaje = tcp.Read();
						System.out.println("CLiente envia: " + mensaje);
						
						try {
							System.out.println("Aquiviene el split");
							String[] nombre =  mensaje.split(":");	
							System.out.println(" asda"+nombre.length);
							if(nombre.length!=2)
							{
								throw new Exception("La longitud del protocolo es " + nombre.length);
							}					
							//creamos el jugador	
							System.out.println("Aqui llega 1");
							System.out.println(nombre[0]);
							System.out.println(nombre[1]);
							gamer a = new gamer(nombre[1]);							
							if(!this.IsFullParties())
							{								
								TCPconection sa = new TCPconection(tcp);	
								System.out.println("contador de jugadores:"+(GetnumJugadores()));
								System.out.println("partida:"+(GetnumJugadores()));
								partidas[(GetnumJugadores()/2)].IntroducirJugador(a, sa);		
								
							}							
							else
							{
								System.out.println("El servidor esta lleno. Peticion rechazada.");
								System.out.println(" -- server envia: '-2' --");
								tcp.Write("-2");
							}
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Error de sintaxis en protocolo ");		
							System.out.println("-- server envia: '-1 --'");
							tcp.Write("-1");
						} finally {					
							System.out.println("Aqui llega 2");
							etapa = 2;
						}				
						break;

					case 2:
						System.out.println("Cerrar socket cliente");
						//tcp.closecliente();
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
		
		
	}

}
