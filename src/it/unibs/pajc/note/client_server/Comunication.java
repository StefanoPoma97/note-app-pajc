package it.unibs.pajc.note.client_server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.data.UserArchive;
import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;

public class Comunication implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Stringhe per la comunicazione
	private static final String LOGIN= "login";
	private static final String CREATE_ACCOUNT= "create_account";
	private static final String LOAD_NOTE= "load_notes";
	
	
	
	//dati utili
	private String info;
	private String error;
	private String title;
	private User utente= null;
	private ArrayList<String> name_pass= null;
	private ArrayList<Note> notes= null;
	private ArrayList<User> users= null;
	private ArrayList<String> labels= null;
	private Set<User> usersSet=null;
	private boolean login;
	private ValidationError create;
	private Note note=null;
	private int ID;
	private boolean bool;
	
	//Info message
	public String getError(){
		return info;
	}
	
	public void setError(String in){
		error=in;
	}
	
	public String getInfo(){
		return info;
	}
	
	public void setInfo(String in){
		info=in;
	}
	
	//Login
	public void setLogin(String name, String pass){
		name_pass= new ArrayList<>();
		name_pass.add(name);
		name_pass.add(pass);
	}
	
//	public ArrayList<String> getLogin(){
//		return name_pass;
//	}
	
	public void setLoginResult(boolean er){
		login=er;
	}
	
	public boolean getLoginResult(){
		return login;
	}
	
	public void setCreate(ValidationError er){
		create=er;
	}
	
	public ValidationError getCreateResult(){
		return create;
	}
	
	//MyNotes
	public void setUser(User u){
		utente=u;
	}
	
	public void setNotes(ArrayList<Note> list){
		notes=list;
	}
	
	public ArrayList<Note> getNotes(){
		return notes;
	}
	public void setUsers(ArrayList<User> list){
		users=list;
	}
	
	public ArrayList<User> getUsers(){
		return users;
	}
	
	public void setUsersSet(Set<User> list){
		usersSet=list;
	}
	
	public Set<User> getUsersSet(){
		return usersSet;
	}
	
	//my Labels
	public void setLabels(ArrayList<String> list){
		labels=list;
	}
	
	public ArrayList<String> getLabels(){
		return labels;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String in){
		title=in;
	}
	
	//add note
	public void setNote(Note n){
		note=n;
	}
	
	public Note getNote (){
		return note;
	}
	
	//update note
	public void setID (int i){
		ID=i;
	}
	
	public int getID (){
		return ID;
	}
	
	public boolean getBoolean(){
		return bool;
	}
	
	public void setBoolean(boolean b){
		bool=b;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Comunication createResponse(NoteArchive noteArchive, UserArchive userArchive){
		Comunication output= new Comunication();
		switch (info) {
		case "login":{
			String name= name_pass.get(0);
			String pass= name_pass.get(1);
			output= new Comunication();
			output.setLoginResult(userArchive.authenticate(name, pass));
			output.setInfo("login_response");
			return output;
		}
		
		case "create":{
			String name= name_pass.get(0);
			String pass= name_pass.get(1);
			output= new Comunication();
			output.setCreate(userArchive.add(new User(name, pass)));
			output.setInfo("login_response");
			return output;
		}
		
		case "load_notes":{
			ArrayList<Note>notes= (ArrayList<Note>)noteArchive.getWhere(x->x.getAuthor().equals(utente));
			System.out.println("ECCO l'elenco delle Note: "+notes);
			output= new Comunication();
			output.setNotes(notes);
			output.setInfo("load_notes_response");
			return output;
			
		}
		
		case "load_labels":{
			ArrayList<String>labels= (ArrayList<String>)userArchive.getlabelsByUser(utente);
			output= new Comunication();
			output.setLabels(labels);
			output.setInfo("load_labels_response");
			return output;
			
		}
		
		case "get_labels_by_note":{
			ArrayList<String>labels= (ArrayList<String>)noteArchive.getLabelsByNote(title, utente);
			output= new Comunication();
			output.setLabels(labels);
			output.setInfo("load_labels_by_note_response");
			return output;
			
		}
		
		case "update_label":{
			userArchive.updateLabel(labels, utente);
			output= new Comunication();
			output.setInfo("update_labels_response");
			return output;
			
		}
		
		case "add_label":{
			userArchive.addLabel(title, utente);
			output= new Comunication();
			output.setInfo("add_label_response");
			return output;
			
		}

		
		case "get_notes_by_label":{
			output= new Comunication();
			output.setInfo("get_notes_by_label_response");
			output.setNotes(noteArchive.getNotesByLabel(title, utente));
			return output;
		}
		
		case "add_note":{
			output= new Comunication();
			output.setInfo("add_note_response");
			output.setCreate(noteArchive.add(note));
			return output;
		}
		
		case "update":{
			output= new Comunication();
			output.setInfo("update_response");
			output.setCreate(noteArchive.update(note, ID));
			return output;
		}
		
		case "get_id_by_title":{
			output= new Comunication();
			output.setInfo("get_id_by_title_response");
			output.setID(noteArchive.getIDbyTitle(title));
			return output;
		}
		
		case "is_pinned":{
			output= new Comunication();
			output.setInfo("is_pinned_response");
			output.setBoolean(noteArchive.isPinned(title, utente));
			return output;
		}
		case "is_public":{
			output= new Comunication();
			output.setInfo("is_public_response");
			output.setBoolean(noteArchive.isPublic(title, utente));
			return output;
		}
		
		case "filter_by_title":{
			output= new Comunication();
			output.setInfo("filter_by_title_response");
			output.setNotes(noteArchive.FilterByTitle(utente));
			return output;
		}
		case "filter_by_pin":{
			output= new Comunication();
			output.setInfo("filter_by_pin_response");
			output.setNotes(noteArchive.FilterByPin(utente));
			return output;
		}
		case "filter_by_like":{
			output= new Comunication();
			output.setInfo("filter_by_like_response");
			output.setNotes(noteArchive.FilterByLike(utente));
			return output;
		}
		case "filter_by_data":{
			output= new Comunication();
			output.setInfo("filter_by_data_response");
			output.setNotes(noteArchive.FilterByData(utente));
			return output;
		}
		case "get_all_user":{
			output= new Comunication();
			output.setInfo("get_all_user_response");
			output.setUsers(userArchive.getAllUsers(utente));
			return output;
		}
		
		case "get_shared_user":{
			output= new Comunication();
			output.setInfo("get_shared_user_response");
			output.setUsersSet(noteArchive.getSharredUser(title, utente));
			return output;
		}
		
		
		default:
			return output;
			
		}
	}
}
