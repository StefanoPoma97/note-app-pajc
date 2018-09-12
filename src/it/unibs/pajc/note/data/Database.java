package it.unibs.pajc.note.data;

import java.io.Serializable;

public class Database implements Serializable {
	private static final long serialVersionUID = 1L;
	private NoteArchive notes = NoteArchive.getIstance();
	private UserArchive users = UserArchive.getIstance();

	public Database() {
	}

	/**
	 * classe utile per salvare in un singolo oggetto note e utenti
	 * 
	 * @param n
	 * @param u
	 * @author Stefano Poma
	 */
	public Database(NoteArchive n, UserArchive u) {
		notes = n;
		users = u;
	}

	/**
	 * serve per estrarre notearchive
	 * 
	 * @return restituisce NoteArchive
	 * @author Stefano Poma
	 */
	public NoteArchive getNotes() {
		return notes;
	}

	/**
	 * serve per impostare notearchive
	 * 
	 * @param notes
	 * @author Stefano Poma
	 */
	public void setNotes(NoteArchive notes) {
		this.notes = notes;
	}

	/**
	 * restituisce l'archvio utenti
	 * 
	 * @return UserArchive
	 * @author Stefano Poma
	 */
	public UserArchive getUsers() {
		return users;
	}

	/**
	 * imposta l'archivio utenti
	 * 
	 * @param users
	 * @author Stefano Poma
	 */
	public void setUsers(UserArchive users) {
		this.users = users;
	}

}
