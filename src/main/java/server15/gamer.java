package server15;

public class gamer {
	
	protected String identificador;
	protected int apuesta;
	protected int total;
	
	public gamer(String identificador)
	{
		this.identificador = identificador;		
	}
	public void apuestagamer(int apuesta, int total)	{
		
		this.apuesta = apuesta;
		this.total = total;
	}

	public String getIdentificador() {
		return identificador;
	}

	public int getApuesta() {
		return apuesta;
	}

	public int getTotal() {
		return total;
	}

}
