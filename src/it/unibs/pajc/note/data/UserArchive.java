package it.unibs.pajc.note.data;

import java.util.ArrayList;
import java.util.Set;

import it.unibs.pajc.note.model.User;

public class UserArchive{
	
	private ArrayList<User> users;
	
	
	public ArrayList<User> getUsers() {
		return users;
	}

	public boolean addUser (User newUser){
		if (authentication(newUser)==false){
			createId(newUser);
			users.add(newUser);
			return true;
		}
		else
			return false;
	}
	
	public boolean login (User login){
		if (authentication(login)==true)
			return true;
		else
			return false;
		
		
	}
	
	private boolean authentication(User tmp){
		if (users.contains(tmp))
			return true;
		else 
			return false;
		
	}
	
	private void createId(User in){
		StringBuffer str= new StringBuffer(in.getName());
		str.append(in.getPassword());
		in.setID(str.toString().hashCode());
	}
	

}
