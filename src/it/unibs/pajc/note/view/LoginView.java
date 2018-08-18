package it.unibs.pajc.note.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import it.unibs.pajc.note.status.ValidationError;

public class LoginView extends JPanel {
	
	//componenti utilizzati
	private JTextField textFieldPassword;
	private JTextField textFieldName;
	private JButton btnCreateAccount;
	private JButton btnLogin;
	private JFormattedTextField textName;
	private JFormattedTextField textPassword;

	/**
	 * metodo per far apparire messaggio di errore o segnalazione
	 * @param in stringa
	 */
	private void showMessage(String in){
		JOptionPane.showMessageDialog(null, in);
	}
	
	/**
	 * metodo per far apparire messaggio di errore o segnalazione
	 * @param in ValidateError
	 */
	private void showMessage(ValidationError in){
		JOptionPane.showMessageDialog(null, in.toString());
	}
	
	
	/**
	 * Create the panel.
	 */
	//costruttore è necessario che riceva il MainView
	public LoginView(MainView view) {
		setPreferredSize(new Dimension(500, 500));
		this.build();
		this.actionListener(view);

	}
	
	/**
	 * metodo per settare tutti gli actionListener necessari
	 * @param view
	 */
	private void actionListener(MainView view){
		//ACTION LISTENER
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean validate= view.login(textFieldName.getText(), textFieldPassword.getText());
				if (validate){
					setVisible(false);
				}
				else{
					//TODO creare archivio errori
					showMessage("Login errato");
					textFieldName.setText("");
					textFieldPassword.setText("");
				}						
			}
		});

		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						ValidationError validate= view.create(textFieldName.getText(), textFieldPassword.getText());
						if (validate.equals(ValidationError.CORRECT)){
							setVisible(false);
						}
							
						
						else{
							textFieldName.setText("");
							textFieldPassword.setText("");
							showMessage(validate.toString());
						}
			}
		});
	}
	
	/**
	 * Metodo per costruire l'interfaccia grafica relativa al Login
	 */
	private void build() {

		this.setLayout(new GridBagLayout());
		
	//row 0
		//col 0 vuota
		//col 1
	GridBagConstraints gc = new GridBagConstraints();
	this.textName = new JFormattedTextField();
	textName.setHorizontalAlignment(SwingConstants.CENTER);
	textName.setText("Name");
	textName.setEditable(false);
	this.textName.setPreferredSize(new Dimension(150, 30));
	gc.weightx=0.001;
//	gc.weighty=0.001;
	gc.gridx = 1;
	gc.gridy = 0;
	gc.insets = new Insets(30, 0, 10, 30);
	gc.anchor = GridBagConstraints.LINE_END;
	this.add(this.textName, gc);
	
		//col 2
	gc = new GridBagConstraints();
	this.textFieldName = new JTextField();
	this.textFieldName.setPreferredSize(new Dimension(180, 30));
	gc.weightx=0.001;
//	gc.weighty=0.001;
	gc.gridx = 2;
	gc.gridy = 0;
	gc.insets = new Insets(30, 30, 10, 0);
	gc.anchor = GridBagConstraints.LINE_START;
	this.add(this.textFieldName, gc);
	
	//row 1
		//col 0 vuota
		//col 1
	gc = new GridBagConstraints();
	this.textPassword = new JFormattedTextField();
	textPassword.setHorizontalAlignment(SwingConstants.CENTER);
	textPassword.setText("Password");
	textPassword.setEditable(false);
	this.textPassword.setPreferredSize(new Dimension(150, 30));
	gc.weightx=0.001;
//	gc.weighty=0.001;
	gc.gridx = 1;
	gc.gridy = 1;
	gc.insets = new Insets(0, 0, 10, 30);
	gc.anchor = GridBagConstraints.LINE_END;
//	gc.anchor = GridBagConstraints.CENTER;
	this.add(this.textPassword, gc);
	
		//col 2
	gc = new GridBagConstraints();
	this.textFieldPassword = new JTextField();
	this.textFieldPassword.setPreferredSize(new Dimension(180, 30));
	gc.weightx=0.001;
//	gc.weighty=0.001;
	gc.gridx = 2;
	gc.gridy = 1;
	gc.insets = new Insets(0, 30, 10, 0);
	gc.anchor = GridBagConstraints.LINE_START;
//	gc.anchor = GridBagConstraints.CENTER;
	this.add(this.textFieldPassword, gc);
	
	//row 2
		//col 0 vuota
		//col 1
	gc = new GridBagConstraints();
	this.btnCreateAccount = new JButton("New Account");
	this.btnCreateAccount.setPreferredSize(new Dimension(150, 40));
	gc.weightx=0.001;
	gc.weighty=0.001;
	gc.gridx = 1;
	gc.gridy = 2;
	//aumenta in che modo(uniforme)
//	gc.fill=GridBagConstraints.BOTH;
	//ancoraggio
	gc.anchor = GridBagConstraints.LINE_END;
	gc.insets = new Insets(10, 10, 10, 40);
	this.add(this.btnCreateAccount, gc);
	
		//col 2
	gc = new GridBagConstraints();
	this.btnLogin = new JButton("Login");
	this.btnLogin.setPreferredSize(new Dimension(150, 40));
	gc.weightx=0.001;
	gc.weighty=0.001;
	gc.gridx = 2;
	gc.gridy = 2;
	gc.insets = new Insets(10, 10, 10, 10);
//	gc.fill = GridBagConstraints.CENTER;
	gc.insets = new Insets(10, 40, 10, 10);
	gc.anchor = GridBagConstraints.LINE_START;
	this.add(this.btnLogin, gc);

	}

}
