package es.ucm.fdi.negocio;

import java.util.ArrayList;
import java.util.Scanner;

import es.ucm.fdi.integracion.DAOSesionImp;
import es.ucm.fdi.integracion.DAOUsuarioImp;
import es.ucm.fdi.integracion.TOChat;
import es.ucm.fdi.integracion.TOUsuario;
import es.ucm.fdi.vista.Vista;

public class ASGestionChatsImp implements ASGestionChats {
	
	private DAOUsuarioImp usuarioDAO;
	//private DAOSesionImp sesionDAO;
	
	public ASGestionChatsImp(DAOUsuarioImp usuarioDAO,DAOSesionImp sesionDAO ) {
		this.usuarioDAO = usuarioDAO;
		//this.sesionDAO = sesionDAO;
	}
	
	public void chatear(TOUsuario usuario){
		
		ArrayList<TOChat> listaChats = usuario.getListaChats();
		TOUsuario usuarioAlt;
		Scanner in = new Scanner(System.in);
		boolean vacia = false;
		String mensaje;
		
		if(mostrarChats(listaChats)){
			int opChat = 0;
			
			while (opChat != -1){
				opChat = opChat(listaChats.size(), in);
				if (opChat != -1 && opChat != -2){
					usuarioAlt = usuarioDAO.read(listaChats.get(opChat).getCorreo());
					vacia = salaChat(usuario, usuarioAlt, in);
					mostrarChats(listaChats);
					if (vacia) opChat = -1;
				}
				else{
					if (opChat == -2) {
						mensaje = "Chat no encontrado.\n";
						Vista.notificar(mensaje);
						//System.out.println("Chat no encontrado.\n");
						mostrarChats(listaChats);
					}
				}
			}
		}
		
		//in.close();
	}
	
	private boolean mostrarChats(ArrayList<TOChat> listaChats){
		
		String correoAlt, lineachat, mensaje;
		TOUsuario usuarioAlt;
		boolean mostrar;
		
		if (listaChats.isEmpty()) {
			mensaje = "No tienes chats abiertos actualmente.\n";
			Vista.notificar(mensaje);
			//System.out.println("No tienes chats abiertos actualmente.\n");
			mostrar = false;
		}
		else{
			mensaje = "Nº\t Nombre \t Mensajes Nuevos";
			Vista.notificar(mensaje);
			//System.out.println("Nº\t Nombre \t Mensajes Nuevos");
			for (int i = 0; i < listaChats.size(); i++){
				correoAlt = listaChats.get(i).getCorreo();
				usuarioAlt = usuarioDAO.read(correoAlt);
				lineachat = "\n" + (i+1) + "\t" + usuarioAlt.getNombre() + "\t";
				//System.out.println("\n" + (i+1) + "\t" + usuarioAlt.getNombre() + "\t");
				if (listaChats.get(i).getMensajesNuevos()) lineachat = lineachat + "MN";
				Vista.notificar(lineachat);
				//System.out.println(lineachat);
			}
			mensaje = "\n Introduce el numero del chat al que desea acceder o 0 para salir.";
			Vista.notificar(mensaje);
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
				Vista.notificar(mensaje);
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
		
		ArrayList<TOChat> listaA = usuario.getListaChats();
		ArrayList<TOChat> listaB = usuarioAlt.getListaChats();
		TOChat chatA = null;
		TOChat chatB = null;
		boolean vacia = false;
		
		int i = 0;
		boolean encontrado = false;
		// Buscamos el chat A
		
		while (i< listaA.size() && !encontrado){
			if (listaA.get(i).getCorreo().equalsIgnoreCase(usuarioAlt.getCorreo())){
				chatA = listaA.get(i);
				encontrado = true;
			}else i++;
		}
		
		i = 0;
		encontrado = false;
		// Buscamos el chat B
		while (i< listaB.size() && !encontrado){
			if (listaB.get(i).getCorreo().equalsIgnoreCase(usuario.getCorreo())){
				chatB = listaB.get(i);
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
			Vista.notificar(chatA.getMensajes());
			//System.out.println(chatA.getMensajes());
			
			//Scanner in = new Scanner(System.in);
			String mensaje;
			String check = "";
			show = "\n Has entrado en la sala de chat. Introduce el comando /salirchat para salir. Puedes bloquear al usuario con el comando /bloquearusuario.";
			Vista.notificar(show);
			//System.out.println("\n Has entrado en la sala de chat. Introduce el comando /salirchat para salir. Puedes bloquear al usuario con el comando /bloquearusuario.");
			while (!check.equalsIgnoreCase("/salirchat") && !check.equalsIgnoreCase("/bloquearusuario")){
				mensaje = in.nextLine();
				check = mensaje.trim();
				if (!check.equalsIgnoreCase("/salirchat") && !check.equalsIgnoreCase("/bloquearusuario")){
					mensaje = usuario.getNombre() + ":\t" + mensaje + "\n";
					chatA.addMensajes(mensaje);
					chatB.addMensajes(mensaje);
					chatB.setMensajesNuevos(true);
					Vista.notificar(mensaje);
					//System.out.println(mensaje);
				}else{
					if (check.equalsIgnoreCase("/bloquearusuario")){
						ASGestionConocerImp.bloquearUsuario(usuario, usuarioDAO.read(chatA.getCorreo()));
						show = "Se ha bloqueado al usuario correctamente.\n";
						Vista.notificar(show);
						//System.out.println("Se ha bloqueado al usuario correctamente.\n");
						if (usuario.getListaChats().isEmpty()) vacia = true;
					}
				}
					
			}
			//in.close();
			
		}
		else {
			show = "No se ha podido abrir el chat.";
			Vista.notificar(show);
			//System.out.println("No se ha podido abrir el chat.");
		}
		
		return vacia;
	}
	
}
