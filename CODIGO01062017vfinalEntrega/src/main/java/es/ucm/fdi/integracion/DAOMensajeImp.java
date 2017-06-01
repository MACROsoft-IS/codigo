package es.ucm.fdi.integracion;

import es.ucm.fdi.datos.BDMemoria;

public class DAOMensajeImp implements DAOMensaje{
	

	private static BDMemoria<TOMensaje> bdMensajes; 
	
    public DAOMensajeImp() {
		DAOMensajeImp.bdMensajes = new BDMemoria<TOMensaje>();
	}

	public TOMensaje createMsj(String emisor, String mensaje, String idChat, String name) {
		TOMensaje tMensaje = new TOMensaje(emisor, mensaje, idChat, name, bdMensajes.getIds().size());
		bdMensajes.insert(tMensaje, tMensaje.getId());
		return tMensaje;
	}

	public void removeUser(String idMensaje) {
		bdMensajes.removeId(idMensaje);
	}

	public TOMensaje read(String idMensaje) {
		return bdMensajes.find(idMensaje);
	}
	

}
