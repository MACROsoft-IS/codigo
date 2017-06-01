package es.ucm.fdi.integracion;

import java.util.Vector;

import es.ucm.fdi.datos.BDMemoria;

public class DAOChatImp implements DAOChat {
	

	private static BDMemoria<TOChat> bdChat; 
	
	public DAOChatImp() {
		DAOChatImp.bdChat = new BDMemoria<TOChat>();
	}

	public TOChat createChat(String correoA, String correoB) {
		TOChat chat = new TOChat(correoA, correoB);
		bdChat.insert(chat, chat.getChatid());
		return chat;
	}

	public void removeChat(String idChat) {
		bdChat.removeId(idChat);
	}

	public TOChat readChat(String idChat) {
		return bdChat.find(idChat);
	}
	
	public Vector<String> chatIds(){
		return bdChat.getIds();
	}
	
	public void añadirMsjIdVector(TOChat chat, String idMensaje){
		chat.getMensajes().add(idMensaje);
	}
	
	public void eliminarMsjIdVector(TOChat chat, String idMensaje){
		int i = 0;
		boolean eliminado = false;
		
		while (i < chat.getMensajes().size() && !eliminado){
			if (chat.getMensajes().get(i).equalsIgnoreCase(idMensaje)) {
				chat.getMensajes().remove(i);
				eliminado = true;
			}
			else i++;
		}
	}

}
