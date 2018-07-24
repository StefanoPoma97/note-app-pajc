package it.unibs.pajc.note.data;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unibs.pajc.note.model.Note;

public class NoteArchive extends Archive {

	private List<Note> notes;

	public NoteArchive() {
		notes = new ArrayList<>();
	}

	@Override
	protected boolean validation(Object o) {
		Note n = (Note) o;
		if (n.getTitle().isEmpty())
			return false;
		return true;
	}

	@Override
	protected void setID(Object o) {
		Note n = (Note) o;
		OptionalInt maxID = notes.stream().mapToInt(x -> x.getID()).max();
		if (maxID.isPresent()) {
			int id = maxID.getAsInt();
			n.setID(++id);
		} else {
			n.setID(0);
		}
	}

	@Override
	protected void store(Object o) {
		Note n = (Note) o;
		notes.add(n);
	}
	
	/**
	 * Metodo per applicare un filtro di ricerca all'archivio
	 * @param pred Il filtro su cui si basa la ricerca
	 * @return La lista filtrata
	 */
	public List<Note> getWhere(Predicate<Note> pred) {
		return notes.stream().filter(pred).collect(Collectors.toList());
	}
	
	
	public List<Note> getNotes() {
		return notes;
	}
	
	

	// public List<Note> getUserNote (User us){
	// List<Note> out= (List<Note>) notes.stream()
	// .filter(n -> n.getAuthor().equalsIgnoreCase(us.getName()))
	// .collect(Collectors.toList());
	// return out;
	// }
	//
	// public void updateNote (Note note, int index){
	// int newID = notes.get(index).getID();
	// notes.remove(index);
	// note.setID(newID);
	// notes.add(note);
	// }
	//
	// private void createId(Note note){
	// note.setID(id);
	// id++;
	// }

	@Override
	public String toString() {
		return notes.toString();
	}
}
