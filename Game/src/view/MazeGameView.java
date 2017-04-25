package view;

import model.Crab;
import model.MazeBoard;
import model.MazeCell;
import model.MiniMap;
import model.Litter;

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
import java.util.ArrayList;
import java.util.Random;


public class MazeGameView extends JPanel implements KeyListener, ActionListener {

	//=================================================================//

	private static final long serialVersionUID = 1L;
	
	//Timer
	Timer t = new Timer(10,this);
	private int timeRemaining = 120;
	private int timeCheck = 0;
	
	//Screen dimensions
	static private int screenWidth = MainFrame.getFrameWidth();
	static private int screenHeight = MainFrame.getFrameHeight();

	//create the maze board
	private int numRows = 30;
	private int numCols = 30;
	private int cellWidth = 200;
	private int cellHeight = 200;
	private MazeBoard board = new MazeBoard(numRows,numCols,cellWidth,cellHeight, screenWidth, screenHeight);
	private MazeCell[][] grids = board.getGrid(); 
	
	//miniMap 
	private MiniMap miniMap = new MiniMap();
	private int miniWidth = miniMap.getWidth();
	private int miniHeight = miniMap.getHeight();
	private MazeCell miniCharacter;

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
	
	//Litter
		ArrayList<BufferedImage> litterTypes = makeLitterList();
		Random rand = new Random();
		Litter[] gameLitter = board.generateLitter(100);

	//Locations for components
	private final int timeRemainingLabelXLoc = screenWidth/2;
	private final int timeRemainingLabelYLoc = 10;


	//=================================================================//


	//Constructor
	public MazeGameView(){
		t.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		this.setBackground(Color.BLUE);
	}

	//paintComponent
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		// MAZE DRAWING
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
				g2.setColor(Color.CYAN);
				
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
		
		g.setColor(Color.BLACK);
		g.drawRect(0,0,numRows*miniWidth, numCols*miniHeight);
		g.fillRect(0,0,numRows*miniWidth, numCols*miniHeight);
		
		
		//MINIMAP DRAWING
		for(int i = 0; i < numRows; i++){
			for(int j =0; j < numCols; j++){
				MazeCell currG = grids[i][j];
				int topLX = j*miniWidth; //top left corner x value
				int topLY = i*miniHeight; //top left corner y value
				int topRX = topLX + miniWidth; //top right corner x value
				int topRY = topLY; //top right corner y value
				int bottomLX = topLX; //bottom left corner x value
				int bottomLY = topLY + miniHeight; //bottom left corner y value
				int bottomRX = topRX; //bottom right corner x value
				int bottomRY = bottomLY; //bottom right corner y value
				
				Graphics2D g2 = (Graphics2D)g;
				g2.setStroke(new BasicStroke(1));
				g2.setColor(Color.RED);
				
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
		
		//MINI MAP CHARACTER DRAWING
		miniCharacter = board.inWhichCell(characterXLoc,characterYLoc);
		g.setColor(Color.GREEN);
		g.drawRect(miniCharacter.getX() * miniWidth + miniWidth/4, miniCharacter.getY()*miniHeight + miniHeight/4,miniWidth/2,miniHeight/2);
		g.fillRect(miniCharacter.getX() * miniWidth + miniWidth/4, miniCharacter.getY()*miniHeight + miniHeight/4,miniWidth/2,miniHeight/2);
		
		//SalinityMeter Drawing FIX MAGIC NUMBERS
		if(board.isOnCorrectPath(characterXLoc, characterYLoc)){
			g.setColor(Color.GREEN);
			g.drawRect(screenWidth - 50,10,30,30);
			g.fillRect(screenWidth - 50,10,30,30);
		}
		else{
			g.setColor(Color.RED);
			g.drawRect(screenWidth - 50,10,30,30);
			g.fillRect(screenWidth - 50,10,30,30);
		}
		
		g.setColor(Color.WHITE);
		g.drawString("Time Remaining: ", timeRemainingLabelXLoc, timeRemainingLabelYLoc);
		g.drawString(""+timeRemaining, screenWidth/2 + 120, 10);
		
		for(int i = 0; i < gameLitter.length; i++){
			Litter lit = gameLitter[i];
			g.drawImage(litterTypes.get(lit.getType()), lit.getXLoc(), lit.getYLoc(),characterWidth, characterHeight, this);
		}

		
		g.drawImage(crabImg, testCrab.getXLoc(), testCrab.getYLoc(), characterWidth, characterHeight, this);
	}//paintComponent


	public void actionPerformed(ActionEvent arg0) {
		timeCheck++;
		if(timeCheck == 100){
			timeRemaining--;
			timeCheck = 0;
		}
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
	
	public ArrayList<BufferedImage> makeLitterList(){
		ArrayList<BufferedImage> litterIcons = new ArrayList<BufferedImage>();
		litterIcons.add(createImage("characters/apple.jpeg"));
		litterIcons.add(createImage("characters/can.png"));
		litterIcons.add(createImage("characters/plasticBag.png"));
		litterIcons.add(createImage("characters/plasticbottle.jpg"));
		return litterIcons;
	}


	//Getters
	public int getScreenWidth(){
		return screenWidth;
	}

	public int getScreenHeight(){
		return screenHeight;
	}

}