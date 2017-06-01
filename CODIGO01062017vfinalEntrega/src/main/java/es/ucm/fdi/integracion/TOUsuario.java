package es.ucm.fdi.integracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;


public class TOUsuario implements Serializable { 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5651487630211357147L;
	
	private String nombre;
	private String correo;
	private String contrasena;
	private String foto;
	private String descripcion;
	private boolean activo;
	private ArrayList<String> listaConocidos;
	private ArrayList<TOPendiente> listaPendientes;
	private Vector<String> chats;
	private String avisos;

	public TOUsuario(String nombre, String correo, String contrasena, String foto, String descripcion) {
		this.nombre = nombre;
		this.correo = correo;
		this.contrasena = contrasena;
		this.descripcion = descripcion;
		this.foto = foto;
		this.activo = true;
		this.listaConocidos = new ArrayList<String>();
		this.listaConocidos.add(correo);
		this.listaPendientes = new ArrayList<es.ucm.fdi.integracion.TOPendiente>();
		this.chats = new Vector<String>();
		this.avisos = null; 
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
	
	public ArrayList<String> getListaConocidos(){
		return this.listaConocidos;
	}
	
	public ArrayList<TOPendiente> getListaPendientes(){
		return this.listaPendientes;
	}
	
	public Vector<String> getChats() { 
		return this.chats;
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
		return "\nAqui se mostraria la foto de perfil" + System.getProperty("line.separator") +
				"   Nombre: " + this.nombre + System.getProperty("line.separator") + 
				"   Acerca de: " + this.descripcion + System.getProperty("line.separator");
	}
	

	
}

