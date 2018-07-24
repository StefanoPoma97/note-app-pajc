package it.unibs.pajc.note.data;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import it.unibs.pajc.note.model.Identifiable;

public abstract class Archive<E extends Identifiable> {
	protected List<E> elements;
	
	/**
	 * TODO: Per il momento possiamo usare ArrayList
	 */
	public Archive() {
		elements = new ArrayList<E>();
	}
	
	public boolean add(E e) {
		if(!validation(e))
			return false;
		
		setID(e);
		store(e);
		
		return true;
	}

	/**
	 * Metodo per la validazione dell'istanza da inserire nell'archivio
	 * Metodo dummy, fare l'override per ogni classe.
	 * @param e L'istanza della classe da validare
	 * @return true se la classe è valida.
	 */
	protected boolean validation(E e) {
		return true;
	}
	                     
	protected void setID(E e) {
//		Note n = (Note) i;
		OptionalInt maxID = elements.stream().mapToInt(x -> x.getID()).max();
		if (maxID.isPresent()) {
			int id = maxID.getAsInt();
			e.setID(++id);
		} else {
			e.setID(0);
		}
	}
	
	
	protected void store(E e) {
		elements.add(e);
	}
	
	
	/**
	 * Metodo per applicare un filtro di ricerca all'archivio
	 * @param pred Il filtro su cui si basa la ricerca
	 * @return La lista filtrata
	 */
	public List<E> getWhere(Predicate<E> pred) {
		return elements.stream().filter(pred).collect(Collectors.toList());
	}
	
	
	
	

	
	
	
}
