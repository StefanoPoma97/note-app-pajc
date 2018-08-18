package it.unibs.pajc.note.data;

import java.util.ArrayList;
import java.util.Set;

import it.unibs.pajc.note.model.Tag;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;
import it.unibs.pajc.note.utility.AuthenticationUtility;

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
	protected ValidationError validate(User e) {
		if (e.getName().isEmpty())
			return ValidationError.NAME_EMPTY;
		//Se pwd è uguale a stringa vuota
		if (e.getPassword().equals(AuthenticationUtility.generateHash("")))
			return ValidationError.PASSWORD_EMPTY;
		
		//due user con stesso username -> TODO: equals
		if(elements.contains(e))
			return ValidationError.USER_PRESENT;
		return ValidationError.CORRECT;
		
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
		String tmp = AuthenticationUtility.generateHash(password);
		//prende dalla lista tutti gli utenti con lo stesso username (solo 1), poi confronta la password
		if (this.getWhere(x -> x.getName().equals(username)).isEmpty())
			return false;
		User u = this.getWhere(x -> x.getName().equals(username)).get(0);
		if (u.getPassword().equals(tmp)) {
			return true;
		}
		else 
			return false;
	}
	
	public Set<Tag> getTagsByUser(User us){
		return getWhere(x->x.getID()==us.getID()).get(0).getPersonalTag();
	}
	
//	public void addTag(Tag tag, User us){
//		getWhere(x->x.getID()==us.getID()).get(0).addTag(tag);
//		System.out.println("label aggiunta con successo");
//		System.out.println(getWhere(x->x.getID()==us.getID()).get(0).getPersonalTag());
//	}
	
	public ArrayList<String> getlabelsByUser(User u){
		return getWhere(x->x.getID()==u.getID()).get(0).getLabel();
	}
	
	public boolean addLabel (String label, User us){
		boolean out= getWhere(x->x.getID()==us.getID()).get(0).addLabel(label);
		System.out.println("label" +label+" aggiunta con successo al utente: "+us);
		System.out.println("elenco delle sue labels: "+getWhere(x->x.getID()==us.getID()).get(0).getLabel());
		return out;
	}
	
	public void updateLabel(ArrayList<String>str, User us){
		getWhere(x->x.getID()==us.getID()).get(0).updateLabel(str);
	}

}
