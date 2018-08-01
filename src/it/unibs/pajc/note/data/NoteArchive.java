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
	
	@Override
	public String toString() {
		return elements.toString();
	}
}
