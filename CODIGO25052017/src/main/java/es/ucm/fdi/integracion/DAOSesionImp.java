package es.ucm.fdi.integracion;

import es.ucm.fdi.datos.BDMemoria;
import es.ucm.fdi.negocio.ASGestionCuentasImp;

public class DAOSesionImp implements DAOSesion {

	private BDMemoria<TOSesion> cn; 
	//al no haber una comunicacion "normal" de sql, directamente tomamos como una conexion abierta.
	//nos ahorramos el patron singleton, ya que no es necesario mantener una conexion a la escucha
	//y la clase BDMemoria ya lo implementa
	
	public DAOSesionImp(){
		this.cn = ASGestionCuentasImp.getBdSesionConnection();
	}
	
	public void insertSesion(TOSesion tSesionUsuario){
		//da errores nose porque
		//cn.insert(tSesionUsuario,tSesionUsuario.getSesionID());	
	}
	public void closeSesion(TOSesion sesion){
		//Do nothing
		//nunca eliminamos la sesion de la base de datos. Como si fuera un log
	}

	public TOSesion read(String sesionID) {
		return cn.find(sesionID);
	}

	public void removeSesion(String id) {
		//Do nothing
		//por si queremos eliminar mas adelante sesiones o algo
	}

}
