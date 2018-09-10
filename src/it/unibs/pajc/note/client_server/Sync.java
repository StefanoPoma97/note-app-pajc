package it.unibs.pajc.note.client_server;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import it.unibs.pajc.note.model.User;

public class Sync {
	
	private User utente=null;
	private Integer ID=null;
	Set<User> refreshedBy= new HashSet<>();
	
	public Sync (User u){
		utente=u;
	}
	
	public Integer getID(){
		return ID;
	}
	
	public User getUser(){
		return utente;
	}
	
	public void setID(int id){
		ID=id;
	}
	
	public void deleteID(){
		ID=null;
	}
	
	@Override
	public String toString() {
		String str=("INDIRIZZO: "+ID+" MODIFICATO SA: "+utente.getName());
		return str;
	}
	
	public void addRefreshed(User u){
		refreshedBy.add(u);
	}
	
	public ArrayList<User> getRefreshedBy(){
		return new ArrayList<User>(refreshedBy);
	}
	
	@Override
	public boolean equals(Object arg0) {
		Sync o = (Sync)arg0;
		return toString().endsWith(o.toString());
	}

}
