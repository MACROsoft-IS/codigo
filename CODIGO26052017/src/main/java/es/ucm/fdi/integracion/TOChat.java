package es.ucm.fdi.integracion;

public class TOChat {
	private String correo;
	private String mensajes; // mirar StringBuffer
	private boolean mensajesNuevos;

	public TOChat(String correo) {
		this.correo = correo;System.out.println("\n1.- Si\n 2.- No\n 3.- Bloquear Usuario\n 4.- Salir\n");
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
