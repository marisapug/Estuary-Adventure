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
	
	private int numRows = 30;
	private int numCols = 30;
	
	private MazeBoard board = new MazeBoard(numRows,numCols,30,30);
	private MazeCell[][] grid = board.getGrid(); 
		
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
//		g.drawLine(20, 20, 10,10);//(endPoint X, endPoint Y, start X, start Y)
		
		for(int i = 0; i < numRows; i++){
			for(int j =0; j < numCols; j++){
				MazeCell currG = grid[i][j];
				int topLX = currG.getX()*currG.getWidth(); //top left corner x value
				int topLY = currG.getY()*currG.getHeight(); //top left corner y value
				int topRX = topLX + currG.getWidth(); //top right corner x value
				int topRY = topLY; //top right corner y value
				int bottomLX = topLX; //bottom left corner x value
				int bottomLY = topLY + currG.getHeight(); //bottom left corner y value
				int bottomRX = topRX; //bottom right corner x value
				int bottomRY = bottomLY; //bottom right corner y value
				if(currG.getHasTopWall()){
					g.drawLine(topLX, topLY, topRX, topRY);
				}
				if(currG.getHasBottomWall()){
					g.drawLine(bottomLX, bottomLY, bottomRX, bottomRY);
				}
				if(currG.getHasRightWall()){
					g.drawLine(topRX, topRY, bottomRX, bottomRY);
				}
				if(currG.getHasLeftWall()){
					g.drawLine(topLX, topLY, bottomLX, bottomLY);
				}
			}
		}
		
		g.setColor(Color.RED);
	}
	
	
	
	//Set Button Listeners
	public void setupListeners(){
		
		
		
	}

	
}
