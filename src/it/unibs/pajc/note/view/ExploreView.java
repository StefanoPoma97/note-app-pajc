package it.unibs.pajc.note.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
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

import it.unibs.pajc.note.client_server.Comunication;
import it.unibs.pajc.note.model.Note;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;

public class ExploreView extends JPanel {
	// Pannelli contenitivi
	private JPanel contentList;
	private JPanel contentButton;
	private JPanel contentNote;
	private JPanel contentModify;
	private JPanel contentInfo;
	private GridBagConstraints gc_1;
	private GridBagConstraints gc_2;
	private GridBagConstraints gc_3;
	private GridBagConstraints gc_4;

	// componenti interni
	private JButton btnShareWithMe;
	private JButton btnExplore;
	private JButton btnPin;
	private JButton btnShare;
	private JButton btnLike;
	private JComboBox comboLabelsAdd;
	private JComboBox comboFilter;
	private JComboBox comboLabels;
	private JCheckBox chckbxPublic;
	private JButton comboColors;
	private JPanel panelLabels;
	private JPanel panelShare;

	private String[] labels = new String[] {};
	private String[] filters = new String[] {};
	private String[] colors = new String[] { "", "White", "Yellow", "Green", "Purple" };
	// array che conterr� solo le note visualizzabili in questo momento (non
	// necessariamente tutto l'archivio)
	private ArrayList<Note> notes = new ArrayList<>();
	private Integer modifyID = null;
	private Integer modifyNoteID = null;
	private JTextField textFieldTitle;
	private JButton btnSave;
	private JTextArea textAreaNote;
	private JTextField textFieldTitleNote;
	private GridBagConstraints gbc_textFieldTitleNote;
	JLabel lbl_title = new JLabel();
	// private NoteArchive noteArchive= new NoteArchive();

	private JLabel textLike;
	private JTextField textFieldNewLabel;
	private JButton btnAddLabel;

	private ArrayList<String> temporanyLabels = new ArrayList<>();
	private ArrayList<String> actualLabels = new ArrayList<>();
	private boolean nuova = false;
	private boolean modifica = false;
	private Object[] colours = {};
	private Set<User> sharedUser = new HashSet<>();
	private String modifyTitle = null;
	// private int modifyID;
	private JButton btnNewButton;
	private StringBuffer modifyText = null;

	static void removeBlankSpace(StringBuffer sb) {
		int j = 0;
		for (int i = 0; i < sb.length(); i++) {
			if (!Character.isWhitespace(sb.charAt(i))) {
				sb.setCharAt(j++, sb.charAt(i));
			}
		}
		sb.delete(j, sb.length());
	}

	/**
	 * salva tutte le modifiche
	 * 
	 * @param view
	 * @author Stefano Poma
	 */
	private void save(MainView view) {
		if (textFieldTitleNote.getText().equals("Select one note...")) {
			showInfoMessage("Devi prima selezionare una nota");
		} else {
			// System.out.println("TITOLO DELLA NOTA DA CERCARE: "+modifyTitle);
			// Note note = view.getNoteByTitle(modifyTitle);
			Note note = view.getNotebyID(modifyNoteID);
			if (textFieldTitleNote.getText().toCharArray().length > 15) {
				showErrorMessage("Title max length = 15!!");
				textFieldTitleNote.setText("");
				return;
			}
			note.setTitle(textFieldTitleNote.getText());
			note.setBody(textAreaNote.getText());
			ValidationError validate;
			if (modifyID == null) {
				// System.out.println("YO bro non dovresti entrare in questo if");
			} else {
				note.setUpdatedAt(new GregorianCalendar());
				validate = view.exUpdate(note, modifyID);
				// System.out.println("UPDATE "+validate);
				if (validate.equals(ValidationError.TITLE_EMPTY)) {
					showInfoMessage(validate);
					return;
				}
			}

			notes = view.getAllNote();
			modifyID = null;
			textFieldTitleNote.setText("Select one note...");
			textAreaNote.setText("");
			modifica = false;
			btnSave.setEnabled(false);
			createModifyNote(view);
			refreshButton(view);
			repaint();

			// System.out.println(note.getTitle()+" "+note.getBody()+ "
			// "+note.getSharedWith().toString());

		}

		refreshNoteList(view);
	}

	/**
	 * metodo per visualizzare un messaggio di errore data una stringa
	 * 
	 * @param in
	 * @author Stefano Poma
	 */
	private void showErrorMessage(String in) {
		JOptionPane.showMessageDialog(this, in, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * metodo per far apparire messaggio di errore dato un ValidationError
	 * 
	 * @param in
	 *            ValidateError
	 * @author Stefano Poma
	 */
	private void showErrorMessage(ValidationError in) {
		JOptionPane.showMessageDialog(this, in.toString(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * metodo per far apparire un messaggio di segnalazione dato un ValidationError
	 * 
	 * @param in
	 * @author Stefano Poma
	 */
	private void showInfoMessage(ValidationError in) {
		JOptionPane.showMessageDialog(null, in.toString());
	}

	/**
	 * metodo per far apparire un messaggio di segnalazione data una stringa
	 * 
	 * @param in
	 * @author Stefano Poma
	 */
	private void showInfoMessage(String in) {
		JOptionPane.showMessageDialog(null, in);
	}

	/**
	 * costruttore
	 */
	public ExploreView(MainView view) {
		loadInfo(view);
		buildContent(view);
		buildComponent(view);
	}

	/**
	 * metodo per caricare tutte le informazioni per il primo avvio si appogger� a
	 * NoteController che a sua volta tramite la classe Client richieder� al server
	 * tutte le info necessarie
	 * 
	 * @param view
	 * @author Stefano Poma
	 */
	private void loadInfo(MainView view) {
		// carico le mie note
		notes = view.getAllNote();
		// System.out.println("CARICO LE NOTE "+notes);

		// carico i filtri
		filters = new String[] { "Filters", "Titolo", "Data", "Like", "Author" };
	}

	/**
	 * metodo per creare i vari componenti
	 * 
	 * @param view
	 * @author Stefano Poma
	 */
	private void buildComponent(MainView view) {

		refreshButton(view);
		createModifyNote(view);
		refreshNoteList(view);
		refreshButtonModify(view);

	}

	/**
	 * metodo che aggiorna e scrive la lista delle nostre note
	 * 
	 * @param view
	 * @author Stefano Poma
	 */
	private void refreshNoteList(MainView view) {

		contentList.removeAll();
		contentList.revalidate();

		view.iRefresh();
		contentList.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		for (int i = 0; i < notes.size(); i++) {
			int tmpID = notes.get(i).getID();
			// ROW 0
			// col 0
			gc = new GridBagConstraints();
			JLabel lbl_title = new JLabel();
			String titolo = notes.get(i).getTitle();
			StringBuffer str = new StringBuffer();
			int count = 0;
			// per ragioni di semplicit� il titolo non pu� contenere pi� di 15 caratteri
			for (char c : titolo.toCharArray()) {
				if (count < 15) {
					str.append(c);
				} else {
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
			gc.fill = GridBagConstraints.BOTH;
			gc.insets = new Insets(10, 10, 10, 10);
			contentList.add(lbl_title, gc);

			// col 1
			gc = new GridBagConstraints();
			JLabel lbl_name = new JLabel();
			String autore = notes.get(i).getAuthor().getName();
			lbl_name.setText(autore);
			lbl_name.setHorizontalAlignment(SwingConstants.LEFT);
			lbl_name.setVerticalAlignment(SwingConstants.CENTER);
			lbl_name.setPreferredSize(new Dimension(300, 20));
			lbl_name.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					lbl_name.setForeground(Color.BLUE);
					// lbl_name.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					lbl_name.setForeground(new JLabel().getForeground());
					// lbl_name.setBorder(null);
				}
			});
			gc.gridx = 1;
			gc.gridy = i;
			gc.insets = new Insets(10, 10, 10, 10);
			gc.fill = GridBagConstraints.BOTH;
			contentList.add(lbl_name, gc);

			// col 2
			gc = new GridBagConstraints();
			boolean isShare = view.isShare(notes.get(i));
			JButton btn_modify = new JButton("V");
			if (isShare)
				btn_modify.setText("M");

			btn_modify.setPreferredSize(new Dimension(20, 20));
			gc.gridx = 3;
			gc.gridy = i;
			gc.weightx = 0;
			gc.insets = new Insets(10, 10, 10, 10);
			gc.fill = GridBagConstraints.BOTH;
			contentList.add(btn_modify, gc);
			btn_modify.setToolTipText("Modifica");
			btn_modify.setActionCommand(notes.get(i).getBody());
			int moment_id = notes.get(i).getID();
			// System.out.println("MOMENT ID: "+moment_id);
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

					createModifyNote(view);
					nuova = false;
					modifica = true;
					modifyTitle = null;
					modifyID = null;
					modifyTitle = lbl_title.getText();
					modifyNoteID = moment_id;
					textFieldTitleNote.setText(titolo);
					textAreaNote.setText(btn_modify.getActionCommand());
					Note note = view.getNotebyID(moment_id);
					textLike.setText(Integer.toString(note.getLike()));
					if (view.iLikeThisNote(note))
						btnLike.setBackground(Color.RED);
					note = null;
					textAreaNote.setEditable(false);
					btnSave.setEnabled(false);

					if (isShare) {
						btnSave.setEnabled(true);

						// modifyID= view.getIDbyTitle(lbl_title.getText());
						modifyID = tmpID;
						//
						// if(modifyID==-1){
						// showInfoMessage("La nota � stata aggiornata, necessario un refresh della
						// pagina");
						// refreshNoteList(view);
						// createModifyNote(view);
						// modifyID=null;
						// return;
						//
						// }

						Comunication input = view.modifyID(modifyID);
						if (!input.getBoolean()) {
							showInfoMessage("accesso contemporaneo ad un'altro utente");
							modifyID = null;
							return;
						}
						if (input.getInfo().equals("modify_id_response_refresh")) {
							showInfoMessage("La nota � stata aggiornata, necessario un refresh della pagina");
							loadInfo(view);
							refreshNoteList(view);
						} else {
							modifyText = new StringBuffer();
							modifyText.append(titolo);
							modifyText.append(btn_modify.getActionCommand());
							textAreaNote.setBackground(new JTextArea().getBackground());
							textAreaNote.setEditable(true);
						}

					}

				}
			});

			// col 3
			gc = new GridBagConstraints();
			GregorianCalendar data = null;
			if (notes.get(i).getUpdatedAt() == null)
				data = (GregorianCalendar) notes.get(i).getCreatedAt();
			else
				data = (GregorianCalendar) notes.get(i).getUpdatedAt();
			SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy " + " hh:mm");
			fmt.setCalendar(data);
			String dateFormatted = fmt.format(data.getTime());
			JLabel lb_data = new JLabel(dateFormatted);
			// lb_data.setPreferredSize(new Dimension(20, 20));
			gc.gridx = 2;
			gc.gridy = i;
			gc.weightx = 0;
			gc.insets = new Insets(10, 10, 10, 10);
			gc.fill = GridBagConstraints.BOTH;
			contentList.add(lb_data, gc);
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

		}

		contentList.repaint();

		repaint();

	}

	/**
	 * crea l'area di modifica della nota
	 * 
	 * @param view
	 * @author Stefano Poma
	 */
	private void createModifyNote(MainView view) {
		contentNote.removeAll();
		contentNote.revalidate();

		loadInfo(view);

		view.stopModify();

		contentNote.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		modifyID = null;
		// row 0
		// col 0-3
		gbc_textFieldTitleNote = new GridBagConstraints();
		textFieldTitleNote = new JTextField();
		textFieldTitleNote.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldTitleNote.setText("Select one note...");
		textFieldTitleNote.setPreferredSize(new Dimension(100, 30));
		textFieldTitleNote.setEditable(false);
		gbc_textFieldTitleNote.weightx = 1;
		gbc_textFieldTitleNote.gridx = 0;
		gbc_textFieldTitleNote.gridy = 0;
		gbc_textFieldTitleNote.gridwidth = 3;
		gbc_textFieldTitleNote.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldTitleNote.anchor = GridBagConstraints.LINE_START;
		gbc_textFieldTitleNote.insets = new Insets(10, 10, 10, 10);
		contentNote.add(textFieldTitleNote, gbc_textFieldTitleNote);

		// row 1
		// col 0-3
		gc = new GridBagConstraints();
		textAreaNote = new JTextArea();
		textAreaNote.setText("");
		JScrollPane scrollPaneNote = new JScrollPane(textAreaNote);
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 3;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(10, 10, 5, 10);
		contentNote.add(scrollPaneNote, gc);

		// row 2
		// col 0
		try {
			ImageIcon addIcon = new ImageIcon("LikeButton.png");
			Image im = addIcon.getImage();
			Image newimg = im.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			btnLike = new JButton(new ImageIcon(newimg));
			btnLike.setContentAreaFilled(true);
			btnLike.setMargin(new Insets(0, 0, 0, 0));
			btnLike.setBorder(null);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		btnLike.setEnabled(false);
		btnLike.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnLike.setEnabled(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnLike.setEnabled(false);
			}
		});
		btnLike.setToolTipText("Pinned the note");
		btnLike.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textFieldTitleNote.getText().equals("Select one note..."))
					return;
				// Note note= view.getNoteByTitleLike(textFieldTitleNote.getText());
				// System.out.println("ID DELLA NOTA A CUI METTO LIKE: "+modifyNoteID);
				Note note = view.getNotebyID(modifyNoteID);
				// System.out.println("NOTA A CUI METTO LIKE: "+note);

				if (btnLike.getBackground().equals(new JButton().getBackground())) {
					btnLike.setBackground(Color.RED);
					note.addLike();
					note = view.addLikedUser(note);
					view.exUpdate(note, note.getID());
					Integer l = Integer.valueOf(textLike.getText());
					l++;
					textLike.setText(Integer.toString(l));
					note = null;
					// createModifyNote(view);
				}

				else {
					btnLike.setBackground(new JButton().getBackground());
					note.removeLike();
					note = view.removeLikedUser(note);
					view.exUpdate(note, note.getID());
					Integer l = Integer.valueOf(textLike.getText());
					l--;
					textLike.setText(Integer.toString(l));
					note = null;
					// createModifyNote(view);
				}

			}
		});
		gc = new GridBagConstraints();
		gc.insets = new Insets(5, 5, 5, 5);
		gc.gridx = 1;
		gc.gridy = 2;
		contentNote.add(btnLike, gc);

		// col 1
		textLike = new JLabel();
		textLike.setBorder(null);
		textLike.setEnabled(false);
		gc = new GridBagConstraints();
		gc.insets = new Insets(5, 10, 5, 5);
		gc.gridx = 0;
		gc.gridy = 2;
		contentNote.add(textLike, gc);

		contentNote.repaint();
	}

	/**
	 * aggiorna il pannello contenente i bottodi di modifica alla nota selezionata
	 * 
	 * @param view
	 * @author Stefano Poma
	 */
	private void refreshButtonModify(MainView view) {
		contentModify.removeAll();
		contentModify.revalidate();

		btnExplore = new JButton("Back");
		btnExplore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnSave.setEnabled(false);
				view.stopModify();
				view.noteView();

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
		contentModify.add(btnExplore);

		try {
			ImageIcon addIcon = new ImageIcon("SaveButton.png");
			Image im = addIcon.getImage();
			Image newimg = im.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			btnSave = new JButton(new ImageIcon(newimg));
			btnSave.setContentAreaFilled(true);
			btnSave.setMargin(new Insets(0, 0, 0, 0));
			btnSave.setBorder(null);
			btnSave.setOpaque(true);
			// btnNewNote.setBorder(BorderFactory.createEmptyBorder());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		btnSave.setEnabled(false);

		btnSave.setToolTipText("Save the note");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save(view);
			}
		});
		contentModify.add(btnSave);

		try {
			ImageIcon addIcon = new ImageIcon("Colors.png");
			Image im = addIcon.getImage();
			Image newimg = im.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
			comboColors = new JButton(new ImageIcon(newimg));
			comboColors.setContentAreaFilled(true);
			comboColors.setMargin(new Insets(0, 0, 0, 0));
			comboColors.setBorder(null);
			comboColors.setOpaque(true);
			// btnNewNote.setBorder(BorderFactory.createEmptyBorder());
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
		Object[] colours = { "White", "Yellow", "Green", "Magenta" };
		comboColors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch (JOptionPane.showOptionDialog(null, "Which colour do you like?", "Choose a colour",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, colours, colours[0])) {
				case 0: {
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

		contentModify.repaint();
	}

	/**
	 * aggiorna il pannello contenente i bottoni di navigazione
	 * 
	 * @param view
	 * @author Stefano Poma
	 */
	private void refreshButton(MainView view) {

		contentButton.removeAll();
		contentButton.revalidate();

		loadInfo(view);

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
				// comboFilter.setEnabled(false);
			}
		});
		comboFilter.setToolTipText("Filter your list");
		comboFilter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (modifyText != null) {
					StringBuffer str = new StringBuffer();
					str.append(textFieldTitleNote.getText());
					str.append(textAreaNote.getText());
					removeBlankSpace(str);
					removeBlankSpace(modifyText);
					// System.out.println("IL MIO TESTO: "+str.toString());
					// System.out.println("IL TESTO DEL CONFRONTO: "+modifyText.toString());
					if (!modifyText.toString().equals(str.toString()) && modifyID != null) {
						// System.out.println("non sono uguali");

						if (JOptionPane.showConfirmDialog(null, "Vuoi salvare le modifiche?", "INFO",
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							save(view);

						}
					}
				}
				modifyText = null;

				switch ((String) comboFilter.getSelectedItem()) {

				case "Titolo": {
					notes = view.exFilterByTitle();
					// System.out.println(notes);
					btnSave.setEnabled(false);
					refreshNoteList(view);
					createModifyNote(view);
					comboFilter.setEnabled(false);
					break;
				}
				case "Data":
					notes = view.exFilterByData();
					btnSave.setEnabled(false);
					refreshNoteList(view);
					createModifyNote(view);
					comboFilter.setEnabled(false);
					break;

				case "Like":
					notes = view.exFilterByLike();
					btnSave.setEnabled(false);
					refreshNoteList(view);
					createModifyNote(view);
					comboFilter.setEnabled(false);
					break;
				case "Author":
					notes = view.exFilterByAuthor();
					btnSave.setEnabled(false);
					refreshNoteList(view);
					createModifyNote(view);
					comboFilter.setEnabled(false);
					break;
				case "Filters":
					comboFilter.setEnabled(false);
					btnSave.setEnabled(false);
					refreshNoteList(view);
					createModifyNote(view);
					break;

				default:
					comboFilter.setEnabled(false);
					break;
				}
			}
		});

		btnShareWithMe = new JButton("Share With Me");
		btnShareWithMe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnShareWithMe.setEnabled(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnShareWithMe.setEnabled(false);
			}
		});
		btnShareWithMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (modifyText != null) {
					StringBuffer str = new StringBuffer();
					str.append(textFieldTitleNote.getText());
					str.append(textAreaNote.getText());
					removeBlankSpace(str);
					removeBlankSpace(modifyText);
					// System.out.println("IL MIO TESTO: "+str.toString());
					// System.out.println("IL TESTO DEL CONFRONTO: "+modifyText.toString());
					if (!modifyText.toString().equals(str.toString()) && modifyID != null) {
						// System.out.println("non sono uguali");

						if (JOptionPane.showConfirmDialog(null, "Vuoi salvare le modifiche?", "INFO",
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							save(view);

						}
					}
				}
				modifyText = null;
				notes = view.shareWithMe();
				btnSave.setEnabled(false);
				refreshNoteList(view);
				createModifyNote(view);
				// System.out.println("NOTE CHE POSSO MODIFICARE "+notes);
			}
		});

		contentButton.add(comboFilter);
		contentButton.add(btnShareWithMe);
		contentButton.repaint();

	}

	/**
	 * metodo per la creazione dei pannelli contenitivi
	 * 
	 * @param view
	 * @author Stefano Poma
	 */
	private void buildContent(MainView view) {
		// setta il tipo di layout da utilizzare
		GridBagLayout gridBagLayout = new GridBagLayout();
		this.setLayout(gridBagLayout);

		// row 0
		// col 0 Pannello Buttons
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 5, 5);
		this.contentButton = new JPanel();
		contentButton.setBackground(Color.LIGHT_GRAY);
		this.contentButton.setPreferredSize(new Dimension(500, 50));
		gc.weightx = 0; // crescita dello 0% lungo l'asse X
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.BOTH; // riempie tutto lo spazio a disposizione in tutte le direzioni
		gc.anchor = GridBagConstraints.SOUTHWEST;
		this.add(this.contentButton, gc);
		contentButton.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// col 1
		gc_1 = new GridBagConstraints();
		gc_1.insets = new Insets(0, 0, 5, 0);
		this.contentInfo = new JPanel();
		this.contentInfo.setPreferredSize(new Dimension(100, 50));
		contentInfo.setBackground(new Color(0, 0, 255));
		gc_1.weightx = 1; // cresce completamente 100% lungo X
		gc_1.gridx = 1;
		gc_1.gridy = 0;
		gc_1.fill = GridBagConstraints.HORIZONTAL; // occupa lo spazio a disposizione solo in orizzontale
		gc_1.anchor = GridBagConstraints.LINE_START; // si fissa al inizio della cella
		this.add(this.contentInfo, gc_1);

		// row 1
		// col 0

		gc_2 = new GridBagConstraints();
		gc_2.insets = new Insets(0, 0, 5, 5);
		gc_2.anchor = GridBagConstraints.WEST;

		this.contentList = new JPanel();
		this.contentList.setPreferredSize(new Dimension(500, notes.size() * 47));
		gc_2.weightx = 0;
		gc_2.weighty = 0;
		gc_2.gridx = 0;
		gc_2.gridy = 1;
		gc_2.fill = GridBagConstraints.BOTH;
		JScrollPane scroll = new JScrollPane(contentList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(30, 30));
		this.add(scroll, gc_2);

		// col 1
		gc_3 = new GridBagConstraints();
		gc_3.insets = new Insets(0, 0, 5, 0);
		this.contentNote = new JPanel();
		contentNote.setBackground(new Color(240, 230, 140));
		// contentNote.setLayout(null);
		gc_3.weightx = 1;
		gc_3.weighty = 1.0;
		gc_3.gridx = 1;
		gc_3.gridy = 1;
		gc_3.fill = GridBagConstraints.BOTH;
		// gc.insets = new Insets(10, 10, 0, 0);
		gc_3.anchor = GridBagConstraints.LINE_START;
		this.add(this.contentNote, gc_3);

		// row 2
		// col 0 vuota
		// col 1
		gc_4 = new GridBagConstraints();
		gc_4.insets = new Insets(0, 0, 5, 0);
		this.contentModify = new JPanel();
		contentModify.setBackground(new Color(255, 255, 0));
		this.contentModify.setPreferredSize(new Dimension(100, 50));
		gc_4.weightx = 0.001;
		gc_4.gridx = 1;
		gc_4.gridy = 2;
		gc_4.fill = GridBagConstraints.HORIZONTAL;
		// gc.insets = new Insets(10, 10, 0, 0);
		gc_4.anchor = GridBagConstraints.LINE_START;
		this.add(this.contentModify, gc_4);

	}
}
