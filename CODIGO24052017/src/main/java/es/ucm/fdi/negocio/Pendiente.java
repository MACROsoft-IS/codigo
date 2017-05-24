package es.ucm.fdi.negocio;

public class Pendiente{
	public enum tPendiente{
		MANDO,
		RECIBO
	}
	private String correo;
	private tPendiente tipo;
	
	public Pendiente(String correo, tPendiente tipo){
		this.correo = correo;
		this.tipo = tipo;
	}
	
	public String getCorreo(){
		return correo;
	}
	
	public tPendiente getTipo(){
		return tipo;
	}
}
