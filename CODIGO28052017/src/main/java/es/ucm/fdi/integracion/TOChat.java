package es.ucm.fdi.integracion;

import java.io.Serializable;

public class TOChat implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6557099304694838170L;
	
	private String correo;
	private String mensajes; // mirar StringBuffer
	private boolean mensajesNuevos;

	public TOChat(String correo) {
		this.correo = correo;
		this.mensajes = "";
		this.mensajesNuevos = false;
	}

	public String getCorreo() {
		return correo;
	}

	public String getMensajes() {
		return mensajes;
	}

	public void addMensajes(String mensajes) {
		this.mensajes = this.mensajes + mensajes;
	}

	public boolean getMensajesNuevos() {
		return mensajesNuevos;
	}

	public void setMensajesNuevos(boolean mensajesNuevos) {
		this.mensajesNuevos = mensajesNuevos;

	}

}
