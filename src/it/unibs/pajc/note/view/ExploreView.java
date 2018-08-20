package it.unibs.pajc.note.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;


public class ExploreView extends JPanel {
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
	private JButton btnShare;
	private JComboBox comboLabelsAdd;
	private JComboBox comboFilter;
	private JComboBox comboLabels;
	private JCheckBox chckbxPublic;
	private JButton comboColors;
	private JPanel panelLabels;
	private JPanel panelShare;
	
	private String[] labels = new String[] {};
	private String[] filters = new String[] {};
	private String[] colors = new String[] {"", "White", "Yellow", "Green","Purple"};
	//array che conterrï¿½ solo le note visualizzabili in questo momento (non necessariamente tutto l'archivio)
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
	private ArrayList<String> actualLabels = new ArrayList<>();
	private boolean nuova=false;
	private boolean modifica=false;
	private Object[] colours={};
	private Set<User> sharedUser= new HashSet<>();
	

	 /* metodo per far apparire messaggio di errore 
	 * @param in stringa
	 */
	private void showErrorMessage(String in){
		JOptionPane.showMessageDialog(this,
			    in,
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * metodo per far apparire messaggio di errore 
	 * @param in ValidateError
	 */
	private void showErrorMessage(ValidationError in){
		JOptionPane.showMessageDialog(this,
			    in.toString(),
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * metodo per far apparire un messaggio di segnalazione
	 * @param in
	 */
	private void showInfoMessage(ValidationError in){
		JOptionPane.showMessageDialog(null, in.toString());
	}
	
	/**
	 * metodo per far apparire un messaggio di segnalazione
	 * @param in
	 */
	private void showInfoMessage(String in){
		JOptionPane.showMessageDialog(null, in);
	}

	/**
	 * Create the panel. (Costruttore)
	 */
	public ExploreView(MainView view) {
//		loadInfo(view);
		buildContent(view);
//		buildComponent(view);
	}
	
	/**
	 * metodo per caricare tutte le informazioni per il primo avvio
	 * si appoggerï¿½ a NoteController che a sua volta tramite la classe Client richiederï¿½
	 * al server tutte le info necessarie
	 */
	private void loadInfo(MainView view){
		//carico le mie note
		notes=view.getMyNote();
		
		ArrayList<String> _labels= new ArrayList<>();
		_labels= view.getMyLabel();
		labels= _labels.toArray(new String [_labels.size()]);
		
		//carico i filtri
		filters=new String[] {"Filters", "Titolo", "Data", "Like", "Pinned"};	
	}
	


	
	/**
	 * metodo per creare i vari componenti e le funzioni
	 */
	private void buildComponent(MainView view){
		
		refreshButtonModify(view);

		refreshButton(view);
		
		//modifica nota
		createModifyNote(view);
		
		//lista note
		refreshNoteList(view);
	
	}
	
	
	/**
	 * metodo che aggiorna e scrive la lista delle nostre note
	 * aggiunge actionlistener al bottone per modificare la singola nota
	 * TODO implementare una Scroll bar funzionante
	 */
	private void refreshNoteList(MainView view){

		contentList.removeAll();
		contentList.revalidate();
		
		contentList.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		for (int i=0; i<notes.size(); i++){
			
			gc = new GridBagConstraints();
			JLabel lbl_title = new JLabel();
			String titolo=notes.get(i).getTitle();
			StringBuffer str= new StringBuffer();
			int count=0;
			//per ragioni di semplicità il titolo non può contenere più di 15 caratteri
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
			lbl_title.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					lbl_title.setForeground(Color.BLUE);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					lbl_title.setForeground(new JLabel().getForeground());
				}
			});
			gc.gridx = 0;
			gc.gridy = i;
			gc.fill=GridBagConstraints.BOTH;
			gc.insets = new Insets(10, 10, 10, 10);
			contentList.add(lbl_title,gc);
			
			gc = new GridBagConstraints();
			JLabel lbl_name = new JLabel();
			
			//per ragioni di semplicità il corpo, nella isualizzazione ad elenco, non può contenere più di 20 caratteri
			String corpo=notes.get(i).getBody();
			str= new StringBuffer();
			count=0;
			for (char c : corpo.toCharArray()) {
			  if(count<15){
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
			lbl_name.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					lbl_name.setForeground(Color.BLUE);
//					lbl_name.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
				}
				@Override
				public void mouseExited(MouseEvent e) {
					lbl_name.setForeground(new JLabel().getForeground());
//					lbl_name.setBorder(null);
				}
			});
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
			btn_modify.setActionCommand(corpo);
			btn_modify.setEnabled(false);
			btn_modify.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					btn_modify.setEnabled(true);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					btn_modify.setEnabled(false);
				}
			});
			btn_modify.setToolTipText("Modify this note");
			btn_modify.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					temporanyLabels= new ArrayList<>();
					sharedUser= new HashSet<>();
					System.out.println("Sharred user: "+sharedUser);
					nuova=false;
					modifica=true;
					if (view.isPinned(lbl_title.getText()))
						btnPin.setBackground(Color.RED);
					else
						btnPin.setBackground(new JButton().getBackground());
					
					chckbxPublic.setSelected(view.isPublic(lbl_title.getText()));
					
					textFieldTitleNote.setText(lbl_title.getText());
					textAreaNote.setText(btn_modify.getActionCommand());
					temporanyLabels= view.getLabelsByNote(lbl_title.getText());
					sharedUser= view.getSharredUser(lbl_title.getText());
					System.out.println("Sharred USer "+sharedUser);
					modifyID= view.getIDbyTitle(lbl_title.getText());
					refreshLabelPanel(view);
					refreshSharePanel(view);
				}
			});
			
			//
			gc = new GridBagConstraints();
			GregorianCalendar data = null;
			if (notes.get(i).getUpdatedAt()==null)
				data=(GregorianCalendar)notes.get(i).getCreatedAt();
			else
				data=(GregorianCalendar)notes.get(i).getUpdatedAt();
			SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy "+" hh:mm");
		    fmt.setCalendar(data);
		    String dateFormatted = fmt.format(data.getTime());
			JLabel lb_data = new JLabel(dateFormatted);
//			lb_data.setPreferredSize(new Dimension(20, 20));
			gc.gridx = 3;
			gc.gridy = i;
			gc.weightx=0;
			gc.insets = new Insets(10, 10, 10, 10);
			gc.fill=GridBagConstraints.BOTH;
			contentList.add(lb_data,gc);
			lb_data.setEnabled(false);
			lb_data.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					lb_data.setEnabled(true);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					lb_data.setEnabled(false);
				}
			});
			
			//
			gc = new GridBagConstraints();
			JButton btn_pn=null;
			 try {
				  ImageIcon addIcon = new ImageIcon("Pinbutton.png");
				  Image im= addIcon.getImage();
				  Image newimg = im.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
				  btn_pn = new JButton(new ImageIcon(newimg));
//				  btn_pn.setContentAreaFilled(false);
				  btn_pn.setMargin(new Insets(0, 0, 0, 0));
				  btn_pn.setBorder(null);
//					btnNewNote.setBorder(BorderFactory.createEmptyBorder());
				  btn_pn.setEnabled(false);
				  if(notes.get(i).getPin())
					  btn_pn.setBackground(Color.RED);
					
			  } catch (Exception ex) {
			    System.out.println(ex);
			  } 
			
//			lb_data.setPreferredSize(new Dimension(20, 20));
			gc.gridx = 4;
			gc.gridy = i;
			gc.weightx=0;
			gc.insets = new Insets(10, 10, 10, 10);
			gc.fill=GridBagConstraints.BOTH;
			contentList.add(btn_pn,gc);
			

			
			
		}
		
		contentList.repaint();
		textFieldTitleNote.setText("Select one note...");
		textAreaNote.setText("");
		repaint();
		System.out.println("refresh");
		
	}
	
	
	/**
	 * metodo per aggiornare il pannello relativo alle label associate alla nota che stiamo modificando
	 * @param view
	 */
	private void refreshSharePanel(MainView view){
		panelShare.removeAll();
		panelShare.revalidate();
		//TODO implementare una scroll bar funzionante
//		JScrollPane scrollPaneLabel= new JScrollPane(panelLabels);
//		panelLabels.add(scrollPaneLabel);
		JButton btnShareIcon = new JButton("Shared with:");
		btnShareIcon.setContentAreaFilled(false);
		btnShareIcon.setMargin(new Insets(0, 0, 0, 0));
		btnShareIcon.setBorder(null);
		btnShareIcon.setEnabled(false);
		btnShareIcon.setHorizontalAlignment(SwingConstants.LEFT);

		panelShare.add(btnShareIcon);
		
		if (sharedUser.size()>0)
			chckbxPublic.setSelected(true);
			
		
		ArrayList<User> cp= new ArrayList<>(sharedUser);
		for (int i=0; i<cp.size(); i++){
			JButton btnNewButton = new JButton(cp.get(i).getName());
			btnNewButton.setActionCommand(cp.get(i).getName());
			btnNewButton.setPreferredSize(new Dimension(80, 15));
			btnNewButton.setContentAreaFilled(false);
			btnNewButton.setBorder(BorderFactory.createEmptyBorder());
			btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
			btnNewButton.setEnabled(false);
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					btnNewButton.setEnabled(true);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					btnNewButton.setEnabled(false);
				}
			});
			btnNewButton.setToolTipText("Remove this User");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null, "Vuoi eliminare il seguente utente condiviso: "+btnNewButton.getActionCommand(), "Avviso",dialogButton);
					if(dialogResult == 0) {
						for (User u: cp){
							if (u.getName().equals(btnNewButton.getActionCommand())){
								cp.remove(u);
								break;
							}
								
							
						}
						System.out.println("CP "+cp);
						sharedUser.clear();
						sharedUser= new HashSet<>(cp);
						refreshSharePanel(view);
					} else {
					  System.out.println("No Option");
					} 
				}
			});
			panelShare.add(btnNewButton);
		}
		
		panelShare.repaint();
	}
	
	
	/**
	 * metodo per aggiornare il pannello relativo alle label associate alla nota che stiamo modificando
	 * @param view
	 */
	private void refreshLabelPanel(MainView view){
		panelLabels.removeAll();
		panelLabels.revalidate();
		//TODO implementare una scroll bar funzionante
//		JScrollPane scrollPaneLabel= new JScrollPane(panelLabels);
//		panelLabels.add(scrollPaneLabel);
		actualLabels=temporanyLabels;
		Set<String> hs = new HashSet<>();
		hs.addAll(actualLabels);
		actualLabels.clear();
		actualLabels.addAll(hs);
		for (int i=0; i<actualLabels.size(); i++){
			JButton btnNewButton = new JButton(actualLabels.get(i));
			btnNewButton.setActionCommand(actualLabels.get(i));
			btnNewButton.setPreferredSize(new Dimension(80, 15));
			btnNewButton.setContentAreaFilled(false);
			btnNewButton.setBorder(BorderFactory.createEmptyBorder());
			btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 9));
			btnNewButton.setEnabled(false);
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					btnNewButton.setEnabled(true);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					btnNewButton.setEnabled(false);
				}
			});
			btnNewButton.setToolTipText("Remove this label");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null, "Vuoi eliminare la seguente label: "+btnNewButton.getActionCommand(), "Avviso",dialogButton);
					if(dialogResult == 0) {
					  temporanyLabels.remove(btnNewButton.getActionCommand());
					  actualLabels= new ArrayList<>();
					  refreshLabelPanel(view);
					} else {
					  System.out.println("No Option");
					} 
				}
			});
			panelLabels.add(btnNewButton);
		}
		
		panelLabels.repaint();
	}
	
	/**
	 * crea l'area di modifica per la nota selezionata
	 */
	private void createModifyNote(MainView view){
		contentNote.removeAll();
		contentNote.revalidate();
		
		loadInfo(view);
		contentNote.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
			
		//row 0
			//col 0-3
			gbc_textFieldTitleNote = new GridBagConstraints();
			textFieldTitleNote = new JTextField();
			textFieldTitleNote.setHorizontalAlignment(SwingConstants.LEFT);
			textFieldTitleNote.setText("Select one note...");
			textFieldTitleNote.setPreferredSize(new Dimension(100, 30));
			gbc_textFieldTitleNote.weightx=1;
			gbc_textFieldTitleNote.gridx = 0;
			gbc_textFieldTitleNote.gridy = 0;
			gbc_textFieldTitleNote.gridwidth=3;
			gbc_textFieldTitleNote.fill=GridBagConstraints.HORIZONTAL;
			gbc_textFieldTitleNote.anchor= GridBagConstraints.LINE_START;
			gbc_textFieldTitleNote.insets = new Insets(10, 10, 10, 10);
			contentNote.add(textFieldTitleNote,gbc_textFieldTitleNote);
			
		//row 1
			//col 0-3
			gc = new GridBagConstraints();
			textAreaNote = new JTextArea();
			textAreaNote.setText("");
			JScrollPane scrollPaneNote= new JScrollPane(textAreaNote);
			gc.weightx=1;
			gc.weighty=1;
			gc.gridx = 0;
			gc.gridy = 1;
			gc.gridwidth=3;
			gc.fill=GridBagConstraints.BOTH;
			gc.anchor= GridBagConstraints.LINE_START;
			gc.insets = new Insets(10, 10, 5, 10);
			contentNote.add(scrollPaneNote,gc);
			
		//row 2
			//col 0-3
			gc = new GridBagConstraints();
			panelLabels = new JPanel();
			panelLabels.setPreferredSize(new Dimension(500, 20));
//			JScrollPane scrollPaneLabel= new JScrollPane(panelLabels);
			panelLabels.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			gc.weightx=1;
			gc.weighty=0;
			gc.gridx = 0;
			gc.gridy = 2;
			gc.gridwidth=3;
			gc.fill=GridBagConstraints.HORIZONTAL;
			gc.anchor= GridBagConstraints.LINE_START;
			gc.insets = new Insets(5, 10, 5, 10);
			contentNote.add(panelLabels,gc);
			
			//row 3
			//col 0-3
			gc = new GridBagConstraints();
			panelShare = new JPanel();
			panelShare.setPreferredSize(new Dimension(500, 20));
//			JScrollPane scrollPaneLabel= new JScrollPane(panelLabels);
			panelShare.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			gc.weightx=1;
			gc.weighty=0;
			gc.gridx = 0;
			gc.gridy = 3;
			gc.gridwidth=3;
			gc.fill=GridBagConstraints.HORIZONTAL;
			gc.anchor= GridBagConstraints.LINE_START;
			gc.insets = new Insets(5, 10, 5, 10);
			contentNote.add(panelShare,gc);
			
		//row 4
			//col 0
			gc = new GridBagConstraints();
			comboLabelsAdd = new JComboBox();
			comboLabelsAdd.setModel(new DefaultComboBoxModel(labels));
			comboLabelsAdd.setToolTipText("");
			comboLabelsAdd.setSelectedItem("Labels");
			comboLabelsAdd.setEnabled(false);
			comboLabelsAdd.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					comboLabelsAdd.setEnabled(true);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					
				}
			});
			comboLabelsAdd.setToolTipText("Chose label to add");
			gc.weightx=0;
			gc.weighty=0;
			gc.gridx = 0;
			gc.gridy = 4;
			gc.gridwidth=1;
			gc.anchor= GridBagConstraints.LINE_START;
			gc.insets = new Insets(10, 10, 10, 10);
			contentNote.add(comboLabelsAdd,gc);
			
			//col 1
			gc = new GridBagConstraints();
			textFieldNewLabel = new JTextField();
			textFieldNewLabel.setText("");
			textFieldNewLabel.setPreferredSize(new Dimension(100, 30));
			gc.weightx=0;
			gc.weighty=0;
			gc.gridx = 1;
			gc.gridy = 4;
			gc.gridwidth=1;
			gc.anchor= GridBagConstraints.LINE_START;
			gc.insets = new Insets(10, 10, 10, 10);
			contentNote.add(textFieldNewLabel,gc);
			
			//col 2
			gc = new GridBagConstraints();
			btnAddLabel = new JButton();
			btnAddLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
			btnAddLabel.setToolTipText("");
			btnAddLabel.setText("add new label");
			btnAddLabel.setName("");
			btnAddLabel.setEnabled(false);
			btnAddLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					btnAddLabel.setEnabled(true);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					btnAddLabel.setEnabled(false);
				}
			});
			
			
			comboLabelsAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					comboLabelsAdd.setEnabled(false);
			}});
			
			comboLabelsAdd.setEnabled(false);
			btnAddLabel.setToolTipText("Add selected label");
			btnAddLabel.setPreferredSize(new Dimension(80, 30));
			gc.weightx=0;
			gc.weighty=0;
			gc.gridx = 2;
			gc.gridy = 4;
			gc.gridwidth=1;
			gc.anchor= GridBagConstraints.LINE_START;
			gc.insets = new Insets(10, 10, 10, 10);
			btnAddLabel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(textFieldTitleNote.getText().equals("Select one note...")){
						showInfoMessage("prima seleziona una nota");
						comboLabelsAdd.setSelectedItem("Labels");
						textFieldNewLabel.setText("");
						return;
					}
					if (textFieldNewLabel.getText().isEmpty() && comboLabelsAdd.getSelectedItem().toString().equals("Labels")){
						
						showInfoMessage("Label is empty");
					}
					
					if (!textFieldNewLabel.getText().isEmpty() && !comboLabelsAdd.getSelectedItem().toString().equals("Labels")){
						showInfoMessage("slezionare una sola label");
						comboLabelsAdd.setSelectedItem("Labels");
						textFieldNewLabel.setText("");
					}
					else{
						if(view.getMyLabel().size()>10){
							showErrorMessage("Max number of Label reach");
						}
						else{
								if (textFieldNewLabel.getText().isEmpty()){
//									System.out.println("Label aggiunta la utente e salvata nelle temporanee");
//									System.out.println("TEMPORANY PRIMA "+temporanyLabels);
									temporanyLabels.add(comboLabelsAdd.getSelectedItem().toString());
									refreshLabelPanel(view);
//									System.out.println("TEMPORANY DOPO "+temporanyLabels);
									textFieldNewLabel.setText("");
									comboLabelsAdd.setSelectedItem("Labels");
									System.out.println(temporanyLabels);
								}
								else{
									view.addLabel(textFieldNewLabel.getText());
									System.out.println("Label aggiunta la utente e salvata nelle temporanee");
									temporanyLabels.add(textFieldNewLabel.getText());
									refreshLabelPanel(view);
									textFieldNewLabel.setText("");
									System.out.println(temporanyLabels);
								}
							
						}
					}
					
				}
			});
			contentNote.add(btnAddLabel,gc);
			
			contentNote.repaint();
	}
	
	
	private void createColors(){
//		ArrayList<Object> colour = new ArrayList<>();
//		JButton btnColor=null;
//		 try {
//			  ImageIcon addIcon = new ImageIcon("White.png");
//			  Image im= addIcon.getImage();
//			  Image newimg = im.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
//			  btnColor = new JButton(new ImageIcon(newimg));
//			  btnColor.setMargin(new Insets(0, 0, 0, 0));
//			  btnColor.setBorder(null);
//			  btnColor.setOpaque(true);
//			  btnColor.setBorderPainted(false);
//			  btnColor.setContentAreaFilled(false);
//			  btnColor.setFocusPainted(false);
//			  btnColor.setOpaque(false);
//		  } catch (Exception ex) {
//		    System.out.println(ex);
//		  } 
//		colour.add(btnColor);
//		 try {
//			  ImageIcon addIcon = new ImageIcon("Yellow.png");
//			  Image im= addIcon.getImage();
//			  Image newimg = im.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
//			  btnColor = new JButton(new ImageIcon(newimg));
//			  btnColor.setMargin(new Insets(0, 0, 0, 0));
//			  btnColor.setBorder(null);
//			  btnColor.setOpaque(true);
//			  btnColor.setBorderPainted(false);
//			  btnColor.setContentAreaFilled(false);
//			  btnColor.setFocusPainted(false);
//			  btnColor.setOpaque(false);
//		  } catch (Exception ex) {
//		    System.out.println(ex);
//		  } 
//		colour.add(btnColor);
//		colours = colour.toArray();
		
	}
	/**
	 * aggiorna il pannello contenente i bottodi di modifica alla nota selezionata
	 * @param view
	 */
	private void refreshButtonModify(MainView view){
		contentModify.removeAll();
		contentModify.revalidate();
		
//		createColors();
		
		btnExplore = new JButton("Back");
		btnExplore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
	

			}
		});
//		btnExplore.setEnabled(false);
//		btnExplore.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseEntered(MouseEvent arg0) {
//				btnExplore.setEnabled(true);
//				
//			}
//			@Override
//			public void mouseExited(MouseEvent e) {
//				btnExplore.setEnabled(false);
//			}
//		});
		btnExplore.setToolTipText("Explore section");
		contentInfo.add(btnExplore);
		
		 try {
			  ImageIcon addIcon = new ImageIcon("PinButton.png");
			  Image im= addIcon.getImage();
			  Image newimg = im.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
			  btnPin = new JButton(new ImageIcon(newimg));
			  btnPin.setContentAreaFilled(true);
			  btnPin.setMargin(new Insets(0, 0, 0, 0));
			  btnPin.setBorder(null);
			 

		  } catch (Exception ex) {
		    System.out.println(ex);
		  } 
		 btnPin.setEnabled(false);
		 btnPin.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					btnPin.setEnabled(true);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					btnPin.setEnabled(false);
				}
			});
		 btnPin.setToolTipText("Pinned the note");
		btnPin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (modifica || nuova){
					if (btnPin.getBackground().equals(Color.RED))
						btnPin.setBackground(new JButton().getBackground());
					else
						btnPin.setBackground(Color.RED);
				}
				
			}
		});
		contentModify.add(btnPin);
		
		chckbxPublic = new JCheckBox("Public");
		chckbxPublic.setEnabled(false);
		chckbxPublic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				chckbxPublic.setEnabled(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				chckbxPublic.setEnabled(false);
			}
		});
		chckbxPublic.setToolTipText("The note will be public");
		contentModify.add(chckbxPublic);
		
		
		 try {
			  ImageIcon addIcon = new ImageIcon("SaveButton.png");
			  Image im= addIcon.getImage();
			  Image newimg = im.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
			  btnSave = new JButton(new ImageIcon(newimg));
			  btnSave.setContentAreaFilled(true);
			  btnSave.setMargin(new Insets(0, 0, 0, 0));
			  btnSave.setBorder(null);
			  btnSave.setOpaque(true);
//				btnNewNote.setBorder(BorderFactory.createEmptyBorder());
		  } catch (Exception ex) {
		    System.out.println(ex);
		  } 
		 btnSave.setEnabled(false);
		 btnSave.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					btnSave.setEnabled(true);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					btnSave.setEnabled(false);
				}
			});
		 btnSave.setToolTipText("Save the note");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Note note = new Note(textFieldTitleNote.getText());
				if(textFieldTitleNote.getText().toCharArray().length>15){
					showErrorMessage("Title max length = 15!!");
					textFieldTitleNote.setText("");
					return;
				}
				note.setBody(textAreaNote.getText());
//				System.out.println("DEVO AGGIUNGERE QUESTE temporany labels: "+temporanyLabels);
				note.addLabels(temporanyLabels);
				note.addSharedUsers(sharedUser);
				ValidationError validate;
//				System.out.println("ID che sto modificando "+modifyID);
				note.setPin(btnPin.getBackground().equals(Color.RED));
				if (sharedUser.size()>0)
					note.setPublic(true);
				else
				note.setPublic(chckbxPublic.isSelected());
				
				if (modifyID==null){
					validate = view.addNote(note);
					if (validate.equals(ValidationError.TITLE_EMPTY)){
						showInfoMessage(validate);
						return;
					}
					System.out.println("nota aggiunta");
					notes=view.getMyNote();
					textFieldTitleNote.setText("Select one note...");
					textAreaNote.setText("");
					temporanyLabels=new ArrayList<>();
					sharedUser= new HashSet<>();
					actualLabels=new ArrayList<>();
					nuova=false;
					btnPin.setBackground(new JButton().getBackground());
					chckbxPublic.setSelected(false);
					view.updateMyLabels();
					createModifyNote(view);
					refreshButton(view);
					repaint();
		
				}
					
				else{
					note.setUpdatedAt(new GregorianCalendar());
					validate=view.update(note, modifyID);
					if (validate.equals(ValidationError.TITLE_EMPTY)){
						showInfoMessage(validate);
						return;
					}
					System.out.println("nota aggiornata");
					notes=view.getMyNote();
					modifyID= null;
					textFieldTitleNote.setText("Select one note...");
					textAreaNote.setText("");
					actualLabels=new ArrayList<>();
					temporanyLabels=new ArrayList<>();
					sharedUser= new HashSet<>();
					modifica=false;
					btnPin.setBackground(new JButton().getBackground());
					chckbxPublic.setSelected(false);
					view.updateMyLabels();
					sharedUser= new HashSet<>();
					createModifyNote(view);
					refreshButton(view);
					repaint();
		
				}
				
				refreshNoteList(view);
			}
		});
		contentModify.add(btnSave);
		
		

	

		 try {
			  ImageIcon addIcon = new ImageIcon("Colors.png");
			  Image im= addIcon.getImage();
			  Image newimg = im.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
			  comboColors = new JButton(new ImageIcon(newimg));
			  comboColors.setContentAreaFilled(true);
			  comboColors.setMargin(new Insets(0, 0, 0, 0));
			  comboColors.setBorder(null);
			  comboColors.setOpaque(true);
//				btnNewNote.setBorder(BorderFactory.createEmptyBorder());
		  } catch (Exception ex) {
		    System.out.println(ex);
		  } 
		comboColors.setEnabled(false);
		comboColors.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					comboColors.setEnabled(true);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					comboColors.setEnabled(false);
				}
			});
		comboColors.setToolTipText("Change background color");
		Object [] colours={"White", "Yellow", "Green", "Magenta"};
		comboColors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch (	JOptionPane.showOptionDialog(null,
						    "Which colour do you like?",
						    "Choose a colour",
						    JOptionPane.YES_NO_CANCEL_OPTION,
						    JOptionPane.QUESTION_MESSAGE,
						    null,
						    colours,
						    colours[0])) {
				case 0:{
					textAreaNote.setBackground(Color.white);
					textFieldTitleNote.setBackground(Color.white);
					comboColors.setEnabled(false);
					break;
				}
				case 1:
					textAreaNote.setBackground(Color.yellow);
					textFieldTitleNote.setBackground(Color.yellow);
					comboColors.setEnabled(false);
					break;
				case 2:
					textAreaNote.setBackground(Color.green);
					textFieldTitleNote.setBackground(Color.green);
					comboColors.setEnabled(false);
					break;
				case 3:
					textAreaNote.setBackground(Color.magenta);
					textFieldTitleNote.setBackground(Color.magenta);
					comboColors.setEnabled(false);
					break;
				
					default:
						break;
							}
						}
		});
		contentModify.add(comboColors);
		
		
		 try {
			  ImageIcon addIcon = new ImageIcon("shareButton.png");
			  Image im= addIcon.getImage();
			  Image newimg = im.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
			  btnShare = new JButton(new ImageIcon(newimg));
			  btnShare.setContentAreaFilled(true);
			  btnShare.setMargin(new Insets(0, 0, 0, 0));
			  btnShare.setBorder(null);
			 

		  } catch (Exception ex) {
		    System.out.println(ex);
		  } 
		 btnShare.setEnabled(false);
		 btnShare.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					btnShare.setEnabled(true);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					btnShare.setEnabled(false);
				}
			});
		 btnShare.setToolTipText("Other users can modify your note");
		 btnShare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ArrayList<User> listUser= new ArrayList<>();
				listUser= view.getAllUsers();
				ArrayList<String> cp= (ArrayList<String>)listUser.stream()
						.map(x->x.getName())
						.collect(Collectors.toList());
				System.out.println("LISTA "+cp);
						
				String[] users = cp.toArray(new String[0]);
				System.out.println("TUTTI GLI USER "+users[1]);
			    String input = (String)JOptionPane.showInputDialog(null, "Share with...",
			        "Choise one account", JOptionPane.QUESTION_MESSAGE, null, // Use
			                                                                        // default
			                                                                        // icon
			        users, // Array of choices
			        users[0]); // Initial choice
			    
			    int index = -1;
			    for (int i=0;i<users.length;i++) {
			        if (users[i].equals(input)) {
			            index = i;
			            break;
			        }
			    }
			   
			    sharedUser.add(listUser.get(index));
			    refreshSharePanel(view);
			 
		}});
		 contentModify.add(btnShare);
		
		contentModify.repaint();
	}
	
	/**
	 * aggiorna il pannello contenente i bottoni di navigazione
	 * @param view
	 */
	private void refreshButton (MainView view){
		
		contentButton.removeAll();
		contentButton.revalidate();
		
		loadInfo(view);
		
		//TODO rendere più carina la visualizzazione con le immagini
		  try {
			  ImageIcon addIcon = new ImageIcon("RefreshButton.png");
			  Image im= addIcon.getImage();
			  Image newimg = im.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
			  btnRefresh = new JButton(new ImageIcon(newimg));
			  btnRefresh.setContentAreaFilled(false);
			  btnRefresh.setMargin(new Insets(0, 0, 0, 0));
			  btnRefresh.setBorder(null);
//				btnNewNote.setBorder(BorderFactory.createEmptyBorder());
		  } catch (Exception ex) {
		    System.out.println(ex);
		  } 
		  btnRefresh.setEnabled(false);
		  btnRefresh.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent arg0) {
						btnRefresh.setEnabled(true);
					}
					@Override
					public void mouseExited(MouseEvent e) {
						btnRefresh.setEnabled(false);
					}
				});
		  btnRefresh.setToolTipText("Refresh your list");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sharedUser= new HashSet<>();
				refreshLabelPanel(view);
				refreshNoteList(view);
				refreshSharePanel(view);
			}
		});
		
		
		  try {
			  ImageIcon addIcon = new ImageIcon("AddButton.png");
			  Image im= addIcon.getImage();
			  Image newimg = im.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
			  btnNewNote = new JButton(new ImageIcon(newimg));
			  btnNewNote.setContentAreaFilled(false);
			  btnNewNote.setMargin(new Insets(0, 0, 0, 0));
			  btnNewNote.setBorder(null);
//				btnNewNote.setBorder(BorderFactory.createEmptyBorder());
		  } catch (Exception ex) {
		    System.out.println(ex);
		  } 
		  btnNewNote.setEnabled(false);
		  btnNewNote.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent arg0) {
						btnNewNote.setEnabled(true);
					}
					@Override
					public void mouseExited(MouseEvent e) {
						btnNewNote.setEnabled(false);
					}
				});
		  btnNewNote.setToolTipText("Create a new note");
		btnNewNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				temporanyLabels= new ArrayList<>();
				sharedUser= new HashSet<>();
				refreshLabelPanel(view);
				actualLabels= new ArrayList<>();
				refreshLabelPanel(view);
				btnPin.setBackground(new JButton().getBackground());
				chckbxPublic.setSelected(false);
				nuova=true;
				modifica=false;
				modifyID=null;
				textAreaNote.setText("");
				textFieldTitleNote.setText("");
			}
		});
		contentButton.add(btnNewNote);
		contentButton.add(btnRefresh);
		
//		ArrayList<String> _labels= new ArrayList<>();
//		_labels= view.getMyLabel();
//		labels= _labels.toArray(new String [_labels.size()]);
		comboLabels = new JComboBox();
		comboLabels.setModel(new DefaultComboBoxModel(labels));
		comboLabels.setToolTipText("");
		comboLabels.setSelectedItem("Labels");
		comboFilter = new JComboBox();
		comboFilter.setModel(new DefaultComboBoxModel(filters));
		comboFilter.setToolTipText("");
		comboFilter.setEnabled(false);
		comboFilter.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent arg0) {
						comboFilter.setEnabled(true);
					}
					@Override
					public void mouseExited(MouseEvent e) {
//						comboFilter.setEnabled(false);
					}
				});
		comboFilter.setToolTipText("Filter your list");
		comboFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch ((String)comboFilter.getSelectedItem()) {
				
				case "Titolo":{
					notes=view.FilterByTitle();
					refreshNoteList(view);
					sharedUser= new HashSet<>();
					refreshLabelPanel(view);
					comboFilter.setEnabled(false);
					break;
				}
				case "Data":
					notes=view.FilterByData();
					refreshNoteList(view);
					sharedUser= new HashSet<>();
					refreshLabelPanel(view);
					comboFilter.setEnabled(false);
					break;
					
				case "Like":
					notes=view.FilterByLike();
					refreshNoteList(view);
					sharedUser= new HashSet<>();
					refreshLabelPanel(view);
					comboFilter.setEnabled(false);
					break;
				case "Pinned":
					notes=view.FilterByPin();
					refreshNoteList(view);
					sharedUser= new HashSet<>();
					refreshLabelPanel(view);
					comboFilter.setEnabled(false);
					break;
				case "Filters":
					sharedUser= new HashSet<>();
					refreshLabelPanel(view);
					comboFilter.setEnabled(false);
					break;
					
					default :
						comboFilter.setEnabled(false);
						break;
							}
			}
		});
		
		contentButton.add(comboLabels);
		contentButton.add(comboFilter);
		comboLabels.setEnabled(false);
		comboLabels.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent arg0) {
						comboLabels.setEnabled(true);
					}
					@Override
					public void mouseExited(MouseEvent e) {
//						comboLabels.setEnabled(false);
					}
				});
		comboLabels.setToolTipText("filter by labels");
		comboLabels.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String label = (String)comboLabels.getSelectedItem();
				if (label=="Labels"){ 
					notes=view.getMyNote();
					temporanyLabels= new ArrayList<>();
					sharedUser= new HashSet<>();

				}
				else{
					
					notes=view.getNotesByLabel(label);
					temporanyLabels= new ArrayList<>();
					sharedUser= new HashSet<>();
				}
				modifica=false;
				nuova=false;
				sharedUser= new HashSet<>();
				refreshLabelPanel(view);
				btnPin.setBackground(new JButton().getBackground());
				chckbxPublic.setSelected(false);
				comboLabels.setEnabled(false);
				refreshLabelPanel(view);	
				refreshNoteList(view);
			}
		});
		contentButton.repaint();
		
	}
	
	/**
	 * metodo per la creazione dell'interfaccia utilizzando il GridBagLayout
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
		this.contentList.setPreferredSize(new Dimension(430, 30));
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
