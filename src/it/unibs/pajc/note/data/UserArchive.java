package it.unibs.pajc.note.data;

import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.utility.AuthenticationUtility;
import it.unibs.pajc.note.utility.ValidationStatus;

public class UserArchive extends Archive<User> {
	

	public UserArchive() {
		super();
	}

	/**
	 * Un utente è valido se:
	 * - username != ""
	 * - pwd != ""
	 * - username unico nell'archivio
	 * ritorna un Enum per segnalarlo
	 */
	@Override
	protected ValidationStatus validate(User e) {
		if (e.getName().isEmpty())
			return ValidationStatus.NAME_EMPTY;
		//Se pwd è uguale a stringa vuota
		if (e.getPassword().equals(AuthenticationUtility.generateHashString("")))
			return ValidationStatus.PASSWORD_EMPTY;
		
		//due user con stesso username -> TODO: equals
		if(elements.contains(e))
			return ValidationStatus.USER_PRESENT;
		return ValidationStatus.CORRECT;
		
		//TODO comunicare in che tipo di errore si � capitati (password, nome, o unicit� nome)
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
