package es.ucm.fdi;

import es.ucm.fdi.integracion.TOSesion;
import es.ucm.fdi.integracion.TOUsuario;
import es.ucm.fdi.integracion.TOPendiente.tPendiente;
import es.ucm.fdi.negocio.ASGestionConocerImp;
import es.ucm.fdi.negocio.ASGestionCuentasImp;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ConocerGente2 extends TestCase {

	/**
	 * @return the suite of tests being tested
	 */
	public  static Test suite() {
		return new TestSuite(ConocerGente2.class);
	}
	
	
	public void TestConocerGente(){
		
		ASGestionCuentasImp as = new ASGestionCuentasImp();
		ASGestionConocerImp asconocer = new ASGestionConocerImp(as.getUsuarioDAO(), as.getSesionDAO());

		
		as.crearCuenta("conocedor", "conocedor@ucm.es", "contrasena1",null,null);
		as.crearCuenta("conocido1", "conocido1@ucm.es", "contrasena1",null,null);
		as.crearCuenta("desconocido1", "desconocido1@ucm.es", "contrasena1",null,null);
		as.crearCuenta("desconocido2", "desconocido2@ucm.es", "contrasena1",null,null);
		as.crearCuenta("pendiente1", "pendiente1@ucm.es", "contrasena1",null,null);
		as.crearCuenta("pendiente2", "pendiente2@ucm.es", "contrasena1",null,null);
		
		
		
		
		TOSesion usuario = as.iniciarSesion("conocido1@ucm.es", "contrasena1");
		usuario.getCuentaActiva().añadirConocido("conocedor@ucm.es");
		as.cerrarSesion(usuario);
		
		usuario = as.iniciarSesion("pendiente1@ucm.es", "contrasena1");
		usuario.getCuentaActiva().añadirPendiente("conocedor@ucm.es", tPendiente.RECIBO);
		usuario.getCuentaActiva().añadirPendiente("pendiente2@ucm.es", tPendiente.MANDO);
		as.cerrarSesion(usuario);
		
		usuario = as.iniciarSesion("pendiente2@ucm.es", "contrasena1");
		usuario.getCuentaActiva().añadirPendiente("conocedor@ucm.es", tPendiente.MANDO);
		usuario.getCuentaActiva().añadirPendiente("pendiente1@ucm.es", tPendiente.RECIBO);
		as.cerrarSesion(usuario);
		
		usuario = as.iniciarSesion("conocedor@ucm.es", "contrasena1");
		usuario.getCuentaActiva().añadirConocido("conocido1@ucm.es");
		usuario.getCuentaActiva().añadirPendiente("pendiente1@ucm.es", tPendiente.MANDO);
		usuario.getCuentaActiva().añadirPendiente("pendiente2@ucm.es", tPendiente.RECIBO);
		
		asconocer.conocerGente(usuario.getCuentaActiva());
		
		
		
		
		
		
		
	}
}