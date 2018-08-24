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
	
	
	public MultiServer(Socket _socket)
	{
		super("MS#"+clientId);
		socket= _socket;
		System.out.println("CONNESSIONE STABILITA CON: "+socket.getInetAddress()+" sulla porta: "+socket.getPort());
		clientName= "MS#"+clientId++;	
		
	}
	
	
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
//				if ("QUIT".equals(response))
//					break;
			}

			
		}
		catch(IOException | ClassNotFoundException e)
		{
			System.err.println("Errore di comunicazione: " +e);
		}

		System.out.println("SERVER STOP dentro");
	
	}
	


}