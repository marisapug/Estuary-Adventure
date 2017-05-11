package view;

import model.BeachBoard;
import model.BeachCell;
import model.Boat;
import model.Grass;
import model.Oyster;
import model.OysterGabion;
import model.Seawall;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import javax.swing.JPanel;
import javax.swing.Timer;

import model.ShoreCrab;
import model.Wave;

public class BeachGameView extends JPanel implements KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;

	//=======================================================================//

	private int screenWidth = MainFrame.getFrameWidth();
	private int screenHeight = MainFrame.getFrameHeight();

	private int numRows = 15;
	private int numCols = 10;

	//Timer
	private int timerSpeed = 10;
	private Timer t = new Timer(timerSpeed,this);
	private int totalTime = 2;
	private int timeRemaining = totalTime;
	private int timeCheck = 0;

	//BeachBoard
	private BeachBoard board;

	//BeachGrid
	private int cellWidth;
	private int cellHeight;

	private BufferedImage sandImage = createImage("beachImages/sand_tile.jpg");

	//Object Buckets
	private BufferedImage boxImage = createImage("beachImages/boardwalk_tile.jpg");
	private int bucketLogoWidth = 50;
	private int bucketLogoHeight = 50;

	private int totalSandHealth;

	//Grass and Barrier Images
	private BufferedImage grassImg = createImage("beachImages/grass.png");
	private BufferedImage wallImg = createImage("beachImages/seawall.png");
	private BufferedImage gabionImg = createImage("beachImages/gabionImg.png");
	private BufferedImage[] objectImgArray = {grassImg,wallImg,gabionImg};
	private int barrierImgWidth;
	private int barrierImgHeight;
	private int grassImgWidth;
	private int grassImgHeight;



	//ShoreCrab
	private ShoreCrab crab;


	private BufferedImage crabImg0 = createImage("characters/bluecrab_0.png");
	private BufferedImage crabImg1 = createImage("characters/bluecrab_1.png");
	private BufferedImage crabImg2 = createImage("characters/bluecrab_2.png");
	private int crabXVel;
	private int crabYVel;

	private BufferedImage[] crabPics = {crabImg0, crabImg1, crabImg2};

	private int crabPicNum = 0;
	private int crabNumPics = 3;
	private boolean crabIsMoving = false;
	private int swimSpeed = 6;
	private int swimTimer = swimSpeed;

	//Oysters
	private int oysterSpawnTimer;
	private int oysterSpawnTick;
	private BufferedImage oysterImg = createImage("beachImages/oyster.png");

	//Grass
	private int grassTimerTick;
	private int grassTimer;

	//BoatStuff
	private ArrayList<Boat> gameBoats;
	private int newBoatTimer;
	private int newBoatTimerTime;
	private BufferedImage smallBoatImageRight = createImage("beachImages/speedboat.png");
	private BufferedImage mediumBoatImageLeft = createImage("beachImages/cleanvessel.png");
	private BufferedImage largeBoatLeft = createImage("beachImages/cargoship.jpg");

	private BufferedImage[][] boatImgArray = {
			{smallBoatImageRight},
			{mediumBoatImageLeft},
			{largeBoatLeft}
	};

	//wave stuff
	private ArrayList<Wave> gameWaves;
	private int waveSpeed;
	private BufferedImage waveImage = createImage("beachImages/waveImage.png");

	//features bar
	private int featuresBarWidth;
	private int featuresBarHeight;

	//Shore health bar
	private int meterX;
	private int meterY;
	private int totalShoreHealth;

	private int meterWidth;
	private int meterHeight;

	private String healthTitleText;
	private int healthTitleX;
	private int healthTitleFontSize;
	private int healthTitleY;
	private String healthTitleFontStyle;

	//Held Object
	private int heldObjectWidth;
	private int heldObjectHeight;

	//Timer stuff
	private String timeRemainingLabel;
	private String timeRemainingFontStyle;
	private int timeRemainingFontSize;
	private int timeRemainingLabelXLoc;
	private int timeRemainingLabelYLoc;
	private int timeXLoc;
	private int timeYLoc;

	//oyster count stuff
	private String oysterCountLabel;
	private String oysterCountFontStyle;
	private int oysterCountFontSize;
	private int oysterCountLabelXLoc;
	private int oysterCountLabelYLoc;
	private int oysterCountXLoc;
	private int oysterCountYLoc;


	//Game State
	private boolean startScreenVisible;
	private BufferedImage startBackground = createImage("background/Estuary_Background_1.jpg");
	private JButton startButton;
	private String startTitle = "Defend the Estuary!";
	private int startTitleFontSize = screenWidth/50;
	private String startTitleFontStyle = "TimesRoman";
	private int startTitleX = screenWidth/2 - ((startTitleFontSize * startTitle.length())/4);
	private int startTitleY = screenHeight/4;
	
	private JButton goToStartButton;
	
	private boolean hasWon;
	private boolean isGameOver;
	private String winText;
	private String loseText;
	private int gameOverTextSize;
	private String gameOverTextStyle;
	private int gameOverTextX;
	private int gameOverTextY;

	//tutorial stuff
	private boolean isTutorial;
	private int tutorialState;
	private int grassState;
	private int seawallState;
	private int gabionState;
	private int firstBoatState;
	private int secondBoatState;

	private int finishTutorialTextState;
	private int finishTutorialState;

	private int tutPauseTimer;
	private int tutPauseTotal;

	private boolean hasSpawnedOysters;
	private boolean hasSpawnedFirstBoat;
	private boolean hasSpawnedSecondBoat;
	
	private String tutorialTextStyle;
	private int tutorialTextSize;
	
	private String firstBoatStateText;
	private String grassStateText;
	private String seawallStateText;
	private String oysterStateText;
	private String gabionStateText;
	private String secondBoatStateText;
	private String finishTutorialStateText;

	//=======================================================================//

	//CONSTRUCTOR 
	public BeachGameView(){

		addKeyListener(this);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		
		//intialize game state
		startScreenVisible = true;
		startButton = new JButton("PLAY Game!");
		this.add(startButton);
		startButton.setVisible(true);
		startButton.setFocusable(false);
		
		goToStartButton = new JButton("Go to Start Screen");
		goToStartButton.setFocusable(false);
		goToStartButton.setVisible(false);
		this.add(goToStartButton);

		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				//tutorial Stuff
				isTutorial = true;
				hasSpawnedOysters = false;
				hasSpawnedFirstBoat = false;
				hasSpawnedSecondBoat = false;

				firstBoatState = 0;
				grassState = 1;
				seawallState = 2;
				gabionState = 3;
				secondBoatState = 4;

				finishTutorialTextState = 9;
				finishTutorialState = 10;
				tutorialState = firstBoatState;

				tutPauseTotal = 300;
				tutPauseTimer = tutPauseTotal;

				//all game stuff
				board = new BeachBoard(numRows,numCols,screenWidth,screenHeight);

				cellWidth = board.getCellWidth();
				cellHeight = board.getCellHeight();

				crab = board.getGameCrab();
				crabXVel = crab.getXVel();
				crabYVel = crab.getYVel();

				barrierImgWidth = cellWidth;
				barrierImgHeight = cellHeight;

				totalSandHealth = board.getTotalSandHealth();

				grassImgWidth = cellWidth/3;
				grassImgHeight = cellWidth/3;
				grassTimerTick = 50; //half a second
				grassTimer = 0;

				oysterSpawnTimer = 0; 
				oysterSpawnTick = 400; // 4 seconds

				//features bar intialization
				featuresBarWidth = board.getFeaturesBarWidth();
				featuresBarHeight = board.getFeaturesBarHeight();

				//health bar intialization
				meterX = screenWidth - screenWidth/4;
				meterY = featuresBarHeight/8;
				meterWidth = screenWidth/5;
				meterHeight = (featuresBarHeight*4)/5;
				totalShoreHealth = board.getTotalShoreHealth();

				gameBoats = board.getGameBoats();
				newBoatTimer = 600;
				newBoatTimerTime = 0;

				gameWaves = board.getGameWaves();
				waveSpeed = board.getWaveSpeed();

				heldObjectWidth = crab.getWidth()/2;
				heldObjectHeight = crab.getHeight()/2;

				//timer initialization
				timeRemainingLabel = "Time Remaining: ";
				timeRemainingFontStyle = "TimesRoman";
				timeRemainingFontSize = screenWidth/50;
				timeRemainingLabelXLoc = (screenWidth/2) - (timeRemainingLabel.length()*timeRemainingFontSize)/4;
				timeRemainingLabelYLoc = featuresBarHeight - (featuresBarHeight - timeRemainingFontSize)/2;
				timeXLoc = timeRemainingLabelXLoc + timeRemainingLabel.length()*timeRemainingFontSize/2;
				timeYLoc = timeRemainingLabelYLoc;

				//health initialization
				healthTitleText = "Estuary Health";
				healthTitleX = meterX + (meterWidth/4);
				healthTitleFontSize = screenWidth/60;
				healthTitleY = featuresBarHeight - (featuresBarHeight - healthTitleFontSize)/2;
				healthTitleFontStyle = "TimesRoman";

				//oyster count initialization	
				oysterCountLabel = "Oyster Count: ";
				oysterCountFontStyle = "TimesRoman";
				oysterCountFontSize = screenWidth/60;
				oysterCountLabelXLoc = (screenWidth/4) - (oysterCountLabel.length()*oysterCountFontSize)/4;
				oysterCountLabelYLoc = featuresBarHeight - (featuresBarHeight - oysterCountFontSize)/2;
				oysterCountXLoc = oysterCountLabelXLoc + oysterCountLabel.length()*oysterCountFontSize/2;
				oysterCountYLoc = oysterCountLabelYLoc;
				
				//tutorial text initializations
				tutorialTextStyle = "TimesRoman";
				tutorialTextSize = screenHeight/40;
				
				firstBoatStateText = "Oh No! The WAKES from the ships are destroying the shore!";
				grassStateText = "Plant grass to heal the shore over time";
				seawallStateText = "Use seawalls to protect the shore";
				oysterStateText = "You need three oysters to set an oyster gabion";
				gabionStateText = "Gabions are STRONGER than seawalls";
				secondBoatStateText = "Barriers block the WAKES!";
				finishTutorialStateText = "GET READY TO PLAY!";


				//button visibility
				startButton.setVisible(false);
				
				//GameState
				startScreenVisible = false;

				hasWon = false;
				isGameOver = false;
				
				winText = "Congratulations, you defended the estuary!";
				loseText = "Oh no! The estuary has been compromised!";
				gameOverTextSize = screenHeight/30;
				gameOverTextStyle = "TimesRoman";
				gameOverTextX = (screenWidth/2) - (winText.length()*gameOverTextSize)/4;
				gameOverTextY = screenHeight/3;


				//start timer
				t.start();
			}
		});
		
		goToStartButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				startScreenVisible = true;
				goToStartButton.setVisible(false);
				startButton.setVisible(true);
				t.stop();
				repaint();
			}
		});
		
	}//end constructor


	//PAINT COMPONENT
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		//draws start screen
		if(startScreenVisible){
			g.drawImage(startBackground, 0, 0, screenWidth, screenHeight, this);
			g.setColor(Color.WHITE);
			g.setFont(new Font(startTitleFontStyle,Font.BOLD,startTitleFontSize));
			g.drawString(startTitle,startTitleX,startTitleY);
		}

		//draws everthing else
		else{

			//ocean drawing
			g.setColor(Color.CYAN);
			g.fillRect(0, 0, screenWidth, screenHeight);
			g.fillRect(0, screenHeight-cellHeight, screenWidth, screenHeight);

			//features bar
			g.setColor(Color.BLUE);
			g.drawRect(0, 0, featuresBarWidth, featuresBarHeight);
			g.fillRect(0, 0, featuresBarWidth, featuresBarHeight);

			//HEALTH bar
			Graphics2D g2 = (Graphics2D)g;
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.RED);
			g.fillRect(meterX, meterY, (int)(meterWidth * ((double)board.getCurrentShoreHealth()/(double)totalShoreHealth)), meterHeight);
			g.setColor(Color.BLACK);
			g.drawRect(meterX, meterY, meterWidth, meterHeight);

			g.setFont(new Font(healthTitleFontStyle,Font.BOLD,healthTitleFontSize));
			g.setColor(Color.WHITE);
			g.drawString(healthTitleText, healthTitleX, healthTitleY);

			//paints board
			for(int i = 0; i < numRows; i++){
				for(int j = 0; j < numCols; j++){
					BeachCell tempBC = board.getGrid()[i][j];
					if(tempBC.getType() == 0){
						int tempHeight = (int)(tempBC.getHeight() * ((double)tempBC.getHealth()/(double)totalSandHealth));
						int tempYLoc = tempBC.getYLoc() + (cellHeight - tempHeight);
						g.drawImage(sandImage, tempBC.getXLoc(), tempYLoc, tempBC.getWidth(), tempBC.getHeight(), this);
						g.setColor(Color.BLACK);
					}
					g2.setStroke(new BasicStroke(3));
					g2.setColor(Color.green);
					if(crab.getCurrObject() == 1 && tempBC.getCanHoldGrass() && !tempBC.getHasGrass()){
						g2.drawRect( tempBC.getXLoc()+(tempBC.getWidth()/10), tempBC.getYLoc() + (tempBC.getHeight()/10), tempBC.getWidth()- (tempBC.getWidth()/5), tempBC.getHeight() - (tempBC.getHeight()/5));
					}
					g2.setColor(Color.RED);
					if((crab.getCurrObject() == 2 || (crab.getCurrObject() == 3 && crab.getNumOysters() >= 3)) && tempBC.getCanHoldBarrier()){
						g2.drawRect( tempBC.getXLoc()+(tempBC.getWidth()/10), tempBC.getYLoc() + (tempBC.getHeight()/10), tempBC.getWidth()- (tempBC.getWidth()/5), tempBC.getHeight() - (tempBC.getHeight()/5));
					}
				}
			}

			//Time Remaining Drawing
			g.setFont(new Font(timeRemainingFontStyle,Font.BOLD,timeRemainingFontSize));
			g.setColor(Color.WHITE);
			g.drawString(timeRemainingLabel, timeRemainingLabelXLoc, timeRemainingLabelYLoc);
			g.drawString(String.valueOf(timeRemaining), timeXLoc, timeYLoc);

			//Oysters Count Drawing
			g.setFont(new Font(oysterCountFontStyle,Font.BOLD,oysterCountFontSize));
			g.setColor(Color.WHITE);
			g.drawString(oysterCountLabel, oysterCountLabelXLoc, oysterCountLabelYLoc);
			g.drawString(String.valueOf(crab.getNumOysters()), oysterCountXLoc, oysterCountYLoc);


			//paint waves
			for(Wave w: gameWaves){
				g.drawImage(waveImage, w.getXLoc(), w.getYLoc(), w.getWidth(), w.getHeight(), this);
			}

			//paints boats
			for(Boat b: board.getGameBoats()){
				g.drawImage(boatImgArray[b.getSize()][0],b.getXLoc(),b.getYLoc(),b.getWidth(),b.getHeight(),this);
			}

			//paints buckets

			//grass box
			g.drawImage(boxImage,board.getGrassBucketXLoc(),board.getGrassBucketYLoc(),board.getGrassBucketWidth(),board.getGrassBucketHeight(), this);
			g.drawImage(grassImg, board.getGrassBucketXLoc() + board.getGrassBucketWidth()/3, 
					board.getGrassBucketYLoc() + board.getGrassBucketHeight()/4, bucketLogoWidth, bucketLogoHeight, this);

			//seawall box
			g.drawImage(boxImage,board.getSeawallBucketXLoc(),board.getSeawallBucketYLoc(),board.getSeawallBucketWidth(),board.getSeawallBucketHeight(),this);
			g.drawImage(wallImg, board.getSeawallBucketXLoc() + board.getSeawallBucketWidth()/3, 
					board.getSeawallBucketYLoc() + board.getSeawallBucketHeight()/4, bucketLogoWidth, bucketLogoHeight, this);

			//gabion box
			g.drawImage(boxImage,board.getGabionBucketXLoc(),board.getGabionBucketYLoc(),board.getGabionBucketWidth(),board.getGabionBucketHeight(),this);
			g.drawImage(gabionImg, board.getGabionBucketXLoc() + board.getGabionBucketWidth()/3, 
					board.getGabionBucketYLoc() + board.getGabionBucketHeight()/4, bucketLogoWidth, bucketLogoHeight, this);

			//paints walls
			for(Seawall s: board.getGameSeawalls()){
				g.drawImage(wallImg, s.getXLoc(), s.getYLoc() + barrierImgHeight/2, barrierImgWidth, barrierImgHeight - barrierImgHeight/2, this);
			}

			//draw oysters
			for(Oyster o : board.getGameOysters()){
				g.drawImage(oysterImg,o.getXLoc(),o.getYLoc(),o.getWidth(),o.getHeight(),this);
			}

			//paints gabions
			for(OysterGabion og: board.getGameGabs()){
				g.drawImage(gabionImg,og.getXLoc(), og.getYLoc(), barrierImgWidth, barrierImgHeight,this);
			}

			//paints grass
			for(Grass grass: board.getGameGrass()){
				g.drawImage(grassImg,grass.getXLoc() + cellWidth/2 - (grassImgWidth/2), 
						grass.getYLoc() + cellHeight/2 - (grassImgHeight/2), grassImgWidth, grassImgHeight, this);
			}

			//TUTORIAL TEXT AND IMAGES
			g.setFont(new Font(tutorialTextStyle,Font.BOLD,tutorialTextSize));
			if(isTutorial){
				g2.setStroke(new BasicStroke(6));
				g.setColor(Color.BLACK);
				if(tutorialState == firstBoatState){
					g.drawString(firstBoatStateText, screenWidth/2 - (firstBoatStateText.length()*tutorialTextSize)/5, screenHeight/3);
				}
				else if(tutorialState == grassState){
					g.drawString(grassStateText, screenWidth/2 - (grassStateText.length()*tutorialTextSize)/5, screenHeight/3);
					g2.setColor(Color.green);
					g2.drawRect(board.getGrassBucketXLoc(),board.getGrassBucketYLoc(),board.getGrassBucketWidth(),board.getGrassBucketHeight());
				}
				else if(tutorialState == seawallState){
					g.drawString(seawallStateText, screenWidth/2 - (seawallStateText.length()*tutorialTextSize)/5, screenHeight/3);
					g2.setColor(Color.green);
					g2.drawRect(board.getSeawallBucketXLoc(),board.getSeawallBucketYLoc(),board.getSeawallBucketWidth(),board.getSeawallBucketHeight());
				}
				else if(tutorialState == gabionState){
					g.drawString(oysterStateText, screenWidth/2 - (oysterStateText.length()*tutorialTextSize)/5, screenHeight/3);
					g.drawString(gabionStateText, screenWidth/2 - (gabionStateText.length()*tutorialTextSize)/5, screenHeight/3 + tutorialTextSize);
					g2.setColor(Color.green);
					g2.drawRect(board.getGabionBucketXLoc(),board.getGabionBucketYLoc(),board.getGabionBucketWidth(),board.getGabionBucketHeight());
				}
				else if(tutorialState == secondBoatState){
					g.drawString(secondBoatStateText, screenWidth/2 - (secondBoatStateText.length()*tutorialTextSize)/5, screenHeight/3);
				}
				else if(tutorialState == finishTutorialState){
					g.drawString(finishTutorialStateText, screenWidth/2 - (finishTutorialStateText.length()*tutorialTextSize)/5, screenHeight/3);
				}
			}//end draw tutorial stuff

			//draws crab
			g.drawImage(crabPics[crabPicNum],crab.getXLoc(),crab.getYLoc(),crab.getWidth(),crab.getHeight(),this);

			//draws object held by crab
			if(crab.getCurrObject() != 0){
				g.drawImage(objectImgArray[crab.getCurrObject() - 1],crab.getXLoc() + crab.getWidth()/2 - heldObjectWidth/2, crab.getYLoc() + crab.getHeight() - heldObjectHeight, heldObjectWidth, heldObjectHeight, this);
			}
			
			//end game draw strings
			if(isGameOver){
				g.setFont(new Font(gameOverTextStyle, Font.BOLD, gameOverTextSize));
				g.setColor(Color.BLACK);
				if(hasWon){
					g.drawString(winText, gameOverTextX, gameOverTextY);
				}
				else{
					g.drawString(loseText, gameOverTextX, gameOverTextY);
				}
			}

			//----END DRAW EVERYTHING ELSE
		}

	}


	//ACTION PERFORMED
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		
		//Swim speed (rendering crab movement)
		if(swimTimer > 0){
			swimTimer--;
		}

		if(crabXVel == 0 && crabYVel == 0){
			crabIsMoving = false;
		}

		if(crabIsMoving && swimTimer == 0){
			crabPicNum = (crabPicNum + 1) % crabNumPics;
			swimTimer = swimSpeed;
		}
		
		
		if(tutPauseTimer < tutPauseTotal){
			tutPauseTimer++;
			return;
		}
		//tutorial tick stuff
		board.updateCurrentCellsHealth();
		board.updateCurrentShoreHealth();

		//tracks crab movements
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

		board.sandToOcean();

		//Boat Ticks
		for(Boat bt: gameBoats){
			bt.move();
			board.makeWaves(bt);
			board.resetWaves(bt);
		}
		board.removeBoatsOffScreen();

		//remove oyster when grabbed by crab
		board.removeOyster(crab);

		board.removeHitWaves();
		board.removeDeadBarriers();


		//Wave 
		for(Wave wv: gameWaves){
			wv.move(waveSpeed);
		}


		if(isTutorial){
			if(tutorialState == firstBoatState){
				if(!hasSpawnedFirstBoat){
					board.spawnMediumBoat();
					hasSpawnedFirstBoat = true;
				}
				else if(board.getGameBoats().size() < 1){
					tutorialState = grassState;
				}
			}
			else if(tutorialState == grassState){
				board.setSpecificObjectFromBucket(1);
				if(board.getGameGrass().size() >= 1){
					tutorialState = seawallState;
				}
			}
			else if(tutorialState == seawallState){
				board.setSpecificObjectFromBucket(2);
				if(board.getGameSeawalls().size() >= 1){
					tutorialState = gabionState;
				}
			}
			else if(tutorialState == gabionState){
				board.setSpecificObjectFromBucket(3);
				while(board.getGameOysters().size() < 3 && !hasSpawnedOysters){
					board.spawnOyster();
				}
				hasSpawnedOysters = true;
				if(board.getGameGabs().size() >= 1){
					tutorialState = secondBoatState;
				}
			}
			else if(tutorialState == secondBoatState){
				if(!hasSpawnedSecondBoat){
					board.spawnMediumBoat();
					hasSpawnedSecondBoat = true;
				}
				else if(board.getGameBoats().size() < 1 && board.getGameWaves().size() < 1){
					tutorialState = finishTutorialTextState;
				}
			}
			else if(tutorialState == finishTutorialTextState){
				tutPauseTimer = 0;
				tutorialState = finishTutorialState;
			}
			else if(tutorialState == finishTutorialState){
				board.resetShore();
				isTutorial = false;
			}
			return;
		}
		//-----------------------------------------------------------

		//Clock Time
		timeCheck++;
		if(timeCheck == 100){
			timeRemaining--;
			timeCheck = 0;
		}

		if(timeRemaining < 60){
			newBoatTimer = 300;
		}

		//Checks if you lose
		if(board.getCurrentShoreHealth() <= 0){
			hasWon = false;
			isGameOver = true;
			goToStartButton.setVisible(true);
			t.stop();
		}

		//Checks if you win
		if(timeRemaining <= 0){
			hasWon = true;
			isGameOver = true;
			goToStartButton.setVisible(true);
			t.stop();
		}

		//bucket stuff, gives crab object from the bucket
		board.setObjectFromBucket();


		//BOAT STUFF
		//Boat timer increment
		if(newBoatTimerTime < newBoatTimer){
			newBoatTimerTime++;
		}else{
			board.generateRandomBoat();
			newBoatTimerTime = 0;
		}

		//oysterStuff
		if(oysterSpawnTimer >= oysterSpawnTick){
			board.spawnOyster();
			oysterSpawnTimer = 0;
		}else{
			oysterSpawnTimer++;
		}



	}//actionPerformed


	//MOVE CRAB IMAGE
	public void moveCrabImgUp(){
		crabIsMoving = true;
		crabXVel = 0;
		crabYVel = -crab.getYIncr();
	}
	public void moveCrabImgDown(){
		crabIsMoving = true;
		crabXVel = 0;
		crabYVel = crab.getYIncr();
	}
	public void moveCrabImgLeft(){
		crabIsMoving = true;
		crabXVel = -crab.getXIncr();
		crabYVel = 0;
	}
	public void moveCrabImgRight(){
		crabIsMoving = true;
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

		else if(code == KeyEvent.VK_SPACE){
			board.placeObject(crab.getCurrObject(), crab.getXLoc(), crab.getYLoc(), crab.getWidth(), crab.getHeight());
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_UP){
			crabYVel = 0;
		}
		else if(code == KeyEvent.VK_DOWN){
			crabYVel = 0;
		}
		else if(code == KeyEvent.VK_LEFT){
			crabXVel = 0;
		}
		else if(code == KeyEvent.VK_RIGHT){
			crabXVel = 0;
		}
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
