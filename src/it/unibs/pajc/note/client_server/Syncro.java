package it.unibs.pajc.note.client_server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibs.pajc.note.data.UserArchive;
import it.unibs.pajc.note.model.User;

public class Syncro {

	private ArrayList<Sync> lista = new ArrayList<>();
	private ArrayList<Sync> refreshID = new ArrayList<>();

	private static Syncro sc = null;

	/**
	 * costruttore privato (singletone)
	 * 
	 * @author Stefano Poma
	 */
	private Syncro() {
	}

	/**
	 * serve per restituire la sola istanza di notearchive
	 * 
	 * @return istanza unica di NoteArchive
	 */
	public static Syncro getIstance() {
		if (sc == null)
			sc = new Syncro();
		return sc;
	}

	public void addSync(Sync add) {
		lista.add(add);
	}

	public void addRefresh(Sync add) {
		if (!refreshID.contains(add)) {
			refreshID.add(add);
			Set<Sync> cp = new HashSet<Sync>(refreshID);
			refreshID = new ArrayList<>(cp);
			// System.out.println("AGGIUNTO NUOVO REFRESH ORA E': "+refreshID);
		}

	}

	public void stopModify(User u) {
		// se non essite lo aggiunge
		ArrayList<User> utenti = (ArrayList<User>) lista.stream().map(x -> x.getUser()).collect(Collectors.toList());
		if (!utenti.contains(u)) {
			Sync sync2 = new Sync(u);
			addSync(sync2);
		}

		// elimina ultimo id di modifica
		Sync s = lista.stream().filter(x -> x.getUser().equals(u)).collect(Collectors.toList()).get(0);

		if (s.getID() != null) {
			Sync sync = new Sync(s.getUser());
			sync.setID(s.getID());
			addRefresh(sync);
		}

		s.deleteID();

		System.out.println("LISTA MODIFICHE ATTIVE:");
		for (Sync sr : lista) {
			System.out.println(sr);
		}

		System.out.println("REFRESH LISTA COMPLETA:");
		for (Sync sr : refreshID) {
			System.out.println(sr);
		}

	}

	public Comunication modify(User u, int id) {
		Comunication output = new Comunication();
		output.setInfo("modify_id_response");
		// se non essite lo aggiunge
		ArrayList<User> utenti = (ArrayList<User>) lista.stream().map(x -> x.getUser()).collect(Collectors.toList());
		if (!utenti.contains(u)) {
			Sync sync3 = new Sync(u);
			addSync(sync3);
		}

		// lista degli ID attivi
		ArrayList<Integer> indirizzi = (ArrayList<Integer>) lista.stream().filter(x -> !x.getUser().equals(u))
				.map(x -> x.getID()).collect(Collectors.toList());

		if (indirizzi.contains(id)) {
			output.setBoolean(false);
			return output;
		}

		else {

			if (refresh(u, id)) {
				// System.out.println("NECESSARIO REFRESH");
				output.setInfo("modify_id_response_refresh");
				output.setBoolean(true);
			} else {
				Sync sy = lista.stream().filter(x -> x.getUser().equals(u)).collect(Collectors.toList()).get(0);
				Sync sync4 = new Sync(sy.getUser());
				sync4.setID(id);
				lista.remove(sy);
				lista.add(sync4);
				output.setBoolean(true);
			}

			System.out.println("LISTA MODIFICHE ATTIVE:");
			for (Sync sr : lista) {
				System.out.println(sr);
			}

			System.out.println("REFRESH LISTA COMPLETA:");
			for (Sync sr : refreshID) {
				System.out.println(sr);
				System.out.println(sr.getRefreshedBy());
			}

			return output;

		}

	}

	public boolean refresh(User u, int id) {

		ArrayList<Integer> interi = new ArrayList<>();
		interi = (ArrayList<Integer>) refreshID.stream().filter(x -> !x.getUser().equals(u)).map(x -> x.getID())
				.collect(Collectors.toList());

		ArrayList<Sync> sn = (ArrayList<Sync>) refreshID.stream().filter(x -> !x.getUser().equals(u))
				.filter(x -> x.getID() == id).collect(Collectors.toList());

		// if (interi.isEmpty()){
		// System.out.println("LISTA ID VUOTA QUINDI NIENTE REFRESH");
		// return false;
		// }
		if (!sn.isEmpty()) {
			ArrayList<User> user = new ArrayList<>(UserArchive.getIstance().all());
			user.remove(sn.get(0).getUser());
			if (sn.get(0).getRefreshedBy().equals(user))
				refreshID.remove(sn.get(0));
		}

		if (sn.isEmpty()) {
			// System.out.println("SPECIFICO SYNC CHE HA MODIFICATO IL MIO ID VUOTO");
			return false;
		}

		else if (interi.contains(id) && !sn.get(0).getRefreshedBy().contains(u)) {
			// System.out.println("C'E UN SYNC CHE COINCIDE");
			// for(int i=0; i<refreshID.size()-1; i++){
			// if(!refreshID.get(i).getUser().equals(u)){
			// refreshID.get(i).addRefreshed(u);
			// }
			// if(refreshID.get(i).getRefreshedBy().equals(UserArchive.getIstance().all())){
			// refreshID.remove(i);
			// i--;
			// }
			// }
			sn.get(0).addRefreshed(u);
			// System.out.println("AGGIUNTO COME UTENTE CHE HA REFRESH FATTO");
			ArrayList<User> user = new ArrayList<>(UserArchive.getIstance().all());
			// user.remove(sn.get(0).getUser());
			System.out.println("Refreshed by: " + sn.get(0).getRefreshedBy());
			System.out.println(user);
			if (sn.get(0).getRefreshedBy().size() == user.size())
				refreshID.remove(sn.get(0));

			return true;
		} else
			return false;

	}

	public void iRefresh(User u) {
		ArrayList<Sync> sync = (ArrayList<Sync>) refreshID.stream().filter(x -> !x.getUser().equals(u))
				.collect(Collectors.toList());

		for (Sync s : sync) {
			s.addRefreshed(u);
			if (s.getRefreshedBy().equals(UserArchive.getIstance().all()))
				sync.remove(s);
		}
	}
}
