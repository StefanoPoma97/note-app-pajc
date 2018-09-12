package it.unibs.pajc.note.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class Note extends Identifiable implements Serializable {

	private String title;
	private String body;
	private Calendar createdAt;
	private Calendar updatedAt;
	private boolean isPublic;
	private boolean pin = false;
	private User author;
	private Set<Tag> tags;
	private ArrayList<String> labels = new ArrayList<>();
	private int likes = 0;
	private Set<User> sharedWith = new HashSet<>();
	private ArrayList<User> likedBy = new ArrayList<>();

	/**
	 * costruttore che permette di settare la data di creazione
	 * 
	 * @param _title
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public Note(String _title) {
		this.title = _title;
		createdAt = new GregorianCalendar();
	}

	/**
	 * resttuisce il titolo
	 * 
	 * @return titolo della nota
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * aggiunge un nuovo tag alla nota
	 * 
	 * @param newTag
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public void addTag(Tag newTag) {
		tags.add(newTag);
	}

	/**
	 * ritorna tutte le labels associate alla nota
	 * 
	 * @return ArrayList di labels
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public ArrayList<String> getLabels() {
		return labels;
	}

	/**
	 * imposta la labels
	 * 
	 * @param labels
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public void setLabels(ArrayList<String> labels) {
		this.labels = labels;
	}

	/**
	 * aggiunge una nuova label verificando che non sia gi� presente
	 * 
	 * @param lb
	 * @return true se � stata aggiunta con successo
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public boolean addLabel(String lb) {
		if (labels.contains(lb)) {
			return false;
		} else {
			labels.add(lb);
			return true;
		}

	}

	/**
	 * ritorna tutte le labels (dupplicato)
	 * 
	 * @return ArrayList di Label
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public ArrayList<String> getLabel() {
		return labels;
	}

	/**
	 * aggiunge pi� di una label contemporaneamente
	 * 
	 * @param la
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public void addLabels(ArrayList<String> la) {
		for (String str : la) {
			addLabel(str);
			// System.out.println("STO AGGIUNGENDO: "+str);
		}
		// System.out.println("aggiunta con successo labels alla nota");
	}

	/**
	 * imposta il titolo della nota
	 * 
	 * @param title
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * restituisce il corpo della nota
	 * 
	 * @return stringa contenente il testo della nota
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public String getBody() {
		return body;
	}

	/**
	 * imposta il corpo della nota
	 * 
	 * @param body
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * ritorna data dell'ultima modifica
	 * 
	 * @return data ultima modifica
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public Calendar getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * imposta data ultima modifica
	 * 
	 * @param updatedAt
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * restituisce se � pubblica o no
	 * 
	 * @return true e' pubblica
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public boolean isPublic() {
		return isPublic;
	}

	/**
	 * imposta se � pubblica
	 * 
	 * @param isPublic
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * restituisce l'autore
	 * 
	 * @return autore
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * imposta l'autore della nota
	 * 
	 * @param author
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public void setAutor(User author) {
		this.author = author;
	}

	/**
	 * ritorna un set di tag
	 * 
	 * @return Set di tag
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public Set<Tag> getTags() {
		return tags;
	}

	/**
	 * imposta i tag
	 * 
	 * @param tags
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	/**
	 * ritorna la data di creazione
	 * 
	 * @return data di creazione
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public Calendar getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return title + "\t" + body + "\t" + getID();
	}

	/**
	 * imposta se e' segnata
	 * 
	 * @param _pin
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public void setPin(Boolean _pin) {
		pin = _pin;
	}

	/**
	 * ritorna se e' segnata
	 * 
	 * @return true se e' segnata
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public Boolean getPin() {
		return pin;

	}

	/**
	 * aggiunge un like alla nota
	 * 
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public void addLike() {
		likes++;
	}

	/**
	 * rimuove un like alla nota
	 * 
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public void removeLike() {
		likes--;
	}

	/**
	 * restituisce i like della nota
	 * 
	 * @return numero di likes
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public int getLike() {
		return likes;
	}

	/**
	 * ritorna utenti condivisi
	 * 
	 * @return Set di utenti condivisi
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public Set<User> getSharedWith() {
		return sharedWith;
	}

	/**
	 * utenti condivisi
	 * 
	 * @return ArrayList di utenti condivisi
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public ArrayList<User> getSharedWithArray() {
		return new ArrayList<User>(sharedWith);
	}

	/**
	 * aggiunge utenti condivisi
	 * 
	 * @param us
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public void addSharedUsers(Set<User> us) {
		sharedWith.addAll(us);
		// System.out.println("AGGIUNTI NUOVI SHARED US: "+sharedWith);
	}

	/**
	 * ritorna utenti che hanno lasciato like
	 * 
	 * @return ArrayList di utenti che hanno lasciato like
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public ArrayList<User> getLikedBy() {
		return likedBy;
	}

	/**
	 * aggiungi un utente che ha lasciato like
	 * 
	 * @param u
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public void addLikedUser(User u) {
		likedBy.add(u);
		Set<User> cp = new HashSet<>(likedBy);
		likedBy = new ArrayList<>(cp);
	}

	/**
	 * rimuove un utente che ha lasciato like
	 * 
	 * @param u
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public void removeLikedUser(User u) {
		likedBy.remove(u);
	}

}
