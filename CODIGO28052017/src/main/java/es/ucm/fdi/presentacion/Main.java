package es.ucm.fdi.presentacion;

import java.util.Scanner;

import es.ucm.fdi.negocio.*;
import es.ucm.fdi.integracion.*;

public class Main {
	
	static Scanner sc =  new Scanner (System.in);
	
	public static void main(String[] args) {
		
		ASGestionCuentasImp AsGCuentas = new ASGestionCuentasImp();
		ASGestionConocerImp AsGConocer = new ASGestionConocerImp(AsGCuentas.getUsuarioDAO(),AsGCuentas.getSesionDAO());
		ASGestionChatsImp AsGChats = new ASGestionChatsImp(AsGCuentas.getUsuarioDAO(),AsGCuentas.getSesionDAO());
		
		menuPrincipalInicio(AsGCuentas,AsGConocer,AsGChats);
		
		sc.close();
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
					sesion = menuCrearCuenta(AsGCuentas);
					if (sesion != null) menuDeUsuario(AsGCuentas, sesion, AsGConocer, AsGChats);
					break;
				case 2:
					sesion = menuIniciarSesion(AsGCuentas); //donde redigir una vez la sesion iniciada?
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
	
	private static void menuPersonalizarPerfil(ASGestionCuentasImp AsGCuentas, TOSesion cuentaActiva,
			ASGestionConocerImp AsGConocer, ASGestionChatsImp AsGChats) {

		try{
			int opcion = -1;
			String line, auxiliar;
			String pass1, pass2;
			boolean eliminado = false;
			
			System.out.println(AsGCuentas.mostrarPerfil(cuentaActiva.getCuentaActiva()));

			
			while(opcion != 0 && !eliminado) {
				
				System.out.println("Elija una opción: ");
				System.out.println("0 - Atrás");
				System.out.println("1 - Cambiar nombre");
				System.out.println("2 - Cambiar contraseña");
				System.out.println("3 - Cambiar descripción");
				System.out.println("4 - Cambiar foto");
				System.out.println("5 - Eliminar cuenta.");
				
				opcion = Integer.parseInt(sc.nextLine());

				
				switch (opcion) {
				case 1:
					System.out.println("Nuevo nombre: ");
					line = sc.nextLine();
					AsGCuentas.personalizarPerfil(cuentaActiva.getCuentaActiva(), null, line, null, null);
					System.out.println("Se ha cambiado el nombre correctamente.\n");
					break;
				case 2:
					System.out.println("Nueva contraseña: ");
					auxiliar = sc.nextLine();
					pass1 = auxiliar.trim();
					System.out.println("Introduzca de nuevo la nueva contraseña: ");
					auxiliar = sc.nextLine();
					pass2 = auxiliar.trim();
					if (pass1.equalsIgnoreCase(pass2)){
						line = pass1;
						AsGCuentas.personalizarPerfil(cuentaActiva.getCuentaActiva(), line, null, null, null);
						System.out.println("Se ha cambiado la contraseña correctamente.\n");
					}else System.out.println("Se ha producido un error: las nuevas contraseñas no coinciden.\n");
					break;
				case 3:
					System.out.println("Nueva descripción: ");
					line = sc.nextLine();
					AsGCuentas.personalizarPerfil(cuentaActiva.getCuentaActiva(), null, null, line, null);
					System.out.println("Se ha cambiado la descripción correctamente.\n");

					break;
				case 4:
					System.out.println("Nueva foto: ");
					auxiliar = sc.nextLine();
					line = auxiliar.trim();
					AsGCuentas.personalizarPerfil(cuentaActiva.getCuentaActiva(), null, null, null, line);
					System.out.println("Se ha cambiado la contraseña correctamente.\n");
					break;
				case 5:
					System.out.println("¿Está seguro que quiere eliminar la cuenta? Perderá todos los datos y este proceso no se puede revertir. Introduzca 'Si' para confirmar o 'No' para rechazar.\n");
					auxiliar = sc.nextLine();
					line = auxiliar.trim();
					if (line.equalsIgnoreCase("SI")) {
						AsGCuentas.eliminarCuenta(cuentaActiva.getCuentaActiva(), cuentaActiva);
						eliminado = true;
						System.out.println("Su cuenta se ha eliminado correctamente.\n");
					}
					break;
				case 0:
					//menuDeUsuario(AsGCuentas, cuentaActiva, AsGConocer, AsGChats);
					break;
				default:
					System.out.println("Opción no válida");
					break;
				
				}
				
				if ((opcion > 0 && opcion < 6) && !eliminado)
					System.out.println(AsGCuentas.mostrarPerfil(cuentaActiva.getCuentaActiva()));

			}
		}
		catch(NumberFormatException e){
			System.out.println("Error: opción no válida.\n");
			menuPersonalizarPerfil(AsGCuentas, cuentaActiva, AsGConocer, AsGChats);
		}
		
		
	}
	
	private static void menuDeUsuario(ASGestionCuentasImp AsGCuentas, TOSesion cuentaActiva,
			ASGestionConocerImp AsGConocer, ASGestionChatsImp AsGChats) {
		
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
					AsGConocer.conocerGente(cuentaActiva.getCuentaActiva());
					//System.out.println(cuentaActiva.getCuentaActiva().getAvisos());
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

	private static TOSesion menuCrearCuenta(ASGestionCuentasImp AsGCuentas){		

		String nombre, correo, contrasena, foto, descripcion, auxiliar;
		TOSesion sesion = null;
		
		System.out.println("> Introduzca su nombre.\n");
		nombre = sc.nextLine();
		System.out.println("> Introduzca su correo ucm (solo lo anterios a la arroba, nosotros añadimos el resto :) ).\n");
		auxiliar = sc.nextLine();
		correo = auxiliar.trim() + "@ucm.es";
		System.out.println("> Introduzca su contraseña.\n");
		auxiliar = sc.nextLine();
		contrasena = auxiliar.trim();
		System.out.println("> Seleccione su foto de perfil pública (Se añadirá en una futura versión. Para pasar introduzca cualquier caracter).\n");
		auxiliar = sc.nextLine();
		foto = auxiliar.trim();
		System.out.println("> Describa sus gusto y aficiones con pocas palabras (Esta información aparecerá visible al resto de usuarios).\n");
		descripcion = sc.nextLine();
		
		if (!AsGCuentas.crearCuenta(nombre, correo, contrasena, foto, descripcion)) System.out.println("Fallo al crear la cuenta: el correo ya está en uso.");
		else sesion = AsGCuentas.iniciarSesion(correo, contrasena);
		
		return sesion;
	}
	
	private static TOSesion menuIniciarSesion(ASGestionCuentasImp AsGCuentas){	
		
		String auxiliar, correo, contrasena;
		
		System.out.println("> Introduzca su correo.\n");
		auxiliar = sc.nextLine();
		correo = auxiliar.trim();
		System.out.println("> Introduzca su contraseña.\n");
		auxiliar = sc.nextLine();
		contrasena = auxiliar.trim();
		
		TOSesion sesion = AsGCuentas.iniciarSesion(correo, contrasena);
		
		if (sesion != null) System.out.println("Sesión iniciada con éxito.\n");
		else System.out.println("Fallo al iniciar sesión, compruebe que el correo y/o contraseña son correctas.\n");
	
		return sesion;
	}
	
	

}
 
