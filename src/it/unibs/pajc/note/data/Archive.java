package it.unibs.pajc.note.data;

public abstract class Archive {
//	private List<?> elements;
	
	public boolean add(Object o) {
		if(!validation(o))
			return false;
		
		setID(o);
		store(o);
		
		return true;
	}

	protected boolean validation(Object o) {
		return true;
	}
	
	protected void setID(Object o) {}
	
	protected void store(Object o) {}
	
	
	
	

	
	
	
}
