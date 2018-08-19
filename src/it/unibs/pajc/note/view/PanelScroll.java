package it.unibs.pajc.note.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class PanelScroll extends JPanel {

	private JPanel panel1;
	private JPanel panel2;
	private JPanel container= null;
	/**
	 * Create the panel.
	 */
	public PanelScroll(JFrame f) {
		
		 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GridBagLayout gridBagLayout = new GridBagLayout();
		this.setLayout(gridBagLayout);
		
		//row 0
		//col 0  Pannello Buttons
	GridBagConstraints gc = new GridBagConstraints();
	gc.insets = new Insets(0, 0, 5, 5);
	panel1 = new JPanel();
	gc.weighty=1;
	gc.weightx=1; //crescita dello 0% lungo l'asse X
	gc.gridx = 0;
	gc.gridy = 0;
	gc.fill=GridBagConstraints.BOTH; //riempie tutto lo spazio a disposizione in tutte le direzioni
	
	
	int x = 5;
    int y = 5;
    panel1.setLayout(new GridLayout(x, y));
    for (int i = 0; i < x * y; i++) {
      JButton button = new JButton(String.valueOf(i));
      button.setPreferredSize(new Dimension(100, 100));
      panel1.add(button);
    }

    container = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    container.add(panel1);
    JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.getViewport().add(container);
    add(scrollPane);
    this.add(this.container, gc);
//    f.getContentPane().add(scrollPane);
	
		//col 1
	gc = new GridBagConstraints();
	gc.insets = new Insets(0, 0, 5, 0);
	panel2= new JPanel();
	panel2.setBackground(Color.YELLOW);
	gc.weighty=1;
	gc.weightx=1; //crescita dello 0% lungo l'asse X
	gc.gridx = 0;
	gc.gridy = 1;
	gc.fill=GridBagConstraints.BOTH;
	for(int i=0; i<20;i++){
		JButton btn = new JButton("test");
		panel2.add(btn);
	}
	this.add(panel2, gc);
		
		
		

	    f.pack();
	    f.setLocationRelativeTo(null);
	    f.setVisible(true);
		
		
	}

}
