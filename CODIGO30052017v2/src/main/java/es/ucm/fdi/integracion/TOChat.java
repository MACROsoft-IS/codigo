package es.ucm.fdi.integracion;

import java.io.Serializable;
import java.util.Vector;

import es.ucm.fdi.datos.BDMemoria;

public class TOChat implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6557099304694838170L;
	
	private String correo;
	//private String mensajes; // mirar StringBuffer
	//private Vector<TOMensaje> mensajes;
	private boolean mensajesNuevos;
	private String chatid;


	public TOChat(String correo) {
		this.correo = correo;
	//	this.mensajes = new Vector<TOMensaje>();
		this.mensajesNuevos = false;
	}

	public String getCorreo() {
		return correo;
	}
	
	public String getChatid() {
		return chatid;
	}

	public void setChatid(String chatid) {
		this.chatid = chatid;
	}


	/*public String getMensajes() {
		return mensajes;
	}

	public void addMensajes(String mensajes) {
		this.mensajes = this.mensajes + mensajes;
	}*/
	
	/*public Vector<TOMensaje> getMensajes(){
		return this.mensajes;
	}

	public boolean getMensajesNuevos() {
		return mensajesNuevos;
	}

	public void setMensajesNuevos(boolean mensajesNuevos) {
		this.mensajesNuevos = mensajesNuevos;

	}*/

}
