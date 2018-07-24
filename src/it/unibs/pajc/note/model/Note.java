package it.unibs.pajc.note.model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

public class Note extends Identifiable{

	private String title;
	private String body;
	private Calendar createdAt;
	private Calendar updatedAt;
	private boolean isPublic;
	private String author;
	private Set<Tag> tags;

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

	public Calendar getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return title + "\t" + body;
	}

}
