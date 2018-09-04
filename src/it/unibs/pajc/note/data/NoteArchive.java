package it.unibs.pajc.note.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.Tag;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;

public class NoteArchive extends Archive<Note> {

	private static NoteArchive notearchive=null;
	
	/**
	 * costruttore privato (singletone)
	 * @author Stefano Poma
	 */
	private NoteArchive() {
	}

	/**
	 * serve per restituire la sola istanza di notearchive
	 * @return istanza unica di NoteArchive
	 */
	public static NoteArchive getIstance()
	{
		if (notearchive == null)
			notearchive =new NoteArchive();
		return notearchive;
	}
	
	/**
	 * Una nota è valida se il titolo non è vuoto.
	 */
	@Override
	protected ValidationError validate(Note n) {
		if (n.getTitle().isEmpty())
			return ValidationError.TITLE_EMPTY;
		return ValidationError.CORRECT;
	}
	
	@Override
	public String toString() {
		return elements.toString();
	}
	
	/**
	 * dato titolo e autore restituisce true
	 * se la nota e' segnata
	 * @param titolo
	 * @param utente
	 * @return true se � segnata
	 * @author Stefano Poma
	 */
	public Boolean isPinned (String titolo, User utente){
//		System.out.println("IS PINNED???");
//		System.out.println("CERCO TITOLO: "+titolo+" UTENTE: "+utente);
		ArrayList<Note> notes= (ArrayList<Note>) getWhere(x->x.getAuthor().equals(utente));
		return notes.stream()
			.filter(x->x.getTitle().equals(titolo))
			.collect(Collectors.toList())
			.get(0).getPin();
			
	}
	
	/**
	 * dato titolo e autore restituisce true
	 * se la nota e' pubblica
	 * @param titolo
	 * @param utente
	 * @return true se e' pubblica
	 * @author Stefano Poma
	 */
	public Boolean isPublic (String titolo, User utente){
		ArrayList<Note> notes= (ArrayList<Note>) getWhere(x->x.getAuthor().equals(utente));
		return notes.stream()
			.filter(x->x.getTitle().equals(titolo))
			.collect(Collectors.toList())
			.get(0).isPublic();
			
	}
	
	/**
	 * filtra le note di un utente tramite titolo
	 * @param u
	 * @return ArrayList di note filtrate tramite titolo
	 * @author Stefano Poma
	 */
	public ArrayList<Note> FilterByTitle(User u){
		ArrayList<Note> out= (ArrayList<Note>) getWhere(x->x.getAuthor().equals(u));
		Collections.sort(out, new Comparator<Note>() {
		    @Override
		    public int compare(Note s1, Note s2) {
		        return s1.getTitle().compareToIgnoreCase(s2.getTitle());
		    }
		});
		return out;
	}
	
	/**
	 * filtra tramite titolo le note della sezione esplora
	 * @param u
	 * @return ArrayList di note filtrate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> exFilterByTitle(User u){
		ArrayList<Note> out= (ArrayList<Note>) getWhere(x->!x.getAuthor().equals(u));
		Collections.sort(out, new Comparator<Note>() {
		    @Override
		    public int compare(Note s1, Note s2) {
		        return s1.getTitle().compareToIgnoreCase(s2.getTitle());
		    }
		});
		return out;
	}
	
	/**
	 * filtra tramite pin le note
	 * @param u
	 * @return ArrayList di note filtrate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> FilterByPin(User u){
		ArrayList<Note> out= (ArrayList<Note>) getWhere(x->x.getAuthor().equals(u));
		Collections.sort(out, new Comparator<Note>() {
		    @Override
		    public int compare(Note s1, Note s2) {
		        if (s1.getPin() && !s2.getPin())
		        	return -1;
		        if (!s1.getPin() && s2.getPin())
		        	return 1;
		        else return 0;
		        
		    }
		});
		return out;
	}
	
	/**
	 * filtra tramite pin le note della sezione esplora
	 * @param u
	 * @return ArrayList di note filtrate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> exFilterByAuthor(User u){
		ArrayList<Note> out= (ArrayList<Note>) getWhere(x->!x.getAuthor().equals(u));
		Collections.sort(out, new Comparator<Note>() {
		    @Override
		    public int compare(Note s1, Note s2) {
		    	return s1.getAuthor().getName().compareToIgnoreCase(s2.getAuthor().getName());
		    }
		});
		return out;
	}
	
	/**
	 * filtra le note tramte like
	 * @param u
	 * @return ArrayList di note filtrate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> FilterByLike(User u){
		ArrayList<Note> out= (ArrayList<Note>) getWhere(x->x.getAuthor().equals(u));
		Collections.sort(out, new Comparator<Note>() {
		    @Override
		    public int compare(Note s1, Note s2) {
		        if (s1.getLike() > s2.getLike())
		        	return -1;
		        if (s1.getLike() < s2.getLike())
		        	return 1;
		        else return 0;
		        
		    }
		});
		return out;
	}
	
	/**
	 * filtra le note della sezione esplora tramite like
	 * @param u
	 * @return ArrayList di note filtrate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> exFilterByLike(User u){
		ArrayList<Note> out= (ArrayList<Note>) getWhere(x->!x.getAuthor().equals(u));
		Collections.sort(out, new Comparator<Note>() {
		    @Override
		    public int compare(Note s1, Note s2) {
		        if (s1.getLike() > s2.getLike())
		        	return -1;
		        if (s1.getLike() < s2.getLike())
		        	return 1;
		        else return 0;
		        
		    }
		});
		return out;
	}
	
	/**
	 * filtra le note per data
	 * @param u
	 * @return ArrayList di note filtrate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> FilterByData(User u){
		ArrayList<Note> out= (ArrayList<Note>) getWhere(x->x.getAuthor().equals(u));
		Collections.sort(out, new Comparator<Note>() {
		    @Override
		    public int compare(Note s1, Note s2) {
		    	GregorianCalendar data1 = null;
		    	GregorianCalendar data2 = null;
		    	if (s1.getUpdatedAt()==null)
		    		data1=(GregorianCalendar)s1.getCreatedAt();
		    	else
		    		data1=(GregorianCalendar)s1.getUpdatedAt();
		    	
		    	if (s2.getUpdatedAt()==null)
		    		data2=(GregorianCalendar)s2.getCreatedAt();
		    	else
		    		data2=(GregorianCalendar)s2.getUpdatedAt();
		    	
		        return -1*data1.compareTo(data2);
		        
		    }
		});
		return out;
	}
	
	/**
	 * filtra per data le note della sezione esplora
	 * @param u
	 * @return ArrayList di note filtrate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> exFilterByData(User u){
		ArrayList<Note> out= (ArrayList<Note>) getWhere(x->!x.getAuthor().equals(u));
		Collections.sort(out, new Comparator<Note>() {
		    @Override
		    public int compare(Note s1, Note s2) {
		    	GregorianCalendar data1 = null;
		    	GregorianCalendar data2 = null;
		    	if (s1.getUpdatedAt()==null)
		    		data1=(GregorianCalendar)s1.getCreatedAt();
		    	else
		    		data1=(GregorianCalendar)s1.getUpdatedAt();
		    	
		    	if (s2.getUpdatedAt()==null)
		    		data2=(GregorianCalendar)s2.getCreatedAt();
		    	else
		    		data2=(GregorianCalendar)s2.getUpdatedAt();
		    	
		        return -1*data1.compareTo(data2);
		        
		    }
		});
		return out;
	}
	
	/**
	 * note condivise con utente selezionato
	 * @param u
	 * @return ArrayList di note filtrate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> shareWithMe(User u){
		ArrayList<Note> notes=(ArrayList<Note>) getWhere(x->!x.getAuthor().equals(u));
		ArrayList<Note> out= new ArrayList<>();
		for (Note n: notes){
			if(n.getSharedWithArray().contains(u))
				out.add(n);
		}
		return out;
	}
	
	/**
	 * nota cercata dal titolo
	 * @param n
	 * @param u
	 * @return nota che corrisponde a quel titolo
	 * @author Stefano Poma
	 */
	public Note getNoteByTitle(String n, User u){
//		System.out.println("STO CERCANDO LA NOTA: "+n);
		ArrayList<Note> out= shareWithMe(u);
//		System.out.println("NOTE CONDIVISE CON ME: "+out.toString());
		ArrayList<Note> out2= (ArrayList<Note>)out.stream().filter(x->x.getTitle().equals(n)).collect(Collectors.toList());
		//TODO impedire doppioni
		System.out.println(out2);
		if(out2.size()>1)
			System.out.println("DOPPIONEEEE!!!!!");
		return out2.get(0);
	}
	
	/**
	 * trova la nota dato il titolo
	 * @param n
	 * @param u
	 * @return nota con quel titolo
	 * @author Stefano Poma
	 */
	public Note getNoteByTitleLike(String n, User u){
		ArrayList<Note> out2= (ArrayList<Note>)getWhere(x->x.getTitle().equals(n));
		//TODO impedire doppioni
		System.out.println(out2);
		if(out2.size()>1)
			System.out.println("DOPPIONEEEE!!!!!");
		return out2.get(0);
	}
	
	/**
	 * labels associate a una nota
	 * @param title
	 * @param us
	 * @return ArrayList delle labels cercate
	 * @author Stefano Poma
	 */
	public ArrayList<String> getLabelsByNote(String title, User us){
		ArrayList<Note> out= (ArrayList<Note>)getWhere(x->x.getAuthor().equals(us));
		ArrayList<Note> out2= (ArrayList<Note>)out.stream().filter(x->x.getTitle().equals(title)).collect(Collectors.toList());
		return out2.get(0).getLabel();
		}
	
	/**
	 * note associate a una label
	 * @param label
	 * @param us
	 * @return Arraylist delle note cercate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> getNotesByLabel(String label, User us){
		ArrayList<Note> out= (ArrayList<Note>)getWhere(x->x.getAuthor().equals(us));
		ArrayList<Note> out2= (ArrayList<Note>)out.stream().filter(x->x.getLabel().contains(label)).collect(Collectors.toList());
		return out2;
		
	}
	
	/**
	 * cerca ID sulla base del titolo della nota
	 * @param title
	 * @return ID associato a quel titolo (titolo univoco)
	 * @author Stefano Poma
	 */
	public int getIDbyTitle(String title){
		return getWhere(x->x.getTitle().equals(title)).get(0).getID();
	}
	
	/**
	 * utenti condivisi con quello selezionato
	 * @param titolo
	 * @param u
	 * @return Set degli utenti condivisi
	 * @author Stefano Poma
	 */
	public Set<User> getSharredUser (String titolo, User u){
		ArrayList<Note> out= (ArrayList<Note>) getWhere(x->x.getAuthor().equals(u));
		return  out.stream().filter(x->x.getTitle().equals(titolo)).collect(Collectors.toList()).get(0).getSharedWith();
	}
	

	/**
	 * tutte le note di un utente
	 * @param u
	 * @return ArrayList contenente tutte le note
	 * @author Stefano Poma
	 */
	public ArrayList<Note> getAllNote(User u){
		return (ArrayList<Note>)getWhere(x->!x.getAuthor().equals(u));
	}
	
	/**
	 * nota cercata tramite ID
	 * @param ID
	 * @return restituisce la nota con quel ID
	 * @author Stefano Poma
	 */
	public Note getNoteByID(int ID){
		return getWhere(x->x.getID()==ID).get(0);
	}
}
