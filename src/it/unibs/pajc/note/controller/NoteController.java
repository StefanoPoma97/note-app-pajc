package it.unibs.pajc.note.controller;

import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.status.ValidationError;

public class NoteController extends Controller<Note>{
	
	public ValidationError create(String title) {
		System.out.println("info arrivate: titolo= " + title);
		Note n = new Note(title);
		return archive.add(n);
	}
}
