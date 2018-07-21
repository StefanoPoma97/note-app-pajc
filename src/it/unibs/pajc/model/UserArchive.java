package it.unibs.pajc.model;

import java.util.ArrayList;
import java.util.Set;

public class UserArchive {
	
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
		in.setId(str.toString().hashCode());
	}
	

}
