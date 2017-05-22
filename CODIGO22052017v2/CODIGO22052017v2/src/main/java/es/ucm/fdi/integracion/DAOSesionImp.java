package es.ucm.fdi.integracion;

public class DAOSesionImp implements DAOSesion {

	public TOSesionImp createSesion(TOUsuarioImp usuario){
		return new TOSesionImp(usuario);
	}
	
	public void closeSesion(TOSesionImp sesion){
		sesion.setSesionID(null);
		sesion.setCuentaActiva(null);
	}

}
