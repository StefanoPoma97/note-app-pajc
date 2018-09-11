package it.unibs.pajc.note.data;

import java.util.ArrayList;
import java.util.Set;

import it.unibs.pajc.note.model.Tag;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;
import it.unibs.pajc.note.utility.AuthenticationUtility;

public class UserArchive extends Archive<User> {
	
private static UserArchive userarchive=null;
	
	/**
	 * costruttore privato
	 * @author Stefano Poma
	 */
	private UserArchive() {
	}

	/**
	 * metodo per restituire l'unica istanza di userarchive
	 * @return UserArchive
	 * @author Stefano Poma
	 */
	public static UserArchive getIstance()
	{
		if (userarchive == null)
			userarchive =new UserArchive();
		return userarchive;
	}
	
	public static UserArchive getIstance(UserArchive us)
	{
		if (userarchive == null)
			userarchive =us;
		return userarchive;
	}
	

	/**
	 * Un utente Ã¨ valido se:
	 * - username != ""
	 * - pwd != ""
	 * - username unico nell'archivio
	 * ritorna un Enum per segnalarlo
	 */
	@Override
	protected ValidationError validate(User e) {
		if (e.getName().isEmpty())
			return ValidationError.NAME_EMPTY;
		//Se pwd Ã¨ uguale a stringa vuota
		if (e.getPassword().equals(AuthenticationUtility.generateHash("")))
			return ValidationError.PASSWORD_EMPTY;
		
		//due user con stesso username -> TODO: equals
		if(elements.contains(e))
			return ValidationError.USER_PRESENT;
		return ValidationError.CORRECT;
		
		//TODO comunicare in che tipo di errore si ï¿½ capitati (password, nome, o unicitï¿½ nome)
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
	 * @return true se l'utente è accettato
	 * @author Daniele Vezzoli
	 */
	public boolean authenticate(String username, String password) {
		
//		System.out.println("ELENCO TUTTI UTENTI:");
//		System.out.println(all());
		// Questo assume che ci sia un solo utente con quell'username come dovrebbe essere
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
	
	//INUTILE
	public Set<Tag> getTagsByUser(User us){
		return getWhere(x->x.getID()==us.getID()).get(0).getPersonalTag();
	}
	
	/**
	 * restituisce le labels associate ad un utente
	 * @param u
	 * @return ArrayList di Labels
	 * @author Stefano Poma
	 */
	public ArrayList<String> getlabelsByUser(User u){
//		System.out.println("STO CERCANDO LABEL DI: "+u.toString());
//		System.out.println("TUTTI GLI UTENTI: "+all());
		return getWhere(x->x.getID()==u.getID()).get(0).getLabel();
	}
	
	/**
	 * aggiunge una label ad un utente specificato
	 * @param label
	 * @param us
	 * @return true se è stata aggiunta con successo
	 * @author Stefano Poma
	 */
	public boolean addLabel (String label, User us){
		return getWhere(x->x.getID()==us.getID()).get(0).addLabel(label);
		
	}
	
	/**
	 * permette di aggiornare (sostituire) tutte le labels 
	 * associate ad un utente specificato
	 * @param str
	 * @param us
	 * @author Stefano Poma
	 */
	public void updateLabel(ArrayList<String>str, User us){
		getWhere(x->x.getID()==us.getID()).get(0).updateLabel(str);
	}
	
	/**
	 * restituisce tutti gli utenti
	 * @param u
	 * @return ArrayList con tutti gli utenti
	 * @author Stefano Poma
	 */
	public ArrayList<User> getAllUsers(User u){
//		System.out.println("TUTTI GLI UTENTI: "+all());
		return (ArrayList<User>)getWhere(x->!x.getName().equals(u.getName()));
	}

}
