package es.ucm.fdi;

import es.ucm.fdi.integracion.TOUsuarioImp;
import es.ucm.fdi.negocio.ASGestionCuentaImp;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PruebaPersonalizarPerfil extends TestCase {

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(PruebaPersonalizarPerfil.class);
	}
	
	public void testValoresIniciales() {
		
		TOUsuarioImp usuario = new TOUsuarioImp("nombre1", "correo1@ucm.es", "contrasena");
		
		assertTrue("No se ha establecido la contrasena original\n", usuario.getContrasena().equals("contrasena"));
		
		assertTrue("No se ha establecido el nombre original\n", usuario.getNombre().equals("nombre1"));
		
		//Quizá quitar
		//assertTrue("No se ha establecido la descripcion original\n", usuario.getDescripcion().equals(null));
		
		//Quizá quitar
		//assertTrue("No se ha establecido la foto original\n", usuario.getFoto().equals(null));
		
	}
	
	public void testCambiarDatos() {
		
		ASGestionCuentaImp as = new ASGestionCuentaImp();
		TOUsuarioImp usuario = new TOUsuarioImp("nombre1", "correo1@ucm.es", "contrasena");
		
		//Comprobamos un cambio de contraseña
		as.personalizarPerfil(usuario, "c", null, null, null);
		assertTrue("No se ha modificado la contrasena\n", usuario.getContrasena().equals("c"));
		
		//Comprobamos un cambio de nombre
		as.personalizarPerfil(usuario, null, "n", null, null);
		assertTrue("No se ha modificado el nombre\n", usuario.getNombre().equals("n"));
		
		//Comprobamos un cambio de descripción
		as.personalizarPerfil(usuario, null, null, "descripcion", null);
		assertTrue("No se ha modificado la descripcion\n", usuario.getDescripcion().equals("descripcion"));
		
		//Comprobamos un cambio de foto
		as.personalizarPerfil(usuario, null, null, null, "foto");
		assertTrue("No se ha modificado la foto", usuario.getFoto().equals("foto"));
		
	}
	
}
