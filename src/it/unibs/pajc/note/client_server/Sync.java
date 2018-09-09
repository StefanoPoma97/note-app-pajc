package it.unibs.pajc.note.client_server;

import it.unibs.pajc.note.model.User;

public class Sync {
	
	private User utente=null;
	private Integer ID=null;
	
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
		ID=-1;
	}

}
