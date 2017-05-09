package es.ucm.fdi.integracion;

public class TOUsuarioImp{ 

	private String nombre;
	private String correo;
	private String contrasena;
	private String foto;
	private String descripcion;
	private boolean activo;

	public TOUsuarioImp(String nombre, String correo, String contrasena) {
		this.nombre = nombre;
		this.correo = correo;
		this.contrasena = contrasena;
		this.activo = true; // false en caso de espera de confirmacion
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
