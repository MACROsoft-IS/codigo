package es.ucm.fdi.negocio;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

//import es.ucm.fdi.datos.BDMemoria;
import es.ucm.fdi.integracion.DAOSesionImp;
import es.ucm.fdi.integracion.DAOUsuarioImp;
import es.ucm.fdi.integracion.TOSesionImp;
import es.ucm.fdi.integracion.TOUsuarioImp;
import es.ucm.fdi.integracion.TOUsuarioImp.Chat;
//import es.ucm.fdi.integracion.TOUsuarioImp;
import es.ucm.fdi.integracion.TOUsuarioImp.Pendiente;
import es.ucm.fdi.integracion.TOUsuarioImp.Pendiente.tPendiente;

public class ASGestionCuentaImp {

	private DAOUsuarioImp usuarioDAO;
	private DAOSesionImp sesionDAO;
	//public static BDMemoria<TOUsuarioImp> bdCadenas;

	public ASGestionCuentaImp() {
		//ASGestionCuentaImp.bdCadenas = new BDMemoria<TOUsuarioImp>();
		this.sesionDAO = new DAOSesionImp();
		this.usuarioDAO = new DAOUsuarioImp();
	}

	public DAOUsuarioImp getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(DAOUsuarioImp usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public DAOSesionImp getSesionDAO() {
		return sesionDAO;
	}

	public void setSesionDAO(DAOSesionImp sesionDAO) {
		this.sesionDAO = sesionDAO;
	}

	public TOSesionImp iniciarSesion(String correo, String contrasena) {

		TOSesionImp sesion = null;
		TOUsuarioImp usuario = usuarioDAO.read(correo); 

		if (usuario!=null && usuario.getContrasena().equals(contrasena))

			sesion = sesionDAO.createSesion(usuario);
		return sesion;
	}

	/*
	public BDMemoria<TOUsuarioImp> getBdCadenas() {
		return bdCadenas;
	}

	public void setBdCadenas(BDMemoria<TOUsuarioImp> bdCadenas) {
		ASGestionCuentaImp.bdCadenas = bdCadenas;
	}*/

	public boolean crearCuenta(String nombre, String correo, String contrasena) {

		boolean cuenta = false;
		
		if (usuarioDAO.read(correo) == null) {
			
			TOUsuarioImp usuario = new TOUsuarioImp(nombre, correo, contrasena);
			usuarioDAO.create(usuario);
			cuenta = true;
		}
		return cuenta;
	
	}
	
	public void cerrarSesion(TOSesionImp sesion){
		sesionDAO.closeSesion(sesion);
	}
	
	public void eliminarCuenta(TOUsuarioImp usuario, TOSesionImp sesion){
		
		ArrayList<String> listaConocidos = usuario.getListaConocidos(); // Hacemos copia de la lista de conocidos
		ArrayList<Pendiente> listaPendientes = usuario.getListaPendientes(); // Hacemos copia de la lista de pendientes
		ArrayList<Chat> listaChats = usuario.getListaChats(); // Hacemos copia de la lista de chats
		String correoUsuario;
		TOUsuarioImp usuarioAlt;
		Pendiente pendiente;
		Chat chat;

		
		// Lista de conocidos
		for (int i = 0; i < listaConocidos.size(); i++){ // Recorremos la lista de conocidos
			correoUsuario = listaConocidos.get(i); // Recibimos el correo i
			usuarioAlt = usuarioDAO.read(correoUsuario); // Con el correo sacamos el usuarioAlt
			usuarioAlt.eliminarConocido(usuario.getCorreo()); // Borramos de la lista de conocidos del usuarioAlt a el usuario que quiere borrar la cuenta
		}
		
		// Lista de pendientes
		for (int i = 0; i < listaPendientes.size(); i++){ // Recorremos la lista de pendientes
			pendiente = listaPendientes.get(i); // Recibimos el pendiente i
			correoUsuario = pendiente.getCorreo(); // Sacamos el correo del pendiente i
			usuarioAlt = usuarioDAO.read(correoUsuario); // Con el correo sacamos el usuario
			usuarioAlt.eliminarPendiente(usuario.getCorreo()); // Borramos de la lista de pendientes del usuarioAlt a el usuario que quiere borrar la cuenta
		}
		
		// Lista de chats
		for (int i = 0; i < listaChats.size(); i++){ // Recorremos la lista de chats
			chat = listaChats.get(i); // Recibimos el chat i
			correoUsuario = chat.getCorreo(); // Sacamos el correo del chat i
			usuarioAlt = usuarioDAO.read(correoUsuario); // Con el correo sacamos el usuarioAlt 
			usuarioAlt.eliminarChat(usuario.getCorreo()); // Borramos de la lista de chats del usuarioAlt a el usuario que quiere borrar la cuenta
		}
		
		// Eliminamos cuenta
		usuarioDAO.removeAccount(usuario.getCorreo());
		
		// Cerramos sesion
		cerrarSesion(sesion);
		
	}
	
	public boolean opcion() {
		
		int opcion;
		
		System.out.println("1.- Si" + System.getProperty("line.separator") + "2.- No");
		
		Scanner in = new Scanner(System.in);
		
		opcion = in.nextInt();
		
		while(opcion != 1 && opcion != 2) {
			System.out.println("1.- Si" + System.getProperty("line.separator") + "2.- No");
			opcion = in.nextInt();
		}
		
		in.close();
		
		return opcion == 1;
		
	}
	
	public void conocerGente (TOUsuarioImp usuario) {
		
		// FASE 1: Comprobar lista de pendientes
		// 1.a: Si la lista de pendientes esta vacía
		// 1.b: Si en la lista de pendientes hay algun RECIBO
		// 1.c: Mostrar el RECIBO y tratar el SI o NO
		
		ArrayList<Pendiente> listaPendientes = usuario.getListaPendientes(); // Hacemos copia de la lista de pendientes
		
		if (!listaPendientes.isEmpty()){
			for (int i = 0; i < listaPendientes.size(); i++){
				if (listaPendientes.get(i).getTipo() == tPendiente.RECIBO) muestraPendiente(usuario, listaPendientes.get(i).getCorreo());
			}
		}
		
		// FASE 2: Buscar en la base de datos
		// 2.a: Comprobar si el index de la BD == al de conocidos
		// 2.b: Buscar un usuario random y comprobar si esta en conocidos
		// 2.c: Mostrar y tratar el SI o NO
		
		ArrayList<String> listaConocidos = usuario.getListaConocidos(); // Hacemos copia de la lista de conocidos
		
		Random rand = new Random();
		int num;
		String correoAlt;
		Vector<String> correos = BD.getIds();
		boolean ok = false;
		
		if (listaConocidos.size() < correos.size()){
			while (!ok){
				num = rand.nextInt() % correos.size();
				correoAlt = correos.get(num);
				if (!listaConocidos.contains(correoAlt)){
					muestra(usuario, correoAlt);
					ok = true;
				}
			}
		}
		else{
			System.out.println("Ups, has conocido a toda la gente. Pronto tendremos mas amiguitos"); // CAMBIAR
		}
		
		// FASE 3: Tratar el SI o NO
		// 3.a: SI pendiente ---> muestraPendiente(...)
		// 3.b: NO pendiente ---> muestraPendiente(...)
		// 3.c: SI normal    ---> muestra(...)
		// 3.d: NO normal    ---> muestra(...)
		
	}
	
	public void muestraPendiente (TOUsuarioImp usuario, String correoAlt){
		
		TOUsuarioImp usuarioAlt = usuarioDAO.read(correoAlt);
		String correoUsuario = usuario.getCorreo();
		
		usuarioAlt.toString();
		
		if (opcion()){ // SI Pendiente
			usuario.añadirChat(correoAlt);               // Creamos chat en el usuario principal
			usuarioAlt.añadirChat(correoUsuario);        // Creamos chat en el usuarioAlt
			usuarioAlt.añadirConocido(correoUsuario);    // Añadimos conocido en el usuarioAlt
			usuario.eliminarPendiente(correoAlt);        // Eliminamos pendientes en el usuario principal
			usuarioAlt.eliminarPendiente(correoUsuario); // Eliminamos pendientes en el usuarioAlt
		}
		else{ // NO pendiente
			usuarioAlt.añadirConocido(correoUsuario);    // Añadimos conocido en el usuarioAlt
			usuario.eliminarPendiente(correoAlt);        // Eliminamos pendiente en el usuario principal
			usuarioAlt.eliminarPendiente(correoUsuario); // Eliminamos pendiente en el usuarioAlt
		}
		
	}
	
	public void muestra (TOUsuarioImp usuario, String correoAlt){
		
		TOUsuarioImp usuarioAlt = usuarioDAO.read(correoAlt);
		String correoUsuario = usuario.getCorreo();
		
		usuarioAlt.toString();
		
		if (opcion()){
			usuario.añadirPendiente(correoAlt, tPendiente.MANDO);         //Añadimos pendiente en el usuario principal
			usuarioAlt.añadirPendiente(correoUsuario, tPendiente.RECIBO); //Añadimos pendiente en el usuarioAlt
			usuario.añadirConocido(correoAlt);                            //Añadimos conocido en el usuario principal
		}
		else{
			usuario.añadirConocido(correoAlt);                            //Añadimos conocido al principal
			usuarioAlt.añadirConocido(correoUsuario);                     //Añadimos conocido al Alt
		}
	}
	
	public void bloquearUsuario (TOUsuarioImp usuario, TOUsuarioImp usuarioABloquear) {
		
		// Entra dos usuarios
		// Se borra ambos pendiente y ambos chats
		// Se ponen ambos en conocido
		String correoUsuario = usuario.getCorreo();
		String correoAlt = usuarioABloquear.getCorreo();
		
		usuario.eliminarPendiente(correoAlt);
		usuarioABloquear.eliminarPendiente(correoUsuario);
		usuario.eliminarChat(correoAlt);
		usuarioABloquear.eliminarChat(correoUsuario);
		if (!usuario.getListaConocidos().contains(correoAlt)) usuario.añadirConocido(correoAlt);                                //Si no esta aun en lista de conocidos se añade
		if (!usuarioABloquear.getListaConocidos().contains(correoUsuario)) usuarioABloquear.añadirConocido(correoUsuario);      //Si no esta aun en lista de conocidos se añade
	}
	
	public void personalizarPerfil (TOUsuarioImp usuario, String contrasena, String nombre, String descripcion, String foto) {
		
		// Comprobamos que los campos no sean null puesto que el usuario no tiene por que modificar todos los campos. Los campos que no modifique estaran en null.
		
		if (contrasena != null) usuario.setContrasena(contrasena);
		if (nombre != null) usuario.setNombre(nombre); 
		if (descripcion != null) usuario.setDescripcion(descripcion);
		if (foto != null) usuario.setFoto(foto);
		
	}
	
	public void chatear(TOUsuarioImp usuario){
		
		ArrayList<Chat> listaChats = usuario.getListaChats();
		TOUsuarioImp usuarioAlt;
		
		mostrarChats(listaChats);
		
		int opChat = 0;
		
		while (opChat != -1){
			opChat = opChat(listaChats.size());
			usuarioAlt = usuarioDAO.read(listaChats.get(opChat).getCorreo());
			salaChat(usuario, usuarioAlt);
			mostrarChats(listaChats);
		}
	}
	
	public void mostrarChats(ArrayList<Chat> listaChats){
		
		String correoAlt;
		TOUsuarioImp usuarioAlt;
		
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
	
	public void salaChat(TOUsuarioImp usuario, TOUsuarioImp usuarioAlt){
		
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
	
	public void abrirChat(Chat chatA, Chat chatB, TOUsuarioImp usuario){
		
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
