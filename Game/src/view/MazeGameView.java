package view;

import model.Boat;
import model.MazeBoard;
import model.MazeCell;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MazeGameView extends JPanel {
	
	private int screenWidth = 500;
	private int screenHeight = 300;
	
	private int numRows = 10;
	private int numCols = 10;
	
	private MazeBoard board = new MazeBoard(10,10,100,100);
	private ArrayList<MazeCell> walls = board.getStack(); 
		
	private JLabel mazeMessage  = new JLabel("Maze GAMEEEEEEE!");
	
	private JButton moveRightButton = new JButton("Move Right");
	private JButton moveLeftButton = new JButton("Move Left");
	
	public MazeGameView(){
		this.add(mazeMessage);
		this.add(moveLeftButton);
		this.add(moveRightButton);
	}
	
	//try to move rectangle
	
	
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
		for(MazeCell m: walls){
			g.drawRect( m.getX(), m.getY(), m.getWidth(), m.getHeight());
		}
		
		g.setColor(Color.RED);
		
	}
	
	
	//Set Button Listeners
	public void setupListeners(){
		
		
		
	}

	
}
