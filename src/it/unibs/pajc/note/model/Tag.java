package it.unibs.pajc.note.model;

import java.io.Serializable;

public class Tag extends Identifiable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	/**
	 * costruttore
	 * 
	 * @param tag
	 * @author Daniele Vezzoli
	 */
	public Tag(String tag) {
		this.name = tag;
	}

	/**
	 * ritorna il nome del tag
	 * 
	 * @return Stringa con il nome
	 * @author Daniele Vezzoli
	 */
	public String getName() {
		return name;
	}

	/**
	 * imposta il nome del tag
	 * 
	 * @param name
	 * @author Daniele Vezzoli
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object arg0) {
		Tag tmp = (Tag) arg0;
		if (tmp.getName().equals(this.name)) {
			return true;
		} else
			return false;
	}

}
