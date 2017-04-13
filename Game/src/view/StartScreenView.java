package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class StartScreenView extends ViewMain {

	private JLabel welcomeMessage  = new JLabel("Welcome to Estuary Adventure!");
	private JLabel selectMessage = new JLabel("Please select a game mode.");
	private JButton mazeButton = new JButton("Play Maze Game!");
	private JButton beachButton = new JButton("Play Estuary Defense Game!");
	private JButton storyCubesButton = new JButton("Play Story Cubes Game!");

	public StartScreenView(){

		JPanel startPanel = new JPanel();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setSize(getScreenWidth(), getScreenHeight());

		startPanel.add(welcomeMessage);
		
		startPanel.add(selectMessage);
		
		startPanel.add(mazeButton);
		
		startPanel.add(beachButton);
		
		
		startPanel.add(storyCubesButton);

		this.add(startPanel);
		
		this.setVisible(false);

	}

	public JButton getMazeButton(){
		return mazeButton;
	}
	


}