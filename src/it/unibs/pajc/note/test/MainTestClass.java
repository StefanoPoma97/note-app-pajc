package it.unibs.pajc.note.test;

import java.util.GregorianCalendar;
import java.util.function.Predicate;

import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.model.Note;

public class MainTestClass {

	public static void main(String[] args) {
		NoteArchive notes = new NoteArchive();
		Note n = new Note("Hello2");
		Note n1 = new Note("Hello3");
		Note n2 = new Note("Hello21");

		notes.add(n);
		notes.add(n1);
		notes.add(n2);

		
		Predicate<Note> title = x ->  x.getTitle().contains("2");
		Predicate<Note> time = x -> x.getCreatedAt().before(new GregorianCalendar(2002,1,1));
		notes.getWhere(title.and(time)).forEach(System.out::println);

	}
}
