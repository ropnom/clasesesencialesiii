package servidor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class fechas {
	
	
	public static String EnviarFecha12()
	{
		//miramos la hora actual
		Date dt = new Date();
		//introducimos en el formato correcto el dato
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return(format.format(dt));
	}

}
