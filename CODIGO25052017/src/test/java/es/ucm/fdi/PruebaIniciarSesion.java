package es.ucm.fdi;

import es.ucm.fdi.negocio.ASGestionCuentasImp;
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
		
		ASGestionCuentasImp as = new ASGestionCuentasImp();
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1");
		assertTrue("La contrasena es incorrecta \n" + ASGestionCuentasImp.bdUsers, as.iniciarSesion("correo1@ucm.es", "contrasena10") == null);
				
	}
	
	public void testCorreoIncorrecto() {
		
		ASGestionCuentasImp as = new ASGestionCuentasImp();
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1");
		assertTrue("La contrasena es incorrecta \n" + ASGestionCuentasImp.bdUsers, as.iniciarSesion("correo10@ucm.es", "contrasena1") == null);
				
	}
	
	public void testSesionIniciadaCorrectamente() {
		
		ASGestionCuentasImp as = new ASGestionCuentasImp();
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1");
		assertTrue("La sesion no se ha iniciado \n" + ASGestionCuentasImp.bdUsers, as.iniciarSesion("correo1@ucm.es", "contrasena1") != null);
		
	}
	
}
