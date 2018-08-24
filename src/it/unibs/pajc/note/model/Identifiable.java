package it.unibs.pajc.note.model;

import java.io.Serializable;

/**
 * Per la gestione degli id
 * @author danielevezz
 *
 */
public abstract class Identifiable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}
	
	
}
