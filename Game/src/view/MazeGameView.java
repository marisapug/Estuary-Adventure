package view;
import model.Crab;
import model.MazeBoard;
import model.MazeCell;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MazeGameView extends JPanel {
	
	//Screen
	private int screenWidth = 500;
	private int screenHeight = 300;

	//characters
	Crab testCrab = new Crab(3,0); //health, age
	private int characterWidth = 10;
	private int characterHeight = 10;

	//Maze
	private int numRows = 15;
	private int numCols = 15;
	private int mazeSizeX = 30;
	private int mazeSizeY = 30;
	private MazeBoard board = new MazeBoard(numRows,numCols,mazeSizeX,mazeSizeY);
	private MazeCell[][] grid = board.getGrid(); 

	
	//Buttons
	private JButton moveRightButton = new JButton("Move Right");
	private JButton moveLeftButton = new JButton("Move Left");
	private JButton moveUpButton = new JButton("Move Up");
	private JButton moveDownButton = new JButton("Move Down");

	
	//Constructor
	public MazeGameView(){

		this.add(moveLeftButton);
		this.add(moveRightButton);
		this.add(moveUpButton);
		this.add(moveDownButton);

		this.setupListeners();
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
		//		g.drawLine(20, 20, 10,10);//(endPoint X, endPoint Y, start X, start Y)

		//Maze
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

		//Character
		g.drawRect(testCrab.getXLoc(), testCrab.getYLoc(), characterWidth, characterHeight);
		g.setColor(Color.RED);
		g.fillRect(testCrab.getXLoc(), testCrab.getYLoc(), characterWidth, characterHeight);

	}


	//Move Characters
	public void moveCharacterRight(){
		repaint(testCrab.getXLoc(),testCrab.getYLoc(),characterWidth, characterHeight);
		testCrab.moveRight();
		repaint(testCrab.getXLoc(),testCrab.getYLoc(),characterWidth, characterHeight);
	}

	public void moveCharacterLeft(){
		repaint(testCrab.getXLoc(),testCrab.getYLoc(),characterWidth, characterHeight);
		testCrab.moveLeft();
		repaint(testCrab.getXLoc(),testCrab.getYLoc(),characterWidth, characterHeight);	
	}
	
	public void moveCharacterUp(){
		repaint(testCrab.getXLoc(),testCrab.getYLoc(),characterWidth, characterHeight);
		testCrab.moveUp();
		repaint(testCrab.getXLoc(),testCrab.getYLoc(),characterWidth, characterHeight);	
	}
	
	public void moveCharacterDown(){
		repaint(testCrab.getXLoc(),testCrab.getYLoc(),characterWidth, characterHeight);
		testCrab.moveDown();
		repaint(testCrab.getXLoc(),testCrab.getYLoc(),characterWidth, characterHeight);	
	}
	
	
	//Set Button Listeners 
	public void setupListeners(){
		moveLeftButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				moveCharacterLeft();
			}
		});

		moveRightButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				moveCharacterRight();
			}
		});
		
		moveUpButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				moveCharacterUp();
			}
		});

		moveDownButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				moveCharacterDown();
			}
		});
	}//setupListener

}
