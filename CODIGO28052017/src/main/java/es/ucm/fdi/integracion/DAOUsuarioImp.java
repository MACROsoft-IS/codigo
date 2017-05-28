package es.ucm.fdi.integracion;

import es.ucm.fdi.datos.BDMemoria;

public class DAOUsuarioImp implements DAOUsuario {
	
	private static BDMemoria<TOUsuario> bdUsers; 
	//al no haber una comunicacion "normal" de sql, directamente tomamos como una conexion abierta.
	//nos ahorramos el patron singleton, ya que no es necesario mantener una conexion a la escucha
	//y la clase BDMemoria ya lo implementa

	public DAOUsuarioImp() {
		DAOUsuarioImp.bdUsers = new BDMemoria<TOUsuario>();
	}
	
	public void createUser(TOUsuario tUsuario) {
		bdUsers.insert(tUsuario, tUsuario.getCorreo());
	}

	public TOUsuario read(String correo) {
		return bdUsers.find(correo);
	}

	public void removeUser(String correo){
		bdUsers.removeId(correo);
	}
	
	public BDMemoria<TOUsuario> getBdUsersConnection() { 
		return DAOUsuarioImp.bdUsers;
	}
	
	

}
