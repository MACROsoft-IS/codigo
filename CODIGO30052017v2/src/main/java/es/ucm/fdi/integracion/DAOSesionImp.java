package es.ucm.fdi.integracion;

import es.ucm.fdi.datos.BDMemoria;

public class DAOSesionImp implements DAOSesion {

	private static BDMemoria<TOSesion> bdSesiones; 
	//al no haber una comunicacion "normal" de sql, directamente tomamos como una conexion abierta.
	//nos ahorramos el patron singleton, ya que no es necesario mantener una conexion a la escucha
	//y la clase BDMemoria ya lo implementa
	
	public DAOSesionImp(){
		DAOSesionImp.bdSesiones = new BDMemoria<TOSesion>();
	}
	
	public void insertSesion(TOSesion tSesionUsuario){
		bdSesiones.insert(tSesionUsuario, tSesionUsuario.getSesionID());	
	}
	public void closeSesion(TOSesion sesion){
		//Para cerrar sesión solo cambiaremos el booleano de la sesión
		sesion.setSesionEstado(false);
	}

	public TOSesion read(String sesionID) {
		return bdSesiones.find(sesionID);
	}

	public void removeSesion(String id) {
		// Las sesiones se almacenarán en la base de datos.
	}
	
	public BDMemoria<TOSesion> getBdUsersConnection() { 
		return DAOSesionImp.bdSesiones;
	}

}
