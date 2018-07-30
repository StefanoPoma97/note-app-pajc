package it.unibs.pajc.note.data;

import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.utility.Errors;

public class NoteArchive extends Archive<Note> {


	public NoteArchive() {
		super();
	}

	/**
	 * Una nota è valida se il titolo non è vuoto.
	 */
	@Override
	protected Errors validate(Note n) {
		if (n.getTitle().isEmpty())
			return Errors.TITLE_EMPTY;
		return Errors.CORRECT;
	}
	
	
	/**
	 * metodo che aggiorna una nota. concretamente salva l'ID, elimina la nota 
	 * richimando validate verifica che sia valida e poi la aggiunge settando il vecchio ID
	 * @param note
	 * @param index
	 * @return Errors
	 */
	 public Errors updateNote (Note note, int index){
	 int newID = elements.get(index).getID();
	 elements.remove(index);
	 if (validate(note).equals(Errors.TITLE_EMPTY))
		 return Errors.TITLE_EMPTY;
	 note.setID(newID);
	 elements.add(note);
	 return Errors.CORRECT;
	 }

	@Override
	public String toString() {
		return elements.toString();
	}
}
