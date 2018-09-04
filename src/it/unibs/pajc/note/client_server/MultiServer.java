package it.unibs.pajc.note.client_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.data.UserArchive;



public class MultiServer extends Thread{
	private Socket socket;
	private String clientName;
	static int clientId;
	private ObjectOutputStream output_stream;
	private ObjectInputStream input_stream;
	
	/**
	 * costrutture del MultiServer
	 * riceve in ingresso un Socket e crea una connessione con quest'ultimo
	 * @param _socket
	 * @author Stefano Poma
	 */
	public MultiServer(Socket _socket)
	{
		super("MS#"+clientId);
		socket= _socket;
		System.out.println("CONNESSIONE STABILITA CON: "+socket.getInetAddress()+" sulla porta: "+socket.getPort());
		clientName= "MS#"+clientId++;	
		
	}
	
	/**
	 * metodo esteso dalla classe Thread
	 * crea gli stream e mediante un ciclo while ogni volta che c'è qualcosa in ingresso restituisce un risultasto in uscita
	 */
	public void run()
	{
	
		try {
			output_stream = new ObjectOutputStream(socket.getOutputStream());
			output_stream.flush();
			input_stream= new ObjectInputStream(socket.getInputStream());
			
			System.out.println("STREAM CREATI...");
			Comunication input;
			while((input= (Comunication) input_stream.readObject())!=null)
			{
				System.out.println("\nCLIENT -> "+input.getInfo()+ ", ricevuta da: "+socket.getInetAddress());
				
				
				
				
				output_stream.writeObject(input.createResponse(NoteArchive.getIstance() ,UserArchive.getIstance()));
				output_stream.flush();
				//TODO messaggio di salvataggio su file gestirlo con save sui file

			}

			
		}
		catch(IOException | ClassNotFoundException e)
		{
			System.err.println("Errore di comunicazione: " +e);
			//TODO dopo errore di comunicazione salvare tutto su file
		}

//		System.out.println("SERVER STOP dentro");
	
	}
	


}
