package es.ucm.fdi.negocio;

import java.util.ArrayList;
import java.util.Scanner;

import es.ucm.fdi.integracion.DAOSesionImp;
import es.ucm.fdi.integracion.DAOUsuarioImp;
import es.ucm.fdi.integracion.TOUsuario;

public class ASGestionChatsImp implements ASGestionChats {
	
	private DAOUsuarioImp usuarioDAO;
	private DAOSesionImp sesionDAO;
	
	public ASGestionChatsImp(DAOUsuarioImp usuarioDAO,DAOSesionImp sesionDAO ) {
		this.usuarioDAO = usuarioDAO;
		this.sesionDAO = sesionDAO;
	}
	
	public void chatear(TOUsuario usuario){
		
		ArrayList<Chat> listaChats = usuario.getListaChats();
		TOUsuario usuarioAlt;
		
		mostrarChats(listaChats);
		
		int opChat = 0;
		
		while (opChat != -1){
			opChat = opChat(listaChats.size());
			if (opChat != -1){
				usuarioAlt = usuarioDAO.read(listaChats.get(opChat).getCorreo());
				salaChat(usuario, usuarioAlt);
				mostrarChats(listaChats);
			}
		}
	}
	
	public void mostrarChats(ArrayList<Chat> listaChats){
		
		String correoAlt;
		TOUsuario usuarioAlt;
		
		for (int i = 0; i < listaChats.size(); i++){
			correoAlt = listaChats.get(i).getCorreo();
			usuarioAlt = usuarioDAO.read(correoAlt);
			System.out.println("\n" + (i+1) + "\t" + usuarioAlt.getNombre() + "\t");
			if (listaChats.get(i).getMensajesNuevos()) System.out.println("MN");
		}
		System.out.println("\n Introduce el numero del chat al que desea acceder o 0 para salir.");
	}
	
	public int opChat(int size){
		
		int opcion;
				
		Scanner in = new Scanner(System.in);
		
		opcion = in.nextInt();
		
		while(opcion < 0 && opcion >= size) {
			System.out.println("Chat no encontrado.");
			opcion = in.nextInt();
		}
		
		in.close();
		
		return opcion--;
	}
	
	public void salaChat(TOUsuario usuario, TOUsuario usuarioAlt){
		
		ArrayList<Chat> listaA = usuario.getListaChats();
		ArrayList<Chat> listaB = usuarioAlt.getListaChats();
		Chat chatA = null;
		Chat chatB = null;
		
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
		
		abrirChat(chatA, chatB, usuario);
		
	}
	
	public void abrirChat(Chat chatA, Chat chatB, TOUsuario usuario){
		
		if (chatA != null && chatB != null){
			chatA.setMensajesNuevos(false);
			System.out.println(chatA.getMensajes());
			
			Scanner in = new Scanner(System.in);
			String mensaje;
			String check = null;
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
			in.close();
			
		}
		else System.out.println("No se ha podido abrir el chat.");
	}
	
}
