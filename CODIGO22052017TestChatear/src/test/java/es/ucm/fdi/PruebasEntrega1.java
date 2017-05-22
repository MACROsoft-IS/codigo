package es.ucm.fdi;


import java.util.ArrayList;

import es.ucm.fdi.integracion.TOSesionImp;
import es.ucm.fdi.integracion.TOUsuarioImp;
import es.ucm.fdi.integracion.TOUsuarioImp.Pendiente;
import es.ucm.fdi.integracion.TOUsuarioImp.Pendiente.tPendiente;
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
		
		FactoriaDAO factoria2=new FactoriaDAOMysql();
		
		DAOCadenas daoCadenas=new DAOCadenas();
		daoCadenas.insert(asdasda);
		
		FactoriaDAO factoria=new FactoriaDAOBDMemoria(bdCadenas, bdCadenas1,bdCadenas2); 
		FactoriaDAO factoria=new FactoriaDAOBDMemoria();
		
		ASGestionCuentaImp as = new ASGestionCuentaImp(daoCadenas);
		ASGestionCuentaImp as = new ASGestionCuentaImp(factoria);
		
		  //DAOCUenta daoCuentas=factoria.creaDAOCuentas(bdcadenas);
		assertTrue("La BD debía estar vacía y tiene elementos.Contenido actual \n"
						+ ASGestionCuentaImp.bdCadenas, bdCadenas.getIds().isEmpty());
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1");
		assertTrue("La BD debe tener sólo un elemento.Contenido actual \n" + ASGestionCuentaImp.bdCadenas, ASGestionCuentaImp.bdCadenas.getIds().size() == 1);
		
		as.crearCuenta("nombre2", "correo2@ucm.es", "contrasena2");
		assertTrue("La BD debe tener dos elemento.Contenido actual \n"+ ASGestionCuentaImp.bdCadenas, ASGestionCuentaImp.bdCadenas.getIds().size() == 2);
	
	}
	
	 */
	
	
	
	/*public void testCrearCuenta(){
		
		ASGestionCuentaImp as = new ASGestionCuentaImp();
		
		assertTrue("La BD debía estar vacía y tiene elementos.Contenido actual \n"
						+ ASGestionCuentaImp.bdCadenas, ASGestionCuentaImp.bdCadenas.getIds().isEmpty());
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1");
		assertTrue("La BD debe tener sólo un elemento.Contenido actual \n" + ASGestionCuentaImp.bdCadenas, ASGestionCuentaImp.bdCadenas.getIds().size() == 1);
		
		as.crearCuenta("nombre2", "correo2@ucm.es", "contrasena2");
		assertTrue("La BD debe tener dos elemento.Contenido actual \n"+ ASGestionCuentaImp.bdCadenas, ASGestionCuentaImp.bdCadenas.getIds().size() == 2);
	
	}
	*/
	
	public void testIniciarSesion(){
		
		ASGestionCuentaImp as = new ASGestionCuentaImp();
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1");
		as.crearCuenta("Alter", "alter@ucm.es", "alter");
		
		assertTrue("Error al iniciar sesión.", !as.iniciarSesion("correo1@ucm.es", "contrasena1").equals(null));
	}
	
	public void testPrueba(){
		TOUsuarioImp usuario = new TOUsuarioImp("correo", "Nombre", "Contraeña");
		
		usuario.añadirPendiente("paquito", tPendiente.MANDO);
		usuario.añadirPendiente("juani", tPendiente.RECIBO);
		
		usuario.eliminarPendiente("juani");
		
		ArrayList<Pendiente> lista = usuario.getListaPendientes();
		
		if (lista.contains("juani")) System.out.println("La has cagao");
		else System.out.println("eres un crack");
		
		System.out.println(lista.size());
		
		assertTrue("Solo deberia haber un pendiente", lista.size() == 1);
	}
	
    public void testEliminarCuenta(){
		
		ASGestionCuentaImp as = new ASGestionCuentaImp();
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1");
		TOUsuarioImp to = new TOUsuarioImp("nombre1", "correo1@ucm.es", "contrasena1");
		
		as.eliminarCuenta(to, as.iniciarSesion("correo1@ucm.es", "contrasena1"));
		
		assertTrue("No deberia de existir la cuenta con correo correo1@ucm.es", as.getUsuarioDAO().read("correo1@ucm.es") == null);
	}

    public void testCerrarSesion(){
	

		ASGestionCuentaImp as = new ASGestionCuentaImp();
		
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1");
		TOUsuarioImp to = new TOUsuarioImp("nombre1", "correo1@ucm.es", "contrasena1");
		TOSesionImp tos = new TOSesionImp(to);

		as.iniciarSesion("correo1@ucm.es", "contrasena1");
		as.cerrarSesion(tos);
		
		assertTrue("No deberia de existir la sesion con correo correo1@ucm.es",tos.getSesionID().equals(null) && tos.getCuentaActiva() == null);
}
    
    
	
}
