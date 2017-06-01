package es.ucm.fdi.interfaz;


import java.util.Scanner;
import java.util.Vector;
import es.ucm.fdi.integracion.TOChat;
import es.ucm.fdi.integracion.TOMensaje;
import es.ucm.fdi.integracion.TOUsuario;
import es.ucm.fdi.negocio.ASGestionChatsImp;

public class InterfazChats {
	

	static Scanner in =  new Scanner (System.in);
	
	private ASGestionChatsImp asGChat;
	
	public InterfazChats(ASGestionChatsImp asGChat){
		this.asGChat = asGChat;
	}
	
	public void chatear(TOUsuario usuario){
		
		Vector<String> listaChats = asGChat.getChats(usuario);
		TOUsuario usuarioAlt;
		Scanner in = new Scanner(System.in);
		TOChat chat = null;
		String mensaje;
		
		if(mostrarChats(listaChats, usuario)){
			int opChat = 0;
			
			while (opChat != -1){
				opChat = opChat(listaChats.size(), in);
				if (opChat != -1 && opChat != -2){
					usuarioAlt = asGChat.getUsuario(listaChats.get(opChat), usuario);
					chat = asGChat.salaChat(listaChats, usuario, usuarioAlt);
					chato(chat, usuario, usuarioAlt);
					listaChats = asGChat.getChats(usuario);
					mostrarChats(listaChats, usuario);
					if (listaChats.isEmpty()) opChat = -1;
				}
				else{
					if (opChat == -2) {
						mensaje = "Chat no encontrado.\n";
						Interfaz.notificar(mensaje);
						mostrarChats(listaChats, usuario);
					}
				}
			}
		}
		
	}
	
	private boolean mostrarChats(Vector<String> listaChats, TOUsuario usuario){
		
		String lineachat, mensaje, idChat;
		TOUsuario usuarioAlt;
		boolean mostrar;
		
		if (listaChats.isEmpty()) {
			mensaje = "No tienes chats abiertos actualmente.\n";
			Interfaz.notificar(mensaje);
			mostrar = false;
		}
		else{
			mensaje = "Nº\t Nombre \t Mensajes Nuevos";
			Interfaz.notificar(mensaje);
			for (int i = 0; i < listaChats.size(); i++){
				idChat = listaChats.get(i);
				usuarioAlt = asGChat.getUsuario(idChat, usuario);
				lineachat = "\n" + (i+1) + "\t" + usuarioAlt.getNombre() + "\t";
				if (asGChat.getMsjNvs(idChat, usuario)) lineachat = lineachat + "MN";
				Interfaz.notificar(lineachat);
			}
			mensaje = "\n Introduce el numero del chat al que desea acceder o 0 para salir.";
			Interfaz.notificar(mensaje);
			mostrar = true;
		}
		return mostrar;

	}
	
	private int opChat(int size, Scanner in){
		try{
			int opcion;
			String mensaje;
			
			opcion = Integer.parseInt(in.nextLine());
			
			while(opcion < 0 || opcion > size) {
				mensaje = "Chat no encontrado.";
				Interfaz.notificar(mensaje);
				opcion = in.nextInt();
			}
			
			opcion = opcion - 1;
			return opcion;
		}
		catch(NumberFormatException e){
			int respuesta = -2;
			return respuesta;
		}
		
	}
	
	public void chato(TOChat chat, TOUsuario usuario, TOUsuario usuarioAlt){
		
		String show;
		int aux;
		
		if (chat.getCorreoA().equalsIgnoreCase(usuario.getCorreo())) chat.setMensajesNuevosA(false);
		else chat.setMensajesNuevosB(false);
		
		mostrarMensajes(chat.getMensajes(), 0);// Mostramos los mensajes anteriores
		
		String mensaje;
		String check = "";
		show = "\n Has entrado en la sala de chat. Introduce el comando /salirchat para salir. Puedes bloquear al usuario con el comando /bloquearusuario.";
		Interfaz.notificar(show);
		while (!check.equalsIgnoreCase("/salirchat") && !check.equalsIgnoreCase("/bloquearusuario")){
			mensaje = in.nextLine();
			check = mensaje.trim();
			if (!check.equalsIgnoreCase("/salirchat") && !check.equalsIgnoreCase("/bloquearusuario")){
				asGChat.crearMsj(chat, usuario, mensaje);
				
				//Ponemos mensajes nuevos ON
				if (chat.getCorreoA().equalsIgnoreCase(usuario.getCorreo())) chat.setMensajesNuevosB(true);
				else chat.setMensajesNuevosA(true);
				
				
				//Mostramos el ultimo mensaje
				aux = chat.getMensajes().size() - 1;
				if (aux == -1) aux = 0;
				mostrarMensajes(chat.getMensajes(), aux);
				
				//El otro usuario contesta
				asGChat.crearMsj(chat, usuarioAlt, "Aprobado general.\n");
				
				if (chat.getCorreoA().equalsIgnoreCase(usuarioAlt.getCorreo())) chat.setMensajesNuevosB(true);
				else chat.setMensajesNuevosA(true);
				
				//Mostramos el ultimo mensaje
				aux = chat.getMensajes().size() - 1;
				if (aux == -1) aux = 0;
				mostrarMensajes(chat.getMensajes(), aux);
			}else{
				if (check.equalsIgnoreCase("/bloquearusuario")){
					asGChat.bloquearUsuario(usuario, usuarioAlt);
					show = "Se ha bloqueado al usuario correctamente.\n";
					Interfaz.notificar(show);
				}
			}
		}
		
	}
	
	private void mostrarMensajes(Vector<String> listaMensajes, int indice){
		TOMensaje tMensaje;
		
		for(int i = indice; i < listaMensajes.size(); i++){
			tMensaje = asGChat.getMensaje(listaMensajes.get(i));
			Interfaz.notificar(tMensaje.toString());
		}
	}
}

