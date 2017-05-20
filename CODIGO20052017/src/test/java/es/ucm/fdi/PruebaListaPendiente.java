package es.ucm.fdi;

import java.util.ArrayList;

import es.ucm.fdi.integracion.TOUsuarioImp;
import es.ucm.fdi.integracion.TOUsuarioImp.Pendiente;
import es.ucm.fdi.integracion.TOUsuarioImp.Pendiente.tPendiente;
import es.ucm.fdi.negocio.ASGestionCuentaImp;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PruebaListaPendiente extends TestCase {
	
	public  static Test suite() {
		return new TestSuite(PruebaListaPendiente.class);
	}
	
	public void prueba(){
		TOUsuarioImp usuario = new TOUsuarioImp("correo", "Nombre", "Contraeña");
		
		usuario.añadirPendiente("paquito", tPendiente.MANDO);
		usuario.añadirPendiente("juani", tPendiente.RECIBO);
		
		usuario.eliminarPendiente("juani");
		
		ArrayList<Pendiente> lista = usuario.getListaPendientes();
		
		if (lista.contains("juani")) System.out.println("La has cagao");
		else System.out.println("eres un crack");
		
		assertTrue("Solo deberia haber un pendiente", lista.size() == 1);
	}
}
