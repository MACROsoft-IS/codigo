package es.ucm.fdi.integracion;

import java.util.Vector;

import es.ucm.fdi.datos.BDMemoria;

public class DAOChatImp implements DAOChat {
	

	//private static BDMemoria<TOChat> bdChat; 
	
	public DAOChatImp() {
		//DAOChatImp.bdChat = new BDMemoria<TOChat>();
	}

	public void createChat(TOUsuario usuario, String correo) {
		//bdMensajes.insert(tMensaje, tMensaje.getSesionID());
		usuario.getChats().insert(new TOChat(correo), correo);
	}

	public void removeChat(TOUsuario usuario, String id) {
		usuario.getChats().removeId(id);
	}

	public TOChat readChat(TOUsuario usuario, String id) {
		return usuario.getChats().find(id);
	}
	
	public Vector<String> chatIds(TOUsuario usuario){
		return usuario.getChats().getIds();
	}

}
