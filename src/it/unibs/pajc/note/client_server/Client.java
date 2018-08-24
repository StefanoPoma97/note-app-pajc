package it.unibs.pajc.note.client_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import it.unibs.pajc.note.controller.NoteController;
import it.unibs.pajc.note.controller.UserController;
import it.unibs.pajc.note.data.NoteArchive;

public class Client {
	
	private String hostName;
	private int port;
	private Socket client=null;
	private ObjectOutputStream output_stream;
	private ObjectInputStream input_stream;
	private UserController userController= new UserController();
	private NoteController noteController= new NoteController();
	
//	private static Client instance=null;
	
	
//	private Client()
//	{
//		hostName = "127.0.0.1";
//		port= 2270;
//		System.out.println("IN ATTESA DI CONNESSIONE... su Host: "+hostName+" alla porta: "+port);
//	}
//	
//	public static Client getIstance()
//	{
//		if (instance == null)
//			instance =new Client();
//		return instance;
//	}
	
	public Client()
	{
		hostName = "127.0.0.1";
		port= 2270;
		System.out.println("IN ATTESA DI CONNESSIONE... su Host: "+hostName+" alla porta: "+port);
	}
	
	/**
	 * metodo per la connessione al server, ritorna una Stringa che specifica se la connessione � avvenuta
	 * @return
	 */
	public String connetti()
	{
		try {
			client = new Socket(hostName, port);
			output_stream = new ObjectOutputStream(client.getOutputStream());
			output_stream.flush();
			input_stream = new ObjectInputStream(client.getInputStream());
			
			System.out.println("CONNESSIONE STABILITA");
			String out ="CONNESSIONE STABILITA";
			return out;
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}
	
	/** 
	 * metodo per interrompere la comunicazione con il server
	 */
	public void stop()
	{
		
		if (output_stream!=null && input_stream!= null && client!=null)
		{
			try {
				output_stream.close();
				input_stream.close();
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * metodo per comunicare 
	 *ora solo una stringa pi� vanti una classe comunications
	 * @param input
	 */
	public Comunication comunica (Comunication output)
	{
		try {
			System.out.println("CLIENT-> "+output.getInfo());

			
			output_stream.writeObject(output);
			output_stream.flush();

			Comunication input=(Comunication) input_stream.readObject();
			System.out.println("SERVER-> "+input.getInfo());
			return input;
			
		} catch (Exception e) {
			Comunication input= new Comunication();
			input.setError(e.toString());
			return input;
		}
	}

}
