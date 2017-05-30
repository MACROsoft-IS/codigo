package es.ucm.fdi.negocio;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import es.ucm.fdi.integracion.DAOChatImp;
import es.ucm.fdi.integracion.DAOUsuarioImp;
import es.ucm.fdi.integracion.TOPendiente;
import es.ucm.fdi.integracion.TOUsuario;
import es.ucm.fdi.integracion.TOPendiente.tPendiente;

public class ASGestionConocerImp implements ASGestionConocer{
	
	private DAOUsuarioImp usuarioDAO;
	private DAOChatImp chatDAO;
	
	//QUE PASA SI LLAMAMOS A LA SESION O QUE PASA CON ESTO..
	public ASGestionConocerImp (DAOUsuarioImp usuarioDAO, DAOChatImp chatDAO) {
		this.usuarioDAO = usuarioDAO;
		this.chatDAO = chatDAO;
	}
	

	public TOUsuario damePersonaValidaAleatoria(TOUsuario usuario){
		
		///////!!!!!!!!!!!!!!!!!!!!!!NO PODEMOS EXTRAER UN CORREO VALIDO DE LA BD aleatoriamente , ni desordenarla previamente,
		
		Vector<String> correos = usuarioDAO.getBdUsersConnection().getIds(); 
		ArrayList<String> listaConocidos = usuario.getListaConocidos(); // Hacemos copia de la lista de conocidos
		
		int num;
		String correoAMostrar = null;
		Random rand = new Random();
		
		if (listaConocidos.size() < correos.size()) {
			
			do {
				num = rand.nextInt(correos.size());
				//num = (int) Math.random() * correos.size();
				correoAMostrar = correos.get(num);
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
			usuario.anadirConocido(correoUsuarioConocer);
			if (pendiente) { //si es un correo pendiente
				chatDAO.createChat(usuario, correoUsuarioConocer);
				//usuario.anadirChat(correoUsuarioConocer);
				chatDAO.createChat(usuarioConocido, correoUsuario);
				//usuarioConocido.anadirChat(correoUsuario);
				usuario.eliminarPendiente(correoUsuarioConocer);
				usuarioConocido.eliminarPendiente(correoUsuario);
				usuarioConocido.addAviso("Se ha añadido a " + usuario.getNombre() + " a la lista de chat.\n");
			}
			else {
				usuario.anadirPendiente(correoUsuarioConocer, tPendiente.MANDO);         //Añadimos pendiente en el usuario principal
				usuarioConocido.anadirPendiente(correoUsuario, tPendiente.RECIBO); //Añadimos pendiente en el usuarioAlt
			}
			break;
		// NO 
		case 2:
			usuario.anadirConocido(correoUsuarioConocer); 
			if (pendiente) {
				usuario.eliminarPendiente(correoUsuarioConocer);
				usuarioConocido.eliminarPendiente(correoUsuario); 
			}
			else {
				usuarioConocido.anadirConocido(correoUsuario);   
			}
			break;
		//Bloquear usuario
		case 3:
			usuario.anadirConocido(correoUsuarioConocer);
			if (pendiente){
				usuario.eliminarPendiente(correoUsuarioConocer);
				usuarioConocido.eliminarPendiente(correoUsuario);
			}
			else{
				usuarioConocido.anadirConocido(correoUsuario);
			}
			//bloquearUsuario(usuario, usuarioConocido);
			
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
	
	/*public void bloquearUsuario (TOUsuario usuario, TOUsuario usuarioABloquear) {
		
		// Entra dos usuarios
		// Se borra ambos pendiente y ambos chats
		// Se ponen ambos en conocido
		String correoUsuario = usuario.getCorreo();
		String correoAlt = usuarioABloquear.getCorreo();
		
		usuario.eliminarPendiente(correoAlt);
		usuarioABloquear.eliminarPendiente(correoUsuario);
		chatDAO.removeChat(usuario, correoAlt);
		//usuario.eliminarChat(correoAlt);
		chatDAO.removeChat(usuarioABloquear, correoUsuario);
		//usuarioABloquear.eliminarChat(correoUsuario);
		
		if (!usuario.getListaConocidos().contains(correoAlt)) 
			usuario.anadirConocido(correoAlt); 
			//Si no esta aun en lista de conocidos se añade
		if (!usuarioABloquear.getListaConocidos().contains(correoUsuario)) 
			usuarioABloquear.anadirConocido(correoUsuario);      
			//Si no esta aun en lista de conocidos se añade
	}*/
	
}
