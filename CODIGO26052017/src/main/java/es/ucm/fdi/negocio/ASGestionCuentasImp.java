package es.ucm.fdi.negocio;

import java.util.ArrayList;

import es.ucm.fdi.datos.BDMemoria;
import es.ucm.fdi.integracion.DAOSesionImp;
import es.ucm.fdi.integracion.DAOUsuarioImp;
import es.ucm.fdi.integracion.TOChat;
import es.ucm.fdi.integracion.TOPendiente;
import es.ucm.fdi.integracion.TOSesion;
import es.ucm.fdi.integracion.TOUsuario;

public class ASGestionCuentasImp implements ASGestionCuentas {

	private DAOUsuarioImp usuarioDAO;
	private DAOSesionImp sesionDAO;
	
	public static BDMemoria<TOUsuario> bdUsers; 
	public static BDMemoria<TOSesion> bdSession; 
	
	public ASGestionCuentasImp() {
		ASGestionCuentasImp.bdUsers = new BDMemoria<TOUsuario>(); 
		ASGestionCuentasImp.bdSession = new BDMemoria<TOSesion>(); 
		
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
	
	public static BDMemoria<TOUsuario> getBdUsersConnection() { 
		return ASGestionCuentasImp.bdUsers;
	}
	
	public static BDMemoria<TOSesion> getBdSesionConnection() { 
		return ASGestionCuentasImp.bdSession;
	}

	
	public TOSesion iniciarSesion(String correo, String contrasena) {

		TOSesion sesion = null;
		TOUsuario usuario = usuarioDAO.read(correo); 

		if (usuario!=null && usuario.getContrasena().equals(contrasena)) {
			sesion = new TOSesion(usuario);
			sesionDAO.insertSesion(sesion);
		}
		return sesion;
		
	}
	
	public boolean crearCuenta(String nombre, String correo, String contrasena, String foto, String descripcion) {

		boolean cuenta = false;
		
		if(nombre != null && correo != null && contrasena != null) {
			if (usuarioDAO.read(correo) == null) {
				TOUsuario usuario = new TOUsuario(nombre, correo, contrasena, foto, descripcion);
				usuarioDAO.createUser(usuario);
				cuenta = true;
			}
		}
		return cuenta;
	
	}
	
	public void cerrarSesion(TOSesion sesion){
		//sesion.setCuentaActiva(null);
		//sesion.setSesionID(null);
		sesion.setSesionEstado(false);
	}
	
	public void eliminarCuenta(TOUsuario usuario, TOSesion sesion){
		
		ArrayList<String> listaConocidos = usuario.getListaConocidos(); // Hacemos copia de la lista de conocidos
		ArrayList<TOPendiente> listaPendientes = usuario.getListaPendientes(); // Hacemos copia de la lista de pendientes
		ArrayList<TOChat> listaChats = usuario.getListaChats(); // Hacemos copia de la lista de chats
		String correoUsuario;
		TOUsuario usuarioAlt;
		TOPendiente pendiente;
		TOChat chat;

		
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
			usuarioAlt.addAviso("El usuario " + usuario.getNombre() + " ha eliminado su cuenta y se ha borrado el chat.\n");
		}
		
		// Eliminamos cuenta
		usuarioDAO.removeUser(usuario.getCorreo());
		
		// Cerramos sesion
		cerrarSesion(sesion);
		
	}
	
	public void personalizarPerfil (TOUsuario usuario, String contrasena, String nombre, String descripcion, String foto) {
		
		// Comprobamos que los campos no sean null puesto que el usuario no tiene por que modificar todos los campos. Los campos que no modifique estaran en null.
		
		if (contrasena != null) usuario.setContrasena(contrasena);
		if (nombre != null) usuario.setNombre(nombre); 
		if (descripcion != null) usuario.setDescripcion(descripcion);
		if (foto != null) usuario.setFoto(foto);
		
	}
	
	public String mostrarPerfil(TOUsuario usuario){
		
		String muestra = null;
		
		if (usuario != null) muestra = "\t\t Perfil de usuario\n\t > Foto: " +  usuario.getFoto() + 
				".\n\t > Nombre: " + usuario.getNombre() +
				".\n\t > Descripción: " + usuario.getDescripcion() + "\n"; 
		
		return muestra;
	}
}
