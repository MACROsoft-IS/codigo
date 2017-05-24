package es.ucm.fdi.negocio;

import es.ucm.fdi.integracion.TOUsuario;

public interface ASGestionConocer {

	public void conocerGente (TOUsuario usuario);
	
	public int opcion();
	
	public int muestraPendiente (TOUsuario usuario, String correoAlt);
	
	public int muestra (TOUsuario usuario, String correoAlt);

}
