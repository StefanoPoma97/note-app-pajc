package it.unibs.pajc.note.data;

import it.unibs.pajc.note.model.Note;

public class NoteArchive extends Archive<Note> {


	public NoteArchive() {
		super();
	}

	/**
	 * Una nota è valida se il titolo non è vuoto.
	 */
	@Override
	protected boolean validate(Note n) {
		if (n.getTitle().isEmpty())
			return false;
		return true;
	}
	
	// public void updateNote (Note note, int index){
	// int newID = notes.get(index).getID();
	// notes.remove(index);
	// note.setID(newID);
	// notes.add(note);
	// }

	@Override
	public String toString() {
		return elements.toString();
	}
}
