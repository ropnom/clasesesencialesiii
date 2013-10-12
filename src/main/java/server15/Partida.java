package server15;

import edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii.TCPconection;

public class Partida extends Thread{
	
	int identificador= 0;
	int contador;
	gamer[] lista;
	TCPconection[] sokets;
	int etapa = 1;
	
	public Partida(int i)
	{
		identificador = i;
		contador = 0;
		lista = new gamer[2];
		sokets = new TCPconection[2];
		etapa = 1;
	}
	
	protected void Waiting(TCPconection s)
	{
		Write("WAIT", s );
	}
	protected void Versus()
	{
		//creamos losmensajes y los enviamos
		String mensage1 = "VERSUS: "+ lista[1].getIdentificador();
		String mensage2 = "VERSUS: "+ lista[0].getIdentificador();
		
		Write(mensage1, sokets[0]);
		Write(mensage2, sokets[1]);
	}
	
	protected void YourBet(TCPconection s)
	{
		Write("YOUR BET", s );
	}
	protected void WaitBet(TCPconection s)
	{
		Write("WAIT BET", s );
	}
	protected void Betof(int jugador)
	{
		String mensage = "BET OF:"+lista[jugador].identificador+";"+lista[jugador].getTotal();
		Write(mensage, sokets[0]);
		Write(mensage, sokets[1]);
	}	
	protected void Winner(int ganador)
	{
		String message;
		if(ganador == 0)
			message = "WINNER: "+lista[0].identificador;
		else if(ganador == 1)
			message = "WINNER: "+lista[1].identificador;
		else
			message = "NONE";	
		
		Write(message, sokets[0]);
		Write(message, sokets[1]);
	}	
	protected void Write(String message,TCPconection s )
	{
		String mensajeserver = "Send--> PARTIDA {"+identificador+"} -"+lista[0].identificador+"-"+lista[1].getIdentificador()+"\n"+message;
		System.out.println(mensajeserver);
		s.Write(message);
	}
	
	protected void Recibe(String m, int a)
	{
		System.out.println("<-- Partida{"+identificador+"}:"+lista[a].getIdentificador()+":  "+m);
	}
	public synchronized boolean IsFull()
	{
		if(lista.length==2)
			return(true);
		else
			return(false);
	}
	public synchronized void IntroducirJugador(gamer a,TCPconection sa)
	{
		//introducimos el jugador
		lista[contador] = a;
		sokets[contador] = sa;
		contador++;
		//enviamos mensajes al jugador
		if(contador==1)
		{
			System.out.println("Iniciando Partida {"+identificador+"} -- Waiting");
			//mensaje al 1º jugador
			this.Waiting(sokets[contador-1]);
		}
		else
		{
			System.out.println("Iniciando Partida {"+identificador+"} -- OK");
			//mensaje al segundo jugador
			this.Versus();
			this.YourBet(sokets[0]);
			this.WaitBet(sokets[1]);
			this.run();
		}
		
	}
	protected int ganador()
	{
		//calculamos el total de las monedas
		int total = lista[0].getApuesta() + lista[1].getApuesta();
		//seleccionamos ganador
		if(total == lista[0].getTotal())
			return(0);
		else if(total == lista[1].getTotal())
			return(1);
		else
			return(-1);	
		
	}
	
	protected void CloseSockets()
	{
		sokets[0].closecliente();
		sokets[1].closecliente();
	}
	protected void Jugada(int i, int j, boolean finish)
	{
		String mensaje = sokets[i].Read();
		Recibe(mensaje, i);
		try {
			String[] protocolo = mensaje.split(":");
			if((protocolo[0]!="MY BET") && (protocolo.length != 3))
			{
				//lanzamos una excepcionde protocolo
				throw new Exception("La longitud del protocolo es " + protocolo.length);					
			}
			else
			{
				lista[i].apuestagamer(Integer.parseInt(protocolo[1]), Integer.parseInt(protocolo[2]));
				this.Betof(i);
				if(!finish)
				{
				this.WaitBet(sokets[i]);
				this.YourBet(sokets[j]);
				etapa = 2;
				}
				else{
					etapa = 3;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error en la recepcion del mensaje");
			System.out.println("Contestacion de Repeticion '-3' ");
			sokets[i].Write("-3");
		} 
	}
	
	protected boolean IniciarPArtida()
	{
		switch (etapa) {
		
		case 1:			
			Jugada(0, 1, false);
			
			break;
			
		case 2:
			Jugada(1, 0, true);
			

		case 3:
			this.Winner(this.ganador());
			this.CloseSockets();
			etapa = 1;
			return(false);
			//break;

		default:
			System.out
					.println("Error en el proceso, Protocolo reiniciado.");
			etapa = 1;
			
			break;
		}
		
		return(true);
	}
	@Override
	public void run() {
		
		while(IniciarPArtida())
		{
			//bucle infinito mientras dura la partida
		}
		
	}
	
	
	

}
