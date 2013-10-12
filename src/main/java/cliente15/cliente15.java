package cliente15;

import java.util.Scanner;

public class cliente15 {

	public static void main(String[] args) {
		
		//inciaciamso nuestra version del juego de lso chinos
		System.out.println("---------------------------------------");
		System.out.println("**** INICIANDO JUEGO DE LOS CHINOS ****");
		System.out.println("---------------------------------------");
		System.out.println();
		System.out.println("Introduzca su Identificador:");
		Scanner a = new Scanner(System.in);
		String identificador = a.next();
		
		Jugador jugador = new Jugador(identificador);
		
		while(jugador.Jugar()==0)
		{
			//bucle infinito			
		}
		
		
	}

}
