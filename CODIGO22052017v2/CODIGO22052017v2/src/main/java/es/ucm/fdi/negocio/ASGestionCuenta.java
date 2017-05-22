package es.ucm.fdi.negocio;

import es.ucm.fdi.integracion.TOSesionImp;

public interface ASGestionCuenta{
	
	public TOSesionImp iniciarSesion(String correo, String contrasena);

	public boolean crearCuenta(String nombre, String correo, String contrasena);
	
	//public void cerrarSesion()
	
}
