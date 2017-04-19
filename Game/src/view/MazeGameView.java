package view;

import model.Crab;
import model.MazeBoard;
import model.MazeCell;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class MazeGameView extends JPanel {

	//=================================================================//

	private static final long serialVersionUID = 1L;
	
	//Screen dimensions
	static private int screenWidth = MainFrame.getFrameWidth();
	static private int screenHeight = MainFrame.getFrameHeight();

	//create the maze board
	private int numRows = 15;
	private int numCols = 15;
	private int cellWidth = 200;
	private int cellHeight = 200;
	private MazeBoard board = new MazeBoard(numRows,numCols,cellWidth,cellHeight);
	private MazeCell[][] grids = board.getGrid(); 
	private int yIncr = board.getXIncr();
	private int xIncr = board.getYIncr();

	//Crab
	Crab testCrab = new Crab(3,0,screenWidth/2 ,screenHeight/2); //health, age
	BufferedImage crabImg = createImage("characters/crab-clip-art-crab7.png");
	private int characterWidth = 30;
	private int characterHeight = 30;
	private int characterXLoc = testCrab.getXLoc();

	//Buttons
	private JButton moveRightButton = new JButton("Move Right");
	private JButton moveLeftButton = new JButton("Move Left");
	private JButton moveUpButton = new JButton("Move Up");
	private JButton moveDownButton = new JButton("Move Down");

	//Labels
	private JLabel timeLabel  = new JLabel("Time Remaining:");

	//=================================================================//


	//Constructor
	public MazeGameView(){
		this.setBackground(Color.CYAN);
		this.add(moveLeftButton);
		this.add(moveRightButton);
		this.add(moveUpButton);
		this.add(moveDownButton);
		this.add(timeLabel);
		this.setupListeners();
	}

	//paintComponent
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		for(int i = 0; i < numRows; i++){
			for(int j =0; j < numCols; j++){
				MazeCell currG = grids[i][j];
				int topLX = currG.getXLoc(); //top left corner x value
				int topLY = currG.getYLoc(); //top left corner y value
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
		g.drawImage(crabImg, testCrab.getXLoc(), testCrab.getYLoc(), characterWidth, characterHeight, this);
	}//paintComponent



	public void moveGridUp(){
		if(!board.hitGridWalls(characterXLoc, testCrab.getYLoc() + characterHeight, 
				testCrab.getXIncr(), testCrab.getYIncr(), 1)){
			repaint();
			board.moveGrid(0, -yIncr);
		}
	}

	public void moveGridDown(){
		if(!board.hitGridWalls(characterXLoc, testCrab.getYLoc(), 
				testCrab.getXIncr(), testCrab.getYIncr(), 0)){
			repaint();
			board.moveGrid(0, yIncr);
		}
	}

	public void moveGridLeft(){
		if(!board.hitGridWalls(characterXLoc + characterWidth, testCrab.getYLoc(), 
				testCrab.getXIncr(), testCrab.getYIncr(), 2)){
			repaint();
			board.moveGrid(-xIncr, 0);
		}
	}

	public void moveGridRight(){
		if(!board.hitGridWalls(characterXLoc, testCrab.getYLoc(), 
				testCrab.getXIncr(), testCrab.getYIncr(), 3)){
			repaint();
			board.moveGrid(xIncr, 0);
		}
	}

	public void setupListeners(){
		moveLeftButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				moveGridRight();
			}

		});
		moveRightButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				moveGridLeft();
			}
		});
		moveUpButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				moveGridDown();
			}
		});
		moveDownButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				moveGridUp();
			}
		});
	}

	public BufferedImage createImage(String fileName){
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(fileName)); 
			return bufferedImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	//Getters
	public int getScreenWidth(){
		return screenWidth;
	}

	public int getScreenHeight(){
		return screenHeight;
	}

}