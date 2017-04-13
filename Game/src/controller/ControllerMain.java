package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import view.StartScreenView;
import view.ViewMain;
import view.MazeGameView;

public class ControllerMain {

	private ViewMain view;
	
	public ControllerMain(ViewMain v){
		this.view = v;
		view.setVisible(true);
	}
	
	
	public void changeView(ViewMain v){
		view.setVisible(false);
		this.view = v;
		view.setVisible(true);
	}
	
	public static void main(String[] args){
		
		StartScreenView startV = new StartScreenView();
		MazeGameView mazeV = new MazeGameView();

		ControllerMain controller = new ControllerMain(mazeV);

	}//main

}
