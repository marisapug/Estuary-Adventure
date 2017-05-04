package view;

import model.Barrier;
import model.BeachBoard;
import model.BeachCell;
import model.Boat;
import model.Grass;
import model.OysterGabion;
import model.Seawall;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.ShoreCrab;
import model.Wave;

public class BeachGameView extends JPanel implements KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;
	
	//=======================================================================//
	
	int screenWidth = MainFrame.getFrameWidth();
	int screenHeight = MainFrame.getFrameHeight();

	int numRows = 10;
	int numCols = 7;
	
	//Timer
	Timer t = new Timer(10,this);

	//BeachBoard
	private BeachBoard board;

	//BeachGrid
	private BeachCell[][] grid;
	private BeachCell crabStartCell;
	private BeachCell crabTopLeftCell;
	private BeachCell crabBottomLeftCell;
	private int cellWidth;
	private int cellHeight;
	
	//Grass and Barrier Images
	private BufferedImage grassImg = createImage("beachImages/grass.png");
	private BufferedImage wallImg = createImage("beachImages/seawall.png");
	private int barrierImgWidth;
	private int barrierImgHeight;
	private int grassImgWidth;
	private int grassImgHeight;

	//ShoreCrab
	private ShoreCrab crab;
	
	private int crabGridStartX;
	private int crabGridStartY;
	private int crabGridTopX;
	private int crabGridTopY;
	private int crabGridBottomX;
	private int crabGridBottomY;
	
	private BufferedImage crabImg = createImage("characters/bluecrab_0.png");
	private int crabXVel;
	private int crabYVel;
	
	//BoatStuff
	private ArrayList<Boat> gameBoats;
	private int newBoatTimer;
	private int newBoatTimerTime;
	
	//wave stuff
	private ArrayList<Wave> gameWaves;
	private int waveSpeed;
	
	//JButtons
	JButton plantButton;
	JButton gabionButton;
	JButton seawallButton;
	
	//=======================================================================//

	//CONSTRUCTOR
	public BeachGameView(){
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		
		//game stuff
		board = new BeachBoard(numRows,numCols,screenWidth,screenHeight);
		grid = board.getGrid();
		crabGridStartX = numRows/2 - 1;
		crabGridStartY = numCols/2;
		crabStartCell = grid[crabGridStartX][crabGridStartY];
		
		cellWidth = board.getCellWidth();
		cellHeight = board.getCellHeight();
		
		crabGridTopX = 1;
		crabGridTopY = 0;
		crabTopLeftCell = grid[crabGridTopX][crabGridTopY];
		
		crabGridBottomX = numRows-1;
		crabGridBottomY = 0;
		crabBottomLeftCell = grid[crabGridBottomX][crabGridBottomY];
		
		crab = new ShoreCrab(crabStartCell.getXLoc(),crabStartCell.getYLoc());
		crabXVel = crab.getXVel();
		crabYVel = crab.getYVel();
		
		barrierImgWidth = cellWidth;
		barrierImgHeight = cellHeight;
		
		grassImgWidth = cellWidth/3;
		grassImgHeight = cellWidth/3;
		
		gameBoats = board.getGameBoats();
		newBoatTimer = 200;
		newBoatTimerTime = 0;
		
		gameWaves = board.getGameWaves();
		waveSpeed = board.getWaveSpeed();
	
		//button initialization
		plantButton = new JButton("Plant Grass");
		gabionButton = new JButton("Place Oyster Gabion");
		seawallButton = new JButton("Place Sea Wall");
		this.add(plantButton);
		this.add(gabionButton);
		this.add(seawallButton);
		plantButton.setFocusable(false);
		gabionButton.setFocusable(false);
		seawallButton.setFocusable(false);
		
		//button listeners
		plantButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				BeachCell tempCell = board.inWhichCell(crab.getXLoc() + (crab.getWidth())/2, crab.getYLoc() + (crab.getHeight())/2);
				if(tempCell != null && tempCell.getCanHoldGrass())
					board.addGrass(tempCell.getXLoc(), tempCell.getYLoc());
			}
		});
		
		seawallButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				BeachCell tempCell = board.inWhichCell(crab.getXLoc() + (crab.getWidth())/2, crab.getYLoc() + (crab.getHeight())/2);
				if(tempCell != null && tempCell.getCanHoldBarrier()){
					board.addWall(tempCell.getXLoc(), tempCell.getYLoc());
					tempCell.setCanHoldBarrier(false);
					if(tempCell.getX() > 0 ){
						board.getGrid()[tempCell.getX() - 1][tempCell.getY()].setCanHoldBarrier(false);

					}
					if(tempCell.getX() < numRows-1){
						board.getGrid()[tempCell.getX() + 1][tempCell.getY()].setCanHoldBarrier(false);

					}
				}
			}
		});
		
		gabionButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				BeachCell tempCell = board.inWhichCell(crab.getXLoc() + (crab.getWidth())/2, crab.getYLoc() + (crab.getHeight())/2);
				if(tempCell != null && tempCell.getCanHoldBarrier()){
					board.addGabion(tempCell.getXLoc(), tempCell.getYLoc());
					tempCell.setCanHoldBarrier(false);
					if(tempCell.getX() > 0 ){
						board.getGrid()[tempCell.getX() - 1][tempCell.getY()].setCanHoldBarrier(false);
					}
					if(tempCell.getX() < numRows-1){
						board.getGrid()[tempCell.getX() + 1][tempCell.getY()].setCanHoldBarrier(false);
					}
				}
			}
		});
		
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
					g.fillRect(tempBC.getXLoc(), tempBC.getYLoc(), tempBC.getWidth(), tempBC.getHeight());
				}
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
		
	}


	//ACTION PERFORMED
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		if (
				((crabXVel < 0) && (crab.getXLoc() - crab.getXIncr() >= 0)) || 
				((crabXVel > 0) && (crab.getXLoc() + crab.getWidth() + crab.getXIncr() <= screenWidth)) ||
				((crabYVel < 0) && (crab.getYLoc() - crab.getYIncr() >= crabTopLeftCell.getYLoc())) ||
				((crabYVel > 0) && (crab.getYLoc() + crab.getYIncr() + crab.getHeight() <= screenHeight - screenHeight/20))
				){
			crab.move(crabXVel,crabYVel);
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

	//Wave 
		 for(Wave wv: gameWaves){
			 wv.move(waveSpeed);
		 }
		 board.removeHitWaves();
		 

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
		if(code == KeyEvent.VK_DOWN){
			moveCrabImgDown();
		}
		if(code == KeyEvent.VK_LEFT){
			moveCrabImgLeft();
		}
		if(code == KeyEvent.VK_RIGHT){
			moveCrabImgRight();
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
