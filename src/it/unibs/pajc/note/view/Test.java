package it.unibs.pajc.note.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Test extends JPanel {
	private JLabel lFileName;
	private JTextField tfFile;
	private JButton fcButton;
	private JLabel lFileError;

	/**
	 * Create the panel.
	 */
	//costruttore richiama il metodo build
	public Test() {
			this.build();
	}
	
	private void build() {
		this.setLayout(new GridBagLayout());
		
		
		//riga1
			//colonna1
		GridBagConstraints gc = new GridBagConstraints();
		this.lFileName = new JLabel("Filename:");
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = new Insets(10, 10, 10, 10);
		gc.anchor = GridBagConstraints.CENTER;
		this.add(this.lFileName, gc);
		
			//colonna2
		gc = new GridBagConstraints();
		this.tfFile = new JTextField(20);
		this.tfFile.setEditable(false);
		gc.gridx = 1;
		gc.gridy = 0;
		gc.weightx=0.0;
		gc.anchor = GridBagConstraints.CENTER;
		this.add(this.tfFile, gc);

			// Colonna 2
		gc = new GridBagConstraints();
		this.fcButton = new JButton("Browse");
		this.fcButton.setPreferredSize(new Dimension(200, 50));
		gc.gridx = 2;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.CENTER;
		this.add(this.fcButton, gc);
		
		// Row 1 - File not selected Error
					// Col 0 - Empty

					// Col 1
		gc = new GridBagConstraints();
		this.lFileError = new JLabel("Please select a file.");
		this.lFileError.setForeground(Color.RED);
		this.lFileError.setVisible(true);
		gc.insets = new Insets(10, 10, 10, 10);
		gc.gridx = 1;
		gc.gridy = 1;
		//quante celle prende lungo x e y
		gc.gridwidth=2;
		//altezza e larghezza da 0.0 a 1.0
//		gc.weighty=0.9;
		gc.anchor = GridBagConstraints.LINE_START;
		this.add(this.lFileError, gc);
	}

}
