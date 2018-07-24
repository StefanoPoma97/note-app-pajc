package it.unibs.pajc.note.data;

import it.unibs.pajc.note.model.User;

public class UserArchive extends Archive<User>{
	
	
	public UserArchive() {
		super();
	}
	
	@Override
	protected boolean validate(User e) {
		if(e.getName().isEmpty())
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return elements.toString();
	}
	
}
