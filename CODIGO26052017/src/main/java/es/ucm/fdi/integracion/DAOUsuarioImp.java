package es.ucm.fdi.integracion;

import es.ucm.fdi.datos.BDMemoria;
import es.ucm.fdi.negocio.ASGestionCuentasImp;

public class DAOUsuarioImp implements DAOUsuario {
	
	private BDMemoria<TOUsuario> cn; 
	//al no haber una comunicacion "normal" de sql, directamente tomamos como una conexion abierta.
	//nos ahorramos el patron singleton, ya que no es necesario mantener una conexion a la escucha
	//y la clase BDMemoria ya lo implementa

	public DAOUsuarioImp() {
		this.cn = ASGestionCuentasImp.getBdUsersConnection();
	}
	
	public void createUser(TOUsuario tUsuario) {
		cn.insert(tUsuario, tUsuario.getCorreo());
	}

	public TOUsuario read(String correo) {
		return cn.find(correo);
	}

	public void removeUser(String correo){
		cn.removeId(correo);
	}

}
