package it.unibs.pajc.note.data;

import java.util.ArrayList;
import java.util.stream.Collectors;

import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.Tag;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;

public class NoteArchive extends Archive<Note> {

	public NoteArchive() {
		super();
	}

	/**
	 * Una nota Ã¨ valida se il titolo non Ã¨ vuoto.
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
	 * dato titolo e autore restituisce true
	 * se la nota è segnata
	 * @param titolo
	 * @param utente
	 * @return
	 */
	public Boolean isPinned (String titolo, User utente){
		ArrayList<Note> notes= (ArrayList<Note>) getWhere(x->x.getAuthor().equals(utente));
		return notes.stream()
			.filter(x->x.getTitle().equals(titolo))
			.collect(Collectors.toList())
			.get(0).getPin();
			
	}
	
	/**
	 * dato titolo e autore restituisce true
	 * se la nota è pubblica
	 * @param titolo
	 * @param utente
	 * @return
	 */
	public Boolean isPublic (String titolo, User utente){
		ArrayList<Note> notes= (ArrayList<Note>) getWhere(x->x.getAuthor().equals(utente));
		return notes.stream()
			.filter(x->x.getTitle().equals(titolo))
			.collect(Collectors.toList())
			.get(0).isPublic();
			
	}
	
	

}
