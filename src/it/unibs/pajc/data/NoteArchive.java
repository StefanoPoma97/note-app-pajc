package it.unibs.pajc.data;

import java.util.ArrayList;
import java.util.stream.Collectors;

import it.unibs.pajc.model.Note;
import it.unibs.pajc.model.User;

public class NoteArchive extends Archive{

	private ArrayList<Note> notes;
	private int id =1;

	public ArrayList<Note> getNotes() {
		return notes;
	}
	
	public void addNote (Note newNote){
		createId(newNote);
		notes.add(newNote);
		
	}
	
	public ArrayList<Note> getUserNote (User us){
		ArrayList<Note> out= (ArrayList<Note>) notes.stream()
				.filter(n -> n.getAuthor().equalsIgnoreCase(us.getName()))
				.collect(Collectors.toList());
		return out;
	}
	
	public void updateNote (Note note, int index){
		int newID = notes.get(index).getId();
		notes.remove(index);
		note.setId(newID);
		notes.add(note);
	}
	
	private void createId(Note note){
		note.setId(id);
		id++;
		
	}
}

