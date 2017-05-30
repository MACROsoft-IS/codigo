package es.ucm.fdi.integracion;

public abstract class DAOFactory {

	private static DAOFactoryImp instancia;

	public  static DAOFactory obtenerInstancia(){return null;};

	public abstract DAOUsuarioImp generaDAOUsuario();
	public abstract DAOSesionImp generaDAOSesion();
	public abstract DAOChatImp generaDAOChat();
	public abstract DAOMensajeImp generaDAOMensaje();
	

}
