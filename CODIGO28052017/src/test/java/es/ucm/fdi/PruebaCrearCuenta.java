package es.ucm.fdi;

import es.ucm.fdi.negocio.ASGestionCuentasImp;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PruebaCrearCuenta extends TestCase {

	/**
	 * @return the suite of tests being tested
	 */
	public  static Test suite() {
		return new TestSuite(PruebaCrearCuenta.class);
	}
	
	public void testCrearCuenta(){
		
		ASGestionCuentasImp as = new ASGestionCuentasImp();
		
		assertTrue("La BD debía estar vacía y tiene elementos.Contenido actual \n" + as.getUsuarioDAO().getBdUsersConnection(), as.getUsuarioDAO().getBdUsersConnection().getIds().isEmpty());
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1","foto","description");
		assertTrue("La BD debe tener sólo un elemento.Contenido actual \n" + as.getUsuarioDAO().getBdUsersConnection(), as.getUsuarioDAO().getBdUsersConnection().getIds().size() == 1);
		
		as.crearCuenta("nombre2", "correo2@ucm.es", "contrasena2","foto","description");
		assertTrue("La BD debe tener dos elemento.Contenido actual \n"+ as.getUsuarioDAO().getBdUsersConnection(), as.getUsuarioDAO().getBdUsersConnection().getIds().size() == 2);
	
	}
	
	public void testCampoVacio() {
		
		ASGestionCuentasImp as = new ASGestionCuentasImp();
		
		assertFalse("Se ha creado la cuenta sin el nombre \n" + as.getUsuarioDAO().getBdUsersConnection(), as.crearCuenta(null, "correo1@ucm.es", "contrasena1","foto","description"));
		
		assertFalse("Se ha creado la cuenta sin el correo \n" + as.getUsuarioDAO().getBdUsersConnection(), as.crearCuenta("nombre1", null, "contrasena1","foto","description"));
		
		assertFalse("Se ha creado la cuenta sin la contraseña \n" + as.getUsuarioDAO().getBdUsersConnection(), as.crearCuenta("nombre1", "correo1@ucm.es",null,"foto","description"));
		
		assertFalse("Se ha creado la cuenta sin la contraseña \n" + as.getUsuarioDAO().getBdUsersConnection(), as.crearCuenta("nombre1", "correo1@ucm.es","contrasena1",null,"description"));
		
		assertFalse("Se ha creado la cuenta sin la contraseña \n" + as.getUsuarioDAO().getBdUsersConnection(), as.crearCuenta("nombre1", "correo1@ucm.es","contrasena1","foto",null));
	}
	
	public void testCorreoRepetido() {
		
		ASGestionCuentasImp as = new ASGestionCuentasImp();
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1","foto","description");
		
		assertFalse("Se ha creado una cuenta con correo repetido \n" + as.getUsuarioDAO().getBdUsersConnection(), as.crearCuenta("nombre2", "correo1@ucm.es", "contrasena1",null,null));
		
	}
	
	public void testNombreRepetido() {
		
		ASGestionCuentasImp as = new ASGestionCuentasImp();
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1","foto","description");
		
		assertTrue("Se ha creado una cuenta sin nombre repetido \n" + as.getUsuarioDAO().getBdUsersConnection(), as.crearCuenta("nombre1", "correo2@ucm.es", "contrasena1","foto","description"));
		
	}
	
}
