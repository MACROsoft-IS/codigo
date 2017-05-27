package es.ucm.fdi.negocio;

import es.ucm.fdi.integracion.TOSesion;

public class BOSesion {

	public void closeSesion(TOSesion sesion){
		sesion.setSesionID(null);
		sesion.setCuentaActiva(null);
	}
	
}
