package es.ucm.fdi.negocio;

import java.util.ArrayList;
import java.util.Vector;

import es.ucm.fdi.integracion.DAOChatImp;
import es.ucm.fdi.integracion.DAOSesionImp;
import es.ucm.fdi.integracion.DAOUsuarioImp;
import es.ucm.fdi.integracion.TOPendiente;
import es.ucm.fdi.integracion.TOSesion;
import es.ucm.fdi.integracion.TOUsuario;

public class ASGestionCuentasImp implements ASGestionCuentas {

	private DAOUsuarioImp usuarioDAO;
	private DAOSesionImp sesionDAO;
	private DAOChatImp chatDAO;
	
	public ASGestionCuentasImp(DAOUsuarioImp daoUsuario, DAOSesionImp daoSesion, DAOChatImp daoChat) {
		this.sesionDAO = daoSesion;
		this.usuarioDAO = daoUsuario;
		this.chatDAO = daoChat;
				
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
		
		if(nombre != null && correo != null && contrasena != null && foto != null && descripcion != null) {
			if (usuarioDAO.read(correo) == null) {
				TOUsuario usuario = new TOUsuario(nombre, correo, contrasena, foto, descripcion);
				usuarioDAO.createUser(usuario);
				cuenta = true;
			}
		}
		return cuenta;
	
	}
	
	public void cerrarSesion(TOSesion sesion){
		sesion.setSesionEstado(false);
	}
	
	public void eliminarCuenta(TOUsuario usuario, TOSesion sesion){
		
		ArrayList<String> listaConocidos = usuario.getListaConocidos();
		ArrayList<TOPendiente> listaPendientes = usuario.getListaPendientes();
		Vector<String> listaChats = usuario.getChats();
		String correoUsuario;
		TOUsuario usuarioAlt;
		TOPendiente pendiente;

		
		// Lista de conocidos
		for (int i = 0; i < listaConocidos.size(); i++){ 
			correoUsuario = listaConocidos.get(i);
			usuarioAlt = usuarioDAO.read(correoUsuario);
			usuarioDAO.eliminarConocido(usuarioAlt, usuario.getCorreo());
		}
		
		// Lista de pendientes
		for (int i = 0; i < listaPendientes.size(); i++){
			pendiente = listaPendientes.get(i);
			correoUsuario = pendiente.getCorreo(); 
			usuarioAlt = usuarioDAO.read(correoUsuario); 
			usuarioDAO.eliminarPendiente(usuarioAlt, usuario.getCorreo());
		}
		
		// Lista de chats
		for (int i = 0; i < listaChats.size(); i++){ 
			String correoA, correoB;
			String idChat = listaChats.get(i);
			correoA = chatDAO.readChat(idChat).getCorreoA();
			correoB = chatDAO.readChat(idChat).getCorreoB();
			if (correoA.equalsIgnoreCase(usuario.getCorreo())) usuarioAlt = usuarioDAO.read(correoB);
			else usuarioAlt = usuarioDAO.read(correoA);
			usuarioDAO.eliminarChatIdVector(usuario, idChat);
			usuarioDAO.eliminarChatIdVector(usuarioAlt, idChat);
			chatDAO.removeChat(idChat);
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
