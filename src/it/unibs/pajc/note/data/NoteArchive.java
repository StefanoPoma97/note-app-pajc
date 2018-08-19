package it.unibs.pajc.note.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.Tag;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;

public class NoteArchive extends Archive<Note> {

	public NoteArchive() {
		super();
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
	
	

}
