package es.ucm.fdi.integracion;

public class TOMensaje {
	
	private String correoUsuario;
	private String nombreUsuario;
	private String contenido;
	private long momento;
	private String id;
	private String chatId;
	
	public TOMensaje(String correo, String contenido, String chatid, String name, int index){
		this.correoUsuario = correo;
		this.nombreUsuario = name;
		this.contenido = contenido;
		this.momento = System.currentTimeMillis();
		this.chatId = chatid;
		this.id = this.chatId + "/" + this.momento + "|" + index;
	}
	
	public String getNombreUsuario(){
		return this.nombreUsuario;
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
	
	public String getChatId(){
		return this.chatId;
	}
	
	@Override
	public String toString(){
		return nombreUsuario + ":\t" + contenido + "\n";
	}
}
