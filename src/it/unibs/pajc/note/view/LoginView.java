package it.unibs.pajc.note.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import it.unibs.pajc.note.status.ValidationError;

public class LoginView extends JPanel {

	// componenti utilizzati
	private JTextField textFieldPassword;
	private JTextField textFieldName;
	private JButton btnCreateAccount;
	private JButton btnLogin;
	private JFormattedTextField textName;
	private JFormattedTextField textPassword;

	/**
	 * mostra un messaggio di errore data una stringa
	 * 
	 * @param in
	 * @author Stefano Poma
	 */
	private void showErrorMessage(String in) {
		JOptionPane.showMessageDialog(this, in, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * metodo per far apparire messaggio di errore dato un ValidationError
	 * 
	 * @param in
	 *            ValidateError
	 * @author Stefano Poma
	 */
	private void showErrorMessage(ValidationError in) {
		JOptionPane.showMessageDialog(this, in.toString(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * metodo per far apparire un messaggio di segnalazione dato un ValidationError
	 * 
	 * @param in
	 * @author Stefano Poma
	 */
	private void showInfoMessage(ValidationError in) {
		JOptionPane.showMessageDialog(null, in.toString());
	}

	/**
	 * metodo per far apparire un messaggio di segnalazione data una stringa
	 * 
	 * @param in
	 * @author Stefano Poma
	 */
	private void showInfoMessage(String in) {
		JOptionPane.showMessageDialog(null, in);
	}

	/**
	 * Create the panel.
	 */
	// costruttore � necessario che riceva il MainView
	public LoginView(MainView view) {
		setPreferredSize(new Dimension(500, 500));
		this.build(view);
		this.actionListener(view);
		String out = view.connetti();

	}

	/**
	 * metodo per settare tutti gli actionListener necessari
	 * 
	 * @param view
	 * @author Stefano Poma
	 */
	private void actionListener(MainView view) {
		// ACTION LISTENER
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean validate = view.login(textFieldName.getText(), textFieldPassword.getText());
				if (validate) {
					setVisible(false);
				} else {
					showErrorMessage("Login errato");
					textFieldName.setText("");
					textFieldPassword.setText("");
				}
			}
		});

		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ValidationError validate = view.create(textFieldName.getText(), textFieldPassword.getText());
				if (validate.equals(ValidationError.CORRECT)) {
					setVisible(false);
				}

				else {
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
	private void build(MainView view) {

		this.setLayout(new GridBagLayout());

		// row 0
		// col 0 vuota
		// col 1
		GridBagConstraints gc = new GridBagConstraints();
		this.textName = new JFormattedTextField();
		textName.setHorizontalAlignment(SwingConstants.CENTER);
		textName.setText("Name");
		textName.setEditable(false);
		this.textName.setPreferredSize(new Dimension(150, 30));
		gc.weightx = 0.001;
		gc.gridx = 1;
		gc.gridy = 0;
		gc.insets = new Insets(30, 0, 10, 30);
		gc.anchor = GridBagConstraints.LINE_END;
		this.add(this.textName, gc);

		// col 2
		gc = new GridBagConstraints();
		this.textFieldName = new JTextField();
		this.textFieldName.setPreferredSize(new Dimension(180, 30));
		gc.weightx = 0.001;
		gc.gridx = 2;
		gc.gridy = 0;
		gc.insets = new Insets(30, 30, 10, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		this.add(this.textFieldName, gc);

		// row 1
		// col 0 vuota
		// col 1
		gc = new GridBagConstraints();
		this.textPassword = new JFormattedTextField();
		textPassword.setHorizontalAlignment(SwingConstants.CENTER);
		textPassword.setText("Password");
		textPassword.setEditable(false);
		this.textPassword.setPreferredSize(new Dimension(150, 30));
		gc.weightx = 0.001;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.insets = new Insets(0, 0, 10, 30);
		gc.anchor = GridBagConstraints.LINE_END;
		this.add(this.textPassword, gc);

		// col 2
		gc = new GridBagConstraints();
		this.textFieldPassword = new JTextField();
		this.textFieldPassword.setPreferredSize(new Dimension(180, 30));
		gc.weightx = 0.001;
		gc.gridx = 2;
		gc.gridy = 1;
		gc.insets = new Insets(0, 30, 10, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		this.add(this.textFieldPassword, gc);

		// row 2
		// col 0 vuota
		// col 1
		gc = new GridBagConstraints();
		this.btnCreateAccount = new JButton("New Account");
		btnCreateAccount.setEnabled(false);
		btnCreateAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!textFieldName.getText().isEmpty() && !textFieldPassword.getText().isEmpty()) {
					// i text field diventano verdi se viene inserito qualcosa (NO controllo su cosa
					// � stato inserito)
					textFieldPassword.setBorder(new LineBorder(Color.GREEN));
					textFieldName.setBorder(new LineBorder(Color.GREEN));
				} else {
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
		gc.weightx = 0.001;
		gc.weighty = 0.001;
		gc.gridx = 1;
		gc.gridy = 2;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(10, 10, 10, 40);
		this.add(this.btnCreateAccount, gc);

		// col 2
		gc = new GridBagConstraints();
		this.btnLogin = new JButton("Login");
		btnLogin.setEnabled(false);
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!textFieldName.getText().isEmpty() && !textFieldPassword.getText().isEmpty()) {
					btnLogin.setEnabled(true);
				}

			}

			@Override
			public void mouseExited(MouseEvent e) {

				btnLogin.setEnabled(false);
			}
		});
		this.btnLogin.setPreferredSize(new Dimension(150, 40));
		gc.weightx = 0.001;
		gc.weighty = 0.001;
		gc.gridx = 2;
		gc.gridy = 2;
		gc.insets = new Insets(10, 10, 10, 10);
		gc.insets = new Insets(10, 40, 10, 10);
		gc.anchor = GridBagConstraints.LINE_START;
		this.add(this.btnLogin, gc);

	}

}
