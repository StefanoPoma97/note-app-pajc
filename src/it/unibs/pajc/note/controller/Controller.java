package it.unibs.pajc.note.controller;

import java.util.List;

import it.unibs.pajc.note.data.Archive;
import it.unibs.pajc.note.model.Identifiable;
import it.unibs.pajc.note.status.ValidationError;

public abstract class Controller<E extends Identifiable> {
	
	protected Archive<E> archive;
	
	/**
	 * metodo che permette di caricare un archivio gi� esistente 
	 * @param e
	 */
	public void setArchive(Archive<E> e) {
		archive = e;
	}

	/**
	 * Metodo per la ricezione di tutte gli elementi
	 * @return Tutti gli elementi dell'archivio
	 */
	public List<E> all() {
		return archive.all();
	}

	/**
	 * Mostra un elemento specifico in base all'id
	 * @param id l'id secondo il quale cercare l'elemento
	 * @return
	 */
	public E show(int id) {
		return archive.getWhere(x -> x.getID() == id).get(0);
	}

	
	// Decidere se ritornare uno stato o la nota modificata
	public ValidationError update(E e, int id) {
		return archive.update(e, id);
	}

	// Magari ritornare uno stato
	public boolean destroy(int id) {
		return archive.remove(x-> x.getID() == id);
	}

}
