package es.ucm.fdi.negocio;

import es.ucm.fdi.integracion.TOSesion;
import es.ucm.fdi.integracion.TOUsuario;

public interface ASGestionCuentas {

	public TOSesion iniciarSesion(String correo, String contrasena);
	public boolean crearCuenta(String nombre, String correo, String contrasena);
	public void cerrarSesion(TOSesion sesion);
	public void eliminarCuenta(TOUsuario usuario, TOSesion sesion);
	public void personalizarPerfil(TOUsuario usuario, String contrasena, String nombre, String descripcion,
			String foto);
	public void bloquearUsuario (TOUsuario usuario, TOUsuario usuarioABloquear);
	
}

