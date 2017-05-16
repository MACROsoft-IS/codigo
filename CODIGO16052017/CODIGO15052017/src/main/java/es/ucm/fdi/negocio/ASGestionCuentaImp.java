package es.ucm.fdi.negocio;

import java.util.ArrayList;


import java.util.Scanner;

//import es.ucm.fdi.datos.BDMemoria;
import es.ucm.fdi.integracion.DAOSesionImp;
import es.ucm.fdi.integracion.DAOUsuarioImp;
import es.ucm.fdi.integracion.TOSesionImp;
import es.ucm.fdi.integracion.TOUsuarioImp;
import es.ucm.fdi.integracion.TOUsuarioImp.Chat;
//import es.ucm.fdi.integracion.TOUsuarioImp;
import es.ucm.fdi.integracion.TOUsuarioImp.Pendiente;

public class ASGestionCuentaImp {

	private DAOUsuarioImp usuarioDAO;
	private DAOSesionImp sesionDAO;
	//public static BDMemoria<TOUsuarioImp> bdCadenas;

	public ASGestionCuentaImp() {
		//ASGestionCuentaImp.bdCadenas = new BDMemoria<TOUsuarioImp>();
		this.sesionDAO = new DAOSesionImp();
		this.usuarioDAO = new DAOUsuarioImp();
	}

	public DAOUsuarioImp getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(DAOUsuarioImp usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public DAOSesionImp getSesionDAO() {
		return sesionDAO;
	}

	public void setSesionDAO(DAOSesionImp sesionDAO) {
		this.sesionDAO = sesionDAO;
	}

	public TOSesionImp iniciarSesion(String correo, String contrasena) {

		TOSesionImp sesion = null;
		TOUsuarioImp usuario = usuarioDAO.read(correo); 

		if (usuario!=null && usuario.getContrasena().equals(contrasena))

			sesion = sesionDAO.createSesion(usuario);

		return sesion;
	}

	/*
	public BDMemoria<TOUsuarioImp> getBdCadenas() {
		return bdCadenas;
	}

	public void setBdCadenas(BDMemoria<TOUsuarioImp> bdCadenas) {
		ASGestionCuentaImp.bdCadenas = bdCadenas;
	}*/

	public boolean crearCuenta(String nombre, String correo, String contrasena) {

		boolean cuenta = false;
		
		if (usuarioDAO.read(correo) == null) {
			
			TOUsuarioImp usuario = new TOUsuarioImp(nombre, correo, contrasena);
			usuarioDAO.create(usuario);
			cuenta = true;
		}
		return cuenta;
	
	}
	
	public void cerrarSesion(TOSesionImp sesion){
		sesionDAO.closeSesion(sesion);
	}
	
	public void eliminarCuenta(TOUsuarioImp usuario, TOSesionImp sesion){
		
		ArrayList<String> listaConocidos = usuario.getListaConocidos(); // Hacemos copia de la lista de conocidos
		ArrayList<Pendiente> listaPendientes = usuario.getListaPendientes(); // Hacemos copia de la lista de pendientes
		ArrayList<Chat> listaChats = usuario.getListaChats(); // Hacemos copia de la lista de chats
		String correoUsuario;
		TOUsuarioImp usuarioAlt;
		Pendiente pendiente;
		Chat chat;

		
		// Lista de conocidos
		for (int i = 0; i < listaConocidos.size(); i++){ // Recorremos la lista de conocidos
			correoUsuario = listaConocidos.get(i); // Recibimos el correo i
			usuarioAlt = usuarioDAO.read(correoUsuario); // Con el correo sacamos el usuarioAlt
			usuarioAlt.eliminarConocido(usuario.getCorreo()); // Borramos de la lista de conocidos del usuarioAlt a el usuario que quiere borrar la cuenta
		}
		
		// Lista de pendientes
		for (int i = 0; i < listaPendientes.size(); i++){ // Recorremos la lista de pendientes
			pendiente = listaPendientes.get(i); // Recibimos el pendiente i
			correoUsuario = pendiente.getCorreo(); // Sacamos el correo del pendiente i
			usuarioAlt = usuarioDAO.read(correoUsuario); // Con el correo sacamos el usuario
			usuarioAlt.eliminarPendiente(usuario.getCorreo()); // Borramos de la lista de pendientes del usuarioAlt a el usuario que quiere borrar la cuenta
		}
		
		// Lista de chats
		for (int i = 0; i < listaChats.size(); i++){ // Recorremos la lista de chats
			chat = listaChats.get(i); // Recibimos el chat i
			correoUsuario = chat.getCorreo(); // Sacamos el correo del chat i
			usuarioAlt = usuarioDAO.read(correoUsuario); // Con el correo sacamos el usuarioAlt 
			usuarioAlt.eliminarChat(usuario.getCorreo()); // Borramos de la lista de chats del usuarioAlt a el usuario que quiere borrar la cuenta
		}
		
		// Eliminamos cuenta
		usuarioDAO.removeAccount(usuario.getCorreo());
		
		// Cerramos sesion
		cerrarSesion(sesion);
		
	}
	
	public int opcion() {
		
		int opcion;
		
		System.out.println("1.- Si" + System.getProperty("line.separator") + "2.- No");
		
		Scanner in = new Scanner(System.in);
		
		opcion = in.nextInt();
		
		while(opcion != 1 && opcion != 2) {
			System.out.println("1.- Si" + System.getProperty("line.separator") + "2.- No");
			opcion = in.nextInt();
		}
		
		in.close();
		
		return opcion;
		
	}
	
	public void conocerGente (TOUsuarioImp usuario) {
		
		ArrayList<Pendiente> listaPendientes = usuario.getListaPendientes(); // Hacemos copia de la lista de pendientes
		String correoUsuario;
		TOUsuarioImp usuarioAlt;
		Pendiente pendiente;
		int opcion;
		
		// Lista de pendientes
		for (int i = 0; i < listaPendientes.size(); i++){ // Recorremos la lista de pendientes
			pendiente = listaPendientes.get(i); // Recibimos el pendiente i
			correoUsuario = pendiente.getCorreo(); // Sacamos el correo del pendiente i
			usuarioAlt = usuarioDAO.read(correoUsuario);// Con el correo sacamos el usuario
			System.out.println(usuarioAlt.toString()); //Se muestran datos del usuario
			
			opcion = opcion();
			
			//1 Si, 2 No
			switch(opcion) {
				case 1:
					usuario.añadirChat(usuarioAlt.getCorreo()); // Se añade como un posible match
					usuario.añadirConocido(usuarioAlt.getCorreo()); //Añadimos al usuario a la lista de conocidos
					usuarioAlt.eliminarPendiente(usuario.getCorreo()); // Borramos de la lista de pendientes del usuarioAlt a el usuario que quiere borrar la cuenta
					break;
				case 2:
					usuario.añadirConocido(usuarioAlt.getCorreo()); //Añadimos al usuario a la lista de conocidos
					usuarioAlt.eliminarPendiente(usuario.getCorreo()); // Borramos de la lista de pendientes del usuarioAlt a el usuario que quiere borrar la cuenta
					break;
			}
			
		}
		
	}
	
	public void bloquearUsuario (TOUsuarioImp usuario, TOUsuarioImp usuarioABloquear) {
		
		ArrayList<String> listaConocidos = usuario.getListaConocidos(); // Hacemos copia de la lista de conocidos
		ArrayList<Chat> listaChats = usuario.getListaChats(); // Hacemos copia de la lista de chats
		String correoUsuario;
		Chat chat;
		boolean salir = false;
		int i = 0;
		
		// Lista de conocidos
		while (i < listaConocidos.size() && !salir){ // Buscamos en la lista de conocidos
			correoUsuario = listaConocidos.get(i); // Recibimos el correo i
			if(correoUsuario == usuarioABloquear.getCorreo()) {
				usuario.eliminarConocido(correoUsuario);
				salir = true;
			}
			++i;
		}
		
		i = 0;
		salir = false;
		
		// Lista de chats
		while (i < listaChats.size() && !salir){ // Buscamos en la lista de chats
			chat = listaChats.get(i); // Recibimos el chat i
			correoUsuario = chat.getCorreo(); // Sacamos el correo del chat i
			if(correoUsuario == usuarioABloquear.getCorreo()) {
				usuario.eliminarChat(correoUsuario);
				salir = true;
			}
			++i;
		}
		
		
	}
	
	public void personalizarPerfil (TOUsuarioImp usuario, String contraseña, String nombre, String descripcion) {
		
		/*Poner en la constructora de TOUsuarioImp la foto si se puede implementar*/
		
		usuario.setContrasena(contraseña);
		usuario.setNombre(nombre);
		usuario.setDescripcion(descripcion);
		
	}

}
