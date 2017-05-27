package es.ucm.fdi;

import es.ucm.fdi.integracion.TOSesion;
import es.ucm.fdi.integracion.TOUsuario;
import es.ucm.fdi.integracion.TOPendiente.tPendiente;
import es.ucm.fdi.negocio.ASGestionCuentasImp;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PruebaEliminarCuenta extends TestCase {
	
	
	public  static Test suite() {
		return new TestSuite(PruebaEliminarCuenta.class);
	}
	
	public void testCuentaEliminada(){
		ASGestionCuentasImp as = new ASGestionCuentasImp();
		int i = 0;
		
		
		//Creamos las cuentas para la prueba
		as.crearCuenta("borrar", "borrar@ucm.es", "contrasena1","foto","desc");
		as.crearCuenta("conocido1", "conocido1@ucm.es", "contrasena2","foto","desc");
		as.crearCuenta("conocido2", "conocido2@ucm.es", "contrasena2","foto","desc");
		as.crearCuenta("pendiente1", "pendiente1@ucm.es", "contrasena2","foto","desc");
		as.crearCuenta("pendiente2", "pendiente2@ucm.es", "contrasena2","foto","desc");
		as.crearCuenta("pendiente3", "pendiente3@ucm.es", "contrasena2","foto","desc");
		as.crearCuenta("pendiente4", "pendiente4@ucm.es", "contrasena2","foto","desc");
		//System.out.println(ASGestionCuentaImp.bdCadenas);
		
		//Añadimos conocidos, chats y pendientes a "borrar".
		TOUsuario borrar = as.getUsuarioDAO().read("borrar@ucm.es");
		borrar.añadirConocido("conocido1@ucm.es");
		borrar.añadirConocido("conocido2@ucm.es");
		borrar.añadirPendiente("pendiente1@ucm.es", tPendiente.MANDO);
		borrar.añadirPendiente("pendiente2@ucm.es", tPendiente.MANDO);
		borrar.añadirPendiente("pendiente3@ucm.es", tPendiente.RECIBO);
		borrar.añadirPendiente("pendiente4@ucm.es", tPendiente.RECIBO);
		borrar.añadirChat("conocido1@ucm.es");
		borrar.añadirChat("conocido2@ucm.es");
		
		//Añadimos conocidos y chats a "conocido"
		TOUsuario conocido1 = as.getUsuarioDAO().read("conocido1@ucm.es");
		conocido1.añadirConocido("conocido2@ucm.es");
		conocido1.añadirConocido("borrar@ucm.es");
		conocido1.añadirChat("conocido2@ucm.es");
		conocido1.añadirChat("borrar@ucm.es");
		
		
		//Añadimos conocidos y chats a "conocido"
		TOUsuario conocido2 = as.getUsuarioDAO().read("conocido2@ucm.es");
		conocido2.añadirConocido("conocido1@ucm.es");
		conocido2.añadirConocido("borrar@ucm.es");
		conocido2.añadirChat("conocido1@ucm.es");
		conocido2.añadirChat("borrar@ucm.es");
		
		//Comprobamos que se ha hecho correctamente
		while (i < conocido2.getListaChats().size() &&
				!conocido2.getListaChats().get(i).getCorreo().equalsIgnoreCase("borrar@ucm.es") ) 
				 {++i;}
			
					
		
		assertTrue("Borrar sigue en la lista de chats" , i < conocido2.getListaChats().size());
		assertTrue("Desconocido no se ha añadido a la lista de conocidos" , conocido2.getListaConocidos().contains("borrar@ucm.es"));
		
		//Añadimos conocidos y chats a "pendiente"
		TOUsuario pendiente1 = as.getUsuarioDAO().read("pendiente1@ucm.es");
		pendiente1.añadirPendiente("pendiente2@ucm.es", tPendiente.MANDO);
		pendiente1.añadirPendiente("pendiente3@ucm.es", tPendiente.RECIBO);
		pendiente1.añadirPendiente("pendiente4@ucm.es", tPendiente.RECIBO);
		pendiente1.añadirPendiente("borrar@ucm.es", tPendiente.RECIBO);
		
		
		//Añadimos conocidos y chats a "pendiente"
		TOUsuario pendiente2 = as.getUsuarioDAO().read("pendiente2@ucm.es");
		pendiente2.añadirPendiente("pendiente1@ucm.es", tPendiente.RECIBO);
		pendiente2.añadirPendiente("pendiente3@ucm.es", tPendiente.MANDO);
		pendiente2.añadirPendiente("pendiente4@ucm.es", tPendiente.RECIBO);
		pendiente2.añadirPendiente("borrar@ucm.es", tPendiente.RECIBO);
		
		
		//Añadimos conocidos y chats a "pendiente"
		TOUsuario pendiente3 = as.getUsuarioDAO().read("pendiente3@ucm.es");
		pendiente3.añadirPendiente("pendiente2@ucm.es", tPendiente.RECIBO);
		pendiente3.añadirPendiente("pendiente1@ucm.es", tPendiente.MANDO);
		pendiente3.añadirPendiente("pendiente4@ucm.es", tPendiente.MANDO);
		pendiente3.añadirPendiente("borrar@ucm.es", tPendiente.MANDO);
		
		
		//Añadimos conocidos y chats a "pendiente"
		TOUsuario pendiente4 = as.getUsuarioDAO().read("pendiente4@ucm.es");
		pendiente4.añadirPendiente("pendiente1@ucm.es", tPendiente.MANDO);
		pendiente4.añadirPendiente("borrar@ucm.es", tPendiente.MANDO);
		pendiente4.añadirPendiente("pendiente2@ucm.es", tPendiente.MANDO);
		pendiente4.añadirPendiente("pendiente3@ucm.es", tPendiente.RECIBO);
		
		//Miramos si se ha hecho correctamente
		i = 0;
		while (i < pendiente4.getListaPendientes().size() &&
				!pendiente4.getListaPendientes().get(i).getCorreo().equalsIgnoreCase("borrar@ucm.es") ) 
				 {++i;}
			
					
		
		assertTrue("Borrar sigue en la lista de pendientes" , i < pendiente4.getListaPendientes().size());
		
		
		//Eliminamos la cuenta
		
		TOSesion sesion = as.iniciarSesion("borrar@ucm.es", "contrasena1");
		as.eliminarCuenta(sesion.getCuentaActiva(), sesion);
		assertTrue("La cuenta todavia existe \n" + ASGestionCuentasImp.bdUsers, as.iniciarSesion("borrar@ucm.es", "contrasena1") == null);
		assertFalse("Las otras cuentas no existen \n" + ASGestionCuentasImp.bdUsers, as.iniciarSesion("conocido1@ucm.es", "contrasena2") == null);
		
		
		
		
		//Miramos de nuevo la lista de conocidos y de chats de "conocido2"
		i = 0;
		while (i < conocido2.getListaChats().size() &&
				!conocido2.getListaChats().get(i).getCorreo().equalsIgnoreCase("borrar@ucm.es") ) 
				 {++i;}
			
					
		
		assertFalse("Borrar sigue en la lista de chats" , i < conocido2.getListaChats().size());
		assertFalse("Borrar sigue en la lista de conocidos" , conocido2.getListaConocidos().contains("borrar@ucm.es"));
		
		
		//Miramos de nuevo la lista de pendientes de "pendiente4"
		i = 0;
		while (i < pendiente4.getListaPendientes().size() &&
				!pendiente4.getListaPendientes().get(i).getCorreo().equalsIgnoreCase("borrar@ucm.es") ) 
				 {++i;}
			
					
		
		assertFalse("Borrar sigue en la lista de pendientes" , i < pendiente4.getListaPendientes().size());
	}
	
	
}
	