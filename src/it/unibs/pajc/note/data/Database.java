package it.unibs.pajc.note.data;

import java.io.Serializable;

public class Database implements Serializable {
	private static final long serialVersionUID = 1L;
	private NoteArchive notes;
	private UserArchive users;
	
	public Database (){
	}
	
	/**
	 * classe utile per salvare in un singolo oggetto note e utenti
	 * @param n
	 * @param u
	 */
	public Database (NoteArchive n, UserArchive u){
		notes=n;
		users=u;
	}
	public NoteArchive getNotes() {
		return notes;
	}
	public void setNotes(NoteArchive notes) {
		this.notes = notes;
	}
	public UserArchive getUsers() {
		return users;
	}
	public void setUsers(UserArchive users) {
		this.users = users;
	}
	
	

}
