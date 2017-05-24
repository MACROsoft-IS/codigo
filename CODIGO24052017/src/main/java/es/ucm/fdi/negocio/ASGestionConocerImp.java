package es.ucm.fdi.negocio;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import es.ucm.fdi.integracion.DAOSesionImp;
import es.ucm.fdi.integracion.DAOUsuarioImp;
import es.ucm.fdi.integracion.TOUsuario;
import es.ucm.fdi.negocio.Pendiente.tPendiente;

public class ASGestionConocerImp implements ASGestionConocer{
	
	private DAOUsuarioImp usuarioDAO;
	private DAOSesionImp sesionDAO;
	
	public ASGestionConocerImp (DAOUsuarioImp usuarioDAO,DAOSesionImp sesionDAO ) {
		this.usuarioDAO = usuarioDAO;
		this.sesionDAO = sesionDAO;
	}

	public int opcion() {
		
		int opcion;
		
		System.out.println("1.- Si\n 2.- No\n 3.- Bloquear Usuario\n 4.- Salir\n");
		
		Scanner in = new Scanner(System.in);
		
		opcion = in.nextInt();
		
		while(opcion != 1 && opcion != 2 && opcion != 3 && opcion != 4) {
			System.out.println("1.- Si\n 2.- No\n 3.- Bloquear Usuario\n 4.- Salir\n");
			opcion = in.nextInt();
		}
		
		in.close();
		
		return opcion;
		
	}
	
	public void conocerGente (TOUsuario usuario) {
		
		int opcion = 0;
		while (opcion != 4){ ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			// FASE 1: Comprobar lista de pendientes
			// 1.a: Si la lista de pendientes esta vacía
			// 1.b: Si en la lista de pendientes hay algun RECIBO
			// 1.c: Mostrar el RECIBO y tratar el SI o NO
			
			ArrayList<Pendiente> listaPendientes = usuario.getListaPendientes(); // Hacemos copia de la lista de pendientes
			int i = 0;
			
			if (!listaPendientes.isEmpty()){
				while (i < listaPendientes.size() && opcion != 4){
					if (listaPendientes.get(i).getTipo() == tPendiente.RECIBO) opcion = muestraPendiente(usuario, listaPendientes.get(i).getCorreo());
					i++;
				}
				/*
				for (int i = 0; i < listaPendientes.size(); i++){
					if (listaPendientes.get(i).getTipo() == tPendiente.RECIBO) opcion = muestraPendiente(usuario, listaPendientes.get(i).getCorreo());
				}*/
			}
			
			// FASE 2: Buscar en la base de datos
			// 2.a: Comprobar si el index de la BD == al de conocidos
			// 2.b: Buscar un usuario random y comprobar si esta en conocidos
			// 2.c: Mostrar y tratar el SI o NO
			
			if (opcion != 4){
				ArrayList<String> listaConocidos = usuario.getListaConocidos(); // Hacemos copia de la lista de conocidos
				
				Random rand = new Random();
				int num;
				String correoAlt;
				Vector<String> correos = ASGestionCuentasImp.bdUsers.getIds(); 
				//boolean ok = false;
				
				if (listaConocidos.size() < correos.size()){
					while (/*!ok*/ opcion != 4 && listaConocidos.size() < correos.size()){
						num = rand.nextInt() % correos.size();
						correoAlt = correos.get(num);
						if (!listaConocidos.contains(correoAlt)){
							opcion = muestra(usuario, correoAlt);
							//ok = true;
						}
					}
				}
				else{
					usuario.setAvisos("Has conocido a toda la gente. Pronto habrá más personas a las que conocer");
					opcion = 4; // Con esta linea, nos aseguramos que salga cuando no haya más personas que mostrar
					
					//System.out.println("Ups, has conocido a toda la gente. Pronto tendremos mas amiguitos"); // CAMBIAR
				}
				
				// FASE 3: Tratar el SI o NO
				// 3.a: SI pendiente ---> muestraPendiente(...)
				// 3.b: NO pendiente ---> muestraPendiente(...)
				// 3.c: SI normal    ---> muestra(...)
				// 3.d: NO normal    ---> muestra(...)
			}
			
		}
		
	}
	
	public int muestraPendiente (TOUsuario usuario, String correoAlt){
		
		TOUsuario usuarioAlt = usuarioDAO.read(correoAlt);
		String correoUsuario = usuario.getCorreo();
		
		
		usuarioAlt.toString();
		
		int opcion = opcion();
		
		switch(opcion){
		// SI Pendiente
		case 1:  
		usuario.añadirChat(correoAlt);               // Creamos chat en el usuario principal
		usuarioAlt.añadirChat(correoUsuario);        // Creamos chat en el usuarioAlt
		usuario.añadirConocido(correoAlt);           // Añadimos conocido en el usuario
		usuario.eliminarPendiente(correoAlt);        // Eliminamos pendientes en el usuario principal
		usuarioAlt.eliminarPendiente(correoUsuario); // Eliminamos pendientes en el usuarioAlt
		usuario.addAviso("Se ha añadido a " + usuarioAlt.getNombre() + " a la lista de chat.\n");
		usuarioAlt.addAviso("Se ha añadido a " + usuario.getNombre() + " a la lista de chat.\n");
			break;
		// NO Pendiente
		case 2:  
		usuario.añadirConocido(correoAlt);           // Añadimos conocido en el usuario
		usuario.eliminarPendiente(correoAlt);        // Eliminamos pendiente en el usuario principal
		usuarioAlt.eliminarPendiente(correoUsuario); // Eliminamos pendiente en el usuarioAlt
			break;
		// BLOQUEAR USUARIO
		case 3:  
			//DESCOMENTAR ESTO
			//bloquearUsuario(usuario, usuarioAlt);
		System.out.println("Se ha bloqueado al usuario " + usuarioAlt.getNombre() + ".\n");
			break;
		// SALIR
		case 4: 
		System.out.println("Ha salido de Conocer Gente.\n"); 
		break;
		}
		
		return opcion;
		
		/*if (opcion()){ // SI Pendiente
			usuario.añadirChat(correoAlt);               // Creamos chat en el usuario principal
			usuarioAlt.añadirChat(correoUsuario);        // Creamos chat en el usuarioAlt
			usuario.añadirConocido(correoAlt);           // Añadimos conocido en el usuario
			usuario.eliminarPendiente(correoAlt);        // Eliminamos pendientes en el usuario principal
			usuarioAlt.eliminarPendiente(correoUsuario); // Eliminamos pendientes en el usuarioAlt
			usuario.addAviso("Se ha añadido a " + usuarioAlt.getNombre() + " a la lista de chat.\n");
			usuarioAlt.addAviso("Se ha añadido a " + usuario.getNombre() + " a la lista de chat.\n");
		}
		else{ // NO pendiente
			usuario.añadirConocido(correoAlt);           // Añadimos conocido en el usuario
			usuario.eliminarPendiente(correoAlt);        // Eliminamos pendiente en el usuario principal
			usuarioAlt.eliminarPendiente(correoUsuario); // Eliminamos pendiente en el usuarioAlt
		}*/
		
	}
	
	public int muestra (TOUsuario usuario, String correoAlt){
		
		TOUsuario usuarioAlt = usuarioDAO.read(correoAlt);
		String correoUsuario = usuario.getCorreo();
		
		usuarioAlt.toString();
		
		int opcion = opcion();
		
		switch(opcion){
		// SI NORMAL
		case 1:  
		usuario.añadirPendiente(correoAlt, tPendiente.MANDO);         //Añadimos pendiente en el usuario principal
		usuarioAlt.añadirPendiente(correoUsuario, tPendiente.RECIBO); //Añadimos pendiente en el usuarioAlt
		usuario.añadirConocido(correoAlt);                            //Añadimos conocido en el usuario principal
			break;
		// NO NORMAL
		case 2:  
		usuario.añadirConocido(correoAlt);                            //Añadimos conocido al principal
		usuarioAlt.añadirConocido(correoUsuario);                     //Añadimos conocido al Alt
			break;
		// BLOQUEAR USUARIO
		case 3:  
			
			///DESCOMENTAR ESTO//
		//bloquearUsuario(usuario, usuarioAlt);
		System.out.println("Se ha bloqueado al usuario " + usuarioAlt.getNombre() + ".\n");
			break;
		// SALIR
		case 4: 
		System.out.println("Ha salido de Conocer Gente.\n"); 
		break;
		}
		
		return opcion;
		
		/*
		if (opcion()){
			usuario.añadirPendiente(correoAlt, tPendiente.MANDO);         //Añadimos pendiente en el usuario principal
			usuarioAlt.añadirPendiente(correoUsuario, tPendiente.RECIBO); //Añadimos pendiente en el usuarioAlt
			usuario.añadirConocido(correoAlt);                            //Añadimos conocido en el usuario principal
		}
		else{
			usuario.añadirConocido(correoAlt);                            //Añadimos conocido al principal
			usuarioAlt.añadirConocido(correoUsuario);                     //Añadimos conocido al Alt
		}*/
	}
	
}
