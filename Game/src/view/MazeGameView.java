package view;

import model.Crab;
import model.MazeBoard;
import model.MazeCell;
import model.MazeWall;
import model.MiniMap;
import model.PowerUp;
import model.Predator;
import model.Litter;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
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
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class MazeGameView extends JPanel implements KeyListener, ActionListener {

	//=================================================================//

	private static final long serialVersionUID = 1L;


	//Timer
	Timer t = new Timer(10,this);
	private int totalTime = 120;
	private int timeRemaining = totalTime;
	private int timeCheck = 0;

	//Screen dimensions
	static private int screenWidth = MainFrame.getFrameWidth();
	static private int screenHeight = MainFrame.getFrameHeight();

	//Background image
	private BufferedImage backGroundImg = createImage("background/underwater2.png");

	//create the maze board
	private int numRows = 15;
	private int numCols = 15;
	private int cellWidth = 200;
	private int cellHeight = 200;
	private MazeBoard board = new MazeBoard(numRows,numCols,cellWidth,cellHeight, screenWidth, screenHeight);
	private MazeCell[][] grids = board.getGrid(); 
	private ArrayList<MazeWall> mazeWalls = board.getMazeWalls();

	//miniMap 
	private MiniMap miniMap = new MiniMap();
	private int miniWidth = miniMap.getWidth();
	private int miniHeight = miniMap.getHeight();
	private MazeCell miniCharacter;

	//Salinity Meter
	private int meterWidth = 60;
	private int meterHeight = 30;
	private int greenMeterX = screenWidth - 70;
	private int redMeterX = screenWidth - 130;
	private int meterY = 10;

	private int salinityTextSize = 12;
	private String salinityTextStyle = "TimesRoman";

	private int correctSalinityTextX = screenWidth - 60;
	private int correctSalinityTextY = 30;

	private int wrongSalinityTextX = screenWidth - 120;
	private int wrongSalinityTextY = 30;

	private String correctSalinityTextBCrab = "> 20 ppt";
	private String correctSalinityTextHCrab = "20-30 ppt";
	private String wrongSalinityTextBCrab = "Too low!";
	private String wrongSalinityTextHCrab = "Too high!";

	private String[][] salinityTextArray = {
			{correctSalinityTextHCrab,wrongSalinityTextHCrab},
			{correctSalinityTextBCrab,wrongSalinityTextBCrab},
	};

	private String salinityTitleText = "Current Salinity:";
	private int salinityTitleX = wrongSalinityTextX - 130;
	private int salinityTitleY = 30;
	private int salinityTitleFontSize = 15;
	private String salinityTitleFontStyle = "TimesRoman";



	//Horseshoe Crab images
	private BufferedImage crabRight0 = createImage("characters/horseshoe_crab_right_0.png");
	private BufferedImage crabRight1 = createImage("characters/horseshoe_crab_right_1.png");

	private BufferedImage crabLeft0 = createImage("characters/horseshoe_crab_left_0.png");
	private BufferedImage crabLeft1 = createImage("characters/horseshoe_crab_left_1.png");

	private BufferedImage crabDown0 = createImage("characters/horseshoe_crab_down_0.png");
	private BufferedImage crabDown1 = createImage("characters/horseshoe_crab_down_1.png");

	private BufferedImage crabUp0 = createImage("characters/horseshoe_crab_up_0.png");
	private BufferedImage crabUp1 = createImage("characters/horseshoe_crab_up_1.png");

	//Blue Crab images
	private BufferedImage bCrab0 = createImage("characters/bluecrab_0.png");
	private BufferedImage bCrab1 = createImage("characters/bluecrab_1.png");
	private BufferedImage bCrab2 = createImage("characters/bluecrab_2.png");


	//Crab images arrays
	private BufferedImage[][] crabPics = {
			{crabUp0, crabUp1},
			{crabDown0, crabDown1},
			{crabRight0,crabRight1},
			{crabLeft0, crabLeft1},
	};

	private BufferedImage[][] bCrabPics = {
			{bCrab0, bCrab1, bCrab2},
	};

	private int crabPicNum = 0;
	private int crabNumPics = 1;
	private boolean crabIsMoving = false;
	private int swimSpeed = 5;
	private int swimTimer = swimSpeed;


	//Crab
	private Crab testCrab = new Crab(5,0,screenWidth/2 + 10 ,screenHeight/2 + 10); //health, age
	private int crabDir;
	private BufferedImage crabImg;
	private int characterWidth = 50;
	private int characterHeight = 50;
	private int characterXLoc = testCrab.getXLoc();
	private int characterYLoc = testCrab.getYLoc();
	private int yIncr = testCrab.getXIncr();
	private int xIncr = testCrab.getYIncr();
	private int xVel = testCrab.getXVel();
	private int yVel = testCrab.getYVel();

	//Predator Images
	private BufferedImage bassPredRight = createImage("characters/fish_bass_right.png");
	private BufferedImage bassPredLeft = createImage("characters/fish_bass_left.png");
	private BufferedImage bassPredUp = createImage("characters/fish_bass_up.png");
	private BufferedImage bassPredDown = createImage("characters/fish_bass_down.png");

	private BufferedImage groupPredRight = createImage("characters/fish_group_right.png");
	private BufferedImage groupPredLeft = createImage("characters/fish_group_left.png");
	private BufferedImage groupPredUp = createImage("characters/fish_group_up.png");
	private BufferedImage groupPredDown = createImage("characters/fish_group_down.png");

	//Predator Pic Array,  0 = bass, 1 = group
	private BufferedImage[][] preds = {
			{bassPredUp,bassPredDown,bassPredRight,bassPredLeft},
			{groupPredUp,groupPredDown,groupPredRight,groupPredLeft}
	};

	//Features bar
	private final int featuresBarWidth = screenWidth;
	private final int featuresBarHeight = 50;


	//Litter
	private ArrayList<BufferedImage> litterTypes = makeLitterList();
	Random rand = new Random();
	Litter[] gameLitter = board.getGameLitter();
	private int litterWidth = gameLitter[0].getWidth();
	private int litterHeight = gameLitter[0].getHeight();
	private int xLitterMax = 0;
	private int xLitterMin = 0;


	//Predator Array List
	private ArrayList<Predator> predators = board.getPredators();

	//Time Remaining Label
	private final String timeRemainingLabel = "Time Remaining: ";
	private final int timeRemainingLabelXLoc = (screenWidth/2) - 50;
	private final int timeRemainingLabelYLoc = 30;

	private final int timeRemainingFontSize = 20;

	private final int timeXLoc = timeRemainingLabelXLoc + 155;
	private final int timeYLoc = timeRemainingLabelYLoc;

	//Health
	private int health = testCrab.getHealth();
	private int hitTimer = 0;
	private int cantBeHitLim = 100;

	private BufferedImage healthImg = createImage("MazeExtraImgs/fullHeart.png");
	private final int healthImgWidth = 30;
	private final int healthImgHeight = 30;
	private final int healthImgXLoc = timeRemainingLabelXLoc - 250;
	private final int healthImgYLoc = 10;

	//PowerUps
	private ArrayList<PowerUp> gamePowerUps = board.getGamePowerUps();
	private int powerUpWidth = gamePowerUps.get(0).getWidth();
	private int powerUpHeight = gamePowerUps.get(0).getHeight();
	private BufferedImage powerUpImg = createImage("MazeExtraImgs/powerup.png");

	private String powerHealthText = "Extra Life!";
	private String powerSpeedText = "Speed Boost!";
	private String powerInvincibilityText = "Invincibility!";
	private String [] powerUpTexts = {powerHealthText, powerSpeedText, powerInvincibilityText};

	
	private String powerUpTitleText = "Power Up: ";
	private int powerUpTitleX = timeRemainingLabelXLoc - 175;
	private int powerUpTitleY = 30;
	private int powerUpTitleFontSize = 15;
	private String powerUpTitleFontStyle = "TimesRoman";
	
	private int powerUpX;
	private int powerUpY = 30;

	//StartScreen
	private boolean startScreenVisible;
	private JButton hCrabButton;
	private JButton bCrabButton;

	private BufferedImage startBackgroundImg = createImage("background/2D_estuary.jpg");
	private int titleFontSize = 30;
	private String titleFontStyle = "TimesRoman";
	private String titleText = "Estuary Maze Adventure!";
	private int titleStringX = screenWidth/2 - ((titleFontSize * titleText.length())/4);
	private int titleStringY = screenHeight/4;

	//End Screen
	private boolean endScreenVisible;
	private int endTitleFontSize = 30;
	private String endTitleFontStyle = "TimesRoman";
	private String endLoseText = "Oh no! You lost!";
	private String endWinText = "Congratulations, you won!";
	private int endTitleStringX = screenWidth/2 - ((titleFontSize * titleText.length())/4);
	private int endTitleStringY = screenHeight/4;

	private String endWinTimeText = "Your Time: ";
	private int endWinTimeTextX = endTitleStringX;
	private int endWinTimeTextY = screenHeight/4 + 100;

	private String endLoseTextHealth = "You have no more health!";
	private String endLoseTextTime = "You have no more time left!";
	private int endLoseTextX = endTitleStringX;
	private int endLoseTextY = screenHeight/4 + 100;

	//End Cell
	private MazeCell endCell  = grids[board.getXEnd()][board.getYEnd()];

	private BufferedImage endGrassImg = createImage("MazeExtraImgs/seagrass.png");

	private BufferedImage endBlueCrabImg = createImage("characters/bluecrab_0.png");
	private BufferedImage endHorshoeCrabImg = createImage("characters/horseshoe_crab_left_0.png");

	private BufferedImage[] endCrabImgArray = {endHorshoeCrabImg,endBlueCrabImg};

	//Game state
	private boolean hasWon;

	//=================================================================//


	//Constructor
	public MazeGameView(){

		//Start Timer
		t.start();

		//Buttons
		hCrabButton = new JButton("Horshoe Crab");
		bCrabButton = new JButton("Blue Crab");
		hCrabButton.setFocusable(false);
		bCrabButton.setFocusable(false);

		//StartScreen Visibility
		startScreenVisible = true;
		this.add(bCrabButton);
		this.add(hCrabButton);

		//Button Listeners
		bCrabButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//initializes type of crab
				testCrab.setType(1);
				testCrab.setDir(0);
				crabNumPics = 3;
				remove(bCrabButton);
				remove(hCrabButton);
				startScreenVisible = false;
				timeRemaining = totalTime;
			}

		});

		hCrabButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//initializes type of crab
				testCrab.setType(0);
				testCrab.setDir(2);
				crabNumPics = 2;
				remove(bCrabButton);
				remove(hCrabButton);
				startScreenVisible = false;
				timeRemaining = totalTime;
			}

		});


		//initialize key
		addKeyListener(this);
		this.setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		//initialize game state
		endScreenVisible = false;
		hasWon = false;


		//background
		this.setBackground(Color.BLUE);

	}//constructor



	//paintComponent
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		//START SCREEN DRAWING
		if(startScreenVisible){
			g.drawImage(startBackgroundImg,0,0,screenWidth,screenHeight,this);

			g.setFont(new Font(titleFontStyle,Font.BOLD,titleFontSize));
			g.drawString(titleText,titleStringX,titleStringY);

		}//if


		//EVERYTHING ELSE
		else{

			//BACKGROUND DRAWING
			g.drawImage(backGroundImg, 0, 0, screenWidth, screenHeight, this);

			// MAZE DRAWING
			Graphics2D g2 = (Graphics2D)g;
			g2.setStroke(new BasicStroke(5));
			g2.setColor(Color.CYAN);
			for (MazeWall wall: mazeWalls){
				int startX = wall.getStartX();
				int startY = wall.getStartY();
				int endX = wall.getEndX();
				int endY = wall.getEndY();
				//checks if on Screen
				if(
						((startX > 0 && startX < screenWidth) || (endX > 0 && endX < screenWidth)) ||
						((startY>0 && startY < screenHeight) || (endY > 0 && endY > screenHeight))
						)
					g.drawLine(startX, startY, endX, endY);
			}

			//Draws litter
			for(Litter lit : gameLitter){
				if(lit.getXLoc()+litterWidth > 0 && lit.getXLoc() <= screenWidth && lit.getYLoc()+litterHeight > 0 && lit.getYLoc() < screenHeight)
					g2.drawImage(litterTypes.get(lit.getType()), lit.getXLoc(), lit.getYLoc(),litterWidth, litterHeight, this);
			}

			//Draws Power Ups
			for(PowerUp pu : gamePowerUps){
				g.drawImage(powerUpImg,pu.getXLoc(),pu.getYLoc(), powerUpWidth, powerUpHeight, this);
			}
			//DRAWS PREDATORS
			for(int i = 0; i < predators.size()/2; i++){
				g.drawImage(preds[0][predators.get(i).getDirection()], predators.get(i).getXLoc(), predators.get(i).getYLoc(), predators.get(i).getWidth(), predators.get(i).getHeight(), this);
			}

			for(int i = (predators.size()/2) + 1; i < predators.size(); i++){
				g.drawImage(preds[1][predators.get(i).getDirection()], predators.get(i).getXLoc(), predators.get(i).getYLoc(), predators.get(i).getWidth(), predators.get(i).getHeight(), this);
			}


			//Features Bar Drawing
			g.setColor(Color.yellow);
			g.drawRect(0, 0, featuresBarWidth, featuresBarHeight);
			g.fillRect(0, 0, featuresBarWidth, featuresBarHeight);

			//MINIMAP DRAWING
			//background of minimap
			g.setColor(Color.BLACK);
			g.drawRect(0,0,numRows*miniWidth, numCols*miniHeight);
			g.fillRect(0,0,numRows*miniWidth, numCols*miniHeight);
			//actual lines of minimap
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

			//Time Remaining Drawing
			g.setFont(new Font(titleFontStyle,Font.BOLD,timeRemainingFontSize));
			g.setColor(Color.BLACK);
			g.drawString(timeRemainingLabel, timeRemainingLabelXLoc, timeRemainingLabelYLoc);
			g.drawString(String.valueOf(timeRemaining), timeXLoc, timeYLoc);
			
			//Current Power Up Drawing
			g.setFont(new Font(powerUpTitleFontStyle,Font.BOLD,powerUpTitleFontSize));
			g.setColor(Color.BLACK);
			g.drawString(powerUpTitleText, powerUpTitleX, powerUpTitleY);

			//Crab Health Drawing
			g.setColor(Color.BLACK);
			g.setFont(new Font(titleFontStyle,Font.BOLD,titleFontSize));
			g.drawString(String.valueOf(health), healthImgXLoc + healthImgWidth, healthImgYLoc + healthImgHeight);
			g.drawImage(healthImg, healthImgXLoc, healthImgYLoc, healthImgWidth, healthImgHeight, this);

			
			//SALINITY METER DRAWING
			g.setFont(new Font(salinityTitleFontStyle,Font.BOLD,salinityTitleFontSize));
			g.drawString(salinityTitleText, salinityTitleX, salinityTitleY);

			g.setColor(Color.GREEN);
			g.drawRect(greenMeterX,meterY,meterWidth,meterHeight);
			g.fillRect(greenMeterX,meterY,meterWidth,meterHeight);

			g.setColor(Color.RED);
			g.drawRect(redMeterX,meterY,meterWidth,meterHeight);
			g.fillRect(redMeterX,meterY,meterWidth,meterHeight);


			//highlight for current salinity
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(3));
			g.setFont(new Font(salinityTextStyle,Font.ITALIC,salinityTextSize));
			if(board.isOnCorrectPath(characterXLoc, characterYLoc)){
				g2.drawRect(greenMeterX,meterY,meterWidth,meterHeight);
				g.drawString(salinityTextArray[testCrab.getType()][0], correctSalinityTextX, correctSalinityTextY);
			}
			else{
				g2.drawRect(redMeterX,meterY,meterWidth,meterHeight);
				g.drawString(salinityTextArray[testCrab.getType()][1], wrongSalinityTextX, wrongSalinityTextY);
			}



			//END LOCATION DRAWING
			g.drawImage(endGrassImg, endCell.getXLoc(), endCell.getYLoc(), characterWidth, characterHeight, this);
			g.drawImage(endGrassImg, endCell.getXLoc(), endCell.getYLoc()+150, characterWidth, characterHeight, this);
			g.drawImage(endGrassImg, endCell.getXLoc()+150, endCell.getYLoc(), characterWidth, characterHeight, this);
			g.drawImage(endGrassImg, endCell.getXLoc()+150, endCell.getYLoc()+150, characterWidth, characterHeight, this);
			g.drawImage(endCrabImgArray[testCrab.getType()], endCell.getXLoc()+50, endCell.getYLoc()+50, characterWidth, characterHeight, this);
			g.drawImage(endCrabImgArray[testCrab.getType()], endCell.getXLoc()+125, endCell.getYLoc()+50, characterWidth, characterHeight, this);

			//CRAB DRAWING
			if(testCrab.getType() == 0){
				crabImg = crabPics[crabDir][crabPicNum];
			}
			else
				crabImg = bCrabPics[crabDir][crabPicNum];


			//HIT BLINKING OF CRAB
			if(hitTimer%(cantBeHitLim/20) == 0)
				g.drawImage(crabImg, testCrab.getXLoc(), testCrab.getYLoc(), characterWidth, characterHeight, this);


			//END SCREEN DRAWING
			if(endScreenVisible){

				g.setFont(new Font(endTitleFontStyle,Font.BOLD,endTitleFontSize));

				if(hasWon){
					g.setColor(Color.BLUE);
					g.drawString(endWinText, endTitleStringX, endTitleStringY);
					g.drawString(endWinTimeText + (120 - timeRemaining) + " seconds", endWinTimeTextX, endWinTimeTextY);
				}
				else{
					g.setColor(Color.RED);
					g.drawString(endLoseText, endTitleStringX, endTitleStringY);
					if(timeRemaining == 0){
						g.drawString(endLoseTextTime, endLoseTextX, endLoseTextY);
					}
					else{
						g.drawString(endLoseTextHealth, endLoseTextX, endLoseTextY);
					}
				}
			}

		}//else


	}//paintComponent


	public void actionPerformed(ActionEvent arg0) {

		timeCheck++;
		if(timeCheck == 100){
			timeRemaining--;
			timeCheck = 0;
		}

		//Checks if you lose
		if(health == 0 || timeRemaining == 0){
			hasWon = false;
			endScreenVisible = true;
			t.stop();
		}

		//Checks if you win
		if(testCrab.getXLoc() > endCell.getXLoc() && testCrab.getYLoc() > endCell.getYLoc()){
			hasWon = true;
			endScreenVisible = true;
			t.stop();
		}

		if(swimTimer > 0){
			swimTimer--;
		}

		if(crabIsMoving && swimTimer == 0){
			crabPicNum = (crabPicNum + 1) % crabNumPics;
			swimTimer = swimSpeed;
		}

		//check for power up hit
		Iterator<PowerUp> p = gamePowerUps.iterator();
		while(p.hasNext()){
			PowerUp pu = p.next();
			if(pu.hitPowerUp(characterXLoc, characterYLoc, characterWidth, characterHeight)){
				if(pu.getType() == 0){
					health += 1;
					p.remove();
				}
				else if(pu.getType() == 1){
					xIncr += 2;
					yIncr += 2;
					p.remove();
				}
				else{
					System.out.println("invincibility");
					p.remove();
				}
			}
		}

		//checks for litter hits
		if(board.hitAnyLitter(characterXLoc, characterYLoc, characterWidth, characterHeight) && hitTimer == cantBeHitLim){
			health -= 1;
			hitTimer = 0;
		}

		//checks for predator hits
		if(board.hitAnyPreds(characterXLoc, characterYLoc, characterWidth, characterHeight) && hitTimer == cantBeHitLim){
			health -= 1;
			hitTimer = 0;
		}

		if(hitTimer < cantBeHitLim){
			hitTimer++;
		}

		//floats the litter back and forth in a cell
		if(xLitterMax + board.getGameLitter()[0].getFloatXIncr() + litterWidth <= cellWidth){
			board.floatAllLitterRight();
			xLitterMax += board.getGameLitter()[0].getFloatXIncr();
		}
		else if(xLitterMin + board.getGameLitter()[0].getFloatXIncr() + litterWidth <= cellWidth){
			board.floatAllLitterLeft();
			xLitterMin += board.getGameLitter()[0].getFloatXIncr();
		}
		else{
			xLitterMax = 0;
			xLitterMin = 0;
		}
		repaint();


		//PREDATOR TICKS
		board.setRandomDirections();
		board.moveAllPredators();

		repaint();

		//MOVES MAZE IF CRAB IS NOT HITTING WALL
		if(board.isHittingAnyWalls(characterXLoc - xVel, characterYLoc - yVel, characterWidth, characterHeight)){
			return;
		}
		else{
			repaint();
			board.moveGrid(xVel,yVel);
		}
	}

	public void up(){
		testCrab.setDir(0);
		crabDir = testCrab.getDir();

		crabIsMoving = true;
		xVel = 0;
		yVel = yIncr;
	}
	public void down(){
		//checks crab type, sets direction accordingly
		if(testCrab.getType() == 1){
			testCrab.setDir(0);
		}
		else
			testCrab.setDir(1);

		crabIsMoving = true;
		crabDir = testCrab.getDir();
		xVel = 0;
		yVel = -yIncr;
	}
	public void left(){
		//checks crab type, sets direction accordingly
		if(testCrab.getType() == 1){
			testCrab.setDir(0);
		}
		else
			testCrab.setDir(3);

		crabIsMoving = true;
		crabDir = testCrab.getDir();
		xVel = xIncr;
		yVel = 0;
	}
	public void right(){
		//checks crab type, sets direction accordingly
		if(testCrab.getType() == 1){
			testCrab.setDir(0);
		}
		else
			testCrab.setDir(2);

		crabIsMoving = true;
		crabDir = testCrab.getDir();
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
		crabIsMoving = false;
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
		litterIcons.add(createImage("MazeExtraImgs/apple.png"));
		litterIcons.add(createImage("MazeExtraImgs/chipBag.png"));
		litterIcons.add(createImage("MazeExtraImgs/soda.png"));
		litterIcons.add(createImage("mazeExtraImgs/crumbledpaper.png"));
		return litterIcons;
	}


	public ArrayList<BufferedImage> makePowerUps(){
		ArrayList<BufferedImage> powerUps = new ArrayList<BufferedImage>();
		powerUps.add(createImage("MazeExtraImgs/apple.png"));
		powerUps.add(createImage("MazeExtraImgs/chipBag.png"));
		powerUps.add(createImage("MazeExtraImgs/soda.png"));
		return powerUps;
	}


	//Getters
	public int getScreenWidth(){
		return screenWidth;
	}

	public int getScreenHeight(){
		return screenHeight;
	}

}