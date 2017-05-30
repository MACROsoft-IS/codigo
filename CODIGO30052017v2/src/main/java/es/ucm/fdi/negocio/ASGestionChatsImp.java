package es.ucm.fdi.negocio;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import es.ucm.fdi.integracion.DAOChatImp;
import es.ucm.fdi.integracion.DAOMensajeImp;
import es.ucm.fdi.integracion.DAOSesionImp;
import es.ucm.fdi.integracion.DAOUsuarioImp;
import es.ucm.fdi.integracion.TOChat;
import es.ucm.fdi.integracion.TOUsuario;
import es.ucm.fdi.interfaz.Interfaz;
import es.ucm.fdi.interfaz.InterfazChats;

public class ASGestionChatsImp implements ASGestionChats {
	
	private DAOUsuarioImp usuarioDAO;
	private DAOSesionImp sesionDAO;
	private DAOChatImp chatDAO;
	private DAOMensajeImp mensajeDAO;
	
	public ASGestionChatsImp(DAOUsuarioImp usuarioDAO,DAOSesionImp sesionDAO, DAOChatImp chatDAO, DAOMensajeImp mensajeDAO) {
		this.usuarioDAO = usuarioDAO;
		this.sesionDAO = sesionDAO;
		this.chatDAO = chatDAO;
		this.mensajeDAO = mensajeDAO;
	}
	
	
	public void chatear(TOUsuario usuario){
		
		//ArrayList<TOChat> listaChats = usuario.getListaChats();
		Vector<String> listaChats = chatDAO.chatIds(usuario);
		TOUsuario usuarioAlt;
		Scanner in = new Scanner(System.in);
		boolean vacia = false;
		String mensaje;
		
		if(mostrarChats(listaChats, usuario)){
			int opChat = 0;
			
			while (opChat != -1){
				opChat = opChat(listaChats.size(), in);
				if (opChat != -1 && opChat != -2){
					//usuarioAlt = usuarioDAO.read(listaChats.get(opChat).getCorreo());
					usuarioAlt = usuarioDAO.read(listaChats.get(opChat));
					vacia = salaChat(usuario, usuarioAlt, in);
					listaChats = chatDAO.chatIds(usuario); // Porque ahora no es el mismo objeto, es un vector aparte.
					mostrarChats(listaChats, usuario);
					if (vacia) opChat = -1;
				}
				else{
					if (opChat == -2) {
						mensaje = "Chat no encontrado.\n";
						Interfaz.notificar(mensaje);
						//System.out.println("Chat no encontrado.\n");
						mostrarChats(listaChats, usuario);
					}
				}
			}
		}
		
		//in.close();
	}
	
	private boolean mostrarChats(/*ArrayList<TOChat> listaChats*/ Vector<String> listaChats, TOUsuario usuario){
		
		String correoAlt, lineachat, mensaje;
		TOUsuario usuarioAlt;
		boolean mostrar;
		
		if (listaChats.isEmpty()) {
			mensaje = "No tienes chats abiertos actualmente.\n";
			Interfaz.notificar(mensaje);
			//System.out.println("No tienes chats abiertos actualmente.\n");
			mostrar = false;
		}
		else{
			mensaje = "Nº\t Nombre \t Mensajes Nuevos";
			Interfaz.notificar(mensaje);
			//System.out.println("Nº\t Nombre \t Mensajes Nuevos");
			for (int i = 0; i < listaChats.size(); i++){
				//correoAlt = listaChats.get(i).getCorreo();
				correoAlt = listaChats.get(i);
				usuarioAlt = usuarioDAO.read(correoAlt);
				lineachat = "\n" + (i+1) + "\t" + usuarioAlt.getNombre() + "\t";
				//System.out.println("\n" + (i+1) + "\t" + usuarioAlt.getNombre() + "\t");
				//if (listaChats.get(i).getMensajesNuevos()) lineachat = lineachat + "MN";
				if (chatDAO.readChat(usuario, correoAlt).getMensajesNuevos()) lineachat = lineachat + "MN";
				Interfaz.notificar(lineachat);
				//System.out.println(lineachat);
			}
			mensaje = "\n Introduce el numero del chat al que desea acceder o 0 para salir.";
			Interfaz.notificar(mensaje);
			//System.out.println("\n Introduce el numero del chat al que desea acceder o 0 para salir.");
			mostrar = true;
		}
		return mostrar;

	}
	
	private int opChat(int size, Scanner in){
		try{
			int opcion;
			String mensaje;
			
			//Scanner in = new Scanner(System.in);
			
			opcion = Integer.parseInt(in.nextLine());
			
			while(opcion < 0 || opcion > size) {
				mensaje = "Chat no encontrado.";
				Interfaz.notificar(mensaje);
				//System.out.println("Chat no encontrado.");
				opcion = in.nextInt();
			}
			
			//in.close();
			opcion = opcion - 1;
			return opcion;
		}
		catch(NumberFormatException e){
			//System.out.println("Chat no encontrado.\n");
			int respuesta = -2;
			return respuesta;
		}
		
	}
	
	private boolean salaChat(TOUsuario usuario, TOUsuario usuarioAlt, Scanner in){
		
		//ArrayList<TOChat> listaA = usuario.getListaChats();
		//ArrayList<TOChat> listaB = usuarioAlt.getListaChats();
		Vector<String> listaA = chatDAO.chatIds(usuario);
		Vector<String> listaB = chatDAO.chatIds(usuarioAlt);
		TOChat chatA = null;
		TOChat chatB = null;
		boolean vacia = false;
		
		int i = 0;
		boolean encontrado = false;
		// Buscamos el chat A
		
		while (i< listaA.size() && !encontrado){
			/*if (listaA.get(i).getCorreo().equalsIgnoreCase(usuarioAlt.getCorreo())){
				chatA = listaA.get(i);*/
			if (listaA.get(i).equalsIgnoreCase(usuarioAlt.getCorreo())){
				chatA = chatDAO.readChat(usuario, listaA.get(i));
				encontrado = true;
			}else i++;
		}
		
		i = 0;
		encontrado = false;
		// Buscamos el chat B
		while (i< listaB.size() && !encontrado){
			/*if (listaB.get(i).getCorreo().equalsIgnoreCase(usuario.getCorreo())){
				chatB = listaB.get(i);*/
			if (listaB.get(i).equalsIgnoreCase(usuario.getCorreo())){
				chatB = chatDAO.readChat(usuarioAlt, listaB.get(i));
				encontrado = true;
			}else i++;
		}
		
		vacia = abrirChat(chatA, chatB, usuario, in);
		
		return vacia;
		
	}
	
	private boolean abrirChat(TOChat chatA, TOChat chatB, TOUsuario usuario, Scanner in){
		
		boolean vacia = false;
		String show;
		
		if (chatA != null && chatB != null){
			chatA.setMensajesNuevos(false);
			InterfazChats.notificar(chatA);
			//InterfazChats.notificar(chatB);
			//Interfaz.notificar(chatA.getMensajes()); // ESTO HAY QUE CAMBIARLO POR MOSTRAR LOS MENSAJES DE LA OTRA FORMA
			//System.out.println(chatA.getMensajes());
			
			//Scanner in = new Scanner(System.in);
			String mensaje;
			String check = "";
			show = "\n Has entrado en la sala de chat. Introduce el comando /salirchat para salir. Puedes bloquear al usuario con el comando /bloquearusuario.";
			Interfaz.notificar(show);
			//System.out.println("\n Has entrado en la sala de chat. Introduce el comando /salirchat para salir. Puedes bloquear al usuario con el comando /bloquearusuario.");
			while (!check.equalsIgnoreCase("/salirchat") && !check.equalsIgnoreCase("/bloquearusuario")){
				mensaje = in.nextLine();
				check = mensaje.trim();
				if (!check.equalsIgnoreCase("/salirchat") && !check.equalsIgnoreCase("/bloquearusuario")){
					//mensaje = usuario.getNombre() + ":\t" + mensaje + "\n";
					//chatA.addMensajes(mensaje);
					mensajeDAO.createMsj(chatA, usuario.getCorreo(), mensaje);
					//chatB.addMensajes(mensaje);
					mensajeDAO.createMsj(chatB, usuario.getCorreo(), mensaje);
					chatB.setMensajesNuevos(true);
					mensaje = usuario.getNombre() + ":\t" + mensaje + "\n";
					InterfazChats.notificar(chatA);
					//Interfaz.notificar(mensaje); // CAMBIAR POR MOSTRAR MENSAJES DE LA OTRA FORMA
					//System.out.println(mensaje);
				}else{
					if (check.equalsIgnoreCase("/bloquearusuario")){
						bloquearUsuario(usuario, usuarioDAO.read(chatA.getCorreo()));
						show = "Se ha bloqueado al usuario correctamente.\n";
						Interfaz.notificar(show);
						//System.out.println("Se ha bloqueado al usuario correctamente.\n");
						//if (usuario.getListaChats().isEmpty()) vacia = true;
						if (chatDAO.chatIds(usuario).isEmpty()) vacia = true;
					}
				}
					
			}
			//in.close();
			
		}
		else {
			show = "No se ha podido abrir el chat.";
			Interfaz.notificar(show);
			//System.out.println("No se ha podido abrir el chat.");
		}
		
		return vacia;
	}
	
	public void bloquearUsuario (TOUsuario usuario, TOUsuario usuarioABloquear) {
		
		// Entra dos usuarios
		// Se borra ambos pendiente y ambos chats
		// Se ponen ambos en conocido
		String correoUsuario = usuario.getCorreo();
		String correoAlt = usuarioABloquear.getCorreo();
		
		usuario.eliminarPendiente(correoAlt);
		usuarioABloquear.eliminarPendiente(correoUsuario);
		chatDAO.removeChat(usuario, correoAlt);
		//usuario.eliminarChat(correoAlt);
		chatDAO.removeChat(usuarioABloquear, correoUsuario);
		//usuarioABloquear.eliminarChat(correoUsuario);
		
		if (!usuario.getListaConocidos().contains(correoAlt)) 
			usuario.anadirConocido(correoAlt); 
			//Si no esta aun en lista de conocidos se añade
		if (!usuarioABloquear.getListaConocidos().contains(correoUsuario)) 
			usuarioABloquear.anadirConocido(correoUsuario);      
			//Si no esta aun en lista de conocidos se añade
	}
	
}
