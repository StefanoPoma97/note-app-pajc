package it.unibs.pajc.note.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import it.unibs.pajc.note.utility.AuthenticationUtility;

public class User extends Identifiable implements Serializable {

	private String name;
	private String password;
	//ha senso che sia un HashSet?
	private Set<Tag> personalTag = new HashSet<Tag>();
	private ArrayList<String> labels= new ArrayList<>();

	public User(String name, String password) {
		this.name = name;
		this.password = AuthenticationUtility.generateHash(password);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name != null)
			this.name = name;
	}

	//ritorna l'Hash code della password per ragioni di sicurezza
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if(password != null)
			this.password = AuthenticationUtility.generateHash(password);
	}

	//TODO null pointer eccezione!
	public void addTag(Tag newTag) {
		if(!personalTag.contains(newTag))
		personalTag.add(newTag);
	}

	public Set<Tag> getPersonalTag() {
		return personalTag;
	}

	public ArrayList<String> getLabel(){
		return labels;
	}
	
	public boolean addLabel(String lb){
		if(labels.contains(lb)){
			return false;
		}
		else{
			labels.add(lb);
			return true;
		}
	}
	
	@Override
	public String toString() {

		return this.name + "\n" + getPassword();
	}
	
	@Override
	public boolean equals(Object obj) {
		User tmp= (User)obj;
		if (tmp.getName().equals(this.name)){
			return true;
		}
		else
			return false;
	}
	
	public void updateLabel(ArrayList<String> str){
		labels=str;
		
	}

}
