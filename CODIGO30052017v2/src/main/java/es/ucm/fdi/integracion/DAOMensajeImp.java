package es.ucm.fdi.integracion;

import es.ucm.fdi.datos.BDMemoria;

public class DAOMensajeImp implements DAOMensaje{
	

	//private static BDMemoria<TOMensaje> bdMensajes; 
	
    public DAOMensajeImp() {
		//DAOMensajeImp.bdMensajes = new BDMemoria<TOMensaje>();
	}

	public void createMsj(TOChat chat, String emisor, String mensaje) {
		//bdMensajes.insert(tMensaje, tMensaje.getSesionID());
		TOMensaje tMensaje = new TOMensaje(emisor, mensaje);
		chat.getMensajes().insert(tMensaje, tMensaje.getId());
	}

	public void removeUser(TOChat chat, String id) {
		chat.getMensajes().removeId(id);
	}

	public TOMensaje read(TOChat chat, String id) {
		return chat.getMensajes().find(id);
	}

}
