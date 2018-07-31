package it.unibs.pajc.note.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import it.unibs.pajc.note.model.User;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;

public class LoginView extends JPanel {
	
	//stringa per comunicare informazioni al main form
	private String out=null;
	//user creato o per il login
	private User user;
	
	private JTextField textFieldName;
	private JTextField textFieldPass;	
	
	private ArrayList<ActionListener> subscriberOnList = new ArrayList<>();
	
	//metodo per aggiungere Actionlistener alla lista
	public void addActionList (ActionListener l)
	{
		subscriberOnList.add(l);
	}	
	
	
	//fire della lista 
	private void fireList (ActionEvent e)
	{
		e.setSource(this);
		for (ActionListener l: subscriberOnList)
		{
			l.actionPerformed(e);
		}
	}
	
	
	public void Login (ActionEvent e)
	{
		System.out.println("richiamo il metodo fire");
		fireList(e);
		//user= new User (textFieldName.getText(), textFieldPass.getText());
		setVisible(false);
	}
	
	public void CreateAccount (ActionEvent e)
	{
		System.out.println("richiamo il metodo fire");
		fireList(e);
		System.out.println("aggiornato la lista");
	}
	
	public String getNome(){
		return textFieldName.getText();
	}
	
	public String getPass(){
		return textFieldPass.getText();
	}
	public User getUser(){
		return user;
	}
	
	public String getAction() {
		return out;
	}
	

	
	public LoginView(MainForm main) {
		setLayout(null);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(192, 96, 122, 35);
		textFieldName.setHorizontalAlignment(SwingConstants.CENTER);
		add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldPass = new JTextField();
		textFieldPass.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldPass.setColumns(10);
		textFieldPass.setBounds(192, 142, 122, 35);
		add(textFieldPass);
		
		
		
		JFormattedTextField textName = new JFormattedTextField();
		textName.setHorizontalAlignment(SwingConstants.CENTER);
		textName.setEditable(false);
		textName.setText("Name");
		textName.setBounds(102, 96, 66, 35);
		add(textName);
		
		JFormattedTextField textPassword = new JFormattedTextField();
		textPassword.setText("Password");
		textPassword.setHorizontalAlignment(SwingConstants.CENTER);
		textPassword.setEditable(false);
		textPassword.setBounds(102, 142, 66, 35);
		add(textPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(102, 230, 99, 23);
		add(btnLogin);
		
		JButton btnNewAccount = new JButton("New Account");
		btnNewAccount.setBounds(225, 230, 102, 23);
		add(btnNewAccount);
		
		
		
		
		//action listener
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				System.out.println("riciamo il metodo login");
//				Login(e);
//				System.out.println("out = login");
//				out="login";
//				main.lo(textName.getText(), textPassword.getText(), "login");
			}
		});
		
		btnNewAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				System.out.println("richiamo il metodo create");
//				CreateAccount(e);
//				System.out.println("out = create");
//				out="create";
				main.lo(textFieldName.getText(), textFieldPass.getText(), "Create");
				
			}
		});
		
		

	}
}
