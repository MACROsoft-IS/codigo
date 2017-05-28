package es.ucm.fdi.integracion;

import java.io.Serializable;

public class TOPendiente implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6894489392223262417L;

	public enum tPendiente{
		MANDO,
		RECIBO
	}
	
	private String correo;
	private tPendiente tipo;
	
	public TOPendiente(String correo, tPendiente tipo){
		this.correo = correo;
		this.tipo = tipo;
	}
	
	public String getCorreo(){
		return correo;
	}
	
	public tPendiente getTipo(){
		return tipo;
	}
	 
}
