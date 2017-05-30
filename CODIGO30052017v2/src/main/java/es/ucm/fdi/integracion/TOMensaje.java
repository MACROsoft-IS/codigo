package es.ucm.fdi.integracion;

public class TOMensaje {
	
	private String correoUsuario;
	private String contenido;
	private long momento;
	private String id;
	private String chatid;
	
	public TOMensaje(String correo, String contenido, String chatid){
		this.correoUsuario = correo;
		this.contenido = contenido;
		this.momento = System.currentTimeMillis();
		this.id = "" + this.momento;
		this.chatid=chatid;
	}
	
	public void setCorreoUsuario(String correo){
		this.correoUsuario = correo;
	}
	
	public String getCorreoUsuario(){
		return this.correoUsuario;
	}
	
	public void setContenido(String mensaje){
		this.contenido = mensaje;
	}
	
	public String getContenido(){
		return this.contenido;
	}
	
	public long getMomento(){
		return this.momento;
	}
	
	public String getId(){
		return this.id;
	}
	
	@Override
	public String toString(){
		return this.contenido;
	}
}
