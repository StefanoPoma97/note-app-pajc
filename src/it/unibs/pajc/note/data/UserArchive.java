package it.unibs.pajc.note.data;

import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.utility.AuthenticationUtility;

public class UserArchive extends Archive<User> {
	

	public UserArchive() {
		super();
	}

	@Override
	protected boolean validate(User e) {
		if (e.getName().isEmpty())
			return false;
		//Se pwd è uguale a stringa vuota
		if (e.getPassword().equals(AuthenticationUtility.generateHashString("")))
			return false;
		
		//due user con stesso username -> TODO: equals
		if(elements.contains(e))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return elements.toString();
	}
	
	

	public boolean authenticate(String username, String password) {
		// Questo assume che ci sia un solo utente con quell'username come dovrebbe
		// essere
		String tmp = AuthenticationUtility.generateHashString(password);
		User u = this.getWhere(x -> x.getName().equals(username)).get(0);
		if (u.getPassword().equals(tmp)) {
			return true;
		}
		else 
			return false;
	}
	

}
