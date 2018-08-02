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
	private GridBagConstraints gc_1;

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
//		this.lFileName.setPreferredSize(new Dimension(300, 30));
		//aumenta sullo spazio vuoto lungo x
		gc.weightx=0.4;
		//aumenta sullo spazio vuoto lungo y
		gc.weighty=0.4;
		gc.gridx = 0;
		gc.gridy = 0;
		//aumenta in che modo(uniforme)
		gc.fill=GridBagConstraints.BOTH;
		//ancoraggio
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(10, 10, 10, 10);
		gc.anchor = GridBagConstraints.CENTER;
		this.add(this.lFileName, gc);
		
			//colonna2
		gc_1 = new GridBagConstraints();
		gc_1.insets = new Insets(20, 0, 0, 0);
		this.tfFile = new JTextField(20);
		this.tfFile.setEditable(false);
//		this.tfFile.setPreferredSize(new Dimension(300, 30));
		gc_1.fill=GridBagConstraints.BOTH;
		gc_1.anchor = GridBagConstraints.CENTER;
		gc_1.weighty=0.4;
		gc_1.gridx = 1;
		gc_1.gridy = 0;
		gc_1.weightx=0.5;
		
		this.add(this.tfFile, gc_1);

			// Colonna 2
		gc = new GridBagConstraints();
		this.fcButton = new JButton("Browse");
		this.fcButton.setPreferredSize(new Dimension(30, 30));
		gc.weightx=0.4;
		gc.weighty=0.4;
		gc.gridx = 2;
		gc.gridy = 0;
		gc.fill=GridBagConstraints.BOTH;
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
		gc.weighty=0.4;
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
