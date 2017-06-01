package es.ucm.fdi.integracion;

import es.ucm.fdi.datos.BDMemoria;
import es.ucm.fdi.integracion.TOPendiente.tPendiente;

public class DAOUsuarioImp implements DAOUsuario {

	private static BDMemoria<TOUsuario> bdUsers; 
	//al no haber una comunicacion "normal" de sql, directamente tomamos como una conexion abierta.
	//nos ahorramos el patron singleton, ya que no es necesario mantener una conexion a la escucha
	//y la clase BDMemoria ya lo implementa

	public DAOUsuarioImp() {
		DAOUsuarioImp.bdUsers = new BDMemoria<TOUsuario>();
	}
	
	public void createUser(TOUsuario tUsuario) {
		bdUsers.insert(tUsuario, tUsuario.getCorreo());
	}

	public TOUsuario read(String correo) {
		return bdUsers.find(correo);
	}

	public void removeUser(String correo){
		bdUsers.removeId(correo);
	}
	
	public BDMemoria<TOUsuario> getBdUsersConnection() { 
		return DAOUsuarioImp.bdUsers;
	}
	
	public void anadirConocido(TOUsuario usuario, String correo){
		usuario.getListaConocidos().add(correo);
	}
	
	public void eliminarConocido(TOUsuario usuario, String correo){
		usuario.getListaConocidos().remove(correo);
	}
	
	public void anadirPendiente(TOUsuario usuario, String correo, tPendiente tipo){
		usuario.getListaPendientes().add(new TOPendiente(correo, tipo));
	}
	
	public void eliminarPendiente(TOUsuario usuario, String correo){
		
		int i = 0;
		boolean eliminado = false;
		
		while (i < usuario.getListaPendientes().size() && !eliminado){
			if (usuario.getListaPendientes().get(i).getCorreo().equalsIgnoreCase(correo)) {
				usuario.getListaPendientes().remove(i);
				eliminado = true;
			}
			else i++;
		}
	}
	
	public void añadirChatIdVector(TOUsuario usuario, String idChat){
		usuario.getChats().add(idChat);
	}
	
	public void eliminarChatIdVector(TOUsuario usuario, String idChat){
		int i = 0;
		boolean eliminado = false;
		
		while (i < usuario.getChats().size() && !eliminado){
			if (usuario.getChats().get(i).equalsIgnoreCase(idChat)) {
				usuario.getChats().remove(i);
				eliminado = true;
			}
			else i++;
		}
	}

}
