package it.unibs.pajc.note.status;

public enum ArchiveError implements Error{
	
	ID_NOT_PRESENT("The ID is not present in the archive");
	
	private String description;
	
	ArchiveError(String description) {
		this.description = description;
	}
	
	public String toString() {
		return description;
	}

}
