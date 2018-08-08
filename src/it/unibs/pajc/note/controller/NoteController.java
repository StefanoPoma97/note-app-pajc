package it.unibs.pajc.note.controller;

import java.util.ArrayList;

import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;

public class NoteController extends Controller<Note>{
	
	private NoteArchive noteArchive= new NoteArchive();
	private ArrayList<Note> notes= new ArrayList<>();
	private ArrayList<String> labels= new ArrayList<>();
	
	public NoteController(){
		User u= new User("paolo", "merazza");
		//costruttore solo per test, in realtà si appoggia alla classe client che poi gli da informazioni su archivio note
		for (int i=0; i<5; i++){
			Note nota = new Note("titolo"+i);
			nota.setBody("corpo della nota numero: "+i);
			nota.setAutor(u);
			if(i==0){
				nota.addLabel("Riunione");
			}
			if (i==1){
				nota.addLabel("Memo");
			}
				
			noteArchive.add(nota);
		}
	}
	
	public ArrayList<Note> getMyNote(User us){
		System.out.println("sto cercando lista con utente"+us);
		ArrayList<Note> notes=(ArrayList<Note>)noteArchive.getWhere(x->x.getAuthor().equals(us));
		if (notes.isEmpty())
			System.out.println("mie note vuoto");
		else
			System.out.println("trovate mie note" + notes.get(0).getTitle());
		
		return notes;
	}
	
	public ArrayList<String> getMyLabel(User us){
		return noteArchive.getLabels(us);
	}

	
	public ArrayList<Note> getNotesByLabel(String label){
		return (ArrayList<Note>)noteArchive.getWhere(x-> x.getLabels().contains(label));
	}
	
	/**
	 * aggiunge una nuova nota
	 * @param note
	 * @return
	 */
	public ValidationError addNote(Note note){
		return noteArchive.add(note);
		
	}
	
	public ValidationError update (Note note, int id){
		return noteArchive.update(note, id);
	}
	
	
	public int getIDbyTitle(String title){
		return noteArchive.getWhere(x->x.getTitle().equals(title)).get(0).getID();
	}
	
	public ValidationError create(String title) {
		System.out.println("info arrivate: titolo= " + title);
		Note n = new Note(title);
		return archive.add(n);
	}
}
