package es.ucm.fdi;

import java.util.ArrayList;

import es.ucm.fdi.integracion.TOSesionImp;
import es.ucm.fdi.integracion.TOUsuarioImp;
import es.ucm.fdi.integracion.TOUsuarioImp.Chat;
import es.ucm.fdi.negocio.ASGestionCuentaImp;
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
		
		ASGestionCuentaImp as = new ASGestionCuentaImp();
		Chat chat;
		
		boolean encontrado1 = false;
		boolean encontrado2 = false;
		int i = 0;
		String correoUsuario;
		
		as.crearCuenta("usuario1","correousuario1","contrasena1");
		as.crearCuenta("usuario2","correousuario2","contrasena2");
		
		//Creamos las sesiones de ambas
		
		TOSesionImp sesionusuario1 = as.iniciarSesion("correousuario1","contrasena1");
		TOSesionImp sesionusuario2 = as.iniciarSesion("correousuario2","contrasena2");
	
		TOUsuarioImp usuario1 = sesionusuario1.getCuentaActiva();
		TOUsuarioImp usuario2 = sesionusuario2.getCuentaActiva();
				
		usuario1.añadirChat("correousuario2");
		usuario2.añadirChat("correousuario1");
		
		ArrayList<Chat> listaChats1 = usuario1.getListaChats();
		ArrayList<Chat> listaChats2 = usuario2.getListaChats();
	
		//Mostramos las lista de chats de los dos usuarios
		
		while (i < listaChats1.size() &&
				!listaChats1.get(i).getCorreo().equalsIgnoreCase("correousuario2") ) 
				 {++i;}
		
		assertTrue("El usuario 2 se encuentra en las lista de chats del usuario 1 \n" + 
				ASGestionCuentaImp.bdCadenas, i < listaChats1.size());

		i = 0;
		while (!encontrado2 && i < listaChats2.size()) {
			chat = listaChats2.get(i); // Recibimos el chat i
			correoUsuario = chat.getCorreo(); // Sacamos el correo del chat i
			if (correoUsuario == "correousuario1") encontrado2 = true;
			++i;
		}
		
		assertTrue("El usuario 1 se encuentra en las lista de chats del usuario 2  \n" + 
				ASGestionCuentaImp.bdCadenas, encontrado2);
		
		//usuario 1 bloque al usuario 2
		as.bloquearUsuario(usuario1, usuario2);

		
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
				ASGestionCuentaImp.bdCadenas, encontrado1);

		i = 0;
		while (!encontrado2 && i < listaChats2.size()) {
			chat = listaChats2.get(i); // Recibimos el chat i
			correoUsuario = chat.getCorreo(); // Sacamos el correo del chat i
			if (correoUsuario == "correousuario1") encontrado2 = true;
			++i;
		}
		
		assertFalse("El usuario 1 se encuentra en las lista de chats del usuario 2  \n" + 
				ASGestionCuentaImp.bdCadenas, encontrado2);
		
	}
	
}
