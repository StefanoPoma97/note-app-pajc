package it.unibs.pajc.note.client_server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import it.unibs.pajc.note.data.Database;
import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.data.UserArchive;
import it.unibs.pajc.note.log.FileLogger;
import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.Tag;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.utility.ServizioFile;

public class MultiServerMain {

	public static void main(String[] args) {

		File file = new File("save.dat");
		Database data = null;
		NoteArchive noteArchive = null;
		UserArchive userArchive = null;
		boolean load = false;

		Logger logger = new FileLogger("main", "main.log").get();

		logger.info("Start Server");

		if (file.exists()) {
			try {
				data = (Database) ServizioFile.caricaSingoloOggetto(file);
				noteArchive = NoteArchive.getIstance(data.getNotes());
				userArchive = UserArchive.getIstance(data.getUsers());
			} catch (ClassCastException e) {
				System.err.println("Il file non puo' essere letto");
				logger.severe("Il file non puo' essere letto");
			} finally {
				if (data != null) {
					load = true;
					// System.out.println("Ho caricato il file");
					logger.finer("Caricato il file");
				}
			}
		}

		if (!load) {
			// System.out.println("Creo il file da zero");
			logger.info("Creo il file database");
			data = new Database();
			noteArchive = NoteArchive.getIstance();
			userArchive = UserArchive.getIstance();
		}

		Syncro sn = Syncro.getIstance();
		for (User u : userArchive.all()) {
			sn.addSync(new Sync(u));
		}

		
		
		
		//inizializza il file di di salvataggio per test
		
		 User u= new User("paolo", "merazza");
		 User u1 = new User("utente1", "pass1");
		 u.addLabel("Labels");
		 u.addLabel("Riunione");
		 u.addLabel("Memo");
		 u.addLabel("Memo2");
		 u.addLabel("Memo3");
		 userArchive.add(u);
		 u1.addLabel("Labels");
		 u1.addLabel("Riunione");
		 u1.addLabel("Memo");
		 userArchive.add(u1);
		 System.out.println("AGGIUNTI GLI UTENTI");
		

		 for (int i=0; i<10; i++){
		 Note nota = new Note("titolo"+i);
		 nota.setBody("corpo della nota numero: "+i);
		 nota.setAutor(u);
		 if(i==0){
		 nota.addLabel("Riunione");
		 nota.setPin(true);
		 Set<User> set= new HashSet<>();
		 set.add(u1);
		 nota.addSharedUsers(set);
		 }
		 if (i==1){
		 nota.addLabel("Memo");
		 }
		 if (i==2){
		 nota.addLabel("Memo2");
		 }
		
		 if (i==3){
		 nota.addLabel("Memo3");
		 }

		 noteArchive.add(nota);
		 }
		
		 for (int i=0; i<20; i++){
		 Note nota = new Note("titolo cp"+i);
		 nota.setBody("corpo della nota copia numero: "+i);
		 nota.setAutor(u1);
		 if(i==0){
		 nota.addLabel("Riunione");
		 nota.addLike();
		 nota.addLike();
		 Set<User> set= new HashSet<>();
		 set.add(u);
		 nota.addSharedUsers(set);
		// System.out.println("NOTA CONDIVISA CON "+ nota.getSharedWith().toString());
		 }
		 if (i==1){
		 nota.addLike();
		 nota.addLabel("Memo");
		 }
		
		 noteArchive.add(nota);
		 }
		
		
		
		 file = new File("save.dat");
		 data = new Database(noteArchive, userArchive);
		 ServizioFile.salvaSingoloOggetto(file, data);
		 System.out.println("Ho salvato");

		/**
		 * server.accept() permette di attendere fino a quando non si presenta una
		 * connessione, appena succede viene dichiarata una nuova istanza di MultiServer
		 * e avviato il Thread
		 */
		int port = 2270;
		// System.out.println("MULTISERVER START!");
		try (ServerSocket server = new ServerSocket(port);) {
			while (true) {
				new MultiServer(server.accept()).start();
			}

		} catch (IOException e) {
			System.err.println("Errore di comunicazione: " + e);
		}

	}

}
