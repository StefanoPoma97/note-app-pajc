package it.unibs.pajc.note.client_server;

import java.io.IOException;
import java.net.ServerSocket;

import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.data.UserArchive;
import it.unibs.pajc.note.model.User;



public class MultiServerMain {

	public static void main(String[] args) {
		
		//TODO prendere archivi tramite file
		
		NoteArchive noteArchive= NoteArchive.getIstance();
		UserArchive userArchive= UserArchive.getIstance();
		User utente= new User("paolo", "merazza");
		utente.addLabel("Labels");
		utente.addLabel("Riunione");
		utente.addLabel("Memo");
		utente.addLabel("Memo2");
		utente.addLabel("Memo3");
		userArchive.add(utente);
		userArchive.add(new User("utente1", "pass1"));
		userArchive.add(new User("utente2", "pass2"));
		userArchive.add(new User("utente3", "pass3"));
		
		int port=2270;
		System.out.println("MULTISERVER START!");
		try (
				ServerSocket server = new ServerSocket(port);
			)
		{
			while (true){
				new MultiServer(server.accept()).start();
			}
	
		}
		catch(IOException e)
		{
			System.err.println("Errore di comunicazione: " +e);
		}

		
		System.out.println("Server Stop!");
	}

	}
