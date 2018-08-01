package it.unibs.pajc.note.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class NoteV extends JPanel {

	/**
	 * Create the panel.
	 */
	public NoteV() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel contentList = new JPanel();
		add(contentList);
		
		JPanel contentButton = new JPanel();
		add(contentButton, BorderLayout.NORTH);

	}

}
