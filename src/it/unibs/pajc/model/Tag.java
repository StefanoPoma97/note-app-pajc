package it.unibs.pajc.model;

public class Tag {
	
	private String name;
	private int id;
	
	
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
