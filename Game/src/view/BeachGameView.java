package view;

import model.BeachBoard;
import model.BeachCell;
import model.Boat;
import model.Grass;
import model.OysterGabion;
import model.Seawall;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.ShoreCrab;
import model.Wave;

public class BeachGameView extends JPanel implements KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;

	//=======================================================================//

	int screenWidth = MainFrame.getFrameWidth();
	int screenHeight = MainFrame.getFrameHeight();

	int numRows = 15;
	int numCols = 10;

	//Timer
	Timer t = new Timer(10,this);

	//BeachBoard
	private BeachBoard board;

	//BeachGrid
	private BeachCell[][] grid;
	private int cellWidth;
	private int cellHeight;

	private int totalSandHealth;

	//Grass and Barrier Images
	private BufferedImage grassImg = createImage("beachImages/grass.png");
	private BufferedImage wallImg = createImage("beachImages/seawall.png");
	private int barrierImgWidth;
	private int barrierImgHeight;
	private int grassImgWidth;
	private int grassImgHeight;

	private int totalSeawallHealth;
	private int totalGabionHealth;

	//ShoreCrab
	private ShoreCrab crab;
	private int crabGridTopX;
	private int crabGridTopY;
	private int crabGridBottomX;
	private int crabGridBottomY;

	private BufferedImage crabImg = createImage("characters/bluecrab_0.png");
	private int crabXVel;
	private int crabYVel;

	//Grass
	private int grassTimerTick;
	private int grassTimer;

	//BoatStuff
	private ArrayList<Boat> gameBoats;
	private int newBoatTimer;
	private int newBoatTimerTime;

	//wave stuff
	private ArrayList<Wave> gameWaves;
	private int waveSpeed;

	//Current Object
	private String currObjectHeader = "Current Object: ";
	private int currObjectHeaderX = screenWidth/2;
	private int currObjectHeaderY = screenHeight - (screenHeight*3)/4;

	private int currObjectTypeX = currObjectHeaderX + 100;
	private int currObjectTypeY = currObjectHeaderY;

	//=======================================================================//

	//CONSTRUCTOR
	public BeachGameView(){
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);


		//game stuff
		board = new BeachBoard(numRows,numCols,screenWidth,screenHeight);
		grid = board.getGrid();

		cellWidth = board.getCellWidth();
		cellHeight = board.getCellHeight();

		crab = board.getGameCrab();
		crabXVel = crab.getXVel();
		crabYVel = crab.getYVel();

		barrierImgWidth = cellWidth;
		barrierImgHeight = cellHeight;

		totalSeawallHealth = board.getTotalSeawallHealth();
		totalGabionHealth = board.getTotalGabionHealth();
		totalSandHealth = board.getTotalSandHealth();

		grassImgWidth = cellWidth/3;
		grassImgHeight = cellWidth/3;
		grassTimerTick = 100;
		grassTimer = 0;

		gameBoats = board.getGameBoats();
		newBoatTimer = 200;
		newBoatTimerTime = 0;

		gameWaves = board.getGameWaves();
		waveSpeed = board.getWaveSpeed();

		//start timer
		t.start();
	}


	//PAINT COMPONENT
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		//ocean drawing
		g.setColor(Color.CYAN);
		g.drawRect(0, 0, screenWidth, screenHeight);
		g.fillRect(0, 0, screenWidth, screenHeight);

		g.setColor(Color.YELLOW);
		g.drawRect(0, screenHeight-cellHeight, screenWidth, screenHeight);
		g.fillRect(0, screenHeight-cellHeight, screenWidth, screenHeight);

		//paints board
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				BeachCell tempBC = board.getGrid()[i][j];
				g.setColor(Color.YELLOW);
				if(tempBC.getType() == 0){
					int tempHeight = (int)(tempBC.getHeight() * ((double)tempBC.getHealth()/(double)totalSandHealth));
					int tempYLoc = tempBC.getYLoc() + (cellHeight - tempHeight);
					g.fillRect(tempBC.getXLoc(), tempYLoc, tempBC.getWidth(), tempHeight);
					g.setColor(Color.BLACK);
					// remove after testing
					g.drawString(Integer.toString(tempBC.getHealth()) + " " + Boolean.toString(tempBC.getCanHoldGrass()),tempBC.getXLoc(), tempBC.getYLoc() + cellHeight/2);
				}
				else
					g.drawString(Integer.toString(tempBC.getHealth()) + Boolean.toString(tempBC.getHasBarrier()),tempBC.getXLoc(), tempBC.getYLoc() + cellHeight/2);
			}
		}
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				BeachCell tempBC = board.getGrid()[i][j];
				g.setColor(Color.BLACK);
				g.drawRect(tempBC.getXLoc(), tempBC.getYLoc(), tempBC.getWidth(), tempBC.getHeight());
			}
		}
		
		//paint waves
		g.setColor(Color.BLUE);
		for(Wave w: gameWaves){
			g.drawLine(w.getXLoc(),w.getYLoc(),w.getXLoc() + w.getWidth(), w.getYLoc());
		}

		//paints boats
		g.setColor(Color.RED);
		for(Boat b: board.getGameBoats()){
			g.fillRect(b.getXLoc(),b.getYLoc(),b.getWidth(),b.getHeight());
		}

		//paints walls
		for(Seawall s: board.getGameSeawalls()){
			g.drawImage(wallImg, s.getXLoc(), s.getYLoc(), barrierImgWidth, barrierImgHeight, this);
		}

		//paints gabions
		g.setColor(Color.MAGENTA);
		for(OysterGabion og: board.getGameGabs()){
			g.fillRect(og.getXLoc(), og.getYLoc(), barrierImgWidth, barrierImgHeight);
		}

		//paints grass
		for(Grass grass: board.getGameGrass()){
			g.drawImage(grassImg,grass.getXLoc() + cellWidth/2 - (grassImgWidth/2), 
					grass.getYLoc() + cellHeight/2 - (grassImgHeight/2), grassImgWidth, grassImgHeight, this);
		}

		//draws crab
		g.drawImage(crabImg,crab.getXLoc(),crab.getYLoc(),crab.getWidth(),crab.getHeight(),this);

		//currObject drawing
		g.drawString(currObjectHeader, currObjectHeaderX, currObjectHeaderY);
		g.drawString(Integer.toString(crab.getCurrObject()), currObjectTypeX, currObjectTypeY);

	}


	//ACTION PERFORMED
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		if (
				((crabXVel < 0) && (crab.getXLoc() - crab.getXIncr() >= 0)) || 
				((crabXVel > 0) && (crab.getXLoc() + crab.getWidth() + crab.getXIncr() <= screenWidth)) ||
				((crabYVel < 0) && (crab.getYLoc() - crab.getYIncr() >= board.getCrabTopLeftCell().getYLoc())) ||
				((crabYVel > 0) && (crab.getYLoc() + crab.getYIncr() + crab.getHeight() <= screenHeight - screenHeight/20))
				){
			crab.move(crabXVel,crabYVel);
		}

		//shore stuff
		if(grassTimer >= grassTimerTick){
			board.healCellsAboveGrass();
			grassTimer = 0;
		}
		else{
			grassTimer++;
		}

		//BOAT STUFF
		//Boat timer increment
		if(newBoatTimerTime < newBoatTimer){
			newBoatTimerTime++;
		}else{
			board.generateRandomBoat();
			newBoatTimerTime = 0;
		}

		//Boat Ticks
		for(Boat bt: gameBoats){
			bt.move();
			board.makeWaves(bt);
			board.resetWaves(bt);
		}
		board.removeBoatsOffScreen();
		board.sandToOcean();

		//Wave 
		for(Wave wv: gameWaves){
			wv.move(waveSpeed);
		}
		board.removeHitWaves();
		board.removeDeadBarriers();

	}


	//MOVE CRAB IMAGE
	public void moveCrabImgUp(){
		crabXVel = 0;
		crabYVel = -crab.getYIncr();
	}
	public void moveCrabImgDown(){
		crabXVel = 0;
		crabYVel = crab.getYIncr();
	}
	public void moveCrabImgLeft(){
		crabXVel = -crab.getXIncr();
		crabYVel = 0;
	}
	public void moveCrabImgRight(){
		crabXVel = crab.getXIncr();
		crabYVel = 0;
	}



	//KEY METHODS
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_UP){
			moveCrabImgUp();
		}
		else if(code == KeyEvent.VK_DOWN){
			moveCrabImgDown();
		}
		else if(code == KeyEvent.VK_LEFT){
			moveCrabImgLeft();
		}
		else if(code == KeyEvent.VK_RIGHT){
			moveCrabImgRight();
		}

		else if(code == KeyEvent.VK_1){
			crab.setCurrObject(1);
		}
		else if(code == KeyEvent.VK_2){
			crab.setCurrObject(2);
		}

		else if(code == KeyEvent.VK_3){
			crab.setCurrObject(3);
		}
		else if(code == KeyEvent.VK_SPACE){
			board.placeObject(crab.getCurrObject(), crab.getXLoc(), crab.getYLoc(), crab.getWidth(), crab.getHeight());
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		crabXVel = 0;
		crabYVel = 0;
	}

	//CREATE IMAGE
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

}//END CLASS
