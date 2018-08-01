package it.unibs.pajc.note.view;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Test extends JPanel {

	/**
	 * Create the panel.
	 */
	public Test() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		
//		gridBagLayout.rowHeights = new int[]{50, 150, 50, 50};
//		gridBagLayout.columnWeights = new double[]{getWidth()/4, getWidth()/4};
//		gridBagLayout.columnWidths = new int[]{10,10,10};
//		gridBagLayout.rowWeights = new double[]{50.0, 50.0};
		setLayout(gridBagLayout);
		GridBagConstraints gc = new GridBagConstraints();
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.YELLOW);
//		GridBagConstraints gbc_panel = new GridBagConstraints();
//		gbc_panel.anchor = GridBagConstraints.NORTHWEST;
//		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gc.weightx=0.5;
		gc.weighty=0.5;
		gc.gridx = 0;
		gc.gridy = 0;
		add(panel, gc);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.BLUE);
//		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
//		gbc_panel_1.anchor = GridBagConstraints.NORTHWEST;
//		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gc.gridx = 1;
		gc.gridy = 0;
		add(panel_1, gc);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.RED);
//		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
//		gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
//		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gc.gridx = 0;
		gc.gridy = 1;
		add(panel_2, gc);
		
		JButton panel_3 = new JButton();
		panel_3.setBackground(Color.GREEN);
//		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
//		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
//		gbc_panel_3.anchor = GridBagConstraints.NORTHWEST;
		gc.gridx = 1;
		gc.gridy = 1;
		add(panel_3, gc);

	}

}
