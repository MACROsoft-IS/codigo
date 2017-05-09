package es.ucm.fdi.integracion;

import es.ucm.fdi.negocio.*;

public class DAOUsuarioImp implements DAOUsuario {
	

	public void create(TOUsuarioImp tUsuario) {

		// suponemos que ya se ha comprobado que el correo no esta repetido, y
		// que es valido
		
		ASGestionCuentaImp.bdCadenas.insert(tUsuario, tUsuario.getCorreo());
		
		
	}

	public TOUsuarioImp read(String id) {
		
		return ASGestionCuentaImp.bdCadenas.find(id);
	}

}
