package es.ucm.fdi.integracion;

public class DAOFactoryImp extends DAOFactory{
	private static DAOFactory instancia;

	public static DAOFactory obtenerInstancia() {
		if (instancia == null)
			instancia = new DAOFactoryImp();
		return instancia;
	}

	@Override
	public DAOUsuarioImp generaDAOUsuario() {
		return new DAOUsuarioImp();
	}

	@Override
	public DAOSesionImp generaDAOSesion() {
		return new DAOSesionImp();
	}

	@Override
	public DAOChatImp generaDAOChat() {
		return new DAOChatImp();
	}

	@Override
	public DAOMensajeImp generaDAOMensaje() {
		return new DAOMensajeImp();
	}
	
	

}
