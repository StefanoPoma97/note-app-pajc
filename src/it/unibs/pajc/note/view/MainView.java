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

import it.unibs.pajc.note.client_server.Client;
import it.unibs.pajc.note.client_server.Comunication;
import it.unibs.pajc.note.controller.NoteController;
import it.unibs.pajc.note.controller.UserController;
import it.unibs.pajc.note.data.UserArchive;
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
	
	//Explore view
	private ExploreView exploreView= null;
	
	//componenti utilizzati
	private JTextArea textArea;
	private JPanel contentPanel;
	private ExploreView exploreView_1;
	
	private Client client= new Client();
	
	

	
	
	//Metodi per LoginView
	
	public String connetti(){
		return client.connetti();
	}
	
	/**
	 * Metodo utilizzato nel LoginView che permette di passare nome password 
	 * restituisce true se il login ï¿½ possibile
	 * @param _name
	 * @param _pass
	 */
//	public Boolean login(String _name, String _pass){
//		Boolean validate = userController.login(_name, _pass);
//		if (validate){
//			utente= new User(_name, _pass);
//			initializeNoteView();
//		}
//			
//		return validate;
//	}
	
	public Boolean login (String _name, String _pass){
		Comunication input= new Comunication();
		input.setInfo("login");
		input.setLogin(_name, _pass);
		Comunication output = client.comunica(input);
		if (output.getLoginResult()){
			utente= new User(_name, _pass);
			initializeNoteView();
		}
			
		return output.getLoginResult();
	}
	
	/**
	 * metodo che permette di passare nome e password per la creazione di un nuovo account
	 * @param name
	 * @param pass
	 * @return ValidationError
	 */
//	public ValidationError create(String name, String pass){
//		ValidationError validate = userController.create(name, pass);
//		if (validate.equals(ValidationError.CORRECT)){
//			utente= new User(name, pass);
//			initializeNoteView();
//		}
//		return validate;
//	}
	
	public ValidationError create(String _name, String _pass){
		Comunication input= new Comunication();
		input.setInfo("create");
		input.setLogin(_name, _pass);
		Comunication output = client.comunica(input);
		ValidationError validate= output.getCreateResult();
		if (validate.equals(ValidationError.CORRECT)){
			utente= new User(_name, _pass);
			initializeNoteView();
		}
		return validate;
	}

	
	//metodi per NoteView
	
	/**
	 * restituisce tutte le note associate al utente del login
	 * @return
	 */
//	public ArrayList<Note> getMyNote(){
//		return noteController.getMyNote(utente);
//	}
	
	public ArrayList<Note> getMyNote(){
		Comunication input= new Comunication();
		input.setInfo("load_notes");
		input.setUser(utente);


		Comunication output= new Comunication();
		output= client.comunica(input);
		return output.getNotes();
	}
	
	
	/**
	 * restituisce tutte le label associate all'utente del login
	 * @return
	 */
//	public ArrayList<String> getMyLabel(){
//		return userController.getLabelsByUser(utente);
//	}
	
	
	public ArrayList<String> getMyLabel(){
		Comunication input= new Comunication();
		input.setInfo("load_labels");
		input.setUser(utente);


		Comunication output= new Comunication();
		output= client.comunica(input);
		return output.getLabels();
	}
	
	/**
	 * confronta le label salvate sull'utente e quelle salvate solamente sulle note
	 * se le label associate all'utente non sono connesse a nessuna nota vengono eliminate
	 */
//	public void updateMyLabels(){
//		ArrayList<String> userLabels = userController.getLabelsByUser(utente);
//		ArrayList<String> noteLabels = new ArrayList<>();
//		for (Note nota: noteController.getMyNote(utente)){
//			noteLabels.addAll(noteController.getLabelsByNote(nota.getTitle(), utente));
//		}
//		ArrayList<String> userLabels_cp= new ArrayList<>(userLabels);
//		userLabels_cp.removeAll(noteLabels);
//		userLabels.removeAll(userLabels_cp);
//		userLabels.add(0, "Labels");
//		userController.updateLabel(userLabels, utente);
//		
//	}
	
	public void updateMyLabels(){
		
		
		ArrayList<String> userLabels = getMyLabel();
		ArrayList<String> noteLabels = new ArrayList<>();
		for (Note nota: getMyNote()){
			
			noteLabels.addAll(getLabelsByNote(nota.getTitle()));
		}
		ArrayList<String> userLabels_cp= new ArrayList<>(userLabels);
		userLabels_cp.removeAll(noteLabels);
		userLabels.removeAll(userLabels_cp);
		userLabels.add(0, "Labels");
		
		Comunication input= new Comunication();
		input.setInfo("update_label");
		input.setUser(utente);
		input.setLabels(userLabels);
		Comunication output2= client.comunica(input);
		
	}

	/**
	 * aggiunge una label all'utente del login
	 * TODO meglio Set?
	 * @param label
	 * @return
	 */
//	public boolean addLabel(String label){
//		return userController.addLabel(label, utente);
//	}
	
	public boolean addLabel(String label){
		Comunication input= new Comunication();
		input.setInfo("add_label");
		input.setTitle(label);
		input.setUser(utente);
		
		Comunication out=client.comunica(input);
		return userController.addLabel(label, utente);
	}
	
	/**
	 * restituisce le note associate ad una label
	 * @param label
	 * @return
	 */
//	public ArrayList<Note> getNotesByLabel(String label){
//		return noteController.getNotesByLabel(label, utente);
//	}
	
	public ArrayList<Note> getNotesByLabel(String label){
		Comunication input= new Comunication();
		input.setInfo("get_notes_by_label");
		input.setTitle(label);
		input.setUser(utente);
		
		Comunication output= client.comunica(input);
		return output.getNotes();
	}
	
	/**
	 * restituisce le labels associate ad una nota
	 * @param title
	 * @return
	 */
//	public ArrayList<String> getLabelsByNote(String title){
//		return noteController.getLabelsByNote(title, utente);
//	}
	
	public ArrayList<String> getLabelsByNote(String title){
		Comunication input= new Comunication();
		input.setInfo("get_labels_by_note");
		input.setTitle(title);
		input.setUser(utente);
		
		Comunication output= client.comunica(input);
		return output.getLabels();
		
	}
	
	/**
	 * metodo per aggiungere una nota
	 * @param n
	 * @return
	 */
//	public ValidationError addNote (Note n){
//		n.setAutor(utente);
//		return noteController.addNote(n);
//	}
	
	public ValidationError addNote (Note n){
		n.setAutor(utente);
		Comunication input= new Comunication();
		input.setInfo("add_note");
		input.setNote(n);
		
		Comunication output= client.comunica(input);
		return output.getCreateResult();
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
	
//	public ValidationError exUpdate(Note n, int ID){
//		return noteController.update(n, ID);
//	}
//	
	public ValidationError exUpdate(Note n, int ID){
		Comunication input= new Comunication();
		input.setInfo("update");
		input.setNote(n);
		input.setID(ID);
		
		Comunication output= client.comunica(input);
		return output.getCreateResult();
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
	
	
	//metodi per Explore view
	public void exploreView(){
		initializeExploreView();
		noteView.setVisible(false);
		
	}
	
	public void noteView(){
		initializeNoteView();
		exploreView.setVisible(false);
	}
	
	public ArrayList<Note> getAllNote(){
		return noteController.getAllNote(utente);
	}
	
	public ArrayList<Note> exFilterByTitle(){
		return noteController.exFilterByTitle(utente);
	}
	
	public ArrayList<Note> exFilterByData(){
		return noteController.exFilterByData(utente);
	}
	
	public ArrayList<Note> exFilterByLike(){
		return noteController.exFilterByLike(utente);
	}
	
	public ArrayList<Note> exFilterByAuthor(){
		return noteController.exFilterByAuthor(utente);
	}
	
	public ArrayList<Note> shareWithMe(){
		return noteController.shareWithMe(utente);
	}
	
	public boolean isShare(Note n){
		ArrayList<User> us= new ArrayList<>(n.getSharedWith());
		return us.contains(utente);
	}
	
	public Note getNoteByTitle(String n){
		return noteController.getNoteByTitle(n, utente);
	}
	
	public Note getNoteByTitleLike(String n){
		return noteController.getNoteByTitleLike(n, utente);
	}
	
	public Note addLikedUser(Note n){
		n.addLikedUser(utente);
		return n;
	}
	
	public Note removeLikedUser(Note n){
		n.removeLikedUser(utente);
		return n;
	}
	
	public boolean iLikeThisNote (Note n){
		return n.getLikedBy().contains(utente);
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
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.rowWeights = new double[]{0.0, 1.0};
		gbl_contentPanel.columnWeights = new double[]{1.0};
		contentPanel.setLayout(gbl_contentPanel);
		
		//GridBgLayout per il pannello principale
		loginView = new LoginView(this);
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 5, 0);
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
	
	private void initializeExploreView(){
		exploreView = new ExploreView(this);
		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx=1;
		gc.weighty=1;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill= GridBagConstraints.BOTH;
		contentPanel.add(exploreView, gc);
	}
}


