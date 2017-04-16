package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class StartScreenView extends JPanel {

	//Labels
	private JLabel welcomeMessage  = new JLabel("Welcome to Estuary Adventure!");
	private JLabel selectMessage = new JLabel("Please select a game mode.");
	
	//Buttons
	private JButton mazeButton = new JButton("Play Maze Game!");
	private JButton beachButton = new JButton("Play Estuary Defense Game!");
	private JButton storyCubesButton = new JButton("Play Story Cubes Game!");

	public StartScreenView(){
		this.add(welcomeMessage);
	}
	
}