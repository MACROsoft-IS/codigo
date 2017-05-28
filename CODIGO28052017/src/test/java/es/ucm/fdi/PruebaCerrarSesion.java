package es.ucm.fdi;

import org.junit.Test;

import es.ucm.fdi.integracion.TOSesion;
import es.ucm.fdi.negocio.ASGestionCuentasImp;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PruebaCerrarSesion extends TestCase {
	
	
	public  static TestSuite suite() {
		return new TestSuite(PruebaCerrarSesion.class);
	}
	
	@Test
	public void testSesionCerrada() {
		ASGestionCuentasImp as = new ASGestionCuentasImp();
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1","foto","desc");
		TOSesion sesion = as.iniciarSesion("correo1@ucm.es", "contrasena1");
		
		assertTrue("La sesion se ha iniciado \n" + as.getUsuarioDAO().getBdUsersConnection(), sesion != null);
		
		as.cerrarSesion(sesion);
		
		assertTrue("La desconexion se ha podido completar.\n" + as.getUsuarioDAO().getBdUsersConnection(), (sesion.getSesionEstado() == false));
	}
}
