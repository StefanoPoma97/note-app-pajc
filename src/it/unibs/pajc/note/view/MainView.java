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
	private UserController userController=new UserController();
//	private NoteController noteController=null;
	private JTextArea textArea;
	private LoginV loginV;

	
	
	
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
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		loginV = new LoginV(this);
		contentPanel.add(loginV);
		loginV.setLayout(new BorderLayout(0, 0));
						
		//pannello per messaggi di utilit�
		JPanel contentMessage = new JPanel();
		contentMessage.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(contentMessage, BorderLayout.SOUTH);
		contentMessage.setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		contentMessage.add(textArea);
		
		
		
		
		
		
	}
}
