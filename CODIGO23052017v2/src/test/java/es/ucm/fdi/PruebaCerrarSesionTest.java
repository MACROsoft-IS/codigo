package es.ucm.fdi;

import org.junit.Test;

import es.ucm.fdi.integracion.TOSesionImp;
import es.ucm.fdi.negocio.ASGestionCuentaImp;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PruebaCerrarSesionTest extends TestCase {
	
	
	public  static TestSuite suite() {
		return new TestSuite(PruebaCerrarSesionTest.class);
	}
	
	@Test
	public void testSesionCerrada() {
		ASGestionCuentaImp as = new ASGestionCuentaImp();
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1");
		TOSesionImp sesion = as.iniciarSesion("correo1@ucm.es", "contrasena1");
		
		assertTrue("La sesion no se ha iniciado \n" + ASGestionCuentaImp.bdCadenas, sesion != null);
		
		as.cerrarSesion(sesion);
		
		assertTrue("La desconexion no se ha podido completar.\n" + ASGestionCuentaImp.bdCadenas, (sesion.getCuentaActiva() == null) && (sesion.getSesionID() == null));
	}
}
