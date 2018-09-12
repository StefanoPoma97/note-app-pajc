package it.unibs.pajc.note.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import it.unibs.pajc.note.utility.AuthenticationUtility;

public class User extends Identifiable implements Serializable {

	private String name;
	private String password;
	private Set<Tag> personalTag = new HashSet<Tag>();
	private ArrayList<String> labels = new ArrayList<>();

	/**
	 * costruttore che crea utente con nome e password convertita in hashcode
	 * 
	 * @param name
	 * @param password
	 */
	public User(String name, String password) {
		this.name = name;
		this.password = AuthenticationUtility.generateHash(password);
	}

	/**
	 * restituisce il nome
	 * 
	 * @return nome dell'utente
	 * @author Daniele Vezzoli, Stefano Poma
	 */
	public String getName() {
		return name;
	}

	/**
	 * imposta il nome dell'utente
	 * 
	 * @param name
	 * @author Daniele Vezzoli, Stefano Poma
	 */
	public void setName(String name) {
		if (name != null)
			this.name = name;
	}

	/**
	 * ritorna l'hashcode della password, per ragioni di sicurezza
	 * 
	 * @return hashcode della password
	 * @author Daniele Vezzoli, Stefano Poma
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * imposta la password che viene convertita in hashcode
	 * 
	 * @param password
	 * @author Daniele Vezzoli, Stefano Poma
	 */
	public void setPassword(String password) {
		if (password != null)
			this.password = AuthenticationUtility.generateHash(password);
	}

	/**
	 * aggiunge un tag
	 * 
	 * @param newTag
	 * @author Daniele Vezzoli, Stefano Poma
	 */
	public void addTag(Tag newTag) {
		if (!personalTag.contains(newTag))
			personalTag.add(newTag);
	}

	/**
	 * restituisce i tag personali
	 * 
	 * @return Set di tag
	 */
	public Set<Tag> getPersonalTag() {
		return personalTag;
	}

	/**
	 * restituisce le label
	 * 
	 * @return ArrayList di labels
	 */
	public ArrayList<String> getLabel() {
		return labels;
	}

	/**
	 * aggiunge una label
	 * 
	 * @param lb
	 * @return true se e' stata aggiunta
	 * @author Daniele Vezzoli, Stefano Poma
	 */
	public boolean addLabel(String lb) {
		if (labels.contains(lb)) {
			return false;
		} else {
			labels.add(lb);
			return true;
		}
	}

	@Override
	public String toString() {

		return this.name + "\n";
	}

	@Override
	public boolean equals(Object obj) {
		User tmp = (User) obj;
		if (tmp.getName().equals(this.name)) {
			return true;
		} else
			return false;
	}

	/**
	 * aggiorna l'elenco di labels sovrascrivendone uno nuovo
	 * 
	 * @param str
	 * @author Daniele Vezzoli, Stefano Poma
	 */
	public void updateLabel(ArrayList<String> str) {
		labels = str;

	}

}
