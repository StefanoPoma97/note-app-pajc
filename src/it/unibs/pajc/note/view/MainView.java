package it.unibs.pajc.note.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import it.unibs.pajc.note.controller.NoteController;
import it.unibs.pajc.note.controller.UserController;
import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.Tag;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;
import java.awt.GridBagLayout;

public class MainView {

	private JFrame frame;
	
	//Login View
	private LoginView loginView;
	private String name= null;
	private String password=null;
	private User utente=null;
	private UserController userController=new UserController();
	
	//Note View
	private NoteView noteView=null;
	private String title= null;
	private String body= null;
	private Integer modifyID= null;
	private NoteController noteController=new NoteController();
	
	//componenti utilizzati
	private JTextArea textArea;
	private JPanel contentPanel;
	
	

	
	
	//Metodi per LoginView
	/**
	 * Metodo utilizzato nel LoginView che permette di passare nome password 
	 * restituisce true se il login ï¿½ possibile
	 * @param _name
	 * @param _pass
	 */
	public Boolean login(String _name, String _pass){
		Boolean validate = userController.login(_name, _pass);
		if (validate){
			utente= new User(_name, _pass);
			initializeNoteView();
		}
			
		return validate;
	}
	
	/**
	 * metodo che permette di passare nome e password per la creazione di un nuovo account
	 * @param name
	 * @param pass
	 * @return ValidationError
	 */
	public ValidationError create(String name, String pass){
		ValidationError validate = userController.create(name, pass);
		if (validate.equals(ValidationError.CORRECT)){
			utente= new User(name, pass);
			initializeNoteView();
		}
		return validate;
	}

	
	//metodi per NoteView
	
	/**
	 * restituisce tutte le note associate al utente del login
	 * @return
	 */
	public ArrayList<Note> getMyNote(){
		
		return noteController.getMyNote(utente);
	}
	
	/**
	 * restituisce tutte le label associate all'utente del login
	 * @return
	 */
	public ArrayList<String> getMyLabel(){
		return userController.getLabelsByUser(utente);
	}
	
	/**
	 * confronta le label salvate sull'utente e quelle salvate solamente sulle note
	 * se le label associate all'utente non sono connesse a nessuna nota vengono eliminate
	 */
	public void updateMyLabels(){
		ArrayList<String> userLabels = userController.getLabelsByUser(utente);
		System.out.println("LABEL DEL UTENTE: "+ userLabels);
		ArrayList<String> noteLabels = new ArrayList<>();
		for (Note nota: noteController.getMyNote(utente)){
			noteLabels.addAll(noteController.getLabelsByNote(nota.getTitle(), utente));
		}
		System.out.println("LABEL DELLE NOTE: "+noteLabels);
		ArrayList<String> userLabels_cp= new ArrayList<>(userLabels);
		userLabels_cp.removeAll(noteLabels);
		userLabels.removeAll(userLabels_cp);
		userLabels.add(0, "Labels");
		System.out.println("NUOVE LABEL: "+userLabels);
		userController.updateLabel(userLabels, utente);
		
	}

	/**
	 * aggiunge una label all'utente del login
	 * TODO meglio Set?
	 * @param label
	 * @return
	 */
	public boolean addLabel(String label){
		return userController.addLabel(label, utente);
	}
	
	/**
	 * restituisce le note associate ad una label
	 * @param label
	 * @return
	 */
	public ArrayList<Note> getNotesByLabel(String label){
		return noteController.getNotesByLabel(label, utente);
	}
	
	/**
	 * restituisce le labels associate ad una nota
	 * @param title
	 * @return
	 */
	public ArrayList<String> getLabelsByNote(String title){
		return noteController.getLabelsByNote(title, utente);
	}
	
	/**
	 * metodo per aggiungere una nota
	 * @param n
	 * @return
	 */
	public ValidationError addNote (Note n){
		n.setAutor(utente);
		return noteController.addNote(n);
	}
	
	/**
	 * metodo per aggiornare una nota già essitente
	 * @param n
	 * @param ID
	 * @return
	 */
	public ValidationError update(Note n, int ID){
		n.setAutor(utente);
		return noteController.update(n, ID);
	}
	
	public int getIDbyTitle(String title){
		return noteController.getIDbyTitle(title);
	}
	
	public Boolean isPinned(String titolo){
		return noteController.isPinned(titolo, utente);
	}
	
	public Boolean isPublic(String titolo){
		return noteController.isPublic(titolo, utente);
	}
	
	public ArrayList<Note> FilterByTitle(){
		return noteController.FilterByTitle(utente);
	}
	
	public ArrayList<Note> FilterByPin(){
		return noteController.FilterByPin(utente);
	}
	
	public ArrayList<Note> FilterByLike(){
		return noteController.FilterByLike(utente);
	}
	
	public ArrayList<Note> FilterByData(){
		return noteController.FilterByData(utente);
	}
	
	public ArrayList<User> getAllUsers(){
		return userController.getAllUsers(utente);
	}
	
	public Set<User> getSharredUser(String title){
		return noteController.getSharredUser(title, utente);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		//Pannello principale
		contentPanel = new JPanel();
		frame.getContentPane().add(contentPanel);
		contentPanel.setLayout(new GridBagLayout());
		
		//GridBgLayout per il pannello principale
		loginView = new LoginView(this);
		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx=1;
		gc.weighty=1;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill= GridBagConstraints.BOTH;
		contentPanel.add(loginView, gc);


	}
	
	private void initializeNoteView(){
		noteView = new NoteView(this);
		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx=1;
		gc.weighty=1;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill= GridBagConstraints.BOTH;
		contentPanel.add(noteView, gc);
	}
}


