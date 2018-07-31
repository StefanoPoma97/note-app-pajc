package it.unibs.pajc.note.status;

/**
 * Classe per la differenziazione dei possibili errori in fase di convalida di un oggetto
 * Ogni possibile stato ha associato una descrizione eccetto lo stato CORRECT in quanto non dovrà essere riportato
 * alcun errore.
 * @author Stefano Poma, danielevezz
 *
 */
public enum ValidationError implements Error{
	
	CORRECT(""),
	NAME_EMPTY("The username is required"), 
//	BODY_EMPTY("The body of the note cannot be empty"),
	TITLE_EMPTY("The title of the note is required"),
	USER_PRESENT("The user is already present"),
	PASSWORD_EMPTY("The password cannot be empty");
	
	private String description;
	
	ValidationError(String description) {
		this.description = description;
	}
	
	public String toString() {
		return description;
	}

}
