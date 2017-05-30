package es.ucm.fdi.interfaz;

import java.util.Scanner;

import es.ucm.fdi.integracion.TOSesion;
import es.ucm.fdi.negocio.ASGestionCuentasImp;

public class InterfazCuentas {
	
	static Scanner sc =  new Scanner (System.in);
	
	public TOSesion menuIniciarSesion(ASGestionCuentasImp AsGCuentas) {

		String auxiliar, correo, contrasena;

		System.out.println("> Introduzca su correo.\n");
		auxiliar = sc.nextLine();
		correo = auxiliar.trim();
		System.out.println("> Introduzca su contraseña.\n");
		auxiliar = sc.nextLine();
		contrasena = auxiliar.trim();

		TOSesion sesion = AsGCuentas.iniciarSesion(correo, contrasena);

		if (sesion != null)
			System.out.println("Sesión iniciada con éxito.\n");
		else
			System.out.println("Fallo al iniciar sesión, compruebe que el correo y/o contraseña son correctas.\n");

		return sesion;
	}

	public TOSesion menuCrearCuenta(ASGestionCuentasImp AsGCuentas) {

		String nombre, correo, contrasena, foto, descripcion, auxiliar;
		TOSesion sesion = null;

		System.out.println("> Introduzca su nombre.\n");
		nombre = sc.nextLine();
		System.out.println(
				"> Introduzca su correo ucm (solo lo anterios a la arroba, nosotros añadimos el resto :) ).\n");
		auxiliar = sc.nextLine();
		correo = auxiliar.trim() + "@ucm.es";
		System.out.println("> Introduzca su contraseña.\n");
		auxiliar = sc.nextLine();
		contrasena = auxiliar.trim();
		System.out.println(
				"> Seleccione su foto de perfil pública (Se añadirá en una futura versión. Para pasar introduzca cualquier caracter).\n");
		auxiliar = sc.nextLine();
		foto = auxiliar.trim();
		System.out.println(
				"> Describa sus gusto y aficiones con pocas palabras (Esta información aparecerá visible al resto de usuarios).\n");
		descripcion = sc.nextLine();

		if (!AsGCuentas.crearCuenta(nombre, correo, contrasena, foto, descripcion))
			System.out.println("Fallo al crear la cuenta: el correo ya está en uso.");
		else
			sesion = AsGCuentas.iniciarSesion(correo, contrasena);

		return sesion;
	}
}
