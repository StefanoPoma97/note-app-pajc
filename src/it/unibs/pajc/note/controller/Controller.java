package it.unibs.pajc.note.controller;

import java.util.List;

import it.unibs.pajc.note.data.Archive;
import it.unibs.pajc.note.model.Identifiable;
import it.unibs.pajc.note.status.ValidationError;

public abstract class Controller<E extends Identifiable> {
	
	protected Archive<E> archive;
	
	/**
	 * metodo che permette di caricare un archivio gia' esistente 
	 * @param e
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public void setArchive(Archive<E> e) {
		archive = e;
	}

	/**
	 * Metodo per la ricezione di tutte gli elementi
	 * @return Tutti gli elementi dell'archivio
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public List<E> all() {
		return archive.all();
	}

	/**
	 * Mostra un elemento specifico in base all'id
	 * @param id l'id secondo il quale cercare l'elemento
	 * @return elemente con quel ID
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public E show(int id) {
		return archive.getWhere(x -> x.getID() == id).get(0);
	}

	
	/**
	 * aggiorna una nota esistente, ricevendo la nuova nota e l'ID della nota da modificare
	 * @param e
	 * @param id
	 * @return ValidationError che indica se è stata aggiornata con successo
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public ValidationError update(E e, int id) {
		return archive.update(e, id);
		
	}

	/**
	 * elimina una nota sulla base del suo ID
	 * @param id
	 * @return boolean che indica se è stata eliminata con successo
	 * @author Stefano Poma, Daniele Vezzoli
	 */
	public boolean destroy(int id) {
		return archive.remove(x-> x.getID() == id);
	}

}
