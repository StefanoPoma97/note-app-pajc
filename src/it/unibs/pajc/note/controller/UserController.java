package it.unibs.pajc.note.controller;

import java.util.List;

import it.unibs.pajc.note.data.Archive;
import it.unibs.pajc.note.data.UserArchive;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;

public class UserController extends Controller<User> {

	UserArchive users;
	
	public UserController() {
		users = new UserArchive();
		//solo per test
		users.add(new User ("paolo","merazza"));
		users.add(new User ("utente1","pass1"));
	}
	
	/**
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public Boolean login(String name, String password){
		System.out.println("info arrivate: nome= "+name+ " pass= "+password);
		Boolean validate = users.authenticate(name, password);
		if (validate){
			System.out.println("valido");
		}
		else{
			System.out.println("non valido");
		}
		return validate;
	}
	
	
	@Override
	public void setArchive(Archive<User> e) {
		users=(UserArchive)e;
		
	}
	
	@Override
	public List<User> index() {
		return users.all();
	}

	@Override
	public ValidationError create(User e) {
		return users.add(e);
	}
	
	public ValidationError create(String username, String password) {
		System.out.println("info arrivate: nome= "+username+ " pass= "+password);
		User u = new User(username, password);
		return users.add(u);
	}

	@Override
	public User show(int id) {
		return users.getWhere(n -> n.getID() == id).get(0);
	}

	@Override
	public List<User> personalNotes(User u) {
		return users.getWhere(x -> x.getName().equals(u.getName()));
	}
	
	@Override
	public ValidationError update(int id) {
		User u = users.getWhere(x -> x.getID() == id).get(0);
		return users.update(u, users.all().indexOf(u));
	}

	@Override
	public boolean destroy(int id) {
		return users.remove(n -> n.getID() == id);
	}


}
