package es.ucm.fdi.integracion;

public interface DAOMensaje {

	public void createMsj(TOChat chat, String emisor, String mensaje);
	public void removeUser(TOChat chat, String correo);
	public TOMensaje read(TOChat chat, String id);
}
