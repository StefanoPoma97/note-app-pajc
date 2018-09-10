package it.unibs.pajc.note.client_server;

import java.util.ArrayList;
import java.util.stream.Collectors;

import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.model.User;

public class Syncro {

private ArrayList<Sync> lista= new ArrayList<>();
private ArrayList<Sync> refreshID= new ArrayList<>();

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
	
	public void addRefresh(Sync add){
		refreshID.add(add);
	}
	
	public void stopModify(User u){
		//se non essite lo aggiunge
		ArrayList<User> utenti= (ArrayList<User>)lista.stream()
								.map(x->x.getUser())
								.collect(Collectors.toList());
		if (!utenti.contains(u)){
			Sync sync2=new Sync(u);
			addSync(sync2);
		}
			
		
		//elimina ultimo id di modifica
		Sync s= lista.stream()
					.filter(x->x.getUser().equals(u))
					.collect(Collectors.toList())
					.get(0);
		
		
		if(s.getID()!=null){
			Sync sync= new Sync(s.getUser());
			sync.setID(s.getID());
			addRefresh(sync);
		}
		
		System.out.println("LISTA MODIFICHE ATTIVE:");
		for(Sync sr :lista){
			System.out.println(sr);
		}
		
		System.out.println("REFRESH LISTA COMPLETA:");
		for(Sync sr :refreshID){
			System.out.println(sr);
		}
			
		
		s.deleteID();
	}
	
	public Comunication modify (User u, int id){
		Comunication output= new Comunication();
		output.setInfo("modify_id_response");
		//se non essite lo aggiunge
		ArrayList<User> utenti= (ArrayList<User>)lista.stream()
				.map(x->x.getUser())
				.collect(Collectors.toList());
		if (!utenti.contains(u)){
			Sync sync3=new Sync(u);
			addSync(sync3);
		}
		
		//lista degli ID attivi
		ArrayList<Integer> indirizzi= (ArrayList<Integer>)lista.stream()
				.filter(x->!x.getUser().equals(u))
				.map(x->x.getID())
				.collect(Collectors.toList());
		
		if (indirizzi.contains(id)){
			output.setBoolean(false);
			return output;
		}
			
		else{
			Sync sy= lista.stream()
					.filter(x->x.getUser().equals(u))
					.collect(Collectors.toList())
					.get(0);
			Sync sync4= new Sync(sy.getUser());
			sync4.setID(id);
			lista.remove(sy);
			lista.add(sync4);
			output.setBoolean(true);
			if(refresh(u, id)){
//				System.out.println("NECESSARIO REFRESH");
				output.setInfo("modify_id_response_refresh");
			}
			
			System.out.println("LISTA MODIFICHE ATTIVE:");
			for(Sync sr :lista){
				System.out.println(sr);
			}
			
			System.out.println("REFRESH LISTA COMPLETA:");
			for(Sync sr :refreshID){
				System.out.println(sr);
			}
				
			return output;
			
		}
				
		
	}
	
	public boolean refresh(User u, int id){
		System.out.println("REFRESH LISTA COMPLETA");
		for(Sync s :refreshID){
			System.out.println(s.getID());
		}
		ArrayList<Integer> interi= new ArrayList<>();
		interi=(ArrayList<Integer>)refreshID.stream()
				.filter(x->!x.getUser().equals(u))
				.map(x->x.getID())
				.collect(Collectors.toList());
		System.out.println("LISTA DEI MIEI ID: "+interi);
		if (interi.isEmpty())
			return false;
		else if (interi.contains(id)){
			refreshID=(ArrayList<Sync>)refreshID.stream()
					.filter(x->!x.getID().equals(id))
					.collect(Collectors.toList());
					
			return true;
		}
		else
			return false;
		
	}
}
