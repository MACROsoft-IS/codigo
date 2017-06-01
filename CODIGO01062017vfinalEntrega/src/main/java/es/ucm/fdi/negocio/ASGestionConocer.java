package es.ucm.fdi.negocio;

import java.util.ArrayList;

import es.ucm.fdi.integracion.TOUsuario;

public interface ASGestionConocer {
	
	public TOUsuario damePersonaValidaAleatoria(TOUsuario usuario);
	public void AplicarOpcion(TOUsuario usuarioConocido, TOUsuario usuario, int opcion, boolean pendiente);
	public ArrayList<TOUsuario> dameUsuariosPendientes(TOUsuario usuario);
	

}
