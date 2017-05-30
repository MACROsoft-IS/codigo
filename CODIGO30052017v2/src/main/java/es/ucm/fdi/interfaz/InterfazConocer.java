package es.ucm.fdi.interfaz;

import java.util.ArrayList;
import java.util.Scanner;

import es.ucm.fdi.integracion.TOUsuario;
import es.ucm.fdi.negocio.ASGestionConocerImp;

public class InterfazConocer {

	static Scanner sc = new Scanner(System.in);
	private ASGestionConocerImp AsGConocer;

	public InterfazConocer(ASGestionConocerImp AsGConocer) {
		this.AsGConocer = AsGConocer;
	}

	//// MIRAR LA EXCEPCION DE OPCION !!!
	private int opcion() {
		try {
			int opcion;

			do {
				System.out.println("\n 1.- Si\n 2.- No\n 3.- Bloquear Usuario\n 4.- Salir\n");
				opcion = Integer.parseInt(sc.nextLine());
			} while (opcion < 1 || opcion > 4);

			return opcion;
		} catch (NumberFormatException e) {
			System.out.println("Opcion no valida");
			System.out.println("LETRAS NO!!!");
			return 4;
		}

	}

	public void pantallaConocerGente(TOUsuario usuario) {

		ArrayList<TOUsuario> pendientesAMostrar = this.AsGConocer.dameUsuariosPendientes(usuario);
		int opcion = -1;
		int i = 0;

		if (!pendientesAMostrar.isEmpty()) {
			do {
				System.out.println(pendientesAMostrar.get(i).toString());
				opcion = opcion();
				this.AsGConocer.AplicarOpcion(pendientesAMostrar.get(i), usuario, opcion, true);
				i++;
			} while (opcion != 4 && i < pendientesAMostrar.size());

		}

		TOUsuario usuarioAConocer = this.AsGConocer.damePersonaValidaAleatoria(usuario);
		while (opcion != 4 && usuarioAConocer != null) {
			System.out.println(usuarioAConocer.toString());
			opcion = opcion();
			this.AsGConocer.AplicarOpcion(usuarioAConocer, usuario, opcion, false);
			usuarioAConocer = this.AsGConocer.damePersonaValidaAleatoria(usuario);
		}

		if (usuarioAConocer == null) {
			System.out.println("No tienes mas gente");
		}
	}

}
