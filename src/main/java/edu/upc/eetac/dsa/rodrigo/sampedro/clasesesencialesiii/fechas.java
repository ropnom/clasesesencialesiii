package edu.upc.eetac.dsa.rodrigo.sampedro.clasesesencialesiii;

import java.text.SimpleDateFormat;
import java.util.Date;

public class fechas {

	public static String EnviarFecha12() {
		// miramos la hora actual
		Date dt = new Date();
		// introducimos en el formato correcto el dato
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return (format.format(dt));
	}

	public static String EnviarFechas14(int a) {
		// miramos la hora actual
		Date dt = new Date();
		SimpleDateFormat format = null;
		
		String mensaje = "Error no se ha comprendido el mensaje -1";
		if(a ==0)
		{
			format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			mensaje = format.format(dt);
			
		}
		else if(a==1)
		{
			format = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm:ss");
			mensaje = format.format(dt);
		}		
		
		return (mensaje);
	}

}
