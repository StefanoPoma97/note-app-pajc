package it.unibs.pajc.note.model;

import java.util.Set;

public class User {
	
	
	private String name;
	private String password;
	private int id;
	private Set<Tag> personalTag;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public boolean equals(Object arg0) {
		User tmp= (User)arg0;
		if (tmp.getName().equals(this.name) && tmp.getPassword().equals(this.password) ){
			return true;
		}
		else
			return false;
	}
	
	public void addTag (Tag newTag){
		personalTag.add(newTag);
	}
	
	public void setID(int id) {
		this.id = id;
	}
	public Set<Tag> getPersonalTag() {
		return personalTag;
	}
	
	@Override
	public String toString() {
		
		return this.name;
	}
	
	

}
