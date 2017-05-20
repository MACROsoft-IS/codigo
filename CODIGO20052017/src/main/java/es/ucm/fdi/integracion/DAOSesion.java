package es.ucm.fdi.integracion;

public interface DAOSesion {

	public TOSesionImp createSesion(TOUsuarioImp usuario);
	
	public void closeSesion(TOSesionImp sesion);
}
