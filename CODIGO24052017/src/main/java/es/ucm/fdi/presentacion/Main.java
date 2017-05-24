package es.ucm.fdi.presentacion;

import java.util.Scanner;

import es.ucm.fdi.negocio.*;
import es.ucm.fdi.integracion.*;

public class Main {
	
	static Scanner sc =  new Scanner (System.in);
	
	public static void main(String[] args) {
		
		System.out.println("Bienvenido a MyUni");
		
		ASGestionCuentasImp AsGCuentas = new ASGestionCuentasImp();
		ASGestionConocerImp AsGConocer = new ASGestionConocerImp(AsGCuentas.getUsuarioDAO(),AsGCuentas.getSesionDAO());
		ASGestionChatsImp AsGChats = new ASGestionChatsImp(AsGCuentas.getUsuarioDAO(),AsGCuentas.getSesionDAO());
		
		menuPrincipalInicio(AsGCuentas,AsGConocer,AsGChats);

	} 
	
	private static void menuPrincipalInicio(ASGestionCuentasImp AsGCuentas, ASGestionConocerImp AsGConocer,
			ASGestionChatsImp AsGChats) {
		
	
		int opcion = -1;
		
		while(opcion != 0) {
			
			System.out.println("Elija una opción: ");
			System.out.println("0 - Salir");
			System.out.println("1 - Crear cuenta");
			System.out.println("2 - Iniciar sesión");
			
			opcion = Integer.parseInt(sc.nextLine());
			
			switch (opcion) {
			case 1:
				if(menuCrearCuenta(AsGCuentas)) System.out.println("La cuenta ha sido creada correctamente");
				else System.out.println("Error creando la cuenta");
				break;
			case 2:
				TOSesion sesion;
				sesion = menuIniciarSesion(AsGCuentas); //donde redigir una vez la sesion iniciada?
				if(sesion != null) menuDeUsuario(AsGCuentas, sesion, AsGConocer, AsGChats);
				else System.out.println("Error al iniciar sesión");
				break;
			case 0:
				System.out.println("Gracias por usar la aplicacion");
				break;
			default:
				System.out.println("Opcion no valida");
				break;
			}
		}

		sc.close();
		System.exit(0);
		
	}
	
	private static void menuPersonalizarPerfil(ASGestionCuentasImp AsGCuentas, TOSesion cuentaActiva,
			ASGestionConocerImp AsGConocer, ASGestionChatsImp AsGChats) {

		int opcion = -1;
		String line;
		
		while(opcion != 0) {
			
			System.out.println("Elija una opción: ");
			System.out.println("0 - Atrás");
			System.out.println("1 - Cambiar nombre");
			System.out.println("2 - Cambiar contraseña");
			System.out.println("3 - Cambiar descripción");
			System.out.println("4 - Cambiar foto");
			
			opcion = Integer.parseInt(sc.nextLine());

			
			switch (opcion) {
			case 1:
				System.out.println("Nuevo nombre: ");
				line = sc.nextLine();
				AsGCuentas.personalizarPerfil(cuentaActiva.getCuentaActiva(), null, line, null, null);
				break;
			case 2:
				System.out.println("Nueva contraseña: ");
				line = sc.nextLine();
				AsGCuentas.personalizarPerfil(cuentaActiva.getCuentaActiva(), line, null, null, null);
				break;
			case 3:
				System.out.println("Nueva descripción: ");
				line = sc.nextLine();
				AsGCuentas.personalizarPerfil(cuentaActiva.getCuentaActiva(), null, null, line, null);
				break;
			case 4:
				System.out.println("Nueva foto: ");
				line = sc.nextLine();
				AsGCuentas.personalizarPerfil(cuentaActiva.getCuentaActiva(), null, null, null, line);
				break;
			case 0:
				//AsGCuentas.cerrarSesion(cuentaActiva);
				//System.out.println("Se ha cerrado la sesión correctamente");
				//sc.close();
				//System.exit(0);
				menuDeUsuario(AsGCuentas, cuentaActiva, AsGConocer, AsGChats);
				break;
			default:
				System.out.println("Opción no válida");
				break;
			
			}
		}
		
	}
	
	private static void menuDeUsuario(ASGestionCuentasImp AsGCuentas, TOSesion cuentaActiva,
			ASGestionConocerImp AsGConocer, ASGestionChatsImp AsGChats) {
		
		int opcion = -1;
		
		while(opcion != 0) {
			
			System.out.println("Elija una opción: ");
			System.out.println("0 - Cerrar sesión");
			System.out.println("1 - Conocer gente");
			System.out.println("2 - Chats");
			System.out.println("3 - Personalizar perfil");
			
			opcion = Integer.parseInt(sc.nextLine());
			
			switch (opcion) {
			case 1:
				AsGConocer.conocerGente(cuentaActiva.getCuentaActiva());
				break;
			case 2:
				AsGChats.chatear(cuentaActiva.getCuentaActiva());
				break;
			case 3:
				menuPersonalizarPerfil(AsGCuentas, cuentaActiva, AsGConocer, AsGChats);
				break;
			case 0:
				AsGCuentas.cerrarSesion(cuentaActiva);
				System.out.println("Se ha cerrado la sesión correctamente");
				sc.close();
				break;
			default:
				System.out.println("Opción no válida");
				break;
			}
		
		}
		
	}

	private static boolean menuCrearCuenta(ASGestionCuentasImp AsGCuentas){		

		String line = null;
		
		System.out.print("> ");
		System.out.println("Introduzca nombre, correo y contraseña separados por un espacio");
		
		line = sc.nextLine();
		
		String[] cuenta = line.trim().split(" ");
		
		String nombre = cuenta[0];
		String correo = cuenta[1];
		String contrasena = cuenta[2];
		
		if(nombre != null || correo != null || contrasena != null) {
			return AsGCuentas.crearCuenta(nombre, correo, contrasena);
		}
		else
			return false;
	}
	
	private static TOSesion menuIniciarSesion(ASGestionCuentasImp AsGCuentas){	
		
		String line = null;
		
		System.out.print("> ");
		System.out.println("Introduzca correo y contraseña separados por un espacio");
		
		line = sc.nextLine();
		
		String[] cuenta = line.trim().split(" ");
		
		String correo = cuenta[0];
		String contrasena = cuenta[1];
	
		if(correo != null || contrasena != null) {
			TOSesion sesion = AsGCuentas.iniciarSesion(correo, contrasena);
			return sesion;
		}
		else
			return null;
	}
	
	

}
 
