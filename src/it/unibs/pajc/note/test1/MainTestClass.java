package it.unibs.pajc.note.test1;

import java.io.File;

import it.unibs.pajc.note.data.*;
import it.unibs.pajc.note.model.*;

public class MainTestClass {
	
	//Creare tests
	public static void main(String[] args) {
		
		

		
		//Test sugli utenti
		UserArchive users = new UserArchive();
		
		Tag tag = new Tag("polli");
		Tag tag2 = new Tag("polli2");
		User user1= new User("utente1", "pass1");
		user1.addTag(tag);
		user1.addTag(tag);
		user1.addTag(tag2);
		User user2= new User("utente2", "pass2");
		User user3= new User("utente3", "pass3");
		
		users.add(user1);
		users.add(user2);
		users.add(user3);
		
		User user4= new User("", "pass4");
		User user5= new User("utente5", "");
		User user6= new User("utente1", "pass6");
		
		System.out.println(users.add(user4));
		System.out.println(users.add(user5));
		System.out.println(users.add(user6));
		
		System.out.println(users.toString());
		
		System.out.println(user1.getPersonalTag().toString());
		
		
		
		
		NoteArchive notes = new NoteArchive();
		Note nota1 = new Note("titolo1");
		nota1.setBody("sono la nota1");
		Note nota2 = new Note("titolo2");
		nota2.setBody("sono la nota2");
		Note nota3 = new Note("titolo3");
		nota3.setBody("sono la nota3");
		Note nota4 = new Note("");
		nota4.setBody("sono la nota4");
		Note nota5 = new Note("titolo1 modificato");
		nota5.setBody("sono la nota1 modificata");
		
		System.out.println(notes.add(nota1));
		notes.add(nota2); notes.add(nota3);
		System.out.println(notes.add(nota4));
		System.out.println(notes.toString());
		System.out.println("ID nota 1" +nota1.getID());
		System.out.println("ID nota 2" +nota2.getID());
		notes.updateNote(nota5, 0);
		System.out.println(notes.toString());
		System.out.println("ID nota 1" +nota1.getID());
		
		
	}
}
