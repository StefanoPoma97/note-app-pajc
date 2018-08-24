package it.unibs.pajc.note.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibs.pajc.note.client_server.Client;
import it.unibs.pajc.note.client_server.Comunication;
import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.Tag;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;

public class NoteController extends Controller<Note>{
	
	private NoteArchive noteArchive= NoteArchive.getIstance();
	private ArrayList<Note> notes= new ArrayList<>();
	private Client client=null;
	
	
	public NoteController(){

	}
	
	/**
	 * restituisce le note di un dato autore
	 * @param us
	 * @return
	 */
	public ArrayList<Note> getMyNote(Client _client, User us){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("load_notes");
		input.setUser(us);
		Comunication output= new Comunication();
		output= client.comunica(input);
		return output.getNotes();
		
	}
	
	/**
	 * restituisce tutte le label associate ad una determinata nota e autore
	 * @param title
	 * @param us
	 * @return
	 */
public ArrayList<String> getLabelsByNote(Client _client, String title, User us){
	client=_client;
	Comunication input= new Comunication();
	input.setInfo("get_labels_by_note");
	input.setTitle(title);
	input.setUser(us);
	
	Comunication output= client.comunica(input);
	return output.getLabels();
	}

	/**
	 * restituisce tutte le note associate ad una detrminata label e autore
	 * @param label
	 * @param us
	 * @return
	 */
	public ArrayList<Note> getNotesByLabel(Client _client, String label, User us){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("get_notes_by_label");
		input.setTitle(label);
		input.setUser(us);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
		
	}
	
	/**
	 * aggiunge una nuova nota
	 * @param note
	 * @return
	 */
	public ValidationError addNote(Client _client, Note note){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("add_note");
		input.setNote(note);
		
		Comunication output= client.comunica(input);
		return output.getCreateResult();
		
	}
	
	public ValidationError update (Client _client, Note note, int id){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("update");
		input.setNote(note);
		input.setID(id);
		
		Comunication output= client.comunica(input);
		return output.getCreateResult();
	}
	
	
	public int getIDbyTitle(Client _client, String title){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("get_id_by_title");
		input.setTitle(title);
		
		Comunication output= client.comunica(input);
		return output.getID();
	}
	
	public ValidationError create(String title) {
		System.out.println("info arrivate: titolo= " + title);
		Note n = new Note(title);
		return archive.add(n);
	}
	
	public Boolean isPinned(Client _client, String titolo, User utente){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("is_pinned");
		input.setTitle(titolo);
		input.setUser(utente);
		
		Comunication output= client.comunica(input);
		return output.getBoolean();
		
	}
	
	public Boolean isPublic(Client _client, String titolo, User utente){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("is_public");
		input.setTitle(titolo);
		input.setUser(utente);
		
		Comunication output= client.comunica(input);
		return output.getBoolean();
		
	}
	
	public ArrayList<Note> FilterByTitle(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("filter_by_title");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
	}
	
	public ArrayList<Note> exFilterByTitle (Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("ex_filter_by_title");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
		
	}
	
	public ArrayList<Note> FilterByPin(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("filter_by_pin");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
	}
	
	public ArrayList<Note> FilterByLike(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("filter_by_like");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
	}
	
	public ArrayList<Note> exFilterByLike(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("ex_filter_by_like");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
	}
	
	public ArrayList<Note> FilterByData(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("filter_by_data");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
	}
	
	public ArrayList<Note> exFilterByData(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("ex_filter_by_data");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
	}
	
	public ArrayList<Note> exFilterByAuthor(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("ex_filter_by_author");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
	}
	
	public Set<User> getSharredUser (Client _client, String titolo, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("get_shared_user");
		input.setUser(u);
		input.setTitle(titolo);
		
		Comunication output= client.comunica(input);
		return output.getUsersSet();
	}
	
	public ArrayList<Note> getAllNote(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("get_all_note");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
		
	}
	
	public ArrayList<Note> shareWithMe(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("share_with_me");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
		
	}
	
//	public boolean isShare(Note n, User u){
//		return noteArchive.isShare(n,u);
//	}
	
	public Note getNoteByTitle(Client _client, String n, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("get_note_by_title");
		input.setUser(u);
		input.setTitle(n);
		
		Comunication output= client.comunica(input);
		return output.getNote();
	}
	
	public Note getNoteByTitleLike(Client _client, String n, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("get_note_by_title_like");
		input.setUser(u);
		input.setTitle(n);
		
		Comunication output= client.comunica(input);
		return output.getNote();
	}
	
	
	
}
