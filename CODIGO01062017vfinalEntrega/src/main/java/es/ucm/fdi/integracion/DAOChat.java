package es.ucm.fdi.integracion;

import java.util.Vector;

public interface DAOChat {
	
	public TOChat createChat(String correoA, String correoB);
	public void removeChat(String idChat);
	public TOChat readChat(String idChat);
	public Vector<String> chatIds();
	public void añadirMsjIdVector(TOChat chat, String idMensaje);
	public void eliminarMsjIdVector(TOChat chat, String idMensaje);
}