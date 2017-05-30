package es.ucm.fdi.integracion;

public interface DAOChat {
	
	public void createChat(TOUsuario usuario, String correo);
	public void removeChat(TOUsuario usuario, String correo);
	public TOChat readChat(TOUsuario usuario, String id);
}