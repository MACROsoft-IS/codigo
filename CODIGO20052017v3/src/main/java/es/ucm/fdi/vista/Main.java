package es.ucm.fdi.vista;

import java.util.Scanner;

import es.ucm.fdi.negocio.*;
import es.ucm.fdi.integracion.*;

public class Main {
	

	
	public static void main(String[] args) {
		
		System.out.println("Bienvenido a MyUni");
		
		ASGestionCuentaImp AsGC = new ASGestionCuentaImp();
		
		menuPrincipalInicio(AsGC);
	
			

	}
	
	public static void menuPrincipalInicio(ASGestionCuentaImp AsGC){
		
		Scanner sc =  new Scanner (System.in);
		
		
		System.out.println("Elija una opción: ");
		System.out.println("0 - Salir");
		System.out.println("1 - Crear cuenta");
		System.out.println("2 - Iniciar sesión");
		
		int opcion =  sc.nextInt();
		
		
		//controlar lo que devuelven los menus y como utilizarlo
		
		switch (opcion) {
		case 1:
			menuCrearCuenta(AsGC);
			break;
		case 2:
			TOSesionImp sesion;
			sesion = menuIniciarSesion(AsGC); //donde redigir una vez la sesion iniciada?
			AsGC.conocerGente(sesion.getCuentaActiva());
			break;
		case 0:
			System.out.println("Gracias por usar la aplicacion");
			System.exit(0);
		default:
			System.out.println("Opcion no valida");
			break;
		}
		
		sc.close();
		
	}
	
	public static boolean menuCrearCuenta(ASGestionCuentaImp AsGC){		
		
		Scanner sc = new Scanner(System.in);

		String line = null;
		
		System.out.print("> ");
		System.out.println("Introduzca nombre, correo y contraseña separados por un espacio");
		
		line = sc.nextLine();
		
		String[] cuenta = line.trim().split(" ");
		
		String nombre = cuenta[0];
		String correo = cuenta[1];
		String contrasena = cuenta[2];
		
		sc.close();
		
		return AsGC.crearCuenta(nombre, correo, contrasena);
		
	}
	
	public static TOSesionImp menuIniciarSesion(ASGestionCuentaImp AsGC){	
		
		
		Scanner sc = new Scanner(System.in);

		String line = null;
		
		System.out.print("> ");
		System.out.println("Introduzca correo y contraseña separados por un espacio");
		
		line = sc.nextLine();
		
		String[] cuenta = line.trim().split(" ");
		
		String correo = cuenta[0];
		String contrasena = cuenta[1];
	
		sc.close();
		
		if(correo != null || contrasena != null) {
			TOSesionImp sesion = AsGC.iniciarSesion(correo, contrasena);
			return sesion;
		}
		else
			return null;
	}

}
 
