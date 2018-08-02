package it.unibs.pajc.note.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.BoxLayout;

public class NoteView extends JPanel {
	private JPanel contentList;
	private JPanel contentButton;
	private JPanel contentNote;
	private JPanel contentModify;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private GridBagConstraints gc_1;

	/**
	 * Create the panel.
	 */
	public NoteView(MainView view) {
		build();
		
	}
	
	private void build(){
		GridBagLayout gridBagLayout = new GridBagLayout();
		this.setLayout(gridBagLayout);
		
		GridBagConstraints gc = new GridBagConstraints();
		this.btnNewButton = new JButton();
		this.btnNewButton.setPreferredSize(new Dimension(100, 30));
		gc.weightx=0;
//		gc.weighty=0.001;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill=GridBagConstraints.BOTH;
//		gc.insets = new Insets(10, 10, 0, 0);
		gc.anchor = GridBagConstraints.SOUTHWEST;
		this.add(this.btnNewButton, gc);
		
		gc_1 = new GridBagConstraints();
		gc_1.anchor = GridBagConstraints.WEST;
		this.btnNewButton_1 = new JButton();
		this.btnNewButton_1.setPreferredSize(new Dimension(100, 30));
		gc.weightx=0;
		gc_1.weighty=1.0;
		gc_1.gridx = 0;
		gc_1.gridy = 1;
		gc_1.fill=GridBagConstraints.BOTH;
				;
		this.add(this.btnNewButton_1, gc_1);
		
		gc = new GridBagConstraints();
		this.btnNewButton_2 = new JButton();
//		this.btnNewButton_1.setPreferredSize(new Dimension(100, 30));
		gc.weightx=1;
		gc.weighty=1.0;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.fill=GridBagConstraints.BOTH;
//		gc.insets = new Insets(10, 10, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
				;
		this.add(this.btnNewButton_2, gc);
		
		gc = new GridBagConstraints();
		this.btnNewButton_3 = new JButton();
		this.btnNewButton_3.setPreferredSize(new Dimension(100, 30));
		gc.weightx=0.001;
//		gc.weighty=1.0;
		gc.gridx = 1;
		gc.gridy = 2;
		gc.fill=GridBagConstraints.HORIZONTAL;
//		gc.insets = new Insets(10, 10, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
				;
		this.add(this.btnNewButton_3, gc);
		
		
		
		
		
	}

}
