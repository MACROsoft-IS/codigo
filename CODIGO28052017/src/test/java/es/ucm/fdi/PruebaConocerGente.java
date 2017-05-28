package es.ucm.fdi;

import es.ucm.fdi.integracion.TOUsuario;
import es.ucm.fdi.integracion.TOPendiente.tPendiente;
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
	public void testMostrar(){
			
			ASGestionCuentasImp as = new ASGestionCuentasImp();
			int i  = 0;
			
			as.crearCuenta("conocedor", "conocedor@ucm.es", "contrasena1","foto","desc");
			as.crearCuenta("conocido1", "conocido1@ucm.es", "contrasena1","foto","desc");
			as.crearCuenta("desconocido", "desconocido@ucm.es", "contrasena1","foto","desc");			
			as.crearCuenta("pendiente1", "pendiente1@ucm.es", "contrasena1","foto","desc");
			as.crearCuenta("pendiente2", "pendiente2@ucm.es", "contrasena1","foto","desc");
			as.crearCuenta("desconocido2", "desconocido2@ucm.es", "contrasena1","foto","desc");
			
			
			
			
		
			
			TOUsuario pendiente1 = as.getUsuarioDAO().read("pendiente1@ucm.es");
			
			pendiente1.añadirPendiente("pendiente2@ucm.es", tPendiente.MANDO);
		
					
			
			TOUsuario conocedor = as.getUsuarioDAO().read("conocedor@ucm.es");
			conocedor.añadirConocido("conocido1@ucm.es");
			conocedor.añadirPendiente("pendiente1@ucm.es", tPendiente.MANDO);
			conocedor.añadirPendiente("pendiente2@ucm.es", tPendiente.RECIBO);
			
			TOUsuario desconocido = as.getUsuarioDAO().read("desconocido@ucm.es");
			TOUsuario desconocido2 = as.getUsuarioDAO().read("desconocido2@ucm.es");
			
			
			
			assertFalse("Desconocido ya esta en la lista de conocedor" , conocedor.getListaConocidos().contains("desconocido@ucm.es"));
			
			//Este es el case 1 resultado del metodo "muestra" de ASGestionConocerImp. REPRESENTA EL SI DEL MUESTRA.
			
				while (i < desconocido.getListaPendientes().size() &&
						!desconocido.getListaPendientes().get(i).getCorreo().equalsIgnoreCase("conocedor@ucm.es") ) 
						 {++i;}
					
							
				
				assertFalse("Conocedor ya esta en la lista de pendientes" , i < desconocido.getListaPendientes().size());
				
				
				i = 0;
				while (i < conocedor.getListaPendientes().size() &&
						!conocedor.getListaPendientes().get(i).getCorreo().equalsIgnoreCase("desconocido@ucm.es") ) 
						 {++i;}
				assertFalse("Desconocido ya esta en la lista de pendientes" , i < conocedor.getListaPendientes().size());
				
				
				conocedor.añadirPendiente("desconocido@ucm.es", tPendiente.MANDO);         //Añadimos pendiente en el usuario principal
				desconocido.añadirPendiente("conocedor@ucm.es", tPendiente.RECIBO); //Añadimos pendiente en el usuarioAlt
				conocedor.añadirConocido("desconocido@ucm.es"); 
				
				
				
				i = 0;
				while (i < desconocido.getListaPendientes().size() &&
						!desconocido.getListaPendientes().get(i).getCorreo().equalsIgnoreCase("conocedor@ucm.es") ) 
						 {++i;}
				
				assertTrue("Conocedor no esta en la lista de pendientes" , i < desconocido.getListaPendientes().size());
				
				i = 0;
				while (i < conocedor.getListaPendientes().size() &&
						!conocedor.getListaPendientes().get(i).getCorreo().equalsIgnoreCase("desconocido@ucm.es") ) 
						 {++i;}
				
				assertTrue("Desconocido no esta en la lista de conocidos" , conocedor.getListaConocidos().contains("desconocido@ucm.es"));
				
				assertTrue("Desconocido no esta en la lista de pendientes" , i < conocedor.getListaPendientes().size());
			
			//Este es el case 2 resultado del metodo "muestra" de ASGestionConocerImp. REPRESENTA EL NO DEL MUESTRA.
				
				
				assertFalse("Desconocido ya esta en la lista de conocidos" , conocedor.getListaConocidos().contains("desconocido2@ucm.es"));
				assertFalse("Conocedor ya esta en la lista de conocidos" , desconocido2.getListaConocidos().contains("conocedor@ucm.es"));
				
				conocedor.añadirConocido("desconocido2@ucm.es");                            //Añadimos conocido al principal
				desconocido2.añadirConocido("conocedor@ucm.es");                   		    //Añadimos conocido al Alt
			
				assertTrue("Desconocido no se ha añadido a la lista de conocidos" , conocedor.getListaConocidos().contains("desconocido2@ucm.es"));
				assertTrue("Conocedor no se ha añadido a la lista de conocidos" , desconocido2.getListaConocidos().contains("conocedor@ucm.es"));
			
		}
	
	
	public void testMostrarPendiente(){
		
		ASGestionCuentasImp as = new ASGestionCuentasImp();
		int i  = 0;
		
		as.crearCuenta("conocedorP", "conocedorP@ucm.es", "contrasena1","foto","desc");
		as.crearCuenta("conocido1P", "conocido1P@ucm.es", "contrasena1","foto","desc");		
		as.crearCuenta("pendiente1P", "pendiente1P@ucm.es", "contrasena1","foto","desc");
		as.crearCuenta("pendiente2P", "pendiente2P@ucm.es", "contrasena1","foto","desc");
		
		
		
		
		
		//Este es el case 1 resultado del metodo "muestraPendiente" de ASGestionConocerImp. REPRESENTA EL SI DEL MUESTRAPENDIENTE.
		
		TOUsuario pendiente2P = as.getUsuarioDAO().read("pendiente2P@ucm.es");
		pendiente2P.añadirPendiente("conocedorP@ucm.es", tPendiente.MANDO);
		
		TOUsuario pendiente1P = as.getUsuarioDAO().read("pendiente1P@ucm.es");
		pendiente1P.añadirPendiente("pendiente2P@ucm.es", tPendiente.MANDO);
		pendiente1P.añadirPendiente("conocedorP@ucm.es", tPendiente.RECIBO);
		
	
		TOUsuario conocedorP = as.getUsuarioDAO().read("conocedorP@ucm.es");
		conocedorP.añadirConocido("conocido1P@ucm.es");
		conocedorP.añadirPendiente("pendiente1P@ucm.es", tPendiente.MANDO);
		conocedorP.añadirPendiente("pendiente2P@ucm.es", tPendiente.RECIBO);
		
		
		
		
		while (i < conocedorP.getListaPendientes().size() &&
				!conocedorP.getListaPendientes().get(i).getCorreo().equalsIgnoreCase("pendiente1P@ucm.es") ) 
				 {++i;}
			
					
		
		assertTrue("Pendiente no esta en la lista de pendientes" , i < conocedorP.getListaPendientes().size());
		
		i = 0;
		while (i < pendiente1P.getListaPendientes().size() &&
				!pendiente1P.getListaPendientes().get(i).getCorreo().equalsIgnoreCase("conocedorP@ucm.es") ) 
				 {++i;}
			
			
		
		assertTrue("Conocedor no esta en la lista de pendientes" , i < pendiente1P.getListaPendientes().size());
		
		//No hacemos un assert de los chats porque ya estan hechos en la prueba PruebaChat.
		
		conocedorP.añadirChat("pendiente1P@ucm.es");               // Creamos chat en el usuario principal
		pendiente1P.añadirChat("conocedorP@ucm.es");        // Creamos chat en el usuarioAlt
		conocedorP.añadirConocido("pendiente1P@ucm.es");           // Añadimos conocido en el usuario
		conocedorP.eliminarPendiente("pendiente1P@ucm.es");        // Eliminamos pendientes en el usuario principal
		pendiente1P.eliminarPendiente("conocedorP@ucm.es"); // Eliminamos pendientes en el usuarioAlt
		
		i = 0;
		while (i < pendiente1P.getListaPendientes().size() &&
				!pendiente1P.getListaPendientes().get(i).getCorreo().equalsIgnoreCase("conocedorP@ucm.es") ) 
				 {++i;}
			
		
		
		assertFalse("Conocedor sigue estando en la lista de pendientes" , i < pendiente1P.getListaPendientes().size());
		
		i=0;
		while (i < conocedorP.getListaPendientes().size() &&
				!conocedorP.getListaPendientes().get(i).getCorreo().equalsIgnoreCase("pendiente1P@ucm.es") ) 
				 {++i;}
			
					
		
		assertFalse("Pendiente sigue estando en la lista de pendientes" , i < conocedorP.getListaPendientes().size());
		
		
		//Este es el case 2 resultado del metodo "muestraPendiente" de ASGestionConocerImp. REPRESENTA EL NO DEL MUESTRAPENDIENTE.
		i = 0;
		while (i < conocedorP.getListaPendientes().size() &&
				!conocedorP.getListaPendientes().get(i).getCorreo().equalsIgnoreCase("pendiente2P@ucm.es") ) 
				 {++i;}
			
					
		
		assertTrue("Pendiente no esta en la lista de pendientes" , i < conocedorP.getListaPendientes().size());
		
		i = 0;
		while (i < pendiente2P.getListaPendientes().size() &&
				!pendiente2P.getListaPendientes().get(i).getCorreo().equalsIgnoreCase("conocedorP@ucm.es") ) 
				 {++i;}
			
			
		
		assertTrue("Conocedor no esta en la lista de pendientes" , i < pendiente2P.getListaPendientes().size());
		
		assertFalse("Pendiente ya esta en la lista de conocidos" , conocedorP.getListaConocidos().contains("pendiente2P@ucm.es"));
		
		
		
		
		
		
		conocedorP.añadirConocido("pendiente2P@ucm.es");           // Añadimos conocido en el usuario
		conocedorP.eliminarPendiente("pendiente2P@ucm.es");        // Eliminamos pendiente en el usuario principal
		pendiente2P.eliminarPendiente("conocedorP@ucm.es"); // Eliminamos pendiente en el usuarioAlt
		
		
		assertTrue("Pendiente no esta en la lista de conocidos" , conocedorP.getListaConocidos().contains("pendiente2P@ucm.es"));
		
		
		
		
		i = 0;
		while (i < pendiente1P.getListaPendientes().size() &&
				!pendiente1P.getListaPendientes().get(i).getCorreo().equalsIgnoreCase("conocedorP@ucm.es") ) 
				 {++i;}
			
		
		
		assertFalse("Conocedor sigue estando en la lista de pendientes" , i < pendiente1P.getListaPendientes().size());
		
		i=0;
		while (i < conocedorP.getListaPendientes().size() &&
				!conocedorP.getListaPendientes().get(i).getCorreo().equalsIgnoreCase("pendiente1P@ucm.es") ) 
				 {++i;}
		assertFalse("Pendiente sigue estando en la lista de pendientes" , i < conocedorP.getListaPendientes().size());
	}
	

}
