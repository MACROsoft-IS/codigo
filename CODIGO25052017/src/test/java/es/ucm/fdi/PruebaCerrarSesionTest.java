package es.ucm.fdi;

import org.junit.Test;

import es.ucm.fdi.integracion.TOSesion;
import es.ucm.fdi.negocio.ASGestionCuentasImp;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PruebaCerrarSesionTest extends TestCase {
	
	
	public  static TestSuite suite() {
		return new TestSuite(PruebaCerrarSesionTest.class);
	}
	
	@Test
	public void testSesionCerrada() {
		ASGestionCuentasImp as = new ASGestionCuentasImp();
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1");
		TOSesion sesion = as.iniciarSesion("correo1@ucm.es", "contrasena1");
		
		assertTrue("La sesion no se ha iniciado \n" + ASGestionCuentasImp.bdUsers, sesion != null);
		
		as.cerrarSesion(sesion);
		
		assertTrue("La desconexion no se ha podido completar.\n" + ASGestionCuentasImp.bdUsers, (sesion.getCuentaActiva() == null) && (sesion.getSesionID() == null));
	}
}
