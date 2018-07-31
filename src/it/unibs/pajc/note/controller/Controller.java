package it.unibs.pajc.note.controller;

import java.util.List;

import it.unibs.pajc.note.model.Identifiable;
import it.unibs.pajc.note.status.ValidationError;

public abstract class Controller<E extends Identifiable> {

	/**
	 * Metodo per la ricezione di tutte gli elementi
	 * @return Tutti gli elementi dell'archivio
	 */
	public abstract List<E> index();

	/**
	 * Crea un nuovo elemento, lo aggiunge
	 * @return Se l'elemento è stato creato
	 */
	// Magari ritornare uno stato
	public abstract ValidationError create(E e);

	/**
	 * Mostra un elemento specifico in base all'id
	 * @param id l'id secondo il quale cercare l'elemento
	 * @return
	 */
	public abstract E show(int id);

	
	// Decidere se ritornare uno stato o la nota modificata
	public abstract ValidationError update(int id);

	// Magari ritornare uno stato
	public abstract boolean destroy(int id);

}
