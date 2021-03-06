package es.ucm.fdi.integracion;

import java.io.Serializable;
import java.util.ArrayList;

import es.ucm.fdi.integracion.TOPendiente.tPendiente;

public class TOUsuario implements Serializable { 
	
	private String nombre;
	private String correo;
	private String contrasena;
	private String foto;
	private String descripcion;
	private boolean activo;
	private ArrayList<String> listaConocidos;
	private ArrayList<TOPendiente> listaPendientes;
	private ArrayList<TOChat> listaChats;
	private String avisos;

	public TOUsuario(String nombre, String correo, String contrasena) {
		this.nombre = nombre;
		this.correo = correo;
		this.contrasena = contrasena;
		this.descripcion = null; ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		this.foto = null;
		this.activo = true; // false en caso de espera de confirmacion
		this.listaConocidos = new ArrayList<String>(); //lista de correo de usuarios ya presentados 
		this.listaConocidos.add(correo); // Te añades porque tu estas encantado de conocerte
		this.listaPendientes = new ArrayList<es.ucm.fdi.integracion.TOPendiente>();//lista de usuarios pendientes (estructura de correousuario, enum (MANDO, RECIBO)) 
		this.listaChats = new ArrayList<TOChat>();//lista de correos de usuarios pa chatear (estructura correousuario, string con los mensajes, booleano mensajes nuevos)
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
		this.listaPendientes.add(new TOPendiente(correo, tipo));
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
	
	public ArrayList<TOPendiente> getListaPendientes(){
		return this.listaPendientes;
	}
	
	public void añadirChat(String correo){
		this.listaChats.add(new TOChat(correo));
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
	
	public ArrayList<TOChat> getListaChats(){
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
	
	
	public String showPendientes(){
		String string = "";
		for(int i = 0; i < this.listaPendientes.size(); i++){
			string = string + this.listaPendientes.get(i).getCorreo() + " ";
			
		}
		return string;
	}
	public String showChats(){
		String string = "";
		for(int i = 0; i < this.listaChats.size(); i++){
			string = string + this.listaChats.get(i).getCorreo() + " ";
			
		}
		return string;
	}
	public String showConocidos(){
		String string = "";
		for(int i = 0; i < this.listaConocidos.size(); i++){
			string = string + this.listaConocidos.get(i) + " ";
			
		}
		return string;
	}
	
}

