package view;

import javax.swing.*;

public class StartScreenView extends JFrame {

	private JLabel welcomeMessage  = new JLabel("Welcome to Estuary Adventure!");
	private JLabel selectMessage = new JLabel("Please select a game mode.");
	private JButton mazeButton = new JButton("Play Maze Game!");
	private JButton beachButton = new JButton("Play Beach Defense Game!");
	private JButton storyCubesButton = new JButton("Play Story Cubes Game!");

	//------------------------//
	final int screenWidth = 500;
	final int screenHeight = 300;

	StartScreenView(){

		JPanel startPanel = new JPanel();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setSize(screenWidth, screenHeight);
		
		startPanel.add(welcomeMessage);
		startPanel.add(selectMessage);
		startPanel.add(mazeButton);
		startPanel.add(beachButton);
		startPanel.add(storyCubesButton);
		
		this.add(startPanel);

	}


}
