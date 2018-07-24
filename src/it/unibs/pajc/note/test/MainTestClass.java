package it.unibs.pajc.note.test;

import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.model.Note;

public class MainTestClass {

	public static void main(String[] args) {
		NoteArchive notes = new NoteArchive();
		Note n = new Note("Hello");
		Note n1 = new Note("Hello");
		Note n2 = new Note("Hello");

		notes.add(n);
		notes.add(n1);
		notes.add(n2);
	
		notes.getNotes().forEach(x -> System.out.println(x.getID()));
	}

}
