package es.ucm.fdi.interfaz;

import java.util.Scanner;

import es.ucm.fdi.integracion.TOSesion;
import es.ucm.fdi.negocio.ASGestionChatsImp;
import es.ucm.fdi.negocio.ASGestionConocerImp;
import es.ucm.fdi.negocio.ASGestionCuentasImp;

public class InterfazPersonalizar {
	
	static Scanner sc =  new Scanner (System.in);
	
	public void menuPersonalizarPerfil(ASGestionCuentasImp AsGCuentas, TOSesion cuentaActiva,
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
}
