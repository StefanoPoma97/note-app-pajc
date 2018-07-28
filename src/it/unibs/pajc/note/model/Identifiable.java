package it.unibs.pajc.note.model;
/**
 * Per la gestione degli id
 * @author danielevezz
 *
 */
public abstract class Identifiable {
	private int id;

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}
	
	
}
