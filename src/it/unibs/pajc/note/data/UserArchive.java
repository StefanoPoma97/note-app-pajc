package it.unibs.pajc.note.data;

import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.utility.AuthenticationUtility;
import it.unibs.pajc.note.utility.Errors;

public class UserArchive extends Archive<User> {
	

	public UserArchive() {
		super();
	}

	/**
	 * Un utente Ã¨ valido se:
	 * - username != ""
	 * - pwd != ""
	 * - username unico nell'archivio
	 * ritorna un Enum per segnalarlo
	 */
	@Override
	protected Errors validate(User e) {
		if (e.getName().isEmpty())
			return Errors.NAME_EMPTY;
		//Se pwd Ã¨ uguale a stringa vuota
		if (e.getPassword().equals(AuthenticationUtility.generateHashString("")))
			return Errors.PASSWORD_EMPTY;
		
		//due user con stesso username -> TODO: equals
		if(elements.contains(e))
			return Errors.USER_PRESENT;
		return Errors.CORRECT;
		
		//TODO comunicare in che tipo di errore si è capitati (password, nome, o unicità nome)
	}

	@Override
	public String toString() {
		return elements.toString();
	}
	
	
	/**
	 * Metodo per l'autenticazione dell'utente.
	 * Esso deve avere l'username e la password uguali per essere accettato
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean authenticate(String username, String password) {
		// Questo assume che ci sia un solo utente con quell'username come dovrebbe
		// essere
		String tmp = AuthenticationUtility.generateHashString(password);
		//prende dalla lista tutti gli utenti con lo stesso username (solo 1), poi confronta la password
		User u = this.getWhere(x -> x.getName().equals(username)).get(0);
		if (u.getPassword().equals(tmp)) {
			return true;
		}
		else 
			return false;
	}
	

}
