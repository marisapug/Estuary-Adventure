package view;

import model.Boat;
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
import javax.swing.*;


public class MazeGameView extends JPanel implements ActionListener, KeyListener {
	
	private int screenWidth = 500;
	private int screenHeight = 300;
	
	private int numRows = 40;
	private int numCols = 40;
	private int yIncr = 4;
	private int xIncr = 4;
	private int yChange, xChange;
	
	Timer t = new Timer(5,this);
	
	private MazeBoard board = new MazeBoard(numRows,numCols,100,100);
	private MazeCell[][] grid = board.getGrid(); 
		
	private JLabel mazeMessage  = new JLabel("Maze GAMEEEEEEE!");
	
	private JButton moveRightButton = new JButton("Move Right");
	private JButton moveLeftButton = new JButton("Move Left");
	private JButton moveUpButton = new JButton("Move Up");
	private JButton moveDownButton = new JButton("Move Down");
	
	public MazeGameView(){
		t.start();
		this.add(mazeMessage);
		this.add(moveLeftButton);
		this.add(moveRightButton);
		this.add(moveUpButton);
		this.add(moveDownButton);
		this.addKeyListener(this);
		this.setFocusable(true);
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
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
			for(int i = 0; i < numRows; i++){
				for(int j = 0; j < numCols; j++){
					grid[i][j].setY(grid[i][j].getY() + yChange);
					grid[i][j].setX(grid[i][j].getX() + xChange);
				}
			}
		}
	
	//Set Button Listeners 
	// NOT WORKING FIX BUTTON LISTENERS
	public void setupListeners(){
				
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_UP){
			System.out.println("Up pressed");
			yChange = -yIncr;
			xChange = 0;
		}
		if(code == KeyEvent.VK_DOWN){
			yChange = +yIncr;
			xChange = 0;
		}
		if(code == KeyEvent.VK_LEFT){
			yChange = 0;
			xChange = -xIncr;
		}
		if(code == KeyEvent.VK_RIGHT){
			yChange = 0;
			xChange = xIncr;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
