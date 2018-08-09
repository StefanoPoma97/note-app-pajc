package it.unibs.pajc.note.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.Tag;
import it.unibs.pajc.note.status.ValidationError;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JToolBar;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import java.awt.Font;


public class NoteView extends JPanel {
	//Pannelli contenitivi
	private JPanel contentList;
	private JPanel contentButton;
	private JPanel contentNote;
	private JPanel contentModify;
	private JPanel contentInfo;
	private GridBagConstraints gc_1;
	private GridBagConstraints gc_2;
	private GridBagConstraints gc_3;
	private GridBagConstraints gc_4;
	
	//componenti interni
	private JButton btnRefresh;
	private JButton btnNewNote;
	private JButton btnExplore;
	private JButton btnPin;
	private JComboBox comboFilter;
	private JComboBox comboLabels;
	private JCheckBox chckbxPublic;
	private JComboBox comboColors;
	
	private String[] labels = new String[] {};
	private String[] filters = new String[] {};
	private String[] colors = new String[] {"", "White", "Yellow", "Green","Purple"};
	//array che conterrà solo le note visualizzabili in questo momento (non necessariamente tutto l'archivio)
	private ArrayList <Note> notes= new ArrayList<>();
	private Integer modifyID= null;
	private JTextField textFieldTitle;
	private JButton btnSave;
	private JTextArea textAreaNote;
	private JTextField textFieldTitleNote;
	private GridBagConstraints gbc_textFieldTitleNote;
	JLabel lbl_title = new JLabel();
	private NoteArchive noteArchive= new NoteArchive();
	
	private JTextField textFieldNewLabel;
	private JButton btnAddLabel;
	
	private ArrayList<String> temporanyLabels = new ArrayList<>();
	private boolean nuova=false;
	private boolean modifica=false;
	

	 /* metodo per far apparire messaggio di errore o segnalazione
	 * @param in stringa
	 */
	private void showMessage(String in){
		JOptionPane.showMessageDialog(null, in);
	}
	
	/**
	 * metodo per far apparire messaggio di errore o segnalazione
	 * @param in ValidateError
	 */
	private void showMessage(ValidationError in){
		JOptionPane.showMessageDialog(null, in.toString());
	}

	/**
	 * Create the panel. (Costruttore)
	 */
	public NoteView(MainView view) {
		loadInfo(view);
		buildContent(view);
		buildComponent(view);
		actionListener(view);
		
	}
	
	/**
	 * metodo per specificare gli actionListener di tutti i componenti
	 */
	private void actionListener(MainView view){
		//Action Listener
		
		
		
		comboColors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch ((String)comboColors.getSelectedItem()) {
				case "White":{
					textAreaNote.setBackground(Color.white);
					textFieldTitleNote.setBackground(Color.white);
					break;
				}
				case "Yellow":
					textAreaNote.setBackground(Color.yellow);
					textFieldTitleNote.setBackground(Color.yellow);
					break;
				case "Green":
					textAreaNote.setBackground(Color.green);
					textFieldTitleNote.setBackground(Color.green);
					break;
				case "Purple":
					textAreaNote.setBackground(Color.magenta);
					textFieldTitleNote.setBackground(Color.magenta);
					break;
				
					default:
						break;
							}
						}
		});
		
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshNoteList(view);
			}
		});
		
		comboFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch ((String)comboFilter.getSelectedItem()) {
				//TODO implementare ordinamento alfabetico per titolo
				case "Titolo":{
					noteArchive.removeAll();
					for(int i=notes.size()-1; i>=0; i--){
						noteArchive.add(notes.get(i));
					}
					refreshNoteList(view);
					break;
				}
				case "Dats":
					
					break;
					//TODO implementare ordinamento per numero Like
				case "Like":
					
					break;
				case "Pinned":
					
					break;
				case "Filters":
					break;
					
					default :
						break;
							}
			}
		});
		
		btnNewNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				temporanyLabels= new ArrayList<>();
				nuova=true;
				modifica=false;
				modifyID=null;
				textAreaNote.setText("");
				textFieldTitleNote.setText("");
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Note note = new Note(textFieldTitleNote.getText());
				note.setBody(textAreaNote.getText());
				System.out.println("DEVO AGGIUNGERE QUESTE temporany labels: "+temporanyLabels);
				note.addLabels(temporanyLabels);
				ValidationError validate;
				System.out.println("ID che sto modificando "+modifyID);
				if (modifyID==null){
					validate = view.addNote(note);
					if (validate.equals(ValidationError.TITLE_EMPTY)){
						showMessage(validate);
						return;
					}
					System.out.println("nota aggiunta");
					notes=view.getMyNote();
					textFieldTitleNote.setText("Select one note...");
					textAreaNote.setText("");
					temporanyLabels=null;
					nuova=false;
					refreshButton(view);
					repaint();
		
				}
					
				else{
					validate=view.update(note, modifyID);
					if (validate.equals(ValidationError.TITLE_EMPTY)){
						showMessage(validate);
						return;
					}
					System.out.println("nota aggiornata");
					notes=view.getMyNote();
					modifyID= null;
					textFieldTitleNote.setText("Select one note...");
					textAreaNote.setText("");
					temporanyLabels=new ArrayList<>();
					modifica=false;
					refreshButton(view);
					repaint();
		
				}
				
				refreshNoteList(view);
			}
		});
		
		btnAddLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textFieldNewLabel.getText().isEmpty()){
					
					showMessage("Label is empty");
				}
				else{
					if(view.addLabel(textFieldNewLabel.getText()) && view.getMyLabel().size()>10){
						showMessage("Max number of Label reach");
					}
					else{
						System.out.println("Label aggiunta la utente e salvata nelle temporanee");
						temporanyLabels.add(textFieldNewLabel.getText());
						textFieldNewLabel.setText("");
						System.out.println(temporanyLabels);
					}
				}
				
			}
		});
		
	}
	
	/**
	 * metodo per caricare tutte le informazioni per il primo avvio
	 * si appoggerà a NoteController che a sua volta tramite la classe Client richiederà
	 * al server tutte le info necessarie
	 */
	private void loadInfo(MainView view){
		//carico le mie note
		notes=view.getMyNote();
		
		//carico le mie labels
//		 HashSet<Tag> _labels = new  HashSet<Tag>();
//		_labels=(HashSet<Tag>)view.getMyLabel();
//		System.out.println(_labels);
//		Tag ar []= _labels.toArray(new Tag[_labels.size()]);
//		labels= (String[])Arrays.stream(ar)
//				.map(x->x.toString())
//				.toArray(size -> new String[size]);
		
		ArrayList<String> _labels= new ArrayList<>();
		_labels= view.getMyLabel();
		labels= _labels.toArray(new String [_labels.size()]);
		
		
		//carico i filtri
		filters=new String[] {"Filters", "Titolo", "Data", "Like", "Pinned"};	
	}
	
	

	/**
	 * appoggiandosi al arraylist notes crea la lista
	 */
	private void refreshNoteList(MainView view){

		contentList.removeAll();
		contentList.revalidate();
		contentList.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		for (int i=0; i<notes.size(); i++){
			
			gc = new GridBagConstraints();
			JLabel lbl_title = new JLabel();
			//titolo con una lunghezza massima
			String titolo=notes.get(i).getTitle();
			StringBuffer str= new StringBuffer();
			int count=0;
			for (char c : titolo.toCharArray()) {
			  if(count<15){
				  str.append(c);
			  }
			  else{
				  str.append("...");
				  break;
			  }
			  count++;
			}
			
			
			lbl_title.setText(str.toString());
			lbl_title.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_title.setVerticalAlignment(SwingConstants.CENTER);
			lbl_title.setPreferredSize(new Dimension(100, 20));
			gc.gridx = 0;
			gc.gridy = i;
			gc.fill=GridBagConstraints.BOTH;
			gc.insets = new Insets(10, 10, 10, 10);
			contentList.add(lbl_title,gc);
			
			gc = new GridBagConstraints();
			JLabel lbl_name = new JLabel();
			
			String corpo=notes.get(i).getBody();
			str= new StringBuffer();
			count=0;
			for (char c : corpo.toCharArray()) {
			  if(count<20){
				  str.append(c);
			  }
			  else{
				  str.append("...");
				  break;
			  }
			  count++;
			}
			lbl_name.setText(str.toString());
			lbl_name.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_name.setVerticalAlignment(SwingConstants.CENTER);
			lbl_name.setPreferredSize(new Dimension(300, 20));
			gc.gridx = 1;
			gc.gridy = i;
			gc.insets = new Insets(10, 10, 10, 10);
			gc.fill=GridBagConstraints.BOTH;
			contentList.add(lbl_name,gc);
			
			gc = new GridBagConstraints();
			JButton btn_modify = new JButton("M");
			btn_modify.setPreferredSize(new Dimension(20, 20));
			
			gc.gridx = 2;
			gc.gridy = i;
			gc.weightx=0;
			gc.insets = new Insets(10, 10, 10, 10);
			gc.fill=GridBagConstraints.BOTH;
			contentList.add(btn_modify,gc);
			btn_modify.setToolTipText("Modifica");
			btn_modify.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					temporanyLabels= new ArrayList<>();
					nuova=false;
					modifica=true;
					textFieldTitleNote.setText(lbl_title.getText());
					textAreaNote.setText(lbl_name.getText());
					temporanyLabels= view.getLabelsByNote(lbl_title.getText());
					System.out.println("TEMPORANY LABEL DI QUESTA NOTA: "+temporanyLabels);
					modifyID= view.getIDbyTitle(lbl_title.getText());
					System.out.println("ID Della nota selezionata:  "+modifyID);
				}
			});
			
		}
		
		contentList.repaint();
		textFieldTitleNote.setText("Select one note...");
		textAreaNote.setText("");
		repaint();
		System.out.println("refresh");
		
	}
	
	/**
	 * crea l'area di modifica per la nota selezionata
	 */
	private void createModifyNote(){
		contentNote.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
			
			gbc_textFieldTitleNote = new GridBagConstraints();
			textFieldTitleNote = new JTextField();
			textFieldTitleNote.setHorizontalAlignment(SwingConstants.LEFT);
//			textFieldTitle.setVerticalAlignment(SwingConstants.CENTER);
			textFieldTitleNote.setText("Select one note...");
			textFieldTitleNote.setPreferredSize(new Dimension(100, 30));
			gbc_textFieldTitleNote.weightx=1;
			gbc_textFieldTitleNote.gridx = 0;
			gbc_textFieldTitleNote.gridy = 0;
			gbc_textFieldTitleNote.gridwidth=2;
			gbc_textFieldTitleNote.fill=GridBagConstraints.HORIZONTAL;
			gbc_textFieldTitleNote.anchor= GridBagConstraints.LINE_START;
			gbc_textFieldTitleNote.insets = new Insets(10, 10, 10, 10);
			contentNote.add(textFieldTitleNote,gbc_textFieldTitleNote);
			
			gc = new GridBagConstraints();
			textAreaNote = new JTextArea();
			textAreaNote.setText("");
			JScrollPane scrollPaneNote= new JScrollPane(textAreaNote);
			gc.weightx=1;
			gc.weighty=1;
			gc.gridx = 0;
			gc.gridy = 1;
			gc.gridwidth=2;
			gc.fill=GridBagConstraints.BOTH;
			gc.anchor= GridBagConstraints.LINE_START;
			gc.insets = new Insets(10, 10, 10, 10);
			contentNote.add(scrollPaneNote,gc);
			
			gc = new GridBagConstraints();
			textFieldNewLabel = new JTextField();
			textFieldNewLabel.setText("");
			textFieldNewLabel.setPreferredSize(new Dimension(100, 30));
			gc.weightx=0;
			gc.weighty=0;
			gc.gridx = 0;
			gc.gridy = 2;
			gc.gridwidth=1;
			gc.anchor= GridBagConstraints.LINE_START;
			gc.insets = new Insets(10, 10, 10, 10);
			contentNote.add(textFieldNewLabel,gc);
			
			gc = new GridBagConstraints();
			btnAddLabel = new JButton();
			btnAddLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
			btnAddLabel.setToolTipText("");
			btnAddLabel.setText("add new label");
			btnAddLabel.setName("");
			btnAddLabel.setPreferredSize(new Dimension(80, 30));
			gc.weightx=0;
			gc.weighty=0;
			gc.gridx = 1;
			gc.gridy = 2;
			gc.gridwidth=1;
			gc.anchor= GridBagConstraints.LINE_START;
			gc.insets = new Insets(10, 10, 10, 10);
			contentNote.add(btnAddLabel,gc);
			
			
	}
	
	/**
	 * metodo per creare i vari componenti
	 */
	private void buildComponent(MainView view){
		//bottoni vari
		btnExplore = new JButton("Esplora");
		contentInfo.add(btnExplore);
		
		btnPin = new JButton("Pin");
		contentModify.add(btnPin);
		chckbxPublic = new JCheckBox("Public");
		contentModify.add(chckbxPublic);
		btnSave = new JButton("save");
		contentModify.add(btnSave);
		comboColors = new JComboBox();
		comboColors.setModel(new DefaultComboBoxModel(colors));
		contentModify.add(comboColors);
		
		
		refreshButton(view);
		
		//modifica nota
		createModifyNote();
		
		//lista note
		refreshNoteList(view);
		
		
		
		
		
	}
	
	private void refreshButton (MainView view){
		
		contentButton.removeAll();
		contentButton.revalidate();
		
		btnRefresh = new JButton("Refresh");
		btnNewNote = new JButton("+");	
		contentButton.add(btnNewNote);
		contentButton.add(btnRefresh);
		
		ArrayList<String> _labels= new ArrayList<>();
		_labels= view.getMyLabel();
		labels= _labels.toArray(new String [_labels.size()]);
		comboLabels = new JComboBox();
		comboLabels.setModel(new DefaultComboBoxModel(labels));
		comboLabels.setToolTipText("");
		comboLabels.setSelectedItem("Labels");
		comboFilter = new JComboBox();
		comboFilter.setModel(new DefaultComboBoxModel(filters));
		comboFilter.setToolTipText("");
		contentButton.add(comboLabels);
		contentButton.add(comboFilter);
		
		comboLabels.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String label = (String)comboLabels.getSelectedItem();
				if (label=="Labels"){ //tutte le note
					notes=view.getMyNote();
				}
				else
					//note con un particolare label
					notes=view.getNotesByLabel(label);
				refreshNoteList(view);
			}
		});
		contentButton.repaint();
		
	}
	
	/**
	 * metodo per la creazione dell'interfazzia utilizzando il GridBagLayout
	 */
	private void buildContent(MainView view){
		//setta il tipo di layout da utilizzare
		GridBagLayout gridBagLayout = new GridBagLayout();
		this.setLayout(gridBagLayout);
		
		//row 0
			//col 0  Pannello Buttons
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 5, 5);
		this.contentButton = new JPanel();
		contentButton.setBackground(Color.LIGHT_GRAY);
		this.contentButton.setPreferredSize(new Dimension(300, 50));
		gc.weightx=0; //crescita dello 0% lungo l'asse X
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill=GridBagConstraints.BOTH; //riempie tutto lo spazio a disposizione in tutte le direzioni
		gc.anchor = GridBagConstraints.SOUTHWEST;
		this.add(this.contentButton, gc);
		contentButton.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
			//col 1
		gc_1 = new GridBagConstraints();
		gc_1.insets = new Insets(0, 0, 5, 0);
		this.contentInfo = new JPanel();
		this.contentInfo.setPreferredSize(new Dimension(100, 50));
		contentInfo.setBackground(new Color(0, 0, 255));
		gc_1.weightx=1; //cresce completamente 100% lungo X
		gc_1.gridx = 1;
		gc_1.gridy = 0;
		gc_1.fill=GridBagConstraints.HORIZONTAL; //occupa lo spazio a disposizione solo in orizzontale
		gc_1.anchor = GridBagConstraints.LINE_START; //si fissa al inizio della cella
		this.add(this.contentInfo, gc_1);
		
		
		
		//row 1
			//col 0
		
		gc_2 = new GridBagConstraints();
		gc_2.insets = new Insets(0, 0, 5, 5);
		gc_2.anchor = GridBagConstraints.WEST;
		this.contentList = new JPanel();
		this.contentList.setPreferredSize(new Dimension(300, 30));
		gc_2.weightx=0;
		gc_2.weighty=1.0;
		gc_2.gridx = 0;
		gc_2.gridy = 1;
		gc_2.fill=GridBagConstraints.BOTH;
		this.add(this.contentList, gc_2);
		
		
			//col 1
		gc_3 = new GridBagConstraints();
		gc_3.insets = new Insets(0, 0, 5, 0);
		this.contentNote = new JPanel();
		contentNote.setBackground(new Color(240, 230, 140));
//		contentNote.setLayout(null);
		gc_3.weightx=1;
		gc_3.weighty=1.0;
		gc_3.gridx = 1;
		gc_3.gridy = 1;
		gc_3.fill=GridBagConstraints.BOTH;
//		gc.insets = new Insets(10, 10, 0, 0);
		gc_3.anchor = GridBagConstraints.LINE_START;
		this.add(this.contentNote, gc_3);
		
		//row 2
			//col 0 vuota
			//col 1
		gc_4 = new GridBagConstraints();
		gc_4.insets = new Insets(0, 0, 5, 0);
		this.contentModify = new JPanel();
		contentModify.setBackground(new Color(255, 255, 0));
		this.contentModify.setPreferredSize(new Dimension(100, 50));
		gc_4.weightx=0.001;
		gc_4.gridx = 1;
		gc_4.gridy = 2;
		gc_4.fill=GridBagConstraints.HORIZONTAL;
//		gc.insets = new Insets(10, 10, 0, 0);
		gc_4.anchor = GridBagConstraints.LINE_START;
		this.add(this.contentModify, gc_4);

		
		
		
		
		
	}
}
