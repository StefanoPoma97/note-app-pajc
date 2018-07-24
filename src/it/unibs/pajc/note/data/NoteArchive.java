package it.unibs.pajc.note.data;

import java.util.List;

import it.unibs.pajc.note.model.Note;

public class NoteArchive extends Archive<Note> {


	public NoteArchive() {
		super();
	}

	/**
	 * Una nota è valida se il titolo non è vuoto.
	 */
	@Override
	protected boolean validation(Note n) {
		if (n.getTitle().isEmpty())
			return false;
		return true;
	}
	
	
	public List<Note> getNotes() {
		return elements;
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
