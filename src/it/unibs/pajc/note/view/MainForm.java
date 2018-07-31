package it.unibs.pajc.note.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import it.unibs.pajc.note.model.User;

import java.awt.BorderLayout;

public class MainForm {

	private JFrame frame;
	
	private String in= null;
	private String ps=null;
	private String action=null;
	private User user=null;
	
	public void lo(String name, String pass, String _action){
		in=name;
		ps=pass;
		action=_action;
		System.out.println("nel main form nome: "+in+ " pass: "+ps+" action: "+action);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
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
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		LoginView loginView = new LoginView(this);
		frame.getContentPane().add(loginView, BorderLayout.CENTER);
		
		
		
		
		
		
		loginView.addActionList(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				String name= loginView.getNome();
//				String pass= loginView.getPass();
//				System.out.println(name);
//				System.out.println(pass);
//				user= loginView.getUser();
//				in=loginView.getAction();
//				System.out.println("main : arrivato stringa "+in);
//				JOptionPane.showMessageDialog(null, "verificando...");
//				System.out.println("main : arrivato utente "+user.toString());
			}
		});
		
		
		
	}

}
