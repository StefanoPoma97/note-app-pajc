package it.unibs.pajc.note.data;

import java.util.HashMap;
import java.util.Map;

import it.unibs.pajc.note.exceptions.UserAlreadyPresentException;
import it.unibs.pajc.note.model.User;

public class Authenticator{
	protected Map<User, String> records;

	public Authenticator() {
		records = new HashMap<>(); 
	}
	
	boolean authenticate(User user, String password) {
		//TODO: equals method in User
		if(records.get(user).equals(password)) {
			//L'user è presente e la password è uguale
			return true;
		}
		return false;
	}
	
	void storeRecord(User user, String password) {
		//Se c'è un user nella map con quel username -> throw exc
		//altrimenti crealo
		
		if(records.containsKey(user))
			throw new UserAlreadyPresentException();
		else
			records.put(user, password);
	}
	
}
