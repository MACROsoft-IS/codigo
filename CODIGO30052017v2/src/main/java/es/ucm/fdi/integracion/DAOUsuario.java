package es.ucm.fdi.integracion;

public interface DAOUsuario{

	public void createUser(TOUsuario tUsuario);
	public void removeUser(String correo);
	public TOUsuario read(String id);
}
 