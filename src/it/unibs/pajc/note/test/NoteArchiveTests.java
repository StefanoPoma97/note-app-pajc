package it.unibs.pajc.note.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Predicate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.User;

class NoteArchiveTests {
	
	NoteArchive notes;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Prima di ogni test, ricreo l'archivio
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		notes = new NoteArchive();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void addTest() {

		notes.add(new Note("Hello"));
		notes.add(new Note("Hello1"));
		notes.add(new Note(""));
		
		assertEquals(2,notes.getNotes().size());
	}
	
	@Test
	void searchTitleTest() {
		notes.add(new Note("Hello2"));
		notes.add(new Note("Hello1"));
		notes.add(new Note("Hello21"));
		
		Predicate<Note> title = x ->  x.getTitle().contains("2");

		
		assertEquals(2,notes.getWhere(title).size());
	}
	
	@Test
	void idAutoIncrementTest() {
		notes.add(new Note("Hello"));
		notes.add(new Note("Hello1"));
		notes.add(new Note(""));
		
		int[] ids = notes.getNotes().stream().mapToInt(x -> x.getID()).toArray();
		assertArrayEquals(new int[]{0,1} , ids);

	}
	
	
	@Test
	void removeTest() {
		notes.add(new Note("Hello"));
		notes.add(new Note("Hello1"));
		notes.add(new Note("Hello2"));
		
		Predicate<Note> title = x ->  x.getTitle().contains("2");
		
		notes.remove(title);
		assertEquals(2,notes.getNotes().size());
	}

	@Test
	void validateTest() {
		notes.add(new Note(""));
		assertEquals(notes.getNotes().size(), 0);
	}
	
	
	
	

}
