package es.ucm.fdi;


import es.ucm.fdi.negocio.ASGestionCuentaImp;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PruebasEntrega1 extends TestCase {

	/**
	 * @return the suite of tests being tested
	 */
	public  static Test suite() {
		return new TestSuite(PruebasEntrega1.class);
	}

	/*
	 * Ideas  de profesor
	 public void testCrearCuenta(){
		
		BDMemoria bdCadenas=new BDMemoria();
		FactoriaDAO factoria=new FactoriaDAOBDMemoria();
		FactoriaDAO factoria2=new FactoriaDAOMysql();
		
		ASGestionCuentaImp as = new ASGestionCuentaImp(bdCadenas, factoria);
		  //DAOCUenta daoCuentas=factoria.creaDAOCuentas(bdcadenas);
		assertTrue("La BD debía estar vacía y tiene elementos.Contenido actual \n"
						+ ASGestionCuentaImp.bdCadenas, bdCadenas.getIds().isEmpty());
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1");
		assertTrue("La BD debe tener sólo un elemento.Contenido actual \n" + ASGestionCuentaImp.bdCadenas, ASGestionCuentaImp.bdCadenas.getIds().size() == 1);
		
		as.crearCuenta("nombre2", "correo2@ucm.es", "contrasena2");
		assertTrue("La BD debe tener dos elemento.Contenido actual \n"+ ASGestionCuentaImp.bdCadenas, ASGestionCuentaImp.bdCadenas.getIds().size() == 2);
	
	}
	
	 */
	
	
	
	public void testCrearCuenta(){
		
		ASGestionCuentaImp as = new ASGestionCuentaImp();
		
		assertTrue("La BD debía estar vacía y tiene elementos.Contenido actual \n"
						+ ASGestionCuentaImp.bdCadenas, ASGestionCuentaImp.bdCadenas.getIds().isEmpty());
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1");
		assertTrue("La BD debe tener sólo un elemento.Contenido actual \n" + ASGestionCuentaImp.bdCadenas, ASGestionCuentaImp.bdCadenas.getIds().size() == 1);
		
		as.crearCuenta("nombre2", "correo2@ucm.es", "contrasena2");
		assertTrue("La BD debe tener dos elemento.Contenido actual \n"+ ASGestionCuentaImp.bdCadenas, ASGestionCuentaImp.bdCadenas.getIds().size() == 2);
	
	}
	
	public void testIniciarSesion(){
		
		ASGestionCuentaImp as = new ASGestionCuentaImp();
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1");
		as.crearCuenta("Alter", "alter@ucm.es", "alter");
		
		assertTrue("Error al iniciar sesión.", !as.iniciarSesion("correo1@ucm.es", "contrasena1").equals(null));
	}
	
}
