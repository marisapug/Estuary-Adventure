package view;

import model.Crab;
import model.MazeBoard;
import model.MazeCell;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class MazeGameView extends JPanel implements KeyListener, ActionListener {

	//=================================================================//

	private static final long serialVersionUID = 1L;
	
	//Timer
	Timer t = new Timer(10,this);
	
	//Screen dimensions
	static private int screenWidth = MainFrame.getFrameWidth();
	static private int screenHeight = MainFrame.getFrameHeight();

	//create the maze board
	private int numRows = 15;
	private int numCols = 15;
	private int cellWidth = 200;
	private int cellHeight = 200;
	private MazeBoard board = new MazeBoard(numRows,numCols,cellWidth,cellHeight, screenWidth, screenHeight);
	private MazeCell[][] grids = board.getGrid(); 

	//Crab
	Crab testCrab = new Crab(3,0,screenWidth/2 ,screenHeight/2); //health, age
	BufferedImage crabImg = createImage("characters/crab-clip-art-crab7.png");
	private int characterWidth = 50;
	private int characterHeight = 50;
	private int characterXLoc = testCrab.getXLoc();
	private int characterYLoc = testCrab.getYLoc();
	private int yIncr = testCrab.getXIncr();
	private int xIncr = testCrab.getYIncr();
	private int xVel = testCrab.getXVel();
	private int yVel = testCrab.getYVel();


	//Labels
	private JLabel timeLabel  = new JLabel("Time Remaining: ");

	//=================================================================//


	//Constructor
	public MazeGameView(){
		t.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		this.setBackground(Color.CYAN);
		this.add(timeLabel);
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
				
				Graphics2D g2 = (Graphics2D)g;
				g2.setStroke(new BasicStroke(5));
				g2.setColor(Color.BLUE);
				
				if(currG.getHasTopWall()){
					g2.drawLine(topLX, topLY, topRX, topRY);
				}
				if(currG.getHasBottomWall()){
					g2.drawLine(bottomLX, bottomLY, bottomRX, bottomRY);
				}
				if(currG.getHasRightWall()){
					g2.drawLine(topRX, topRY, bottomRX, bottomRY);
				}
				if(currG.getHasLeftWall()){
					g2.drawLine(topLX, topLY, bottomLX, bottomLY);
				}
			}

		}
		
		//draws green rectangle in correct path
		for(MazeCell m: board.getCorrectPath()){
			g.setColor(Color.green);
			g.drawRect(m.getXLoc()+50,m.getYLoc()+50, cellWidth-100, cellHeight-100);
			g.fillRect(m.getXLoc()+50,m.getYLoc()+50, cellWidth-100, cellHeight-100);
		}
		
		g.drawImage(crabImg, testCrab.getXLoc(), testCrab.getYLoc(), characterWidth, characterHeight, this);
	}//paintComponent


	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(yVel > 0 && board.hitGridWalls(characterXLoc, characterYLoc, 
				testCrab.getXIncr(), testCrab.getYIncr(), 0)){
			return;
		}
		else if(yVel < 0 && board.hitGridWalls(characterXLoc, characterYLoc + characterHeight, 
				testCrab.getXIncr(), testCrab.getYIncr(), 1)){
			return;
		}
		else if(xVel < 0 && board.hitGridWalls(characterXLoc + characterWidth,characterYLoc, 
				testCrab.getXIncr(), testCrab.getYIncr(), 2)){
			return;
		}

		else if(xVel > 0 && board.hitGridWalls(characterXLoc, characterYLoc, 
				testCrab.getXIncr(), testCrab.getYIncr(), 3)){
			return;
		}
		else{
			repaint();
			board.moveGrid(xVel,yVel);
		}
	}
	
	public void up(){
		xVel = 0;
		yVel = yIncr;
	}
	public void down(){
		xVel = 0;
		yVel = -yIncr;
	}
	public void left(){
		xVel = xIncr;
		yVel = 0;
	}
	public void right(){
		xVel = -xIncr;
		yVel = 0;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_UP){
			up();
		}
		if(code == KeyEvent.VK_DOWN){
			down();
		}
		if(code == KeyEvent.VK_LEFT){
			left();
		}
		if(code == KeyEvent.VK_RIGHT){
			right();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		xVel = 0;
		yVel = 0;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
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