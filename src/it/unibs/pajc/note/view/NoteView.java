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
	//array che conterr� solo le note visualizzabili in questo momento (non necessariamente tutto l'archivio)
	private ArrayList <Note> notes= new ArrayList<>();
	private Note modifyNote= null;
	


	/**
	 * Create the panel. (Costruttore)
	 */
	public NoteView(MainView view) {
		loadInfo();
		buildContent();
		buildComponent();
		actionListener();
		
	}
	
	/**
	 * metodo per specificare gli actionListener di tutti i componenti
	 */
	private void actionListener(){
		//Action Listener
		comboColors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch ((String)comboColors.getSelectedItem()) {
				case "White":
					contentNote.setBackground(Color.WHITE);
					break;
				case "Yellow":
					contentNote.setBackground(Color.YELLOW);
					break;
				case "Green":
					contentNote.setBackground(Color.GREEN);
					break;
				case "Purple":
					contentNote.setBackground(Color.MAGENTA);
					break;
					
					default:
						break;
							}
						}
		});
	}
	
	/**
	 * metodo per caricare tutte le informazioni per il primo avvio
	 * si appogger� a NoteController che a sua volta tramite la classe Client richieder�
	 * al server tutte le info necessarie
	 */
	private void loadInfo(){
		labels=new String[] {"", "Riunione", "Memo", "Link"};
		filters=new String[] {"", "Titolo", "Autore", "Like"};
		ArrayList<Note> _list= new ArrayList<>();
		for (int i=0; i<50; i++){
			Note nota = new Note("titolo"+i);
			nota.setBody("corpo della nota numero: "+i);
			_list.add(nota);
		}
		notes=_list;	
	}
	
	

	private void refreshNoteList(){

		contentList.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		for (int i=0; i<notes.size(); i++){
			
			gc = new GridBagConstraints();
			JLabel lbl_title = new JLabel(notes.get(i).getTitle());
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
			JLabel lbl_name = new JLabel(notes.get(i).getBody());
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
					System.out.println("ho selezionato la nota "+lbl_title.getText());
				}
			});
			
		}
		
		
	}
	
	/**
	 * metodo per creare i vari componenti
	 */
	private void buildComponent(){
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
		refreshNoteList();
		
		
		
		
		
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
		contentNote.setLayout(null);
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
