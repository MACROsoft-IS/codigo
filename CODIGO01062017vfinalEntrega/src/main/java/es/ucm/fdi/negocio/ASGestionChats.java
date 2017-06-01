package es.ucm.fdi.negocio;

import java.util.Vector;

import es.ucm.fdi.integracion.TOChat;
import es.ucm.fdi.integracion.TOMensaje;
import es.ucm.fdi.integracion.TOUsuario;

public interface ASGestionChats {
	
	public TOChat salaChat(Vector<String> listaChats, TOUsuario usuario, TOUsuario usuarioAlt);
	public void bloquearUsuario (TOUsuario usuario, TOUsuario usuarioABloquear);
	public Vector<String> getChats(TOUsuario usuario);
	public TOUsuario getUsuario(String idChat, TOUsuario usuario);
	public boolean getMsjNvs(String idChat, TOUsuario usuario);
	public void crearMsj(TOChat chat, TOUsuario usuario, String mensaje);
	public TOMensaje getMensaje(String idMensaje);
	
	
}
