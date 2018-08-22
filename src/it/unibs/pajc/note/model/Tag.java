package it.unibs.pajc.note.model;

import java.io.Serializable;

public class Tag extends Identifiable implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	
	public Tag (String tag){
		this.name=tag;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object arg0) {
		Tag tmp= (Tag)arg0;
		if (tmp.getName().equals(this.name)){
			return true;
		}
		else
			return false;
	}
	
	
	
	
	

}
