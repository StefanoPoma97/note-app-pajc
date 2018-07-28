package it.unibs.pajc.note.model;

import java.util.Set;

import it.unibs.pajc.note.utility.AuthenticationUtility;

public class User extends Identifiable {

	private String name;
	private byte[] password;
	private Set<Tag> personalTag;

	public User(String name, String password) {
		this.name = name;
		this.password = AuthenticationUtility.generateHash(password);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name != null)
			this.name = name;
	}

	public String getPassword() {
		return AuthenticationUtility.hashToString(password);
	}

	public void setPassword(String password) {
		if(password != null)
			this.password = AuthenticationUtility.generateHash(password);
	}

	public void addTag(Tag newTag) {
		personalTag.add(newTag);
	}

	public Set<Tag> getPersonalTag() {
		return personalTag;
	}

	@Override
	public String toString() {

		return this.name;
	}

}
