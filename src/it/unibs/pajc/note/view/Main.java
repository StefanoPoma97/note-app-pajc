package it.unibs.pajc.note.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Main {
	  public static void main(String[] args) {

	    JFrame f = new JFrame();
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    int x = 5;
	    int y = 5;
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(x, y));
	    for (int i = 0; i < x * y; i++) {
	      JButton button = new JButton(String.valueOf(i));
	      button.setPreferredSize(new Dimension(100, 100));
	      panel.add(button);
	    }
	    JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
	    container.add(panel);
	    JScrollPane scrollPane = new JScrollPane(container);
	    f.getContentPane().add(scrollPane);

	    f.pack();
	    f.setLocationRelativeTo(null);
	    f.setVisible(true);
	  }
	}