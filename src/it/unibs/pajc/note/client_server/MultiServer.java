package it.unibs.pajc.note.client_server;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Logger;

import it.unibs.pajc.note.data.Database;
import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.data.UserArchive;
import it.unibs.pajc.note.log.FileLogger;
import it.unibs.pajc.note.utility.ServizioFile;

public class MultiServer extends Thread {
	private Socket socket;
	private String clientName;
	static int clientId;
	private ObjectOutputStream output_stream;
	private ObjectInputStream input_stream;
	Logger logger;

	/**
	 * costrutture del MultiServer riceve in ingresso un Socket e crea una
	 * connessione con quest'ultimo
	 * 
	 * @param _socket
	 * @author Stefano Poma
	 */
	public MultiServer(Socket _socket) {
		super("MS#" + clientId);
		logger = new FileLogger("MultiS" + clientId, "MultiServer - " + clientId + ".log").get();
		logger.info("Thread: " + clientId);
		socket = _socket;
		// System.out.println("CONNESSIONE STABILITA CON: "+socket.getInetAddress()+"
		// sulla porta: "+socket.getPort());
		logger.info("CONNESSIONE STABILITA CON: " + socket.getInetAddress() + " sulla porta: " + socket.getPort());
		clientName = "MS#" + clientId++;
	}

	private void close() throws IOException {
		// System.out.println("SERVER -> CHIUSURA CONNESSIONE");

		if (output_stream != null && input_stream != null && socket != null) {
			output_stream.close();
			input_stream.close();
			socket.close();
		}
		logger.info("Chiusa connessione lato Server: " + socket.getInetAddress());
	}

	/**
	 * metodo per salvare gli archivi su un file
	 * 
	 * @author Stefano Poma
	 */
	private void saveOnFile() {
		File file = new File("save.dat");
		// System.out.println("ELENCO UTENTI:");
		// System.out.println(UserArchive.getIstance().all());
		Database data = new Database(NoteArchive.getIstance(), UserArchive.getIstance());
		ServizioFile.salvaSingoloOggetto(file, data);
		// System.out.println("salvato su file");
		logger.info("Salvato su file il database");
	}

	/**
	 * metodo esteso dalla classe Thread crea gli stream e mediante un ciclo while
	 * ogni volta che c'� qualcosa in ingresso restituisce un risultasto in uscita
	 */
	public void run() {

		try {
			output_stream = new ObjectOutputStream(socket.getOutputStream());
			output_stream.flush();
			input_stream = new ObjectInputStream(socket.getInputStream());

			// System.out.println("STREAM CREATI...");
			Comunication input;
			while ((input = (Comunication) input_stream.readObject()) != null) {
				// System.out.println("\nCLIENT -> "+input.getInfo()+ ", ricevuta da:
				// "+socket.getInetAddress());
				logger.fine("\nCLIENT -> " + input.getInfo());

				Comunication c = input.createResponse(NoteArchive.getIstance(), UserArchive.getIstance());
				logger.info(c.toString());
				output_stream.writeObject(c);
				output_stream.flush();

			}

		} catch (java.net.SocketException e1) {
			// System.err.println("Errore di comunicazione: " +e1);
			logger.warning("Chiusa connessione: " + socket.getInetAddress());
			saveOnFile();
			try {
				close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			interrupt();
		} catch (EOFException e) {
			logger.info("Chiusa connessione: " + socket.getInetAddress());
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Errore di comunicazione: " + e);
			logger.severe("Errore di comunicazione: " + e);

		} finally {
			Arrays.asList(logger.getHandlers()).forEach(h -> h.close());
		}

		// System.out.println("SERVER STOP dentro");

	}

}
