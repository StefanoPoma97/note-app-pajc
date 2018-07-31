package it.unibs.pajc.note.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import it.unibs.pajc.note.model.User;

import java.awt.BorderLayout;

public class MainForm {

	private JFrame frame;

	private String in = null;
	private User user = null;

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

		LoginView loginView = new LoginView();
		frame.getContentPane().add(loginView, BorderLayout.CENTER);

		loginView.addActionList(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				in = loginView.getAction();
				user = loginView.getUser();
				System.out.println("main : arrivato stringa " + in);
//				System.out.println("main : arrivato utente " + user.toString());
			}
		});
	}

}
