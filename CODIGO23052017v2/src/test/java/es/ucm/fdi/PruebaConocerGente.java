package es.ucm.fdi;

import es.ucm.fdi.integracion.TOSesionImp;
import es.ucm.fdi.integracion.TOUsuarioImp;
import es.ucm.fdi.negocio.ASGestionCuentaImp;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PruebaConocerGente extends TestCase {

	/**
	 * @return the suite of tests being tested
	 */
	public  static Test suite() {
		return new TestSuite(PruebaConocerGente.class);
	}
	
	public void testMostrarListaConocidos() {
		
		ASGestionCuentaImp as = new ASGestionCuentaImp();
		
		//Creamos 10 usuarios
		for(int i = 0; i < 10; ++i) as.crearCuenta("usuario" + i,"correousuario" + i, "contrasena");
		
		TOSesionImp sesionusuario1 = as.iniciarSesion("correousuario1", "contrasena");
		
		TOUsuarioImp usuario1 = sesionusuario1.getCuentaActiva();
		
		as.conocerGente(usuario1);
		
	}
	
	public void testListaMasGrande() {
		
		ASGestionCuentaImp as = new ASGestionCuentaImp();
		
		//Creamos una cuenta e iniciamos sesión
		as.crearCuenta("usuario1","correousuario1", "contrasena");
		TOSesionImp sesionusuario1 = as.iniciarSesion("correousuario1", "contrasena");
		TOUsuarioImp usuario1 = sesionusuario1.getCuentaActiva();
		
		//Conocemos a las personas. Sale a la primera iteración del bucle
		as.conocerGente(usuario1);
		
		assertTrue("Se ha mostrado una persona inexistente\n", usuario1.getAvisos().equals("Has conocido a toda la gente. Pronto habrá más personas a las que conocer"));
		
	}
	
	public void testAumentaListas() {
		
		ASGestionCuentaImp as = new ASGestionCuentaImp();
		
		//Creamos una cuenta e iniciamos sesión
		as.crearCuenta("usuario1","correousuario1", "contrasena");
		TOSesionImp sesionusuario1 = as.iniciarSesion("correousuario1", "contrasena");
		TOUsuarioImp usuario1 = sesionusuario1.getCuentaActiva();
		
	}
	
}
