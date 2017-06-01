package es.ucm.fdi.integracion;

import java.io.Serializable;
import java.util.Vector;

public class TOChat implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6557099304694838170L;
	
	private String correoA;
	private String correoB;
	private String chatid;
	private Vector<String> mensajes;
	private boolean mensajesNuevosA;
	private boolean mensajesNuevosB;

	public TOChat(String correoA, String correoB) {
		this.correoA = correoA;
		this.correoB = correoB;
		this.chatid = this.correoA+ "/" + this.correoB;
		this.mensajes = new Vector<String>();
		this.mensajesNuevosA = false;
		this.mensajesNuevosB = false;
	}

	public String getCorreoA() {
		return correoA;
	}
	
	public String getCorreoB(){
		return correoB;
	}
	
	public String getChatid() {
		return chatid;
	}
	
	public Vector<String> getMensajes(){
		return this.mensajes;
	}

	public boolean getMensajesNuevosA() {
		return mensajesNuevosA;
	}

	public void setMensajesNuevosA(boolean mensajesNuevos) {
		this.mensajesNuevosA = mensajesNuevos;

	}
	
	public boolean getMensajesNuevosB() {
		return mensajesNuevosB;
	}

	public void setMensajesNuevosB(boolean mensajesNuevos) {
		this.mensajesNuevosB = mensajesNuevos;

	}


}
