package es.ucm.fdi.integracion;

import java.io.Serializable;

public class TOSesion {

	private String sesionID;
	private TOUsuario cuentaActiva;
	private boolean sesionON;
	
	public TOSesion(TOUsuario usuario) {
		this.cuentaActiva = usuario;
		this.sesionID = this.cuentaActiva.getCorreo() + System.currentTimeMillis();
		this.sesionON = true;
	}

	public String getSesionID() {
		return sesionID;
	}

	public void setSesionID(String sesionID) {
		this.sesionID = sesionID;
	}

	public TOUsuario getCuentaActiva() {
		return cuentaActiva;
	}

	public void setCuentaActiva(TOUsuario cuentaActiva) {
		this.cuentaActiva = cuentaActiva;
	}
	
	public boolean getSesionEstado(){ return this.sesionON; }
	
	public void setSesionEstado(boolean sesion) { this.sesionON = sesion; }
		
}
