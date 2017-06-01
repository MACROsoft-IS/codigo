package es.ucm.fdi.integracion;

import es.ucm.fdi.datos.BDMemoria;

public interface DAOSesion {

	public void insertSesion(TOSesion sesion);
	public void closeSesion(TOSesion sesion);
	public TOSesion read(String id);
	public void removeSesion(String id);
	public BDMemoria<TOSesion> getBdUsersConnection();
}
