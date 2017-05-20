package es.ucm.fdi.integracion;

import es.ucm.fdi.negocio.*;

public class DAOUsuarioImp implements DAOUsuario {
	

	public void create(TOUsuarioImp tUsuario) {

		// suponemos que ya se ha comprobado que el correo no esta repetido, y
		// que es valido
		
		ASGestionCuentaImp.bdCadenas.insert(tUsuario, tUsuario.getCorreo());
		// Llamar correctamente a la BD con Singleton
		
	}

	public TOUsuarioImp read(String id) {
		
		return ASGestionCuentaImp.bdCadenas.find(id);
		// Llamar correctamente a la BD con singleton
	}
	
	public void removeAccount(String correo){
		ASGestionCuentaImp.bdCadenas.removeId(correo);
		// Llamar correctamente a la BD con singleton
	}

}
