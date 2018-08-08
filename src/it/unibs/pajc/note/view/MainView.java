package it.unibs.pajc.note.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import it.unibs.pajc.note.controller.NoteController;
import it.unibs.pajc.note.controller.UserController;
import it.unibs.pajc.note.model.Note;
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
	 * restituisce true se il login � possibile
	 * @param _name
	 * @param _pass
	 */
	public Boolean login(String _name, String _pass){
		Boolean validate = userController.login(_name, _pass);
		if (validate){
			utente= new User(_name, _pass);
			initializeNoteView();
			System.out.println("salvato utente: "+utente);
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
			System.out.println("salvato utente: "+utente);
		}
		return validate;
	}

	public ArrayList<Note> getMyNote(){
		return noteController.getMyNote(utente);
	}
	
	public ArrayList<String> getMyLabel(){
		return noteController.getMyLabel(utente);
	}
	
	public ArrayList<Note> getNotesByLabel(String label){
		return noteController.getNotesByLabel(label);
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


