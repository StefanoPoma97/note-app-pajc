package it.unibs.pajc.model;

import java.util.GregorianCalendar;
import java.util.Set;

public class Note {
	
	private String title;
	private String body;
	private int id;
	private GregorianCalendar createdAt;
	private GregorianCalendar updatedAt;
	private boolean isPublic;
	private String author;
	private Set <Tag> tags;
	private int likes;
	
	
	public Note (String _title){
		this.title=_title;
		createdAt=new GregorianCalendar();
	}


	public String getTitle() {
		return title;
	}

	public void addTag (Tag newTag){
		tags.add(newTag);
	}
	
	public void addLike(){
		likes=likes++;
	}
	
	public void removeLike(){
		likes=likes--;
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


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public GregorianCalendar getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(GregorianCalendar updatedAt) {
		this.updatedAt = updatedAt;
	}


	public boolean isPublic() {
		return isPublic;
	}


	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}


	public String getAuthor() {
		return author;
	}


	public void setAutor(String author) {
		this.author = author;
	}


	public Set<Tag> getTags() {
		return tags;
	}


	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}


	public GregorianCalendar getCreatedAt() {
		return createdAt;
	}
	
	
	

	
	
	

}
