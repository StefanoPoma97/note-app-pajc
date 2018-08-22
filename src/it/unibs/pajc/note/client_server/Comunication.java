package it.unibs.pajc.note.client_server;

import java.io.Serializable;
import java.util.ArrayList;

import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;

public class Comunication implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Stringhe per la comunicazione
	private static final String LOGIN= "login";
	private static final String CREATE_ACCOUNT= "create_account";
	
	
	
	//dati utili
	private String info;
	private String error;
	private User u= null;
	private ArrayList<String> name_pass= null;
	private boolean login;
	
	
	public String getError(){
		return info;
	}
	
	public void setError(String in){
		error=in;
	}
	
	public String getInfo(){
		return info;
	}
	
	public void setInfo(String in){
		info=in;
	}
	
	//metodi get e set
	public void setLogin(String name, String pass){
		name_pass= new ArrayList<>();
		name_pass.add(name);
		name_pass.add(pass);
	}
	
	public ArrayList<String> getLogin(){
		return name_pass;
	}
	
	public void setLoginResult(boolean er){
		login=er;
	}
	
	public boolean getLoginResult(){
		return login;
	}
}
