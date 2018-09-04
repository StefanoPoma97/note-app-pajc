package it.unibs.pajc.note.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import it.unibs.pajc.note.model.Identifiable;
import it.unibs.pajc.note.status.ValidationError;

public abstract class Archive<E extends Identifiable>  implements Serializable{

	protected List<E> elements= new ArrayList<E>();
	
	public Archive() {
	}
	

	
	/**
	 * Aggiunge un elemento all'archivio.
	 * L'elemento per essere inserito deve essere valido. Il metodo validate è definito nelle classi {@link Note} e {@link User}.
	 * In seguito è assegnato un ID a ciascun elemento ed infine questo viene inserito nel database.
	 * viene tornato un Enum per capire che eventuali errori si sono presentati
	 * @param e
	 * @return Errors
	 * @author Daniele Vezzoli
	 */
	public ValidationError add(E e) {

		if (validate(e).equals(ValidationError.CORRECT)){
			setID(e);
			elements.add(e);
			return ValidationError.CORRECT;
		}
		else
			return validate(e);
		
	}
	
	

	/**
	 * Metodo per la validazione dell'istanza da inserire nell'archivio
	 * @param e L'istanza della classe da validare
	 * @return Errors.CORRECT se la classe � valida
	 * @author Daniele Vezzoli
	 */
	protected abstract ValidationError validate(E e);
	               
	
	/**
	 * Metodo per la gestione degli id
	 * Prende l'id max e lo incrementa 
	 * @param e
	 * @author Daniele Vezzoli
	 */
	private void setID(E e) {
		//TODO: far in modo che il max venga salvato in modo da non doverlo ricalcolare
		OptionalInt maxID = elements.stream().mapToInt(x -> x.getID()).max();
		if (maxID.isPresent()) {
			int id = maxID.getAsInt();
			e.setID(++id);
//			System.out.println("IDDDDDDDD="+id);
		} else {
			e.setID(0);
//			System.out.println("IDDDDDDDD=00000000");
		}
	}

	
	/**
	 * Metodo per applicare un filtro di ricerca all'archivio
	 * @param pred Il filtro su cui si basa la ricerca
	 * @return La lista filtrata
	 * @author Daniele Vezzoli
	 */
	public List<E> getWhere(Predicate<E> pred) {
		return elements.stream().filter(pred).collect(Collectors.toList());
	}
	
	
	/**
	 * Rimuove gli elementi in base al {@link Predicate}
	 * @param pred La condizione secondo la quale rimuovere gli elementi
	 * @return true se sono stati rimossi elementi
	 * @author Daniele Vezzoli
	 */
	public boolean remove(Predicate<E> pred) {
		return elements.removeAll(getWhere(pred));
	}
	
	/**
	 * metodo che aggiorna una nota. concretamente salva l'ID, elimina la nota
	 * richimando validate verifica che sia valida e poi la aggiunge settando il
	 * vecchio ID
	 * 
	 * @param note
	 * @param index
	 * @return Errors
	 * @author Daniele Vezzoli
	 */
	public ValidationError update(E e, int id) {
		remove(x->x.getID()==id);
		ValidationError updateStatus = validate(e);
		if(updateStatus == ValidationError.CORRECT) {
			e.setID(id);
			elements.add(e);
		}
		return updateStatus;
	}
	
	/**
	 * 
	 * @return tutti gli elementi
	 * @author Daniele Vezzoli
	 */
	public List<E> all() {
		return elements;
	}
	
	/**
	 * rimuove tutti gli elementi
	 * @author Daniele Vezzoli
	 */
	public void removeAll(){
		elements = new ArrayList<E>();
	}
	
	/**
	 * sostituisce tutti elementi da una lista che viene passata
	 * @param _elements
	 * @author Daniele Vezzoli
	 */
	public void setElements(List<E> _elements){
		elements=_elements;
	}
	

	
	
	
}
