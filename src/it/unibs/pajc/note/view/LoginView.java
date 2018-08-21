package it.unibs.pajc.note.view;

import java.awt.Color;
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
import javax.swing.border.LineBorder;

import it.unibs.pajc.note.status.ValidationError;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView extends JPanel {
	
	//componenti utilizzati
	private JTextField textFieldPassword;
	private JTextField textFieldName;
	private JButton btnCreateAccount;
	private JButton btnLogin;
	private JFormattedTextField textName;
	private JFormattedTextField textPassword;

	 /* metodo per far apparire messaggio di errore 
		 * @param in stringa
		 */
		private void showErrorMessage(String in){
			JOptionPane.showMessageDialog(this,
				    in,
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		}
		
		/**
		 * metodo per far apparire messaggio di errore 
		 * @param in ValidateError
		 */
		private void showErrorMessage(ValidationError in){
			JOptionPane.showMessageDialog(this,
				    in.toString(),
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
		}

		/**
		 * metodo per far apparire un messaggio di segnalazione
		 * @param in
		 */
		private void showInfoMessage(ValidationError in){
			JOptionPane.showMessageDialog(null, in.toString());
		}
		
		/**
		 * metodo per far apparire un messaggio di segnalazione
		 * @param in
		 */
		private void showInfoMessage(String in){
			JOptionPane.showMessageDialog(null, in);
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
					showErrorMessage("Login errato");
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
							showInfoMessage(validate.toString());
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
	btnCreateAccount.setEnabled(false);
	btnCreateAccount.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseEntered(MouseEvent e) {
			if (!textFieldName.getText().isEmpty() && !textFieldPassword.getText().isEmpty()){
				textFieldPassword.setBorder(new LineBorder(Color.GREEN));
				textFieldName.setBorder(new LineBorder(Color.GREEN));
			}
			else{
				textFieldPassword.setBorder(new JTextField().getBorder());
				textFieldName.setBorder(new JTextField().getBorder());
			}
				btnCreateAccount.setEnabled(true);
		}
		@Override
		public void mouseExited(MouseEvent e) {
			textFieldPassword.setBorder(new JTextField().getBorder());
			textFieldName.setBorder(new JTextField().getBorder());
			btnCreateAccount.setEnabled(false);
		}
	});
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
	btnLogin.setEnabled(false);
	btnLogin.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseEntered(MouseEvent e) {
			if (!textFieldName.getText().isEmpty() && !textFieldPassword.getText().isEmpty())
				btnLogin.setEnabled(true);
		}
		@Override
		public void mouseExited(MouseEvent e) {
			btnLogin.setEnabled(false);
		}
	});
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
