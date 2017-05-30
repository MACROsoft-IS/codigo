package es.ucm.fdi.interfaz;

import java.util.Scanner;

import es.ucm.fdi.negocio.*;
import es.ucm.fdi.integracion.*;

public class Launcher {

	public static Scanner sc = new Scanner(System.in);
	
	//lo dejamos aquii??
	

	static InterfazChats interfazChat = new InterfazChats();
	static InterfazCuentas interfazCuentas = new InterfazCuentas();
	static InterfazPersonalizar interfazPersonalizar = new InterfazPersonalizar();
	
	public static void main(String[] args) {
		
		
		// valorar donde hay que iniciarlizar las cosas ,, si fuera o dentro
		DAOFactory fd = new DAOFactoryImp();
		
		DAOUsuarioImp daoUsuario = fd.generaDAOUsuario();
		DAOSesionImp daoSesion = fd.generaDAOSesion();
		DAOChatImp daoChat = fd.generaDAOChat();
		DAOMensajeImp daoMensaje = fd.generaDAOMensaje();
		
//		InterfazConocer interfazConocer =  new InterfazConocer();
//		InterfazChats interfazChat = new InterfazChats();
//		InterfazCuentas interfazCuentas = new InterfazCuentas();
//		InterfazPersonalizar interfazPersonalizar = new InterfazPersonalizar();
			
		//mirar si hay que meter los daos en las cosnrttuxatasa
		
		ASGestionCuentasImp AsGCuentas = new ASGestionCuentasImp(daoUsuario,daoSesion, daoChat);
		ASGestionConocerImp AsGConocer = new ASGestionConocerImp(daoUsuario, daoChat);
		ASGestionChatsImp AsGChats = new ASGestionChatsImp(daoUsuario,daoSesion, daoChat, daoMensaje);
		
		menuPrincipalInicio(AsGCuentas,AsGConocer,AsGChats);
		
		System.exit(0);
	}
	
	
	private static void menuPrincipalInicio(ASGestionCuentasImp AsGCuentas, ASGestionConocerImp AsGConocer,
			ASGestionChatsImp AsGChats) {
		
		try{
			
			int opcion = -1;
			TOSesion sesion = null;
			
			while(opcion != 0) {
				
				System.out.println("\t\t ~Bienvenido a MyUni~\n");
				
				System.out.println("Elija una opción: ");
				System.out.println("0 - Salir");
				System.out.println("1 - Crear cuenta");
				System.out.println("2 - Iniciar sesión");
				
				opcion = Integer.parseInt(sc.nextLine());
				
				switch (opcion) {
				case 1:
					sesion = interfazCuentas.menuCrearCuenta(AsGCuentas);
					if (sesion != null) menuDeUsuario(AsGCuentas, sesion, AsGConocer, AsGChats);
					break;
				case 2:
					sesion = interfazCuentas.menuIniciarSesion(AsGCuentas); //donde redigir una vez la sesion iniciada?
					if (sesion != null) menuDeUsuario(AsGCuentas, sesion, AsGConocer, AsGChats);
					break;
				case 0:
					System.out.println("Gracias por usar la aplicación");
					break;
				default:
					System.out.println("Opción no valida");
					break;
				}
				
			}
		}
		catch(NumberFormatException e){
			System.out.println("Error: opción no válida.\n");
			menuPrincipalInicio(AsGCuentas,AsGConocer,AsGChats);
		}
		
		
	}
	
	private static void menuDeUsuario(ASGestionCuentasImp AsGCuentas, TOSesion cuentaActiva,
			ASGestionConocerImp AsGConocer, ASGestionChatsImp AsGChats) {
		
		InterfazConocer interfazConocer =  new InterfazConocer(AsGConocer);
		
		try{
			int opcion = -1;
			
			while(opcion != 0 && cuentaActiva.getSesionEstado()) {
				
				System.out.println("\t\t ~Menú de usuario~\n");
				
				System.out.println("Elija una opción: ");
				System.out.println("0 - Cerrar sesión");
				System.out.println("1 - Conocer gente");
				System.out.println("2 - Chats");
				System.out.println("3 - Perfil de usuario");
				
				opcion = Integer.parseInt(sc.nextLine());
				
				switch (opcion) {
				case 1:
					interfazConocer.pantallaConocerGente(cuentaActiva.getCuentaActiva());
					//System.out.println(cuentaActiva.getCuentaActiva().getAvisos());
					break;
				case 2:
					AsGChats.chatear(cuentaActiva.getCuentaActiva());
					break;
				case 3:
					interfazPersonalizar.menuPersonalizarPerfil(AsGCuentas, cuentaActiva, AsGConocer, AsGChats);
					break;
				case 0:
					AsGCuentas.cerrarSesion(cuentaActiva);
					System.out.println("Se ha cerrado la sesión correctamente");
					break;
				default:
					System.out.println("Opción no válida");
					break;
				}
			
			}
		}
		catch(NumberFormatException e){
			System.out.println("Error: opción no válida.\n");
			menuDeUsuario(AsGCuentas, cuentaActiva, AsGConocer, AsGChats);
		}
		
		
	}
}

	
	