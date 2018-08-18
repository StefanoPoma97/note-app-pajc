package it.unibs.pajc.note.controller;

import java.util.ArrayList;
import java.util.Set;

import it.unibs.pajc.note.data.UserArchive;
import it.unibs.pajc.note.model.Tag;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;
public class UserController extends Controller<User> {

	UserArchive userArchive = new UserArchive();
	/**
	 * costruttore
	 */
	public UserController() {
		//questo è solo per test, in realtà avrà un istanza di Client che connettendosi al server avrà accesso all'archivio
		
		User utente= new User("paolo", "merazza");
		utente.addLabel("Labels");
		utente.addLabel("Riunione");
		utente.addLabel("Memo");
		utente.addLabel("Memo2");
		utente.addLabel("Memo3");
		userArchive.add(utente);
		userArchive.add(new User("utente1", "pass1"));
		
	}

	/**
	 * metodo per verificare un login
	 * @param name
	 * @param password
	 * @return un boolean (valido o non valido)
	 */
	public Boolean login(String name, String password) {
		
		return userArchive.authenticate(name, password);

		
	}

	/**
	 * metodo per creare un nuovo account
	 * @param username
	 * @param password
	 * @return ValidateError
	 */
	public ValidationError create(String username, String password) {
		User u = new User(username, password);
		return userArchive.add(u);
	}

	public ArrayList<String> getLabelsByUser (User u){
		return userArchive.getlabelsByUser(u);
	}
	
	public boolean addLabel (String label, User us){
		return userArchive.addLabel(label, us);
	}
	
	public void updateLabel(ArrayList<String> str, User us){
		userArchive.updateLabel(str, us);
	}
}
