package es.ucm.fdi.negocio;

public class Chat {
	private String correo;
	private String mensajes; // mirar StringBuffer
	private boolean mensajesNuevos;

	public Chat(String correo) {
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
