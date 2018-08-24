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
	
	public NoteArchive() {
	}

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
	 * se la nota � segnata
	 * @param titolo
	 * @param utente
	 * @return
	 */
	public Boolean isPinned (String titolo, User utente){
		System.out.println("IS PINNED???");
		System.out.println("CERCO TITOLO: "+titolo+" UTENTE: "+utente);
		ArrayList<Note> notes= (ArrayList<Note>) getWhere(x->x.getAuthor().equals(utente));
		return notes.stream()
			.filter(x->x.getTitle().equals(titolo))
			.collect(Collectors.toList())
			.get(0).getPin();
			
	}
	
	/**
	 * dato titolo e autore restituisce true
	 * se la nota � pubblica
	 * @param titolo
	 * @param utente
	 * @return
	 */
	public Boolean isPublic (String titolo, User utente){
		ArrayList<Note> notes= (ArrayList<Note>) getWhere(x->x.getAuthor().equals(utente));
		return notes.stream()
			.filter(x->x.getTitle().equals(titolo))
			.collect(Collectors.toList())
			.get(0).isPublic();
			
	}
	
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
	
	
	public ArrayList<Note> shareWithMe(User u){
		ArrayList<Note> notes=(ArrayList<Note>) getWhere(x->!x.getAuthor().equals(u));
		ArrayList<Note> out= new ArrayList<>();
		for (Note n: notes){
			if(n.getSharedWithArray().contains(u))
				out.add(n);
		}
		return out;
	}
	
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
	
	public Note getNoteByTitleLike(String n, User u){
		ArrayList<Note> out2= (ArrayList<Note>)getWhere(x->x.getTitle().equals(n));
		//TODO impedire doppioni
		System.out.println(out2);
		if(out2.size()>1)
			System.out.println("DOPPIONEEEE!!!!!");
		return out2.get(0);
	}
	
	public ArrayList<String> getLabelsByNote(String title, User us){
		ArrayList<Note> out= (ArrayList<Note>)getWhere(x->x.getAuthor().equals(us));
		ArrayList<Note> out2= (ArrayList<Note>)out.stream().filter(x->x.getTitle().equals(title)).collect(Collectors.toList());
		return out2.get(0).getLabel();
		}
	public ArrayList<Note> getNotesByLabel(String label, User us){
		ArrayList<Note> out= (ArrayList<Note>)getWhere(x->x.getAuthor().equals(us));
		ArrayList<Note> out2= (ArrayList<Note>)out.stream().filter(x->x.getLabel().contains(label)).collect(Collectors.toList());
		return out2;
		
	}
	
	public int getIDbyTitle(String title){
		return getWhere(x->x.getTitle().equals(title)).get(0).getID();
	}
	
	public Set<User> getSharredUser (String titolo, User u){
		ArrayList<Note> out= (ArrayList<Note>) getWhere(x->x.getAuthor().equals(u));
		return  out.stream().filter(x->x.getTitle().equals(titolo)).collect(Collectors.toList()).get(0).getSharedWith();
	}
	

	public ArrayList<Note> getAllNote(User u){
		return (ArrayList<Note>)getWhere(x->!x.getAuthor().equals(u));
//		System.out.println(out);
//		for(Note n: out){
//			System.out.println("CARICO Una NOTA: "+n);
//		}
//		return out;
	}
	
	public Note getNoteByID(int ID){
		return getWhere(x->x.getID()==ID).get(0);
	}
}
