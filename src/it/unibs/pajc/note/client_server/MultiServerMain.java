package it.unibs.pajc.note.client_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.data.UserArchive;
import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.Tag;
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
		System.out.println("AGGIUNTI GLI UTENTI");
		
		
		User u= new User("paolo", "merazza");
		User u1= new User("utente1", "pass1");
		User u2= new User("autente2","pass2");
		u1.addTag(new Tag("Riunione"));
		u1.addTag(new Tag("Memo"));
		u1.addTag(new Tag("Memo2"));
		u1.addTag(new Tag("Memo3"));
		u.addTag(new Tag("Riunione"));
		u.addTag(new Tag("Memo"));
		//costruttore solo per test, in realt� si appoggia alla classe client che poi gli da informazioni su archivio note
		for (int i=0; i<20; i++){
			Note nota = new Note("titolo"+i);
			nota.setBody("corpo della nota numero: "+i);
			nota.setAutor(u);
			if(i==0){
				nota.addLabel("Riunione");
				nota.setPin(true);
				Set<User> set= new HashSet<>();
				set.add(u1);
				nota.addSharedUsers(set);
			}
			if (i==1){
				nota.addLabel("Memo");
			}
			if (i==2){
				nota.addLabel("Memo2");
			}
			
			if (i==3){
				nota.addLabel("Memo3");
			}
			
			
				
			noteArchive.add(nota);
		}
		
		for (int i=0; i<20; i++){
			Note nota = new Note("titolo copia"+i);
			nota.setBody("corpo della nota copia numero: "+i);
			nota.setAutor(u1);
			if(i==0){
				nota.addLabel("Riunione");
				nota.addLike();
				nota.addLike();
				Set<User> set= new HashSet<>();
				set.add(u);
				nota.addSharedUsers(set);
				System.out.println("NOTA CONDIVISA CON "+ nota.getSharedWith().toString());
			}
			if (i==1){
				nota.addLike();
				nota.addLabel("Memo");
			}
				
			noteArchive.add(nota);
		}
		
		for (int i=0; i<5; i++){
			Note nota = new Note("titolo cp"+i);
			nota.setBody("corpo della nota copia numero: "+i);
			nota.setAutor(u2);
			if(i==0){
				nota.setTitle("AAAAAAAAAAAAAA");
				nota.addLike();
				nota.addLike();
				nota.addLike();
				nota.addLabel("Riunione");
				Set<User> set= new HashSet<>();
				set.add(u);
				nota.addSharedUsers(set);
				System.out.println("NOTA CONDIVISA CON "+ nota.getSharedWith().toString());
			}
			if (i==1){
				nota.addLabel("Memo");
			}
				
			noteArchive.add(nota);
		}
		
		
		System.out.println("AGGIUNTE LE NOTE");
		
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
