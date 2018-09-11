package it.unibs.pajc.note.client_server;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import it.unibs.pajc.note.data.Database;
import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.data.UserArchive;
import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;
import it.unibs.pajc.note.utility.ServizioFile;

public class Comunication implements Serializable{

	//per poter essere messa negli stream e' importante che implementi Serializable
	private static final long serialVersionUID = 1L;
	
	
	
	
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
	/**
	 * ritorna messaggio di errore
	 * @return
	 */
	public String getError(){
		return info;
	}
	
	/**
	 * setta il messaggio di errore
	 * @param in
	 * @author Stefano Poma
	 */
	public void setError(String in){
		error=in;
	}
	
	/**
	 * ritorna la striga info, utile per capire che cosa stiamo comunicando
	 * @return stringa info
	 * @author Stefano Poma
	 */
	public String getInfo(){
		return info;
	}
	
	/**
	 * setta la stringa info
	 * @param in
	 * @author Stefano Poma
	 */
	public void setInfo(String in){
		info=in;
	}
	
	//LOGIN
	/**
	 * imposta tutte le variabili per il LogIn
	 * @param name
	 * @param pass
	 * @author Stefano Poma
	 */
	public void setLogin(String name, String pass){
		name_pass= new ArrayList<>();
		name_pass.add(name);
		name_pass.add(pass);
	}
	
	/**
	 * imposta il valore di ritorno per il login (boolean)
	 * @param er
	 * @author Stefano Poma
	 */
	public void setLoginResult(boolean er){
		login=er;
	}
	
	/**
	 * restituisce il risultato in seguito al logn
	 * @return boolean
	 * @author Stefano Poma
	 */
	public boolean getLoginResult(){
		return login;
	}
	
	/**
	 * imposta il valore di ValidationError in seguito ad un operazione che richiede esso come risultato
	 * @param er
	 * @author Stefano Poma
	 */
	public void setCreate(ValidationError er){
		create=er;
	}
	
	/**
	 * restituisce il valore di ValidationError 
	 * @return ValidationError
	 * @author Stefano Poma
	 */
	public ValidationError getCreateResult(){
		return create;
	}
	
	//MyNotes
	
	/**
	 * imposta l'utente selezionato
	 * @param u
	 * @author Stefano Poma
	 */
	public void setUser(User u){
		utente=u;
	}
	
	/**
	 * imposta le note
	 * @param list
	 * @author Stefano Poma
	 */
	public void setNotes(ArrayList<Note> list){
		notes=list;
	}
	
	/**
	 * retituisce un arrayList di note a seconda di cosa stavamo cercando
	 * @return ArrayList contenente le note
	 * @author Stefano Poma
	 */
	public ArrayList<Note> getNotes(){
		return notes;
	}
	
	/**
	 * imposta un ArrayList di utenti
	 * @param list
	 * @author Stefano Poma
	 */
	public void setUsers(ArrayList<User> list){
		users=list;
	}
	
	/**
	 * restituisce un ArrayList di utenti
	 * @return ArrayList contenente gli utenti
	 * @author Stefano Poma
	 */
	public ArrayList<User> getUsers(){
		return users;
	}
	
	/**
	 * imposta un Set di utenti
	 * @param list
	 * @author Stefano Poma
	 */
	public void setUsersSet(Set<User> list){
		usersSet=list;
	}
	
	/**
	 * restituisce un Set di utenti
	 * @return Set contenente gli utenti
	 * @author Stefano Poma
	 */
	public Set<User> getUsersSet(){
		return usersSet;
	}
	
	//my Labels
	/**
	 * imposta un ArrayList di Label
	 * @param list
	 * @author Stefano Poma
	 */
	public void setLabels(ArrayList<String> list){
		labels=list;
	}
	
	/**
	 * restituisce un arrayList di Label
	 * @return ArrayList contenente le Label
	 * @author Stefano Poma
	 */
	public ArrayList<String> getLabels(){
		return labels;
	}
	
	/**
	 * restituisce il titolo
	 * @return titolo
	 * @author Stefano Poma
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * imposta il titolo
	 * @param in
	 * @author Stefano Poma
	 */
	public void setTitle(String in){
		title=in;
	}
	
	//add note
	/**
	 * imposta una Note
	 * @param n
	 * @author Stefano Poma
	 */
	public void setNote(Note n){
		note=n;
	}
	
	/**
	 * restituisce la Note impostata precedentemente
	 * @return Note
	 * @author Stefano Poma
	 */
	public Note getNote (){
		return note;
	}
	
	//update note
	/**
	 * imposta un indirizzo ID
	 * @param i
	 * @author Stefano Poma
	 */
	public void setID (int i){
		ID=i;
	}
	
	/**
	 * restituisce l'indirizzo ID impostato precedentemente
	 * @return ID
	 * @author Stefano Poma
	 */
	public int getID (){
		return ID;
	}
	
	/**
	 * imposta una variabile booleana
	 * @return variabile booleana
	 * @author Stefano Poma
	 */
	public boolean getBoolean(){
		return bool;
	}
	
	/**
	 * restituisce la variabile booleana
	 * @param b
	 * @author Stefano Poma
	 */
	public void setBoolean(boolean b){
		bool=b;
	}
	
	public String toString() {
		return info;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * dati il NoteArchive e UserArchive aggiurnati questo metodo crea una risposta per ogni istanza di COmunication
	 * basandosi sulla stringa info capisce che tipo di informazione viene cercata, la crea, la inserisce in una nuova
	 * istanza di Comunication e la restituisce
	 * @param noteArchive
	 * @param userArchive
	 * @return classe Comunication con all'interno la risposta cercata
	 */
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
			output= new Comunication();
			userArchive.updateLabel(labels, utente);
			output.setInfo("update_labels_response");
			return output;
			
		}
		
		case "add_label":{
			
			output= new Comunication();
			output.setBoolean(userArchive.addLabel(title, utente));
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
		case "get_all_note":{
			output= new Comunication();
			output.setInfo("get_all_note_response");
			output.setNotes(noteArchive.getAllNote(utente));
			return output;
		}
		
		case "ex_filter_by_title":{
			output= new Comunication();
			output.setInfo("ex_filter_by_title_response");
			output.setNotes(noteArchive.exFilterByTitle(utente));
			return output;
		}
		case "ex_filter_by_author":{
			output= new Comunication();
			output.setInfo("ex_filter_by_author_response");
			output.setNotes(noteArchive.exFilterByAuthor(utente));
			return output;
		}
		case "ex_filter_by_data":{
			output= new Comunication();
			output.setInfo("ex_filter_by_data_response");
			output.setNotes(noteArchive.exFilterByData(utente));
			return output;
		}
		case "ex_filter_by_like":{
			output= new Comunication();
			output.setInfo("ex_filter_by_like_response");
			output.setNotes(noteArchive.exFilterByLike(utente));
			return output;
		}
		
		case "share_with_me":{
			output= new Comunication();
			output.setInfo("share_with_me_response");
			output.setNotes(noteArchive.shareWithMe(utente));
			return output;
		}
		
		case "get_note_by_title":{
			output= new Comunication();
			output.setInfo("get_note_by_title_response");
			output.setNote(noteArchive.getNoteByTitle(title, utente));
			return output;
		}
		case "get_note_by_title_like":{
			output= new Comunication();
			output.setInfo("get_note_by_title_like_response");
			output.setNote(noteArchive.getNoteByTitleLike(title, utente));
			return output;
		}
		
		case "get_note_by_id":{
			output= new Comunication();
			output.setInfo("get_note_by_id_response");
			output.setNote(noteArchive.getNoteByID(ID));
			return output;
		}
		
		case "save_on_file":{
			output= new Comunication();
			output.setInfo("save_on_file_response");
			File file = new File("save.dat");
			System.out.println("ELENCO UTENTI:");
			System.out.println(UserArchive.getIstance().all());
			Database data = new Database(NoteArchive.getIstance(), UserArchive.getIstance());
			ServizioFile.salvaSingoloOggetto(file, data);
			System.out.println("salvato su file");
			return output;
		}
		
		case "delete_note":{
			output= new Comunication();
			output.setInfo("delete_note_response");
			output.setBoolean(noteArchive.remove(x->x.getID()==ID));
			return output;
		}
		
		case "stop_modify":{
			output= new Comunication();
			output.setInfo("stop_modify_response");
			Syncro sn= Syncro.getIstance();
			sn.stopModify(utente);
			return output;
		}
		
		case "modify_id":{
			output= new Comunication();
			output.setInfo("modify_id_response");
			Syncro sn= Syncro.getIstance();
			output=sn.modify(utente, ID);
			return output;
		}
		
		case "i_refresh":{
			output= new Comunication();
			output.setInfo("i_refresh_response");
			Syncro sn= Syncro.getIstance();
			sn.iRefresh(utente);
			return output;
		}
		
		
		
		default:
			return output;
			
		}
	}
}
