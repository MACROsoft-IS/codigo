package es.ucm.fdi;

import es.ucm.fdi.integracion.TOSesion;
import es.ucm.fdi.negocio.Pendiente.tPendiente;
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
		
		
		//Creamos las cuentas para la prueba
		as.crearCuenta("borrar", "borrar@ucm.es", "contrasena1");
		as.crearCuenta("conocido1", "conocido1@ucm.es", "contrasena2");
		as.crearCuenta("conocido2", "conocido2@ucm.es", "contrasena2");
		as.crearCuenta("pendiente1", "pendiente1@ucm.es", "contrasena2");
		as.crearCuenta("pendiente2", "pendiente2@ucm.es", "contrasena2");
		as.crearCuenta("pendiente3", "pendiente3@ucm.es", "contrasena2");
		as.crearCuenta("pendiente4", "pendiente4@ucm.es", "contrasena2");
		//System.out.println(ASGestionCuentaImp.bdCadenas);
		
		//Añadimos conocidos, chats y pendientes a "borrar".
		TOSesion usuario = as.iniciarSesion("borrar@ucm.es", "contrasena1");
		usuario.getCuentaActiva().añadirConocido("conocido1@ucm.es");
		usuario.getCuentaActiva().añadirConocido("conocido2@ucm.es");
		usuario.getCuentaActiva().añadirPendiente("pendiente1@ucm.es", tPendiente.MANDO);
		usuario.getCuentaActiva().añadirPendiente("pendiente2@ucm.es", tPendiente.MANDO);
		usuario.getCuentaActiva().añadirPendiente("pendiente3@ucm.es", tPendiente.RECIBO);
		usuario.getCuentaActiva().añadirPendiente("pendiente4@ucm.es", tPendiente.RECIBO);
		usuario.getCuentaActiva().añadirChat("conocido1@ucm.es");
		usuario.getCuentaActiva().añadirChat("conocido2@ucm.es");
		
		//Miramos si hemos añadido bien las cosas
		System.out.println("Informacion de borrar@ucm.es (Conocidos, Pendientes y Chats)");
		System.out.println(usuario.getCuentaActiva().showConocidos());
		System.out.println(usuario.getCuentaActiva().showPendientes());
		System.out.println(usuario.getCuentaActiva().showChats()+ "\n\n");
		as.cerrarSesion(usuario);
		
		//Añadimos conocidos y chats a "conocido"
		usuario = as.iniciarSesion("conocido1@ucm.es", "contrasena2");
		usuario.getCuentaActiva().añadirConocido("conocido2@ucm.es");
		usuario.getCuentaActiva().añadirConocido("borrar@ucm.es");
		usuario.getCuentaActiva().añadirChat("conocido2@ucm.es");
		usuario.getCuentaActiva().añadirChat("borrar@ucm.es");
		as.cerrarSesion(usuario);
		
		//Añadimos conocidos y chats a "conocido"
		usuario = as.iniciarSesion("conocido2@ucm.es", "contrasena2");
		usuario.getCuentaActiva().añadirConocido("conocido1@ucm.es");
		usuario.getCuentaActiva().añadirConocido("borrar@ucm.es");
		usuario.getCuentaActiva().añadirChat("borrar@ucm.es");
		usuario.getCuentaActiva().añadirChat("conocido1@ucm.es");
		
		//Miramos si añadimos bien las cosas
		System.out.println("Informacion de conocido2@ucm.es (Conocidos y Chats)");
		System.out.println(usuario.getCuentaActiva().showConocidos());
		System.out.println(usuario.getCuentaActiva().showChats()+ "\n\n");
		as.cerrarSesion(usuario);
		
		//Añadimos conocidos y chats a "pendiente"
		usuario = as.iniciarSesion("pendiente1@ucm.es", "contrasena2");
		usuario.getCuentaActiva().añadirPendiente("pendiente2@ucm.es", tPendiente.MANDO);
		usuario.getCuentaActiva().añadirPendiente("pendiente3@ucm.es", tPendiente.RECIBO);
		usuario.getCuentaActiva().añadirPendiente("pendiente4@ucm.es", tPendiente.RECIBO);
		usuario.getCuentaActiva().añadirPendiente("borrar@ucm.es", tPendiente.RECIBO);
		
		as.cerrarSesion(usuario);
		
		//Añadimos conocidos y chats a "pendiente"
		usuario = as.iniciarSesion("pendiente2@ucm.es", "contrasena2");
		usuario.getCuentaActiva().añadirPendiente("pendiente1@ucm.es", tPendiente.RECIBO);
		usuario.getCuentaActiva().añadirPendiente("pendiente3@ucm.es", tPendiente.MANDO);
		usuario.getCuentaActiva().añadirPendiente("pendiente4@ucm.es", tPendiente.RECIBO);
		usuario.getCuentaActiva().añadirPendiente("borrar@ucm.es", tPendiente.RECIBO);
		as.cerrarSesion(usuario);
		
		//Añadimos conocidos y chats a "pendiente"
		usuario = as.iniciarSesion("pendiente3@ucm.es", "contrasena2");
		usuario.getCuentaActiva().añadirPendiente("pendiente2@ucm.es", tPendiente.RECIBO);
		usuario.getCuentaActiva().añadirPendiente("pendiente1@ucm.es", tPendiente.MANDO);
		usuario.getCuentaActiva().añadirPendiente("pendiente4@ucm.es", tPendiente.MANDO);
		usuario.getCuentaActiva().añadirPendiente("borrar@ucm.es", tPendiente.MANDO);
		as.cerrarSesion(usuario);
		
		//Añadimos conocidos y chats a "pendiente"
		usuario = as.iniciarSesion("pendiente4@ucm.es", "contrasena2");
		usuario.getCuentaActiva().añadirPendiente("pendiente1@ucm.es", tPendiente.MANDO);
		usuario.getCuentaActiva().añadirPendiente("borrar@ucm.es", tPendiente.MANDO);
		usuario.getCuentaActiva().añadirPendiente("pendiente2@ucm.es", tPendiente.MANDO);
		usuario.getCuentaActiva().añadirPendiente("pendiente3@ucm.es", tPendiente.RECIBO);
		//Mostramos la lista de pendientes de "pendiente4"
		System.out.println("Informacion de pendiente4@ucm.es (Pendientes)");
		System.out.println(usuario.getCuentaActiva().showPendientes() + "\n\n");
		as.cerrarSesion(usuario);
		
		//Eliminamos la cuenta
		usuario = as.iniciarSesion("borrar@ucm.es", "contrasena1");
		as.eliminarCuenta(usuario.getCuentaActiva(), usuario);
		assertTrue("La cuenta todavia existe \n" + ASGestionCuentasImp.bdUsers, as.iniciarSesion("borrar@ucm.es", "contrasena1") == null);
		assertFalse("Las otras cuentas no existen \n" + ASGestionCuentasImp.bdUsers, as.iniciarSesion("conocido1@ucm.es", "contrasena2") == null);
		
		
		
		
		//Miramos de nuevo la lista de conocidos y de chats de "conocido2"
		usuario = as.iniciarSesion("conocido2@ucm.es", "contrasena2");
		assertTrue("No se han borrado bien los conocidos", usuario.getCuentaActiva().showConocidos().equals("conocido2@ucm.es conocido1@ucm.es "));
		assertTrue("No se han borrado bien los chats", usuario.getCuentaActiva().showChats().equals("conocido1@ucm.es "));
		
		
		System.out.println("Informacion de conocido2@ucm.es DESPUES DE BORRAR CUENTA(Conocidos y Chats)");
		System.out.println(usuario.getCuentaActiva().showConocidos());
		System.out.println(usuario.getCuentaActiva().showChats() + "\n\n");
		
		
		//Miramos de nuevo la lista de pendientes de "pendiente4"
		usuario = as.iniciarSesion("pendiente4@ucm.es", "contrasena2");
		assertTrue("No se han borrado bien los pendientes", usuario.getCuentaActiva().showPendientes().equals("pendiente1@ucm.es pendiente2@ucm.es pendiente3@ucm.es "));
		
		System.out.println("Informacion de pendiente4@ucm.es DESPUES DE BORRAR CUENTA (Pendientes)");
		System.out.println(usuario.getCuentaActiva().showPendientes());
		as.cerrarSesion(usuario);
	}
	
	
}
	