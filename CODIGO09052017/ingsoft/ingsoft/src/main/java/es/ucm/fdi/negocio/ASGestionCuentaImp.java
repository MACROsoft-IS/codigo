package es.ucm.fdi.negocio;

import es.ucm.fdi.datos.BDMemoria;
import es.ucm.fdi.integracion.DAOSesionImp;
import es.ucm.fdi.integracion.DAOUsuarioImp;
import es.ucm.fdi.integracion.TOSesionImp;
import es.ucm.fdi.integracion.TOUsuarioImp;
import es.ucm.fdi.integracion.TOUsuarioImp;

public class ASGestionCuentaImp {

	private DAOUsuarioImp usuarioDAO;
	private DAOSesionImp sesionDAO;
	public static BDMemoria<TOUsuarioImp> bdCadenas;

	public ASGestionCuentaImp() {
		ASGestionCuentaImp.bdCadenas = new BDMemoria<TOUsuarioImp>();
		this.sesionDAO = new DAOSesionImp();
		this.usuarioDAO = new DAOUsuarioImp();
	}

	public TOSesionImp iniciarSesion(String correo, String contrasena) {

		TOSesionImp sesion = null;
		TOUsuarioImp usuario = usuarioDAO.read(correo);

		if (usuario!=null && usuario.getContrasena().equals(contrasena))

			sesion = sesionDAO.createSesion(usuario);

		return sesion;
	}

	public BDMemoria<TOUsuarioImp> getBdCadenas() {
		return bdCadenas;
	}

	public void setBdCadenas(BDMemoria<TOUsuarioImp> bdCadenas) {
		ASGestionCuentaImp.bdCadenas = bdCadenas;
	}

	public boolean crearCuenta(String nombre, String correo, String contrasena) {

		boolean cuenta = false;
		
		if (usuarioDAO.read(correo) == null) {
			
			TOUsuarioImp usuario = new TOUsuarioImp(nombre, correo, contrasena);
			usuarioDAO.create(usuario);
			cuenta = true;
		}
		return cuenta;
	
	}

}
