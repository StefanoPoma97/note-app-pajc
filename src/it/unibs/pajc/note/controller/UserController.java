package it.unibs.pajc.note.controller;

import java.util.ArrayList;
import java.util.Set;

import it.unibs.pajc.note.client_server.Client;
import it.unibs.pajc.note.client_server.Comunication;
import it.unibs.pajc.note.data.UserArchive;
import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.Tag;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;
public class UserController extends Controller<User> {

	UserArchive userArchive = UserArchive.getIstance();
	Client client= null;
	/**
	 * costruttore
	 */
	public UserController() {
	}

	/**
	 * metodo per permettere la connessione del client
	 * @author Stefano Poma
	 * @param _client
	 * @return Stringa che indica se è avvenuta la connessione
	 */
	public String connetti(Client _client){
		client=_client;
		return client.connetti();
	}
	
	
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere il login
	 * ottiene una classe Comunication con il riusultato
	 * @param _client
	 * @param _name
	 * @param _pass
	 * @return classe comunication che indica il risultato del login
	 */
	public Comunication login(Client _client, String _name, String _pass) {
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("login");
		input.setLogin(_name, _pass);
		Comunication output = client.comunica(input);
			
		return output;
	}

	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere la creazione di un nuovo utente
	 * ottiene una classe Comunication con il riusultato
	 * @author Stefano Poma
	 * @param _client
	 * @param _name
	 * @param _pass
	 * @return ValidationError che indica il risultato dell'operazione
	 
	 */
	public ValidationError create(Client _client, String _name, String _pass) {
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("create");
		input.setLogin(_name, _pass);
		Comunication output = client.comunica(input);
		ValidationError validate= output.getCreateResult();
		return validate;
	}

	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere tutte le labels associate ad un utente
	 * ottiene una classe Comunication con il riusultato
	 * @author Stefano Poma
	 * @param _client
	 * @param u
	 * @return ArrayList di Label
	 */
	public ArrayList<String> getLabelsByUser (Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("load_labels");
		input.setUser(u);


		Comunication output= new Comunication();
		output= client.comunica(input);
		return output.getLabels();
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere l'aggiunta di una nuova Label
	 * ottiene una classe Comunication con il riusultato
	 * @author Stefano Poma
	 * @param _client
	 * @param label
	 * @param us
	 * @return true se l'operazione è avvenuta
	 */
	public boolean addLabel (Client _client, String label, User us){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("add_label");
		input.setTitle(label);
		input.setUser(us);
		
		Comunication out=client.comunica(input);
		return out.getBoolean();
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere l'aggiornamento delle labels
	 * ottiene una classe Comunication con il riusultato
	 * @author Stefano Poma
	 * @param _client
	 * @param str
	 * @param us
	 */
	public void updateLabel(Client _client, ArrayList<String> str, User us){
		//TODO rendere piï¿½ efficiente
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("update_label");
		input.setUser(us);
		input.setLabels(str);
		Comunication output2= client.comunica(input);
	}
	
	/**
	 * sfruttando la comunicazione con il client crea una classe Comunication indicata 
	 * per richiedere di restituire tutti gli utenti
	 * ottiene una classe Comunication con il riusultato
	 * @author Stefano Poma
	 * @param _client
	 * @param u
	 * @return ArrayList con gli utenti
	 */
	public ArrayList<User> getAllUsers(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("get_all_user");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getUsers();
	}
}
