package it.unibs.pajc.note.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import it.unibs.pajc.note.controller.UserController;
import it.unibs.pajc.note.data.UserArchive;
import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.CardLayout;
import java.awt.GridLayout;

public class MainView {

	private JFrame frame;
	
	//stringhe utili per passare info con il loginView
	private String name= null;
	private String password=null;
	private String action=null;
	private JTextArea textArea;
	private LoginV loginV;
	
	
	//instanze di classi utili al controllo (poi da dividere tra client e server)
	private UserArchive users=new UserArchive();
	
	
	
	/**
	 * Metodo utilizzato nel LoginView che permette di passare nome password e tipo di azione
	 * per poter poi fare i controlli
	 * @param _name
	 * @param _pass
	 * @param _action
	 */
	public Boolean login(String _name, String _pass){
		name=_name;
		password=_pass;
		System.out.println("info arrivate: nome= "+name+ " pass= "+password+" action= "+action);
		Boolean validate = users.authenticate(name, password);
		if (validate){
			System.out.println("valido");
		}
		else{
			System.out.println("non valido");
		}
		return validate;
	}
	
	
	public ValidationError create(String _name, String _pass){
		name=_name;
		password=_pass;
		System.out.println("info arrivate: nome= "+name+ " pass= "+password+" action= "+action);
		ValidationError validate = users.add(new User(name, password));
		return validate;
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
		
		JPanel contentPanel = new JPanel();
		frame.getContentPane().add(contentPanel);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		
		loginV = new LoginV(this);
		contentPanel.add(loginV);
		loginV.setLayout(new BorderLayout(0, 0));
						
		//pannello per messaggi di utilità
		JPanel contentMessage = new JPanel();
		contentMessage.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(contentMessage, BorderLayout.SOUTH);
		contentMessage.setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		contentMessage.add(textArea);
		
		
		//solo per test
		users.add(new User ("paolo","merazza"));
		users.add(new User ("utente1","pass1"));
		
		
		
	}
}
