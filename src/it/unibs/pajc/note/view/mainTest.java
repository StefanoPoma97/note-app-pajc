package it.unibs.pajc.note.view;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import it.unibs.pajc.note.controller.UserController;
import it.unibs.pajc.note.status.ValidationError;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class mainTest {

private JFrame frame;
	
	//stringhe utili per passare info con il loginView
	private String name= null;
	private String password=null;
	private UserController userController=new UserController();

	
	
	
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
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		NoteView noteView = new NoteView((MainView) null);
		frame.getContentPane().add(noteView);
		
		
		
		
		
		
		
	}
}
