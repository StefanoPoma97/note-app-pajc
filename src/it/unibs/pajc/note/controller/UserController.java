package it.unibs.pajc.note.controller;

import it.unibs.pajc.note.data.UserArchive;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;

public class UserController extends Controller<User> {


	public UserController() {
		archive = new UserArchive();
		// solo per test
		archive.add(new User("paolo", "merazza"));
		archive.add(new User("utente1", "pass1"));
	}

	public Boolean login(String name, String password) {
		System.out.println("info arrivate per il Login: nome= " + name + " pass= " + password);
		UserArchive users = (UserArchive) archive;
		
		Boolean validate = users.authenticate(name, password);
		if (validate) {
			System.out.println("valido");
		} else {
			System.out.println("non valido");
		}
		return validate;
	}

	public ValidationError create(String username, String password) {
		System.out.println("info arrivate per creazione account: nome= " + username + " pass= " + password);
		User u = new User(username, password);
		return archive.add(u);
	}

}
