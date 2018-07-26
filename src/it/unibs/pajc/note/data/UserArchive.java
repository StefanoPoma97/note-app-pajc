package it.unibs.pajc.note.data;

import it.unibs.pajc.note.exceptions.UserAlreadyPresentException;
import it.unibs.pajc.note.model.User;

public class UserArchive extends Archive<User> {

	Authenticator auth;

	public UserArchive() {
		super();
		auth = new Authenticator();
	}

	@Override
	protected boolean validate(User e) {
		if (e.getName().isEmpty())
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
		User u = this.getWhere(x -> x.getName().equals(username)).get(0);

		return auth.authenticate(u, password);
	}
	
	protected boolean storeRecord(User user, String password) {
		try {
			auth.storeRecord(user, password);
		} catch (UserAlreadyPresentException e2) {
			return false;
		} 
		return true;
		
	}

}
