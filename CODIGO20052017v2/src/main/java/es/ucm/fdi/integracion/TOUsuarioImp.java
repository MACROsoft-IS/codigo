package es.ucm.fdi.integracion;

import java.util.ArrayList;

import es.ucm.fdi.integracion.TOUsuarioImp.Pendiente.tPendiente;

public class TOUsuarioImp{ 
	
	public static class Pendiente{
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
	
	public class Chat{
		private String correo;
		private String mensajes; //mirar StringBuffer
		private boolean mensajesNuevos;
		
		public Chat(String correo){
			this.correo = correo;
			this.mensajes = null;
			this.mensajesNuevos = false;
		}
		
		public String getCorreo(){
			return correo;
		}
		
		public String getMensajes(){
			return mensajes;
		}
		
		public void addMensajes(String mensajes){
			this.mensajes = this.mensajes + mensajes;
		}
		
		public boolean getMensajesNuevos(){
			return mensajesNuevos;
		}
		
		public void setMensajesNuevos(boolean mensajesNuevos){
			this.mensajesNuevos = mensajesNuevos;
		}
	}
	
	private String nombre;
	private String correo;
	private String contrasena;
	private String foto;
	private String descripcion;
	private boolean activo;
	private ArrayList<String> listaConocidos;
	private ArrayList<Pendiente> listaPendientes;
	private ArrayList<Chat> listaChats;
	private String avisos;

	public TOUsuarioImp(String nombre, String correo, String contrasena) {
		this.nombre = nombre;
		this.correo = correo;
		this.contrasena = contrasena;
		this.activo = true; // false en caso de espera de confirmacion
		this.listaConocidos = new ArrayList<String>(); //lista de correo de usuarios ya presentados 
		this.listaConocidos.add(correo); // Te añades porque tu estas encantado de conocerte
		this.listaPendientes = new ArrayList<Pendiente>();//lista de usuarios pendientes (estructura de correousuario, enum (MANDO, RECIBO)) 
		this.listaChats = new ArrayList<Chat>();//lista de correos de usuarios pa chatear (estructura correousuario, string con los mensajes, booleano mensajes nuevos)
		this.avisos = null; //String de avisos ("se te ha añadido a pepito al chat", "juanito ha borrado su cuenta", "spam varios")
		
		}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public void añadirConocido(String correo){
		this.listaConocidos.add(correo);
	}
	
	public void eliminarConocido(String correo){
		this.listaConocidos.remove(correo);
	}
	
	public ArrayList<String> getListaConocidos(){
		return this.listaConocidos;
	}
	
	public void añadirPendiente(String correo, tPendiente tipo){
		this.listaPendientes.add(new Pendiente(correo, tipo));
	}
	
	public void eliminarPendiente(String correo){
		
		int i = 0;
		boolean eliminado = false;
		
		while (i < this.listaPendientes.size() && !eliminado){
			if (this.listaPendientes.get(i).getCorreo().equalsIgnoreCase(correo)) {
				this.listaPendientes.remove(i);
				eliminado = true;
			}
			else i++;
		}
	}
	
	public ArrayList<Pendiente> getListaPendientes(){
		return this.listaPendientes;
	}
	
	public void añadirChat(String correo){
		this.listaChats.add(new Chat(correo));
	}
	
public void eliminarChat(String correo){
		int i = 0;
		boolean eliminado = false;
		
		while (i < this.listaChats.size() && !eliminado){
			if (this.listaChats.get(i).getCorreo().equalsIgnoreCase(correo)) {
				this.listaChats.remove(i);
				eliminado = true;
			}
			else i++;
		}
	}
	
	public ArrayList<Chat> getListaChats(){
		return this.listaChats;
	}

	public void setAvisos(String avisos){
		this.avisos = avisos;
	}
	
	public String getAvisos(){
		return this.avisos;
	}
	
	public void addAviso(String aviso){
		this.avisos = this.avisos + aviso;
	}

	public String toString() {
		return "Aqui se mostraria la foto de perfil" + System.getProperty("line.separator") +
				this.nombre + System.getProperty("line.separator") + this.descripcion + 
				System.getProperty("line.separator");
	}
}
