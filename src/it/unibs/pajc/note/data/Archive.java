package it.unibs.pajc.note.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import it.unibs.pajc.note.model.Identifiable;
import it.unibs.pajc.note.utility.Errors;

public abstract class Archive<E extends Identifiable>  implements Serializable{

	protected List<E> elements;
	
	/**
	 * TODO: Per il momento possiamo usare ArrayList
	 */
	public Archive() {
		elements = new ArrayList<E>();
	}
	
	/**
	 * Aggiunge un elemento all'archivio.
	 * L'elemento per essere inserito deve essere valido. Il metodo validate è definito nelle classi {@link Note} e {@link User}.
	 * In seguito è assegnato un ID a ciascun elemento ed infine questo viene inserito nel database.
	 * viene tornato un Enum per capire che eventuali errori si sono presentati
	 * @param e
	 * @return Errors
	 */
	public Errors add(E e) {

		if (validate(e).equals(Errors.CORRECT)){
			setID(e);
			elements.add(e);
			return Errors.CORRECT;
		}
		else
			return validate(e);
		
	}
	
	

	/**
	 * Metodo per la validazione dell'istanza da inserire nell'archivio
	 * @param e L'istanza della classe da validare
	 * @return Errors.CORRECT se la classe � valida
	 */
	protected abstract Errors validate(E e);
	               
	
	/**
	 * Metodo per la gestione degli id
	 * Prende l'id max e lo incrementa 
	 * @param e
	 */
	private void setID(E e) {
		//TODO: far in modo che il max venga salvato in modo da non doverlo ricalcolare
		OptionalInt maxID = elements.stream().mapToInt(x -> x.getID()).max();
		if (maxID.isPresent()) {
			int id = maxID.getAsInt();
			e.setID(++id);
		} else {
			e.setID(0);
		}
	}

	
	/**
	 * Metodo per applicare un filtro di ricerca all'archivio
	 * @param pred Il filtro su cui si basa la ricerca
	 * @return La lista filtrata
	 */
	public List<E> getWhere(Predicate<E> pred) {
		return elements.stream().filter(pred).collect(Collectors.toList());
	}
	
	
	/**
	 * Rimuove gli elementi in base al {@link Predicate}
	 * @param pred La condizione secondo la quale rimuovere gli elementi
	 * @return true se sono stati rimossi elementi
	 */
	public boolean remove(Predicate<E> pred) {
		return elements.removeAll(getWhere(pred));
	}
	
	
	public List<E> all() {
		return elements;
	}
	
	
	

	
	
	
}
