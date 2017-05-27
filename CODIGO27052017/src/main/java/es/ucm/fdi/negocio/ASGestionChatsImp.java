package es.ucm.fdi.negocio;

import java.util.ArrayList;
import java.util.Scanner;

import es.ucm.fdi.integracion.DAOSesionImp;
import es.ucm.fdi.integracion.DAOUsuarioImp;
import es.ucm.fdi.integracion.TOChat;
import es.ucm.fdi.integracion.TOUsuario;

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
		
		if(mostrarChats(listaChats)){
			int opChat = 0;
			
			while (opChat != -1){
				opChat = opChat(listaChats.size(), in);
				if (opChat != -1 && opChat != -2){
					usuarioAlt = usuarioDAO.read(listaChats.get(opChat).getCorreo());
					salaChat(usuario, usuarioAlt, in);
					mostrarChats(listaChats);
				}
				else{
					if (opChat == -2) {
						System.out.println("Chat no encontrado.\n");
						mostrarChats(listaChats);
					}
				}
			}
		}
		
		//in.close();
	}
	
	private boolean mostrarChats(ArrayList<TOChat> listaChats){
		
		String correoAlt, lineachat;
		TOUsuario usuarioAlt;
		boolean mostrar;
		
		if (listaChats.isEmpty()) {
			System.out.println("No tienes chats abiertos actualmente.\n");
			mostrar = false;
		}
		else{
			System.out.println("Nº\t Nombre \t Mensajes Nuevos");
			for (int i = 0; i < listaChats.size(); i++){
				correoAlt = listaChats.get(i).getCorreo();
				usuarioAlt = usuarioDAO.read(correoAlt);
				lineachat = "\n" + (i+1) + "\t" + usuarioAlt.getNombre() + "\t";
				//System.out.println("\n" + (i+1) + "\t" + usuarioAlt.getNombre() + "\t");
				if (listaChats.get(i).getMensajesNuevos()) lineachat = lineachat + "MN";
				System.out.println(lineachat);
			}
			System.out.println("\n Introduce el numero del chat al que desea acceder o 0 para salir.");
			mostrar = true;
		}
		return mostrar;

	}
	
	private int opChat(int size, Scanner in){
		try{
			int opcion;
			
			//Scanner in = new Scanner(System.in);
			
			opcion = Integer.parseInt(in.nextLine());
			
			while(opcion < 0 || opcion > size) {
				System.out.println("Chat no encontrado.");
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
	
	private void salaChat(TOUsuario usuario, TOUsuario usuarioAlt, Scanner in){
		
		ArrayList<TOChat> listaA = usuario.getListaChats();
		ArrayList<TOChat> listaB = usuarioAlt.getListaChats();
		TOChat chatA = null;
		TOChat chatB = null;
		
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
		
		abrirChat(chatA, chatB, usuario, in);
		
	}
	
	private void abrirChat(TOChat chatA, TOChat chatB, TOUsuario usuario, Scanner in){
		
		if (chatA != null && chatB != null){
			chatA.setMensajesNuevos(false);
			System.out.println(chatA.getMensajes());
			
			//Scanner in = new Scanner(System.in);
			String mensaje;
			String check = "";
			System.out.println("\n Has entrado en la sala de chat. Introduce el comando /salirchat para salir.");
			while (!check.equalsIgnoreCase("/salirchat")){
				mensaje = in.nextLine();
				check = mensaje.trim();
				if (!check.equalsIgnoreCase("/salirchat")){
					mensaje = usuario.getNombre() + ":\t" + mensaje + "\n";
					chatA.addMensajes(mensaje);
					chatB.addMensajes(mensaje);
					chatB.setMensajesNuevos(true);
					System.out.println(mensaje);
				}
			}
			//in.close();
			
		}
		else System.out.println("No se ha podido abrir el chat.");
	}
	
}
