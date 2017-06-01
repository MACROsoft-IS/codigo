package es.ucm.fdi.negocio;


import java.util.Vector;
import es.ucm.fdi.integracion.DAOChatImp;
import es.ucm.fdi.integracion.DAOMensajeImp;
import es.ucm.fdi.integracion.DAOSesionImp;
import es.ucm.fdi.integracion.DAOUsuarioImp;
import es.ucm.fdi.integracion.TOChat;
import es.ucm.fdi.integracion.TOMensaje;
import es.ucm.fdi.integracion.TOUsuario;

public class ASGestionChatsImp implements ASGestionChats {
	
	private DAOUsuarioImp usuarioDAO;
	private DAOChatImp chatDAO;
	private DAOMensajeImp mensajeDAO;
	
	public ASGestionChatsImp(DAOUsuarioImp usuarioDAO,DAOSesionImp sesionDAO, DAOChatImp chatDAO, DAOMensajeImp mensajeDAO) {
		this.usuarioDAO = usuarioDAO;
		this.chatDAO = chatDAO;
		this.mensajeDAO = mensajeDAO;
	}

	
	public TOChat salaChat(Vector<String> listaChats, TOUsuario usuario, TOUsuario usuarioAlt){
		
		int i = 0;
		boolean encontrado = false;
		String idChat, correoUsuario, correoUsuarioAlt;
		TOChat chat = null;
		correoUsuario = usuario.getCorreo();
		correoUsuarioAlt = usuarioAlt.getCorreo();
		
		while (i < listaChats.size() && !encontrado){
			idChat = listaChats.get(i);
			chat = chatDAO.readChat(idChat);
			if ((chat.getCorreoA().equalsIgnoreCase(correoUsuario) && chat.getCorreoB().equalsIgnoreCase(correoUsuarioAlt)) ||
					(chat.getCorreoA().equalsIgnoreCase(correoUsuarioAlt) && chat.getCorreoB().equalsIgnoreCase(correoUsuario))){
				encontrado = true;
			}
			i++;
		}
		
		return chat;
		
	}
	
	
	public void bloquearUsuario (TOUsuario usuario, TOUsuario usuarioABloquear) {
		
		String correoUsuario = usuario.getCorreo();
		String correoAlt = usuarioABloquear.getCorreo();
		String idChat;
		boolean encontrado = false;
		int i = 0;
		TOChat chat;
		
		usuarioDAO.eliminarPendiente(usuario, correoAlt);
		usuarioDAO.eliminarPendiente(usuarioABloquear, correoUsuario);
		while (i < usuario.getChats().size() && !encontrado){
			idChat = usuario.getChats().get(i);
			chat = chatDAO.readChat(idChat);
			if (chat.getCorreoA().equalsIgnoreCase(correoAlt) || chat.getCorreoB().equalsIgnoreCase(correoAlt)){
				chatDAO.removeChat(idChat);
				usuarioDAO.eliminarChatIdVector(usuario, idChat);
				usuarioDAO.eliminarChatIdVector(usuarioABloquear, idChat);
				encontrado = true;
			}
			i++;
		}
		
		if (!usuario.getListaConocidos().contains(correoAlt)) 
			usuarioDAO.anadirConocido(usuario, correoAlt);
		
		if (!usuarioABloquear.getListaConocidos().contains(correoUsuario)) 
			usuarioDAO.anadirConocido(usuarioABloquear, correoUsuario);
	}
	
	public Vector<String> getChats(TOUsuario usuario){
		return usuario.getChats();
	}
	
	public TOUsuario getUsuario(String idChat, TOUsuario usuario){
		TOChat chat = chatDAO.readChat(idChat);
		TOUsuario usuarioAlt;
		
		if (chat.getCorreoA().equalsIgnoreCase(usuario.getCorreo())) usuarioAlt = usuarioDAO.read(chat.getCorreoB());
		else usuarioAlt = usuarioDAO.read(chat.getCorreoA());
		
		return usuarioAlt;
	}
	
	public boolean getMsjNvs(String idChat, TOUsuario usuario){
		TOChat chat = chatDAO.readChat(idChat);
		
		if (chat.getCorreoA().equalsIgnoreCase(usuario.getCorreo())) return chat.getMensajesNuevosA();
		else return chat.getMensajesNuevosB();
	}
	
	public void crearMsj(TOChat chat, TOUsuario usuario, String mensaje){
		TOMensaje tMensaje =mensajeDAO.createMsj(usuario.getCorreo(), mensaje, chat.getChatid(), usuario.getNombre());
		chatDAO.añadirMsjIdVector(chat, tMensaje.getId());
	}
	
	public TOMensaje getMensaje(String idMensaje){
		return mensajeDAO.read(idMensaje);
	}
	
}
