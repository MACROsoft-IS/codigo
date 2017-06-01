package es.ucm.fdi.negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import es.ucm.fdi.integracion.DAOChatImp;
import es.ucm.fdi.integracion.DAOUsuarioImp;
import es.ucm.fdi.integracion.TOChat;
import es.ucm.fdi.integracion.TOPendiente;
import es.ucm.fdi.integracion.TOUsuario;
import es.ucm.fdi.integracion.TOPendiente.tPendiente;

public class ASGestionConocerImp implements ASGestionConocer{
	
	private DAOUsuarioImp usuarioDAO;
	private DAOChatImp chatDAO;
	
	public ASGestionConocerImp (DAOUsuarioImp usuarioDAO, DAOChatImp chatDAO) {
		this.usuarioDAO = usuarioDAO;
		this.chatDAO = chatDAO;
	}
	

	public TOUsuario damePersonaValidaAleatoria(TOUsuario usuario){
		
		Vector<String> correos = usuarioDAO.getBdUsersConnection().getIds(); 
		ArrayList<String> listaConocidos = usuario.getListaConocidos();
		
		int num = 0;
		String correoAMostrar = null;
		Random rand = new Random();
		Collections.shuffle(correos, rand);
		
		if (listaConocidos.size() < correos.size()) {
			
			do {
				correoAMostrar = correos.get(num);
				if (listaConocidos.contains(correoAMostrar)) num++;
			} while (listaConocidos.contains(correoAMostrar));
			
			return usuarioDAO.read(correoAMostrar);
			
		}
		else {
			return null;
		}
	}
	
	public void AplicarOpcion(TOUsuario usuarioConocido, TOUsuario usuario, int opcion, boolean pendiente){
		
	    String correoUsuarioConocer = usuarioConocido.getCorreo();
	    String correoUsuario = usuario.getCorreo();
	    
		switch (opcion) {
		// SI
		case 1:
			usuarioDAO.anadirConocido(usuario, correoUsuarioConocer);
			if (pendiente) { //si es un correo pendiente
				TOChat chat = chatDAO.createChat(correoUsuario, correoUsuarioConocer);
				usuarioDAO.añadirChatIdVector(usuario, chat.getChatid());
				usuarioDAO.añadirChatIdVector(usuarioConocido, chat.getChatid());
				usuarioDAO.eliminarPendiente(usuario, correoUsuarioConocer);
				usuarioDAO.eliminarPendiente(usuarioConocido, correoUsuario);
				usuarioConocido.addAviso("Se ha añadido a " + usuario.getNombre() + " a la lista de chat.\n");
			}
			else {
				usuarioDAO.anadirPendiente(usuario, correoUsuarioConocer, tPendiente.MANDO);
				usuarioDAO.anadirPendiente(usuarioConocido, correoUsuario, tPendiente.RECIBO);
			}
			break;
		// NO 
		case 2:
			usuarioDAO.anadirConocido(usuario, correoUsuarioConocer);
			if (pendiente) {
				usuarioDAO.eliminarPendiente(usuario, correoUsuarioConocer);
				usuarioDAO.eliminarPendiente(usuarioConocido, correoUsuario);
			}
			else {
				usuarioDAO.anadirConocido(usuarioConocido, correoUsuario);
			}
			break;
		//Bloquear usuario
		case 3:
			usuarioDAO.anadirConocido(usuario, correoUsuarioConocer);
			if (pendiente){
				usuarioDAO.eliminarPendiente(usuario, correoUsuarioConocer);
				usuarioDAO.eliminarPendiente(usuarioConocido, correoUsuario);
			}
			else{
				usuarioDAO.anadirConocido(usuarioConocido, correoUsuario);
			}
			
			break;
		}
	}
	

	public ArrayList<TOUsuario> dameUsuariosPendientes(TOUsuario usuario){

		ArrayList<TOUsuario> listaUsuariosPendientes = new ArrayList<TOUsuario>();
		ArrayList<TOPendiente> listaPendientes = usuario.getListaPendientes(); // Hacemos copia de la lista de pendientes
		int i = 0;
		
		if (!listaPendientes.isEmpty()){
			while (i < listaPendientes.size()){
				if (listaPendientes.get(i).getTipo() == tPendiente.RECIBO)
					listaUsuariosPendientes.add(usuarioDAO.read(listaPendientes.get(i).getCorreo()));
				i++;
			}
		}
		
		return listaUsuariosPendientes;
		
	}
	
}
