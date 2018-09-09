package it.unibs.pajc.note.client_server;

import java.util.ArrayList;
import java.util.stream.Collectors;

import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.model.User;

public class Syncro {

private ArrayList<Sync> lista= new ArrayList<>();

private static Syncro sc=null;
	
	/**
	 * costruttore privato (singletone)
	 * @author Stefano Poma
	 */
	private Syncro() {
	}

	/**
	 * serve per restituire la sola istanza di notearchive
	 * @return istanza unica di NoteArchive
	 */
	public static Syncro getIstance()
	{
		if (sc == null)
			sc =new Syncro();
		return sc;
	}
	
	public void addSync (Sync add){
		lista.add(add);
	}
	
	public void stopModify(User u){
		//se non essite lo aggiunge
		ArrayList<User> utenti= (ArrayList<User>)lista.stream()
								.map(x->x.getUser())
								.collect(Collectors.toList());
		if (!utenti.contains(u))
			addSync(new Sync(u));
		
		//elimina ultimo id di modifica
		Sync s= lista.stream()
					.filter(x->x.getUser().equals(u))
					.collect(Collectors.toList())
					.get(0);
		s.deleteID();
	}
	
	public boolean modify (User u, int id){
		//se non essite lo aggiunge
		ArrayList<User> utenti= (ArrayList<User>)lista.stream()
				.map(x->x.getUser())
				.collect(Collectors.toList());
		if (!utenti.contains(u))
			addSync(new Sync(u));
		
		//lista degli ID attivi
		ArrayList<Integer> indirizzi= (ArrayList<Integer>)lista.stream()
				.filter(x->!x.getUser().equals(u))
				.map(x->x.getID())
				.collect(Collectors.toList());
		
		if (indirizzi.contains(id))
			return false;
		else{
			Sync s= lista.stream()
					.filter(x->x.getUser().equals(u))
					.collect(Collectors.toList())
					.get(0);
			s.setID(id);
			return true;
		}
				
		
	}
}
