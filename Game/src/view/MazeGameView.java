package view;

import model.Boat;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MazeGameView extends JPanel {
	
	private int screenWidth = 500;
	private int screenHeight = 300;
		
	private JLabel mazeMessage  = new JLabel("Maze GAMEEEEEEE!");
	
	private JButton moveRightButton = new JButton("Move Right");
	private JButton moveLeftButton = new JButton("Move Left");
	
	public MazeGameView(){
		this.add(mazeMessage);
		this.add(moveLeftButton);
		this.add(moveRightButton);
	}
	
	//Getters
	public int getScreenWidth(){
		return this.screenWidth;
	}
	
	public int getScreenHeight(){
		return this.screenHeight;
	}
	
	//paintComponent
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawRect(getScreenWidth()/2, getScreenHeight()/2, 20, 20);
		g.setColor(Color.RED);
	}
	
	
	//Set Button Listeners
	public void setupListeners(){
		
		
		
	}

	
}
