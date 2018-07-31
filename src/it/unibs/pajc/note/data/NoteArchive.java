package it.unibs.pajc.note.data;

import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.status.ValidationError;

public class NoteArchive extends Archive<Note> {

	public NoteArchive() {
		super();
	}

	/**
	 * Una nota è valida se il titolo non è vuoto.
	 */
	@Override
	protected ValidationError validate(Note n) {
		if (n.getTitle().isEmpty())
			return ValidationError.TITLE_EMPTY;
		return ValidationError.CORRECT;
	}

	/**
	 * metodo che aggiorna una nota. concretamente salva l'ID, elimina la nota
	 * richimando validate verifica che sia valida e poi la aggiunge settando il
	 * vecchio ID
	 * 
	 * @param note
	 * @param index
	 * @return Errors
	 */
	
	public ValidationError updateNote(Note note, int index) {
		int newID = elements.get(index).getID();
		elements.remove(index);
		ValidationError updateStatus = validate(note);
		if (updateStatus == ValidationError.CORRECT) {
			note.setID(newID);
			elements.add(note);
		}
		return updateStatus;
	}

	

	@Override
	public String toString() {
		return elements.toString();
	}
}
