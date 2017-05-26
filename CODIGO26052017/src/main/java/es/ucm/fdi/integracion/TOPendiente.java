package es.ucm.fdi.integracion;

public class TOPendiente{
	public enum tPendiente{
		MANDO,
		RECIBO
	}
	private String correo;
	private tPendiente tipo;
	
	public TOPendiente(String correo, tPendiente tipo){
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
