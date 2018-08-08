package it.unibs.pajc.note.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.model.Note;

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
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;


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
	


	/**
	 * Create the panel. (Costruttore)
	 */
	public NoteView(MainView view) {
		loadInfo(view);
		buildContent();
		buildComponent();
		actionListener(view);
		
	}
	
	/**
	 * metodo per specificare gli actionListener di tutti i componenti
	 */
	private void actionListener(MainView view){
		//Action Listener
		
		comboLabels.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String label = (String)comboLabels.getSelectedItem();
				if (label==""){ //tutte le note
					notes=view.getMyNote();
				}
				else
					notes=view.getNotesByLabel(label);
				refreshNoteList();
			}
		});
		
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
				refreshNoteList();
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
					refreshNoteList();
					break;
				}
				case "Dats":
					
					break;
					//TODO implementare ordinamento per numero Like
				case "Like":
					
					break;
				case "Pinned":
					
					break;
				
					
					default:
						break;
							}
			}
		});
		
		btnNewNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifyID=null;
				textAreaNote.setText("");
				textFieldTitleNote.setText("");
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Note note = new Note(textFieldTitleNote.getText());
				note.setBody(textAreaNote.getText());
				if (modifyID==null)
					noteArchive.add(note);
				else
					noteArchive.update(note, modifyID);
				
				modifyID= null;
				notes= (ArrayList<Note>)noteArchive.all();
				refreshNoteList();
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
		ArrayList<String> _labels = new ArrayList<>();
		_labels=view.getMyLabel();
		_labels.add(0, "");
		labels=_labels.toArray(new String[_labels.size()]);
		
		//carico i filtri
		filters=new String[] {"", "Titolo", "Data", "Like", "Pinned"};	
	}
	
	

	/**
	 * appoggiandosi al arraylist notes crea la lista
	 */
	private void refreshNoteList(){

		contentList.removeAll();
		contentList.revalidate();
		contentList.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		for (int i=0; i<notes.size(); i++){
			
			gc = new GridBagConstraints();
			JLabel lbl_title = new JLabel();
			lbl_title.setText(notes.get(i).getTitle());
			lbl_title.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_title.setVerticalAlignment(SwingConstants.CENTER);
			lbl_title.setPreferredSize(new Dimension(600, 20));
			gc.gridx = 0;
			gc.gridy = i;
			gc.fill=GridBagConstraints.BOTH;
			gc.insets = new Insets(10, 10, 10, 10);
			contentList.add(lbl_title,gc);
			
			//TODO trovare un modo per non far comparire tutto il testo ma solo TOT caratteri
			gc = new GridBagConstraints();
			JLabel lbl_name = new JLabel();
			lbl_name.setText(notes.get(i).getBody());
			lbl_name.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_name.setVerticalAlignment(SwingConstants.CENTER);
			lbl_name.setPreferredSize(new Dimension(600, 20));
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
			gc.insets = new Insets(10, 10, 10, 10);
			gc.fill=GridBagConstraints.BOTH;
			contentList.add(btn_modify,gc);
			btn_modify.setToolTipText("Modifica");
			btn_modify.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textFieldTitleNote.setText(lbl_title.getText());
					textAreaNote.setText(lbl_name.getText());
					modifyID= noteArchive.getWhere(x->x.getTitle().equals(lbl_title.getText())).get(0).getID();
				}
			});
			
		}
		contentList.repaint();
		repaint();
		
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
			textFieldTitleNote.setText(notes.get(0).getTitle());
			textFieldTitleNote.setPreferredSize(new Dimension(100, 30));
			gbc_textFieldTitleNote.weightx=1;
			gbc_textFieldTitleNote.gridx = 0;
			gbc_textFieldTitleNote.gridy = 0;
			gbc_textFieldTitleNote.fill=GridBagConstraints.HORIZONTAL;
			gbc_textFieldTitleNote.anchor= GridBagConstraints.LINE_START;
			gbc_textFieldTitleNote.insets = new Insets(10, 10, 10, 10);
			contentNote.add(textFieldTitleNote,gbc_textFieldTitleNote);
			
			gc = new GridBagConstraints();
			textAreaNote = new JTextArea();
			textAreaNote.setText(notes.get(0).getBody());
			JScrollPane scrollPaneNote= new JScrollPane(textAreaNote);
			gc.weightx=1;
			gc.weighty=1;
			gc.gridx = 0;
			gc.gridy = 1;
			gc.fill=GridBagConstraints.BOTH;
			gc.anchor= GridBagConstraints.LINE_START;
			gc.insets = new Insets(10, 10, 10, 10);
			contentNote.add(scrollPaneNote,gc);
			
	}
	
	/**
	 * metodo per creare i vari componenti
	 */
	private void buildComponent(){
		//bottoni vari
		btnRefresh = new JButton("Refresh");
		btnNewNote = new JButton("+");
		comboLabels = new JComboBox();
		comboLabels.setModel(new DefaultComboBoxModel(labels));
		comboLabels.setToolTipText("");
		comboFilter = new JComboBox();
		comboFilter.setModel(new DefaultComboBoxModel(filters));
		comboFilter.setToolTipText("");
		
		contentButton.add(btnNewNote);
		contentButton.add(btnRefresh);
		contentButton.add(comboLabels);
		contentButton.add(comboFilter);
		
		
		btnExplore = new JButton("Esplora");
		contentInfo.add(btnExplore);
		
		btnPin = new JButton("Pin");
		contentModify.add(btnPin);
		chckbxPublic = new JCheckBox("Public");
		contentModify.add(chckbxPublic);
		comboColors = new JComboBox();
		
		comboColors.setModel(new DefaultComboBoxModel(colors));
		comboLabels.setToolTipText("");
		contentModify.add(comboColors);
		
		btnSave = new JButton("save");
		contentModify.add(btnSave);
		
		//lista note
		refreshNoteList();
		
		//modifica nota
		createModifyNote();
		
		
		
		
		
		
	}
	
	/**
	 * metodo per la creazione dell'interfazzia utilizzando il GridBagLayout
	 */
	private void buildContent(){
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
