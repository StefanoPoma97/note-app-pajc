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
	
	private Client client=null;
	
	
	public NoteController(){

	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata per richiedere le note
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param us
	 * @return ArrayList contenente le note
	 * @author Stefano Poma
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
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata per richiedere le Labels associate a una data nota
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param title
	 * @param us
	 * @return ArrayList contenetne le Label
	 * @author Stefano Poma
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
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata
	 * per richiedere le note associate ad una specifica Label
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param label
	 * @param us
	 * @return ArrayList contenente le note
	 * @author Stefano Poma
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
 * sfruttando la comunicazione con il client crea una classe Comunication indicata per aggiungere una nuova nota
 * ottiene una classe Comunication con il riusultato
 * @param _client
 * @param note
 * @return ValidationError che indica se è avvenuta l'operazione
 * @author Stefano Poma
 */
	public ValidationError addNote(Client _client, Note note){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("add_note");
		input.setNote(note);
		
		Comunication output= client.comunica(input);
		return output.getCreateResult();
		
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata per aggiornare una nota
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param note
	 * @param id
	 * @return ValidationError che indica se è avvenuta l'operazione
	 * @author Stefano Poma
	 */
	public ValidationError update (Client _client, Note note, int id){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("update");
		input.setNote(note);
		input.setID(id);
		
		Comunication output= client.comunica(input);
		return output.getCreateResult();
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere l'ID della nota sulla base del titolo
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param title
	 * @return l'ID associato a quel titolo
	 * @author Stefano Poma
	 */
	public int getIDbyTitle(Client _client, String title){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("get_id_by_title");
		input.setTitle(title);
		
		Comunication output= client.comunica(input);
		return output.getID();
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere la creazione di una nuova nota
	 * ottiene una classe Comunication con il riusultato
	 * @author Stefano Poma
	 * @param title
	 * @return ValidationError che indica se è avvenuta l'operazione
	 */
	public ValidationError create(String title) {
//		System.out.println("info arrivate: titolo= " + title);
		Note n = new Note(title);
		return archive.add(n);
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata per richiedere se una nota e' segnata o no
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param titolo
	 * @param utente
	 * @return true se è segnata, false se non è segnata
	 * @author Stefano Poma
	 */
	public Boolean isPinned(Client _client, String titolo, User utente){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("is_pinned");
		input.setTitle(titolo);
		input.setUser(utente);
		
		Comunication output= client.comunica(input);
		return output.getBoolean();
		
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata per richiedere se una nota e' pubblica o no
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param titolo
	 * @param utente
	 * @return treu se è pubblica, false se non lo è
	 * @author Stefano Poma
	 */
	public Boolean isPublic(Client _client, String titolo, User utente){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("is_public");
		input.setTitle(titolo);
		input.setUser(utente);
		
		Comunication output= client.comunica(input);
		return output.getBoolean();
		
	}
	
	/**
	 * * sfruttando la comunicazione con il client crea una classe Comunication indicata per richiedere un filtro tramite titolo
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param u
	 * @return ArrayList con le note
	 * @author Stefano Poma
	 */
	public ArrayList<Note> FilterByTitle(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("filter_by_title");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere un filtro tramite titolo sulla sezione esplora
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param u
	 * @return ArrayList con le note
	 * @author Stefano Poma
	 */
	public ArrayList<Note> exFilterByTitle (Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("ex_filter_by_title");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
		
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere un filtro tramite Pin
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param u
	 * @return ArrayList con le note
	 * @author Stefano Poma
	 */
	public ArrayList<Note> FilterByPin(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("filter_by_pin");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere un filtro tramite like
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param u
	 * @return ArrayList con le note
	 * @author Stefano Poma
	 */
	public ArrayList<Note> FilterByLike(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("filter_by_like");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere un filtro tramite like sulla sezione esplora
	 * ottiene una classe Comunication con il riusultato 
	 * @param _client
	 * @param u
	 * @return ArrayList con le note
	 * @author Stefano Poma
	 */
	public ArrayList<Note> exFilterByLike(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("ex_filter_by_like");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere un filtro sulla data
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param u
	 * @return ArrayList con le note
	 * @author Stefano Poma
	 */
	public ArrayList<Note> FilterByData(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("filter_by_data");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere un filtro sulla data nella sezione esplora
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param u
	 * @return ArrayList con le note
	 * @author Stefano Poma
	 */
	public ArrayList<Note> exFilterByData(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("ex_filter_by_data");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere un filtro tramite autore 
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param u
	 * @return ArrayList con le note
	 * @author Stefano Poma
	 */
	public ArrayList<Note> exFilterByAuthor(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("ex_filter_by_author");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere gli utenti condivisi di una nota
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param titolo
	 * @param u
	 * @return Set con gli utenti
	 * @author Stefano Poma
	 */
	public Set<User> getSharredUser (Client _client, String titolo, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("get_shared_user");
		input.setUser(u);
		input.setTitle(titolo);
		
		Comunication output= client.comunica(input);
		return output.getUsersSet();
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere tutte le note presenti
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param u
	 * @return ArrayList con le note
	 * @author Stefano Poma
	 */
	public ArrayList<Note> getAllNote(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("get_all_note");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
//		System.out.println("CONTROLLER: "+output.getNotes());
		return output.getNotes();
		
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere le note condivise con l'utente selezionato
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param u
	 * @return ArrayList con le note
	 * @author Stefano Poma
	 */
	public ArrayList<Note> shareWithMe(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("share_with_me");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
		
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere una nota comunicando il suo titolo
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param n
	 * @param u
	 * @return Nota con quel titolo
	 * @author Stefano Poma
	 */
	public Note getNoteByTitle(Client _client, String n, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("get_note_by_title");
		input.setUser(u);
		input.setTitle(n);
		
		Comunication output= client.comunica(input);
		return output.getNote();
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere una nota tramite il suo titolo al fine di conoscere quanti like ha
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param n
	 * @param u
	 * @return Nota con quel titolo
	 * @author Stefano Poma
	 */
	public Note getNoteByTitleLike(Client _client, String n, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("get_note_by_title_like");
		input.setUser(u);
		input.setTitle(n);
		
		Comunication output= client.comunica(input);
		return output.getNote();
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere una nota tramite il suo ID
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param ID
	 * @return nota con quel ID
	 * @author Stefano Poma
	 */
	public Note getNotebyID(Client _client, int ID){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("get_note_by_id");
		input.setID(ID);
		
		Comunication output= client.comunica(input);
		return output.getNote();
	}
	
	/**
	 * elimina una nota dato il suo ID
	 * @param _client
	 * @param ID
	 * @return true se è stata eliminata con successo
	 */
	public boolean deleteNote(Client _client, int ID){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("delete_note");
		input.setID(ID);
		
		Comunication output= client.comunica(input);
		return output.getBoolean();
	}
	
	public Comunication modifyID(Client _client, int ID, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("modify_id");
		input.setID(ID);
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output;
	}
	
	public boolean stopModify(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("stop_modify");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getBoolean();
	}
	
	public void iRefresh(Client _client, User u){
			client=_client;
			Comunication input= new Comunication();
			input.setInfo("i_refresh");
			input.setUser(u);
			
			client.comunica(input);
			
	}
	
	
	
}
