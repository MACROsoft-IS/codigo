package es.ucm.fdi.interfaz;

import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import es.ucm.fdi.integracion.TOChat;
import es.ucm.fdi.integracion.TOMensaje;
import es.ucm.fdi.negocio.ASGestionChats;

public class InterfazChats {
	static Scanner sc =  new Scanner (System.in);
	
	public InterfazChats(){
		
	}
	
	// ESTA MIERDA FUNCIONA COMO EL OJETE, MENSAJES A DESTIEMPO Y SE DESORDENAN
	public static void chatear(){
		
		ASGestionChats agc;
		
		ASGestionCuentas asc;
		// usuario1 
		UsuarioTO usuario1=asc.dameUSuario();
		
		ChatTo chatusuario1=agc.chatear(usuario1.dameUsuarioConocido());
		
		// usuario2
		UsuarioTO usuario2=asc.dameUSuario();
		Vector<ChatTO> chatspendientes=agc.chatsPendientes(usuario2,0);
		// tamaño 1 por haber iniciado un chat
		ChatTO actualUsuario2=chatspendientes.elementAt(0);
		
		//usuario1
		agc.mandarMensaje(chatusuario1.getId(),"hola");
		
		//usuario2
		Vector<MensajeTO> mensajes=agc.dameMensajesPendientes(usuario2,0);
		mensajedesdechat1=mensajes.elementAt(0);
		agc.mandarMensaje(chatUsuario2.getId,"hola amigo");
		
		// usuario1
		Vector<MensajeTO> mensajesusuario1=agc.dameMensajesPendientes(usuario2,0);
		mensajedesdechat1=mensajes.elementAt(0);
		agc.mandarMensaje(chatUsuario2.getId,"hola amigo");
		
		// usuario1 
		Vector<MensajeTO> mensajesusuario1=agc.dameMensajesPendientes(
				usuario2,mensajedesdechat1.elementAt(0).dameTiempo());
		
		final Date a = new Date();
		Timer b = new Timer();
		
		b.schedule(new TimerTask(){

			@Override
			public void run() {
				TOMensaje mensaje;
				Vector<String> listaMensajes = chat.getMensajes().getIds();
				for (String s : listaMensajes){
					mensaje = chat.getMensajes().find(s);
					if (mensaje.getMomento() < a.getTime()){
						System.out.println(mensaje.getCorreoUsuario() + ":\t" + mensaje + "\n");
					}
				}
			}
			
		}, 0);
	}
}
