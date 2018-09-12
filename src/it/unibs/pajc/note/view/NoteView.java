package it.unibs.pajc.note.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import it.unibs.pajc.note.client_server.Comunication;
import it.unibs.pajc.note.data.NoteArchive;
import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;


public class NoteView extends JPanel {
	//Pannelli contenitivi
	private JPanel contentList;
	private JPanel contentList2;
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
	private JButton btnLogOut;
	private JButton btnPin;
	private JButton btnShare;
	private JButton btnDelete;
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
//	private NoteArchive noteArchive= NoteArchive();
	
	private JTextField textFieldNewLabel;
	private JButton btnAddLabel;
	
	private ArrayList<String> temporanyLabels = new ArrayList<>();
	private ArrayList<String> actualLabels = new ArrayList<>();
	private boolean nuova=false;
	private boolean modifica=false;
	private Object[] colours={};
	private Set<User> sharedUser= new HashSet<>();
	private int actualIndex=-1;
	private StringBuffer modifyText=null;
	
	private static void removeBlankSpace(StringBuffer sb) {
		  int j = 0;
		  for(int i = 0; i < sb.length(); i++) {
		    if (!Character.isWhitespace(sb.charAt(i))) {
		       sb.setCharAt(j++, sb.charAt(i));
		    }
		  }
		  sb.delete(j, sb.length());
		}
	
	/**
	 * salvataggio delle modifiche fatte
	 * @param view
	 * @author Stefano Poma
	 */
	private void save(MainView view){
		Note note = new Note(textFieldTitleNote.getText());
		if(textFieldTitleNote.getText().toCharArray().length>15){
			showErrorMessage("Title max length = 15!!");
			textFieldTitleNote.setText("");
			return;
		}
		if(view.getMyNote().contains(textFieldTitleNote.getText())){
			showInfoMessage("Due note con lo stesso nome");
			return;
		}
		note.setBody(textAreaNote.getText());
//		System.out.println("DEVO AGGIUNGERE QUESTE temporany labels: "+temporanyLabels);
		note.addLabels(temporanyLabels);
//		System.out.println("HO AGGIUNTO ALLA NOTA LE TEMPORANY LABEL: "+temporanyLabels);
		note.addSharedUsers(sharedUser);
		ValidationError validate;
//		System.out.println("ID che sto modificando "+modifyID);
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
//			System.out.println("nota aggiunta");
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
//			System.out.println("nota aggiornata");
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
	
	/**
	 * metodo per far apparire messaggio di errore data una stringa
	 * @param in
	 * @author Stefano Poma
	 */
	private void showErrorMessage(String in){
		JOptionPane.showMessageDialog(this,
			    in,
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * metodo per far apparire messaggio di errore dato un ValidationError
	 * @param in ValidateError
	 * @author Stefano Poma
	 */
	private void showErrorMessage(ValidationError in){
		JOptionPane.showMessageDialog(this,
			    in.toString(),
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * metodo per far apparire un messaggio di segnalazione dato un ValidationError
	 * @param in
	 * @author Stefano Poma
	 */
	private void showInfoMessage(ValidationError in){
		JOptionPane.showMessageDialog(null, in.toString());
	}
	
	/**
	 * metodo per far apparire un messaggio di segnalazione data un stringa
	 * @param in
	 * @author Stefano Poma
	 */
	private void showInfoMessage(String in){
		JOptionPane.showMessageDialog(null, in);
	}

	/**
	 * Create the panel. (Costruttore)
	 */
	public NoteView(MainView view) {
		loadInfo(view);
		buildContent(view);
		buildComponent(view);
	}
	
	/**
	 * metodo per caricare tutte le informazioni per il primo avvio
	 * si appoggerï¿½ a NoteController che a sua volta tramite la classe Client richiederï¿½
	 * al server tutte le informazioni necessarie
	 * @param view
	 * @author Stefano Poma
	 */
	private void loadInfo(MainView view){
		//carico le mie note
		notes=view.getMyNote();
		
		//carico le labels
		ArrayList<String> _labels= new ArrayList<>();
		_labels= view.getMyLabel();
//		System.out.println("LABELS: "+_labels);
		labels= _labels.toArray(new String [_labels.size()]);
		
//		System.out.println("MY Label: "+labels);
		
		//carico i filtri
		filters=new String[] {"Filters", "Titolo", "Data", "Like", "Pinned"};	
	}
	


	
	/**
	 * metodo per creare tutti i componenti principali
	 * @param view
	 * @author Stefano Poma
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
	 * crea la sezione di visualizzazione per la lista delle note (contentList)
	 * @param view
	 * @author Stefano Poma
	 */
	private void refreshNoteList(MainView view){

		//metodi per far si che ogni volta sia possibile fare un refresh completo del pannello
		contentList.removeAll();
		contentList.revalidate();
		view.iRefresh();
		//dimensione variabile a seconda del numero di note, e' necessario affinche la ScrollBar funzioni correttamente
		contentList.setPreferredSize(new Dimension(500, (notes.size()-1)*50));
		contentList.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		for (int i=0; i<notes.size(); i++){
			
			//ROW 0
				//col 0
			gc = new GridBagConstraints();
			JLabel lbl_title = new JLabel();
			//titolo usata poi con il tasto modifica
			String titolo=notes.get(i).getTitle();
			StringBuffer str= new StringBuffer();
			int count=0;
			//per ragioni di semplicitï¿½ il titolo non puï¿½ contenere piï¿½ di 20 caratteri
			for (char c : titolo.toCharArray()) {
			  if(count<20){
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
			if(i==actualIndex)
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
			
				//col 1
			gc = new GridBagConstraints();
			JLabel lbl_name = new JLabel();
			//corpo usata poi con il tasto modifica
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
			
				//col 2
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
					
					modifyID= view.getIDbyTitle(lbl_title.getText());
					Comunication input= view.modifyID(modifyID);
					if(!input.getBoolean()){
						showInfoMessage("accesso contemporaneo ad un'altro utente");
						modifyID=null;
						return;
					}
					if(input.getInfo().equals("modify_id_response_refresh")){
						showInfoMessage("La nota è stata aggiornata, necessario un refresh della pagina");
						loadInfo(view);
						refreshNoteList(view);
						return;
					}
					modifyText=new StringBuffer();
					modifyText.append(titolo);
					modifyText.append(corpo);
//					System.out.println("TASTO MODIFICA");
					temporanyLabels= new ArrayList<>();
					sharedUser= new HashSet<>();
//					System.out.println("Sharred user: "+sharedUser);
					nuova=false;
					modifica=true;
//					System.out.println("PIN INSERISCO TITOLO: "+lbl_title.getText());
					if (view.isPinned(lbl_title.getText())){
						btnPin.setBackground(Color.RED);
					}
						
					else
						btnPin.setBackground(new JButton().getBackground());
					
					chckbxPublic.setSelected(view.isPublic(lbl_title.getText()));
					
					textFieldTitleNote.setText(titolo);
					textAreaNote.setText(corpo);
					temporanyLabels= view.getLabelsByNote(lbl_title.getText());
					sharedUser= view.getSharredUser(lbl_title.getText());
//					System.out.println("Sharred USer "+sharedUser);
					
					refreshLabelPanel(view);
					refreshSharePanel(view);
				}
			});
			
			//col 3
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
			
			//col 4
			gc = new GridBagConstraints();
			JButton btn_pn=null;
			 try {
				  ImageIcon addIcon = new ImageIcon("Pinbutton.png");
				  Image im= addIcon.getImage();
				  Image newimg = im.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
				  btn_pn = new JButton(new ImageIcon(newimg));
				  btn_pn.setMargin(new Insets(0, 0, 0, 0));
				  btn_pn.setBorder(null);
				  btn_pn.setEnabled(false);
				  if(notes.get(i).getPin())
					  btn_pn.setBackground(Color.RED);
					
			  } catch (Exception ex) {
				  ex.printStackTrace();
			  } 
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
//		System.out.println("refresh");
		
	}
	
	
	/**
	 * metodo per aggiornare il pannello relativo agli utenti condivisi associati alla nota che stiamo modificando
	 * @param view
	 * @author Stefano Poma
	 */
	private void refreshSharePanel(MainView view){
		//metodi per permettere un refresh completo del pannello
		panelShare.removeAll();
		panelShare.revalidate();
		
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
	 * @@param view
	 * @author Stefano Poma
	 */
	private void refreshLabelPanel(MainView view){
		//metodi per permettere un refresh completo del singolo pannello
		panelLabels.removeAll();
		panelLabels.revalidate();
		
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
	 * crea l'area di modifica della nota
	 * @param view
	 * @author Stefano Poma
	 */
	private void createModifyNote(MainView view){
		//metodi per permettere un refresh completo del singolo pannello
		contentNote.removeAll();
		contentNote.revalidate();
		
		loadInfo(view);
		view.stopModify();
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
						return;
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
//									System.out.println(temporanyLabels);
								}
								else{
									view.addLabel(textFieldNewLabel.getText());
//									System.out.println("Label aggiunta la utente e salvata nelle temporanee");
									
									temporanyLabels.add(textFieldNewLabel.getText());
//									System.out.println("AGGIUNTA NUOVA LABEL: "+temporanyLabels);
									refreshLabelPanel(view);
									textFieldNewLabel.setText("");
//									System.out.println(temporanyLabels);
								}
							
						}
					}
					
				}
			});
			contentNote.add(btnAddLabel,gc);
			
			contentNote.repaint();
	}
	
	
	
	/**
	 * aggiorna il pannello contenente i bottodi di modifica alla nota selezionata
	 * @param view
	 * @author Stefano Poma
	 */
	private void refreshButtonModify(MainView view){
		contentModify.removeAll();
		contentModify.revalidate();
		
		btnExplore = new JButton("Esplora");
		btnExplore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				view.stopModify();
				view.exploreView();
	

			}
		});
		btnExplore.setEnabled(false);
		btnExplore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnExplore.setEnabled(true);
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnExplore.setEnabled(false);
			}
		});
		btnExplore.setToolTipText("Explore section");
		contentInfo.add(btnExplore);
		
		
		btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				view.stopModify();
				view.logOut();
				view.saveOnFile();
	

			}
		});
		btnLogOut.setEnabled(false);
		btnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnLogOut.setEnabled(true);
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnLogOut.setEnabled(false);
			}
		});
		btnLogOut.setToolTipText("Log Out");
		contentInfo.add(btnLogOut);
		
		 try {
			  ImageIcon addIcon = new ImageIcon("PinButton.png");
			  Image im= addIcon.getImage();
			  Image newimg = im.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
			  btnPin = new JButton(new ImageIcon(newimg));
			  btnPin.setContentAreaFilled(true);
			  btnPin.setMargin(new Insets(0, 0, 0, 0));
			  btnPin.setBorder(null);
			 

		  } catch (Exception ex) {
			  ex.printStackTrace();
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
		  } catch (Exception ex) {
			  ex.printStackTrace();
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
				save(view);
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
			  ex.printStackTrace();
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
			  ex.printStackTrace();
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
				if(textFieldTitleNote.getText().equals("Select one note..."))
					return;
				
				ArrayList<User> listUser= new ArrayList<>();
				listUser= view.getAllUsers();
				if(listUser.isEmpty())
					return;
				
				ArrayList<String> cp= (ArrayList<String>)listUser.stream()
						.map(x->x.getName())
						.collect(Collectors.toList());
//				System.out.println("LISTA "+cp);
						
				String[] users = cp.toArray(new String[0]);
//				System.out.println("TUTTI GLI USER "+users[1]);
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
			   
//			    System.out.println("LISTA UTENTI SELEZIONATO: "+listUser.get(index));
			    if(index>=0)
			    	sharedUser.add(listUser.get(index));
			    
//			    System.out.println("LISTA UTENTI CONDIVISI: "+sharedUser);
			    refreshSharePanel(view);
			 
		}});
		 contentModify.add(btnShare);
		 
		 
		 try {
			  ImageIcon addIcon = new ImageIcon("deleteButton.png");
			  Image im= addIcon.getImage();
			  Image newimg = im.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
			  btnDelete = new JButton(new ImageIcon(newimg));
			  btnDelete.setContentAreaFilled(true);
			  btnDelete.setMargin(new Insets(0, 0, 0, 0));
			  btnDelete.setBorder(null);
			 

		  } catch (Exception ex) {
			  ex.printStackTrace();
		  } 
		 btnDelete.setEnabled(false);
		 btnDelete.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					btnDelete.setEnabled(true);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					btnDelete.setEnabled(false);
				}
			});
		 btnDelete.setToolTipText("Delete the note");
		 btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (textFieldTitleNote.getText().equals("Select one note...")){
					showErrorMessage("Non hai selezionato nessuna nota");
					return;
				}
				
//				System.out.println("MODIFY ID: "+modifyID);
				if(modifyID!=null){
					view.deleteNote(modifyID);
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
					refreshNoteList(view);
					repaint();
				}
				else{
					showErrorMessage("Non puoi eliminare una nota che non è stata salvata");
				}
				
			 
		}});
		 contentModify.add(btnDelete);
		
		contentModify.repaint();
	}
	
	/**
	 * aggiorna il pannello contenente i bottoni di navigazione
	 * @param view
	 * @author Stefano Poma
	 */
	private void refreshButton (MainView view){
		
		contentButton.removeAll();
		contentButton.revalidate();
		
		loadInfo(view);
		
		  try {
			  ImageIcon addIcon = new ImageIcon("RefreshButton.png");
			  Image im= addIcon.getImage();
			  Image newimg = im.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH ) ;  
			  btnRefresh = new JButton(new ImageIcon(newimg));
			  btnRefresh.setContentAreaFilled(false);
			  btnRefresh.setMargin(new Insets(0, 0, 0, 0));
			  btnRefresh.setBorder(null);
		  } catch (Exception ex) {
			  ex.printStackTrace();
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
				if(modifyText!=null){
					StringBuffer str= new StringBuffer();
					str.append(textFieldTitleNote.getText());
					str.append(textAreaNote.getText());
					removeBlankSpace(str);
					removeBlankSpace(modifyText);
//					System.out.println("IL MIO TESTO: "+str.toString());
//					System.out.println("IL TESTO DEL CONFRONTO: "+modifyText.toString());
					if(!modifyText.toString().equals(str.toString()) && modifyID!=null){
//						System.out.println("non sono uguali");
						
							if (JOptionPane.showConfirmDialog(null, "Vuoi salvare le modifiche?", "INFO",
							        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							    save(view);
							
						}
					}
				}
				modifyText=null;
				sharedUser= new HashSet<>();
				view.updateMyLabels();
				view.stopModify();
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
		  } catch (Exception ex) {
			  ex.printStackTrace();
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
				if(modifyText!=null){
					StringBuffer str= new StringBuffer();
					str.append(textFieldTitleNote.getText());
					str.append(textAreaNote.getText());
					removeBlankSpace(str);
					removeBlankSpace(modifyText);
//					System.out.println("IL MIO TESTO: "+str.toString());
//					System.out.println("IL TESTO DEL CONFRONTO: "+modifyText.toString());
					if(!modifyText.toString().equals(str.toString()) && modifyID!=null){
//						System.out.println("non sono uguali");
						
							if (JOptionPane.showConfirmDialog(null, "Vuoi salvare le modifiche?", "INFO",
							        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							    save(view);
							
						}
					}
				}
				modifyText=null;
				view.stopModify();
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
					}
				});
		comboFilter.setToolTipText("Filter your list");
		comboFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(modifyText!=null){
					StringBuffer str= new StringBuffer();
					str.append(textFieldTitleNote.getText());
					str.append(textAreaNote.getText());
					removeBlankSpace(str);
					removeBlankSpace(modifyText);
//					System.out.println("IL MIO TESTO: "+str.toString());
//					System.out.println("IL TESTO DEL CONFRONTO: "+modifyText.toString());
					if(!modifyText.toString().equals(str.toString()) && modifyID!=null){
//						System.out.println("non sono uguali");
						
							if (JOptionPane.showConfirmDialog(null, "Vuoi salvare le modifiche?", "INFO",
							        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							    save(view);
							
						}
					}
				}
				modifyText=null;
				view.stopModify();
				switch ((String)comboFilter.getSelectedItem()) {
				
				case "Titolo":{
					notes=view.FilterByTitle();
					refreshNoteList(view);
					sharedUser= new HashSet<>();
					refreshSharePanel(view);
					refreshLabelPanel(view);
					comboFilter.setEnabled(false);
					break;
				}
				case "Data":
					notes=view.FilterByData();
					refreshNoteList(view);
					sharedUser= new HashSet<>();
					refreshSharePanel(view);
					refreshLabelPanel(view);
					comboFilter.setEnabled(false);
					break;
					
				case "Like":
					notes=view.FilterByLike();
					refreshNoteList(view);
					sharedUser= new HashSet<>();
					refreshSharePanel(view);
					refreshLabelPanel(view);
					comboFilter.setEnabled(false);
					break;
				case "Pinned":
					notes=view.FilterByPin();
					refreshNoteList(view);
					sharedUser= new HashSet<>();
					refreshSharePanel(view);
					refreshLabelPanel(view);
					comboFilter.setEnabled(false);
					break;
				case "Filters":
					sharedUser= new HashSet<>();
					refreshSharePanel(view);
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
					}
				});
		comboLabels.setToolTipText("filter by labels");
		comboLabels.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(modifyText!=null){
					StringBuffer str= new StringBuffer();
					str.append(textFieldTitleNote.getText());
					str.append(textAreaNote.getText());
					removeBlankSpace(str);
					removeBlankSpace(modifyText);
//					System.out.println("IL MIO TESTO: "+str.toString());
//					System.out.println("IL TESTO DEL CONFRONTO: "+modifyText.toString());
					if(!modifyText.toString().equals(str.toString()) && modifyID!=null){
//						System.out.println("non sono uguali");
						
							if (JOptionPane.showConfirmDialog(null, "Vuoi salvare le modifiche?", "INFO",
							        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							    save(view);
							
						}
					}
				}
				
					
				modifyText=null;
				view.stopModify();
				String label = (String)comboLabels.getSelectedItem();
				if (label.equals("Labels")){ 
					notes=view.getMyNote();
//					System.out.println("MIE Labels: "+labels);
					temporanyLabels= new ArrayList<>();
					sharedUser= new HashSet<>();
					refreshSharePanel(view);

				}
				else{
					
					notes=view.getNotesByLabel(label);
					temporanyLabels= new ArrayList<>();
					sharedUser= new HashSet<>();
					refreshSharePanel(view);
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
	 * metodo per la creazione dei pannelli contenitivi
	 * @param view
	 * @author Stefano Poma
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
		this.contentButton.setPreferredSize(new Dimension(500, 50));
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
		this.contentList2 = new JPanel();
//		contentList2.setLayout();
		this.contentList.setPreferredSize(new Dimension(500, (notes.size()-1)*50));
		gc_2.weightx=0;
		gc_2.weighty=0;
		gc_2.gridx = 0;
		gc_2.gridy = 1;
		gc_2.fill=GridBagConstraints.BOTH;
		JScrollPane scroll = new JScrollPane(contentList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		contentList2.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 1));
//		contentList2.add(contentList);
		scroll.setPreferredSize(new Dimension(30, 30));
		this.add(scroll, gc_2);
		
		
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
