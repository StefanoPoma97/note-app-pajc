package it.unibs.pajc.note.view;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import it.unibs.pajc.note.controller.UserController;
import it.unibs.pajc.note.status.ValidationError;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import java.awt.GridBagLayout;

public class mainTest {

private JFrame frame;
	
	//stringhe utili per passare info con il loginView
	private String name= null;
	private String password=null;
	private UserController userController=new UserController();
//	private NoteController noteController=null;
	private JTextArea textArea;
	private LoginView loginView;

	
	
	
	/**
	 * Metodo utilizzato nel LoginView che permette di passare nome password 
	 * restituisce true se il login � possibile
	 * @param _name
	 * @param _pass
	 */
	public Boolean login(String _name, String _pass){
		return userController.login(_name, _pass);
	}
	
	
	public ValidationError create(String name, String pass){
		
		return userController.create(name, pass);
	}

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainTest window = new mainTest();
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
	public mainTest() {
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
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		loginView = new LoginView(this);
		contentPanel.add(loginView);
						
		//pannello per messaggi di utilit�
		JPanel contentMessage = new JPanel();
		contentMessage.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(contentMessage, BorderLayout.SOUTH);
		contentMessage.setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		contentMessage.add(textArea);
		
		
		
		
		
		
	}
}
