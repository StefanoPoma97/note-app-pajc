package it.unibs.pajc.note.data;

import java.util.ArrayList;

import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.User;
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
	
	/**
	 * metodo per restituire le labels associate ad un determinato utente
	 * @param us
	 * @return
	 */
	public ArrayList<String> getLabels(User us){
		ArrayList<String> labels= new ArrayList<>();
		for (Note n: (ArrayList<Note>)getWhere(x->x.getAuthor().equals(us))){
			labels.addAll(n.getLabels());
		}
		return labels;
	}
}
