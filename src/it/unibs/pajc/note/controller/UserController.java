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

	public String connetti(Client _client){
		client=_client;
		return client.connetti();
	}
	
	
	
	/**
	 * metodo per verificare un login
	 * @param name
	 * @param password
	 * @return un boolean (valido o non valido)
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
	 * metodo per creare un nuovo account
	 * @param username
	 * @param password
	 * @return ValidateError
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

	public ArrayList<String> getLabelsByUser (Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("load_labels");
		input.setUser(u);


		Comunication output= new Comunication();
		output= client.comunica(input);
		return output.getLabels();
	}
	
	public boolean addLabel (Client _client, String label, User us){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("add_label");
		input.setTitle(label);
		input.setUser(us);
		
		Comunication out=client.comunica(input);
		return out.getBoolean();
	}
	
	public void updateLabel(Client _client, ArrayList<String> str, User us){
		//TODO rendere pi� efficiente
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("update_label");
		input.setUser(us);
		input.setLabels(str);
		Comunication output2= client.comunica(input);
	}
	
	public ArrayList<User> getAllUsers(Client _client, User u){
		client=_client;
		Comunication input= new Comunication();
		input.setInfo("get_all_user");
		input.setUser(u);
		
		Comunication output= client.comunica(input);
		return output.getUsers();
	}
}
