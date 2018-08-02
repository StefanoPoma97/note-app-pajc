package it.unibs.pajc.note.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import it.unibs.pajc.note.model.User;
import it.unibs.pajc.note.status.ValidationError;

public class LoginV extends JPanel {
	
	
	// stringa per comunicare informazioni al main form
	private String out;
	// user creato o per il login
	private User user = null;


	private JTextField textFieldName;
	private JTextField textFieldPass;	


	private ArrayList<ActionListener> subscriberOnList = new ArrayList<>();

	// metodo per aggiungere Actionlistener alla lista
	public void addActionList(ActionListener l) {
		subscriberOnList.add(l);
	}	
	

	// fire della lista
	private void fireList(ActionEvent e) {
		e.setSource(this);
		for (ActionListener l : subscriberOnList) {
			l.actionPerformed(e);
		}
	}
	

	
	private void showMessage(String in){
		JOptionPane.showMessageDialog(null, in);
	}
	


	public LoginV(MainView view) {
												
		textFieldPass = new JTextField();
		textFieldPass.setBounds(227, 106, 86, 20);
		textFieldPass.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldPass.setColumns(10);
										
		JFormattedTextField textPassword = new JFormattedTextField();
		textPassword.setBounds(126, 106, 66, 20);
		textPassword.setText("Password");
		textPassword.setHorizontalAlignment(SwingConstants.CENTER);
		textPassword.setEditable(false);
								
		JButton btnNewAccount = new JButton("New Account");
		btnNewAccount.setBounds(126, 172, 95, 36);
										
		
						
		textFieldName = new JTextField();
		textFieldName.setBounds(227, 57, 86, 20);
		textFieldName.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldName.setColumns(10);
				
		JFormattedTextField textName = new JFormattedTextField();
		textName.setBounds(126, 57, 66, 20);
		textName.setHorizontalAlignment(SwingConstants.CENTER);
		textName.setEditable(false);
		textName.setText("Name");
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(256, 172, 57, 36);
		setLayout(null);
		add(textFieldPass);
		add(textPassword);
		add(btnNewAccount);
		add(textFieldName);
		add(textName);
		add(btnLogin);
				
		// action listener
				
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean validate= view.login(textFieldName.getText(), textFieldPass.getText());
				if (validate){
					setVisible(false);
				}
				else{
					//TODO creare archivio errori
					showMessage("Login errato");
					textFieldName.setText("");
					textFieldPass.setText("");
				}						
			}
		});

		btnNewAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						ValidationError validate= view.create(textFieldName.getText(), textFieldPass.getText());
						if (validate.equals(ValidationError.CORRECT))
							setVisible(false);
						else{
							textFieldName.setText("");
							textFieldPass.setText("");
							showMessage(validate.toString());
						}
						
				
			}
		});
	}
}
