package es.ucm.fdi.integracion;

public interface DAOSesion {

	public void insertSesion(TOSesion sesion);
	public void closeSesion(TOSesion sesion);
	public TOSesion read(String id);
	public void removeSesion(String id);
}
