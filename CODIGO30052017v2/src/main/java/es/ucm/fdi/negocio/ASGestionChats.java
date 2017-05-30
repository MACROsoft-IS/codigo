package es.ucm.fdi.negocio;

import es.ucm.fdi.integracion.TOUsuario;

public interface ASGestionChats {
	
	public ChatTO chatear(TOUsuario usuario);
	public Vector<ChatTO> chatsPendientes(TOUsuario usuario, long momento);
	
	public void crearMensaje(..)
	public Vector<MensajeTO> mensajesPendientes(TOUsuario usuario, long momento);
	
	
}
