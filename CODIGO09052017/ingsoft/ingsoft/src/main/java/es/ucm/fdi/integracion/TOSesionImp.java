package es.ucm.fdi.integracion;

public class TOSesionImp{

	private String sesionID;
	private TOUsuarioImp cuentaActiva;
	
	public TOSesionImp(TOUsuarioImp usuario) {
		this.cuentaActiva = usuario;
		this.sesionID = this.cuentaActiva.getCorreo() + System.currentTimeMillis();
	}

	public String getSesionID() {
		return sesionID;
	}

	public void setSesionID(String sesionID) {
		this.sesionID = sesionID;
	}

	public TOUsuarioImp getCuentaActiva() {
		return cuentaActiva;
	}

	public void setCuentaActiva(TOUsuarioImp cuentaActiva) {
		this.cuentaActiva = cuentaActiva;
	}
	
	
}
