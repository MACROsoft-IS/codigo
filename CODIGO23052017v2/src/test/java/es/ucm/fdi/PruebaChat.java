package es.ucm.fdi;

import es.ucm.fdi.integracion.TOUsuarioImp;
import es.ucm.fdi.negocio.ASGestionCuentaImp;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PruebaChat extends TestCase {

	public  static Test suite() {
		return new TestSuite(PruebaChat.class);
	}
	
	public void testChat(){
		
		ASGestionCuentaImp as = new ASGestionCuentaImp();
		as.crearCuenta("nombre1", "correo1@ucm.es", "contrasena1");
		as.crearCuenta("nombre2", "correo2@ucm.es", "contrasena2");
		as.crearCuenta("nombre3", "correo3@ucm.es", "contrasena3");
		as.crearCuenta("nombre4", "correo4@ucm.es", "contrasena4");
		as.iniciarSesion("correo1@ucm.es","contraseña1");
		
		assertTrue("La lista de chats debe estar vacía \n", as.getUsuarioDAO().read("correo1@ucm.es").getListaChats().isEmpty());

		TOUsuarioImp uno = as.getUsuarioDAO().read("correo1@ucm.es");
		TOUsuarioImp dos = as.getUsuarioDAO().read("correo2@ucm.es");
		TOUsuarioImp tres = as.getUsuarioDAO().read("correo3@ucm.es");
		TOUsuarioImp cuatro = as.getUsuarioDAO().read("correo4@ucm.es");
		
		uno.añadirConocido("correo2@ucm.es");
		uno.añadirChat("correo2@ucm.es");
		dos.añadirConocido("correo1@ucm.es");
		dos.añadirChat("correo1@ucm.es");
		uno.añadirConocido("correo3@ucm.es");
		uno.añadirChat("correo3@ucm.es");
		tres.añadirConocido("correo1@ucm.es");
		tres.añadirChat("correo1@ucm.es");
		uno.añadirConocido("correo4@ucm.es");
		uno.añadirChat("correo4@ucm.es");
		cuatro.añadirConocido("correo1@ucm.es");
		cuatro.añadirChat("correo1@ucm.es");
		
		dos.añadirConocido("correo3@ucm.es");
		dos.añadirChat("correo3@ucm.es");
		tres.añadirConocido("correo2@ucm.es");
		tres.añadirChat("correo2@ucm.es");
		dos.añadirConocido("correo4@ucm.es");
		dos.añadirChat("correo4@ucm.es");
		cuatro.añadirConocido("correo2@ucm.es");
		cuatro.añadirChat("correo2@ucm.es");

		tres.añadirConocido("correo4@ucm.es");
		tres.añadirChat("correo4@ucm.es");
		cuatro.añadirConocido("correo3@ucm.es");
		cuatro.añadirChat("correo3@ucm.es");
		
		
		
		int ok=0;
		for(int i=0; i<uno.getListaChats().size(); i++){
			if(uno.getListaChats().get(i).getCorreo()=="correo2@ucm.es"){
				ok++;
			}
			if(uno.getListaChats().get(i).getCorreo()=="correo3@ucm.es"){
				ok++;
			}
		}
		
		assertTrue("correo2@ucm.es debe estar entre los chats \n", ok==2);
		
		int ok1=0;
		for(int i=0; i<dos.getListaChats().size(); i++){
			if(dos.getListaChats().get(i).getCorreo()=="correo1@ucm.es"){
				ok1++;
			}
			if(dos.getListaChats().get(i).getCorreo()=="correo3@ucm.es"){
				ok1++;
			}
		}
		
		assertTrue("correo2@ucm.es debe estar entre los chats \n", ok1==2);
		
		int ok2=0;
		for(int i=0; i<tres.getListaChats().size(); i++){
			if(tres.getListaChats().get(i).getCorreo()=="correo2@ucm.es"){
				ok2++;
			}
			if(tres.getListaChats().get(i).getCorreo()=="correo4@ucm.es"){
				ok2++;
			}
		}
		
		assertTrue("correo2@ucm.es debe estar entre los chats \n", ok2==2);
		
		int ok3=0;
		for(int i=0; i<cuatro.getListaChats().size(); i++){
			if(cuatro.getListaChats().get(i).getCorreo()=="correo1@ucm.es"){
				ok3++;
			}
			if(cuatro.getListaChats().get(i).getCorreo()=="correo3@ucm.es"){
				ok3++;
			}
		}
		
		assertTrue("correo2@ucm.es debe estar entre los chats \n", ok3==2);
		
		String aux1=null;
		String aux2=null;
		for(int i=0; i<cuatro.getListaChats().size(); i++){
			if(cuatro.getListaChats().get(i).getCorreo()=="correo1@ucm.es"){
				cuatro.getListaChats().get(i).addMensajes("Hola holita vecinito");	
				aux1=cuatro.getListaChats().get(i).getMensajes();
			}
		}
		for(int i=0; i<uno.getListaChats().size(); i++){
			if(uno.getListaChats().get(i).getCorreo()=="correo4@ucm.es"){
				uno.getListaChats().get(i).addMensajes("Hola holita vecinito");
				aux2=uno.getListaChats().get(i).getMensajes();
			}
		}
		
		assertTrue("El mensaje debe estar en los chats de cada usuario \n" + aux1 + aux2, aux1.equalsIgnoreCase(aux2));
		
		uno.eliminarChat("correo2@ucm.es");
		dos.eliminarChat("correo1@ucm.es");
		uno.eliminarChat("correo3@ucm.es");
		tres.eliminarChat("correo1@ucm.es");
		uno.eliminarChat("correo4@ucm.es");
		cuatro.eliminarChat("correo1@ucm.es");
		
		dos.eliminarChat("correo3@ucm.es");
		tres.eliminarChat("correo2@ucm.es");
		dos.eliminarChat("correo4@ucm.es");
		cuatro.eliminarChat("correo2@ucm.es");
;
		
		
		assertTrue("No debe de haber ningún chat abierto \n", uno.getListaChats().isEmpty()&&dos.getListaChats().isEmpty());
		
		boolean encontrado=false;
		for(int i=0; i<tres.getListaChats().size(); i++){
			if(tres.getListaChats().get(i).getCorreo()=="correo4@ucm.es"){
				encontrado=true;
			}
		}
		assertTrue("Debe estar el chat del 4 en el 3 y el 3 en el 4 \n", encontrado==true);
		/*if(as.opcion()){
			as.muestraPendiente(as.getUsuarioDAO().read("nombre1"), "correo2@ucm.es");
			assertTrue("La lista de chats debe tener un elemento \n" + ASGestionCuentaImp.bdCadenas, as.getUsuarioDAO().read("nombre").getListaChats().size()==1);
			if(as.opcion()){
				as.muestraPendiente(as.getUsuarioDAO().read("nombre1"), "correo3@ucm.es");
				assertTrue("La lista de chats debe tener dos elemento \n" + ASGestionCuentaImp.bdCadenas, as.getUsuarioDAO().read("nombre").getListaChats().size()==2);
				if(!as.opcion()){
					as.muestraPendiente(as.getUsuarioDAO().read("nombre1"), "correo4@ucm.es");
					assertTrue("La lista de chats debe tener dos elemento \n" + ASGestionCuentaImp.bdCadenas, as.getUsuarioDAO().read("nombre").getListaChats().size()==2);
				}
			}
		}*/
		
	}
	
}
