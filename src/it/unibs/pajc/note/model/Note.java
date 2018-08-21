package it.unibs.pajc.note.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

public class Note extends Identifiable implements Serializable{

	private String title;
	private String body;
	private Calendar createdAt;
	private Calendar updatedAt;
	private boolean isPublic;
	private boolean pin=false;
	private User author;
	private Set<Tag> tags;
	private ArrayList<String> labels= new ArrayList<>();
	private int likes=0;
	private Set<User> sharedWith = new HashSet<>();
	private ArrayList<User> likedBy = new ArrayList<>();

	public Note(String _title) {
		this.title = _title;
		createdAt = new GregorianCalendar();
	}

	public String getTitle() {
		return title;
	}

	public void addTag(Tag newTag) {
		tags.add(newTag);
	}

	public ArrayList<String> getLabels() {
		return labels;
	}

	public void setLabels(ArrayList<String> labels) {
		this.labels = labels;
	}

	/**
	 * aggiunge una nuova label verificando che non sia già presente
	 * TODO usare un Set?
	 * @param lb
	 * @return
	 */
	public boolean addLabel(String lb){
		if(labels.contains(lb)){
			return false;
		}
		else{
			labels.add(lb);
			return true;
		}

	}

	public ArrayList<String> getLabel (){
		return labels;
	}
	
	/**
	 * aggiunge più di una label contemporaneamente
	 * @param la
	 */
	public void addLabels(ArrayList<String> la) {
		for(String str: la){
			addLabel(str);
			System.out.println("STO AGGIUNGENDO: "+str);
		}
		System.out.println("aggiunta con successo labels alla nota");
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Calendar getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public User getAuthor() {
		return author;
	}

	public void setAutor(User author) {
		this.author = author;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Calendar getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return title + "\t" + body;
	}
	
	public void setPin(Boolean _pin){
		pin=_pin;
	}
	
	public Boolean getPin(){
		return pin;
		
	}
	
	public void addLike(){
		likes++;
	}
	
	public void removeLike(){
		likes--;
	}
	
	public int getLike(){
		return likes;
	}
	
	
	public Set<User> getSharedWith (){
		return sharedWith;
	}
	
	public ArrayList<User> getSharedWithArray (){
		return new ArrayList<User>(sharedWith);
	}
	
	public void addSharedUsers (Set<User> us){
		sharedWith.addAll(us);
		System.out.println("AGGIUNTI NUOVI SHARED US: "+sharedWith);
	}
	
	public ArrayList<User> getLikedBy (){
		return likedBy;
	}
	
	public void addLikedUser(User u){
		likedBy.add(u);
		Set<User> cp= new HashSet<>(likedBy);
		likedBy=new ArrayList<>(cp);
	}
	
	public void removeLikedUser(User u){
		likedBy.remove(u);
	}
	/**
	 * metodo equals che si basa solo sull'ID dato che è univoco
	 */
//	@Override
//	public boolean equals(Object obj) {
//		// TODO Auto-generated method stub
//		return super.equals(obj);
//	}

}
