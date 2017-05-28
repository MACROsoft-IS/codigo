package es.ucm.fdi;

import java.util.ArrayList;

import es.ucm.fdi.integracion.TOChat;
import es.ucm.fdi.integracion.TOSesion;
import es.ucm.fdi.integracion.TOUsuario;
import es.ucm.fdi.negocio.ASGestionConocerImp;
import es.ucm.fdi.negocio.ASGestionCuentasImp;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PruebaBloquearUsuario extends TestCase{

	/**
	 * @return the suite of tests being tested
	 */
	public  static Test suite() {
		return new TestSuite(PruebaBloquearUsuario.class);
	}
	
	public void testBloqueoUsuario(){
		//Creamos dos cuentas:
		
		ASGestionCuentasImp as = new ASGestionCuentasImp();
		ASGestionConocerImp ac = new ASGestionConocerImp(as.getUsuarioDAO(),as.getSesionDAO());
		TOChat chat;
		
		boolean encontrado1 = false;
		boolean encontrado2 = false;
		int i = 0;
		String correoUsuario;
		
		as.crearCuenta("usuario1","correousuario1","contrasena1","foto","desc");
		as.crearCuenta("usuario2","correousuario2","contrasena2","foto","desc");
		
		//Creamos las sesiones de ambas
		
		TOSesion sesionusuario1 = as.iniciarSesion("correousuario1","contrasena1");
		TOSesion sesionusuario2 = as.iniciarSesion("correousuario2","contrasena2");
	
		TOUsuario usuario1 = sesionusuario1.getCuentaActiva();
		TOUsuario usuario2 = sesionusuario2.getCuentaActiva();
				
		usuario1.añadirChat("correousuario2");
		usuario2.añadirChat("correousuario1");
		
		ArrayList<TOChat> listaChats1 = usuario1.getListaChats();
		ArrayList<TOChat> listaChats2 = usuario2.getListaChats();
	
		//Mostramos las lista de chats de los dos usuarios
		
		while (i < listaChats1.size() &&
				!listaChats1.get(i).getCorreo().equalsIgnoreCase("correousuario2") ) 
				 {++i;}
		
		assertTrue("El usuario 2 se encuentra en las lista de chats del usuario 1 \n" + 
				as.getUsuarioDAO().getBdUsersConnection(), i < listaChats1.size());

		i = 0;
		while (!encontrado2 && i < listaChats2.size()) {
			chat = listaChats2.get(i); // Recibimos el chat i
			correoUsuario = chat.getCorreo(); // Sacamos el correo del chat i
			if (correoUsuario == "correousuario1") encontrado2 = true;
			++i;
		}
		
		assertTrue("El usuario 1 se encuentra en las lista de chats del usuario 2  \n" + 
				as.getUsuarioDAO().getBdUsersConnection(), encontrado2);
		
		//usuario 1 bloque al usuario 2
		ac.bloquearUsuario(usuario1, usuario2);

		
		//comprobamos que no estan en las listas
		
		encontrado1 = false;
		encontrado2 = false;
		i = 0;
		
		while (!encontrado1 && i < listaChats1.size()) {
			chat = listaChats1.get(i); // Recibimos el chat i
			correoUsuario = chat.getCorreo(); // Sacamos el correo del chat i
			if (correoUsuario == "correousuario2") encontrado1 = true;
			++i;
		}
		
		assertFalse("El usuario 2 se encuentra en las lista de chats del usuario 1 \n" + 
				as.getUsuarioDAO().getBdUsersConnection(), encontrado1);

		i = 0;
		while (!encontrado2 && i < listaChats2.size()) {
			chat = listaChats2.get(i); // Recibimos el chat i
			correoUsuario = chat.getCorreo(); // Sacamos el correo del chat i
			if (correoUsuario == "correousuario1") encontrado2 = true;
			++i;
		}
		
		assertFalse("El usuario 1 se encuentra en las lista de chats del usuario 2  \n" + 
				as.getUsuarioDAO().getBdUsersConnection(), encontrado2);
		
	}
	
}
