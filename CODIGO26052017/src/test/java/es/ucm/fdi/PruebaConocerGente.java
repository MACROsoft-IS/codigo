package es.ucm.fdi;

import es.ucm.fdi.integracion.TOSesion;
import es.ucm.fdi.integracion.TOUsuario;
import es.ucm.fdi.negocio.ASGestionConocerImp;
import es.ucm.fdi.negocio.ASGestionCuentasImp;
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
		
		ASGestionCuentasImp as = new ASGestionCuentasImp();
		ASGestionConocerImp asconocer = new ASGestionConocerImp(as.getUsuarioDAO(), as.getSesionDAO());
		
		//Creamos 10 usuarios
		for(int i = 0; i < 10; ++i) as.crearCuenta("usuario" + i,"correousuario" + i, "contrasena","foto" + i, "descripcion" + i) ;
		
		TOSesion sesionusuario1 = as.iniciarSesion("correousuario1", "contrasena");
		
		TOUsuario usuario1 = sesionusuario1.getCuentaActiva();
		
		asconocer.conocerGente(usuario1);
		
	}
	
	public void testListaMasGrande() {
		
		ASGestionCuentasImp as = new ASGestionCuentasImp();
		ASGestionConocerImp asconocer = new ASGestionConocerImp(as.getUsuarioDAO(), as.getSesionDAO());

		
		//Creamos una cuenta e iniciamos sesión
		as.crearCuenta("usuario1","correousuario1", "contrasena","foto1","descripcion1");
		TOSesion sesionusuario1 = as.iniciarSesion("correousuario1", "contrasena");
		TOUsuario usuario1 = sesionusuario1.getCuentaActiva();
		
		//Conocemos a las personas. Sale a la primera iteración del bucle
		asconocer.conocerGente(usuario1);
		
		assertTrue("Se ha mostrado una persona inexistente\n", usuario1.getAvisos().equals("Has conocido a toda la gente. Pronto habrá más personas a las que conocer"));
		
	}
	
	public void testAumentaListas() {
		
		ASGestionCuentasImp as = new ASGestionCuentasImp();
		
		//Creamos una cuenta e iniciamos sesión
		as.crearCuenta("usuario1","correousuario1", "contrasena","foto1","descripcion1");
		TOSesion sesionusuario1 = as.iniciarSesion("correousuario1", "contrasena");
		TOUsuario usuario1 = sesionusuario1.getCuentaActiva();
		
	}
	
}
