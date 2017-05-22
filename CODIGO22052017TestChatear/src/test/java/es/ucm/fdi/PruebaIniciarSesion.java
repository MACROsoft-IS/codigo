package es.ucm.fdi;

import es.ucm.fdi.negocio.ASGestionCuentaImp;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PruebaIniciarSesion extends TestCase {

	/**
	 * @return the suite of tests being tested
	 */
	public  static Test suite() {
		return new TestSuite(PruebaIniciarSesion.class);
	}
	
	public void testContrasenaIncorrecta() {
		
		ASGestionCuentaImp as = new ASGestionCuentaImp();
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1");
		assertTrue("La contrasena es incorrecta \n" + ASGestionCuentaImp.bdCadenas, as.iniciarSesion("correo1@ucm.es", "contrasena10") == null);
				
	}
	
	public void testCorreoIncorrecta() {
		
		ASGestionCuentaImp as = new ASGestionCuentaImp();
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1");
		assertTrue("La contrasena es incorrecta \n" + ASGestionCuentaImp.bdCadenas, as.iniciarSesion("correo10@ucm.es", "contrasena1") == null);
				
	}
	
	public void testSesionIniciadaCorrectamente() {
		
		ASGestionCuentaImp as = new ASGestionCuentaImp();
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1");
		assertTrue("La sesion no se ha iniciado \n" + ASGestionCuentaImp.bdCadenas, as.iniciarSesion("correo1@ucm.es", "contrasena1") != null);
		
	}
	
}
