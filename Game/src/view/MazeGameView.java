package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MazeGameView extends ViewMain{

	private JLabel headerMessage  = new JLabel("Maze Game");

	public MazeGameView(){

		JPanel mazePanel = new JPanel();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setSize(getScreenWidth(), getScreenHeight());
		
		mazePanel.add(headerMessage);
		
		this.add(mazePanel);
		
		this.setVisible(false);

	}
		
	
}
