package es.ucm.fdi.integracion;

import es.ucm.fdi.datos.BDMemoria;
import es.ucm.fdi.integracion.TOPendiente.tPendiente;

public interface DAOUsuario{

	public void createUser(TOUsuario tUsuario);
	public void removeUser(String correo);
	public TOUsuario read(String id);
	public BDMemoria<TOUsuario> getBdUsersConnection();
	public void anadirConocido(TOUsuario usuario, String correo);
	public void eliminarConocido(TOUsuario usuario, String correo);
	public void anadirPendiente(TOUsuario usuario, String correo, tPendiente tipo);
	public void eliminarPendiente(TOUsuario usuario, String correo);
	public void añadirChatIdVector(TOUsuario usuario, String idChat);
	public void eliminarChatIdVector(TOUsuario usuario, String idChat);
}
 