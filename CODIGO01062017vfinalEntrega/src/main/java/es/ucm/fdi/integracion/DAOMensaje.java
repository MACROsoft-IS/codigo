package es.ucm.fdi.integracion;

public interface DAOMensaje {

	public TOMensaje createMsj(String emisor, String mensaje, String idChat, String name);
	public void removeUser(String idMensaje);
	public TOMensaje read(String idMensaje);
}
