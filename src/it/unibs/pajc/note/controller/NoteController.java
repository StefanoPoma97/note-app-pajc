package it.unibs.pajc.note.controller;

import java.util.ArrayList;
import java.util.stream.Collectors;

import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.Tag;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;

public class NoteController extends Controller<Note>{
	
	private NoteArchive noteArchive= new NoteArchive();
	private ArrayList<Note> notes= new ArrayList<>();
	
	
	public NoteController(){
		User u= new User("paolo", "merazza");
		User u1= new User("utente1", "pass1");
		u1.addTag(new Tag("Riunione"));
		u1.addTag(new Tag("Memo"));
		u1.addTag(new Tag("Memo2"));
		u1.addTag(new Tag("Memo3"));
		u.addTag(new Tag("Riunione"));
		u.addTag(new Tag("Memo"));
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
			if (i==2){
				nota.addLabel("Memo2");
			}
			
			if (i==3){
				nota.addLabel("Memo3");
			}
			
			
				
			noteArchive.add(nota);
		}
		
		for (int i=0; i<5; i++){
			Note nota = new Note("titolo copia"+i);
			nota.setBody("corpo della nota copia numero: "+i);
			nota.setAutor(u1);
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
	
public ArrayList<String> getLabelsByNote(String title, User us){
	System.out.println("GET labes by note");
	ArrayList<Note> out= (ArrayList<Note>)noteArchive.getWhere(x->x.getAuthor().equals(us));
	ArrayList<Note> out2= (ArrayList<Note>)out.stream().filter(x->x.getTitle().equals(title)).collect(Collectors.toList());
	return out2.get(0).getLabel();
	}

	
	public ArrayList<Note> getNotesByLabel(String label, User us){
		System.out.println("GET note by labels");
		ArrayList<Note> out= (ArrayList<Note>)noteArchive.getWhere(x->x.getAuthor().equals(us));
		System.out.println(out);
		ArrayList<Note> out2= (ArrayList<Note>)out.stream().filter(x->x.getLabel().contains(label)).collect(Collectors.toList());
		System.out.println(out2);
		return out2;
		
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
