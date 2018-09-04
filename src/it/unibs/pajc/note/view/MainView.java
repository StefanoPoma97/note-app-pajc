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
	
	/**
	 * metodo per connettere il client
	 * @return String che indica se la connessione e' avvenuta
	 * @author Stefano Poma
	 */
	public String connetti(){
		return userController.connetti(client);
	}
	
	/**
	 * metodo per effettuare il login
	 * @param _name
	 * @param _pass
	 * @return true se il login e' avvenuto
	 * @author Stefano Poma
	 */
	public Boolean login (String _name, String _pass){
		
		Comunication output = userController.login(client, _name, _pass);
		if (output.getLoginResult()){
			utente= new User(_name, _pass);
			initializeNoteView();
		}
			
		return output.getLoginResult();
	}
	
	/**
	 * metodo per effettuare il logout, riporta l'utente alla schermata di login
	 * @author Stefano Poma
	 */
	public void logOut(){
		noteView.setVisible(false);
		initializeLoginView();
	}
	
	/**
	 * metodo che permette di passare nome e password per la creazione di un nuovo account
	 * @param name
	 * @param pass
	 * @return ValidationError
	 * @author Stefano Poma
	 */
	
	public ValidationError create(String _name, String _pass){
		
		ValidationError validate= userController.create(client, _name, _pass);
		if (validate.equals(ValidationError.CORRECT)){
			utente= new User(_name, _pass);
			initializeNoteView();
		}
		return validate;
	}

	
	//metodi per NoteView
	
	/**
	 * restituisce tutte le note associate al utente del login
	 * @return Arraylist delle note 
	 * @author Stefano Poma
	 */
	public ArrayList<Note> getMyNote(){
		return noteController.getMyNote(client, utente);
	}
	
	
	/**
	 * restituisce tutte le label associate all'utente del login
	 * @return arrayList contenente le labels
	 * @author Stefano Poma
	 */
	public ArrayList<String> getMyLabel(){
		return userController.getLabelsByUser(client, utente);
	}
	
	/**
	 * confronta le label salvate sull'utente e quelle salvate solamente sulle note
	 * se le label associate all'utente non sono connesse a nessuna nota vengono eliminate
	 * @author Stefano Poma
	 */
	public void updateMyLabels(){
		
		//TODO rendere piï¿½ efficiente
		ArrayList<String> userLabels = getMyLabel();
		ArrayList<String> noteLabels = new ArrayList<>();
		for (Note nota: getMyNote()){
			
			noteLabels.addAll(getLabelsByNote(nota.getTitle()));
		}
		ArrayList<String> userLabels_cp= new ArrayList<>(userLabels);
		userLabels_cp.removeAll(noteLabels);
		userLabels.removeAll(userLabels_cp);
		userLabels.add(0, "Labels");
		
		userController.updateLabel(client, userLabels, utente);
		
	}

	/**
	 * aggiunge una label all'utente del login
	 * @param label
	 * @return true se e' stata aggiunta con successo
	 * @author Stefano Poma
	 */
	public boolean addLabel(String label){
		return userController.addLabel(client, label, utente);
	}
	
	/**
	 * restituisce le note associate ad una label
	 * @param label
	 * @return ArrayList delle note associate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> getNotesByLabel(String label){
		return noteController.getNotesByLabel(client, label, utente);
	}
	
	/**
	 * restituisce le labels associate ad una nota
	 * @param title
	 * @return ArrayList delle labels associte
	 */
	public ArrayList<String> getLabelsByNote(String title){
		return noteController.getLabelsByNote(client, title, utente);
		
	}
	
	/**
	 * metodo per aggiungere una nota
	 * @param n
	 * @return Validation error che indica se l'operazione e' avvenuta
	 * @author Stefano Poma
	 */
	public ValidationError addNote (Note n){
		n.setAutor(utente);
		return noteController.addNote(client, n);
	}
	
	/**
	 * metodo per aggiornare una nota giï¿½ essitente
	 * @param n
	 * @param ID
	 * @return Validation error che indica se l'operazione e' avvenuta
	 * @author Stefano Poma
	 */
	public ValidationError update(Note n, int ID){
		n.setAutor(utente);
		return exUpdate(n, ID);
	}
	
	/**
	 * metodo per aggiornare una nota giï¿½ essitente nella sezione esplora
	 * @param n
	 * @param ID
	 * @return Validation error che indica se l'operazione e' avvenuta
	 * @author Stefano Poma
	 */
	public ValidationError exUpdate(Note n, int ID){
		return noteController.update(client, n, ID);
	}
	
	/**
	 * restituisce ID di una nota in base al titolo
	 * @param title
	 * @return ID associato al titolo
	 * @author Stefano Poma
	 * 
	 */
	public int getIDbyTitle(String title){
		return noteController.getIDbyTitle(client, title);
		
	}
	
	/**
	 * una nota e' segnata
	 * @param titolo
	 * @return true se e' segnata
	 * @author Stefano Poma
	 */
	public Boolean isPinned(String titolo){
		return noteController.isPinned(client, titolo, utente);
	}
	
	/**
	 * la nota e' pubblica
	 * @param titolo
	 * @return true se e' pubblica
	 * @author Stefano Poma
	 */
	public Boolean isPublic(String titolo){
		return noteController.isPublic(client, titolo, utente);
	}

	/**
	 * filtra le note tramite titolo
	 * @return ArrayList di note filtrate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> FilterByTitle(){
		return noteController.FilterByTitle(client, utente);
	}
	
	/**
	 * filtra le note tramite pin
	 * @return ArrayList di note filtrate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> FilterByPin(){
		return noteController.FilterByPin(client, utente);
	}
	
	/**
	 * filtra le note per like
	 * @return ArrayList di note filtrate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> FilterByLike(){
		return noteController.FilterByLike(client, utente);
	}
	
	/**
	 * filtra le note per data
	 * @return ArrayList di note filtrate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> FilterByData(){
		return noteController.FilterByData(client, utente);
	}
	
	/**
	 * restituisce tutti gli utenti
	 * @return ArrayList con tutti gli utenti
	 * ArrayList di note filtate
	 * @author Stefano Poma
	 */
	public ArrayList<User> getAllUsers(){
		return userController.getAllUsers(client, utente);
	}
	
	/**
	 * restituisce gli utenti condivisi
	 * @param title
	 * @return Set con gli utenti condivisi
	 * @author Stefano Poma
	 */
	public Set<User> getSharredUser(String title){
		return noteController.getSharredUser(client, title, utente);
		
	}
	
	
	//metodi per Explore view
	/**
	 * metodo per aprire la sezione Esplora ed oscurare le altr
	 * @author Stefano Poma
	 */
	public void exploreView(){
		initializeExploreView();
		noteView.setVisible(false);
		
	}
	
	/**
	 * metodo per aprire la sezione NoteView ed oscurare le altre
	 * @author Stefano Poma
	 */
	public void noteView(){
		initializeNoteView();
		exploreView.setVisible(false);
	}
	
	/**
	 * metodo per restituire tutte le note di un utente
	 * @return ArrayList con tutte le note dell'utente
	 * @author Stefano Poma
	 */
	public ArrayList<Note> getAllNote(){
		return noteController.getAllNote(client, utente);
	}
	
	/**
	 * filtra le note nella sezione eslora per titolo
	 * @return ArrayList di note filtrate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> exFilterByTitle(){
		return noteController.exFilterByTitle(client, utente);
	}
	
	/**
	 * filtra le note nella sezione esplora per data
	 * @return ArrayList di note filtrate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> exFilterByData(){
		return noteController.exFilterByData(client, utente);
	}
	
	/**
	 * filtra le note nella sezione esplora per like
	 * @return ArrayList di note filtrate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> exFilterByLike(){
		return noteController.exFilterByLike(client, utente);
	}
	
	/**
	 * filtra le note nella sezione esplora per autore
	 * @return ArrayList di note filtrate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> exFilterByAuthor(){
		return noteController.exFilterByAuthor(client, utente);
	}
	
	/**
	 * ritorna condivise con l'utente selezionato
	 * @return ArrayList di note filtrate
	 * @author Stefano Poma
	 */
	public ArrayList<Note> shareWithMe(){
		return noteController.shareWithMe(client, utente);
	}
	
	/**
	 * data una nota controlla se è condivisa con l'utente in questione
	 * @param n
	 * @return true se è condivisa con l'utente selezionato
	 * @author Stefano Poma
	 */
	public boolean isShare(Note n){
		ArrayList<User> us= new ArrayList<>(n.getSharedWith());
		return us.contains(utente);
	}
	
	/**
	 * restituisce una nota dato il suo titolo
	 * @param n
	 * @return nota con titolo=n
	 * @author Stefano Poma
	 */
	public Note getNoteByTitle(String n){
		return noteController.getNoteByTitle(client, n, utente);
	}
	
	/**
	 * restituisce una nota dato il suo titolo
	 * @param n
	 * @return nota con titolo uguale alla stringa in ingresso
	 * @author Stefano Poma
	 */
	public Note getNoteByTitleLike(String n){
		return noteController.getNoteByTitleLike(client, n, utente);
	}
	
	/**
	 * aggiunge un like del dato utente alla nota in questione
	 * @param n
	 * @return nota aggiornata
	 * @author Stefano Poma
	 */
	public Note addLikedUser(Note n){
		n.addLikedUser(utente);
		return n;
	}
	
	/**
	 * rimuove il like del dato utente alla nota
	 * @param n
	 * @return nota aggiornata
	 * @author Stefano Poma
	 */
	public Note removeLikedUser(Note n){
		n.removeLikedUser(utente);
		return n;
	}
	
	/**
	 * specifica se l'utente selezionato ha lasciato like a una nota
	 * @param n
	 * @return true se ha lasciato like
	 * @author Stefano Poma
	 */
	public boolean iLikeThisNote (Note n){
		return n.getLikedBy().contains(utente);
	}
	
	/**
	 * dato un ID restituisce la nota associata
	 * @param ID
	 * @return nota con il dato ID
	 * @author Stefano Poma
	 */
	public Note getNotebyID(int ID){
		return noteController.getNotebyID(client, ID);
	}
	
	/**
	 * comando che permette il salvataggio su file degli archivi
	 * @author Stefano Poma
	 */
	public void saveOnFile(){
		userController.saveOnFile(client);
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
	
	/**
	 * metodo per inizializzare la schermata di Login
	 */
	private void initializeLoginView(){
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
	
	/**
	 * metodo per inizializzare la schermata di visualizzazione delle note
	 */
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
	
	/**
	 * metodo per inizializzare la schermata esplora
	 */
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


