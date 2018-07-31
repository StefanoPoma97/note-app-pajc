//package it.unibs.pajc.note.test1;
//
//import java.io.File;
//
//import it.unibs.pajc.note.data.Database;
//import it.unibs.pajc.note.data.NoteArchive;
//import it.unibs.pajc.note.data.UserArchive;
//import it.unibs.pajc.note.model.Note;
//import it.unibs.pajc.note.utility.ServizioFile;
//
//public class MainTestSave {
//
//	public static void main(String[] args) {
//		
//		
//	//test caricamento File
//		
//		File file = new File("save.dat");
//		Database data=null;
//		NoteArchive notes = new NoteArchive();
//		UserArchive users = new UserArchive();
//		boolean load = false;
//		
//		if(file.exists()) {
//			try {
//				data = (Database) ServizioFile.caricaSingoloOggetto(file);
//				notes=data.getNotes();
//				users=data.getUsers();
//			} catch (ClassCastException e) {
//				System.err.println("Il file non pu� essere letto");
//			} 
//			finally {
//				if(data != null) {
//					load = true;
//					System.out.println("Ho caricato il file");
//				}
//			}
//		}
//		
//		if(!load) {
//			System.out.println("Creo il file da zero");
//			data=new Database();
//		}
//		
//		Note n = new Note("atomico");
//		notes.add(n);
//		System.out.println(notes.toString());
//		// salva file
//		data.setNotes(notes);
//		data.setUsers(users);
//		ServizioFile.salvaSingoloOggetto(file, data);
//		System.out.println("Ho salvato");
//		
//		
//		
//		
//		
////		File file = new File("save.dat");
////		Database data= new Database();
////		NoteArchive notes= new NoteArchive();
////		UserArchive users = new UserArchive();
////		boolean load = false;
////		
////		if(file.exists()) {
////			try {
////				data = (Database) ServizioFile.caricaSingoloOggetto(file);
////				notes = data.getNotes();
////				users = data.getUsers();
////			} catch (ClassCastException e) {
////				System.err.println("Il file non pu� essere letto");
////			} 
////			finally {
////				if(notes != null && users != null) {
////					load = true;
////					System.out.println("Ho caricato il file");
////				}
////			}
////		}
////		
////		if(!load) {
////			System.out.println("Creo il file da zero");
////			notes = new NoteArchive();
////			users = new UserArchive();
////		}
////		
////		// salva file
////		data = new Database(notes, users);
////		ServizioFile.salvaSingoloOggetto(file, data);
////		System.out.println("Ho salvato");
//	
//
//	}
//
//}
