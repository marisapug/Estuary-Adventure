package view;

import java.util.Random;
import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;
import javax.swing.*;

import model.*;

public class DiceGameView extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	// Timer
	Timer diceTimer = new Timer(50, this);

	// Screen
	private int screenWidth = MainFrame.getFrameWidth();
	private int screenHeight = MainFrame.getFrameHeight();

	// Dice
	DiceGame dgame;
	private int diceWidth = 120;
	private int betweenStory = 10;
	private int storyStartX;
	private int storyStartY;
	private int storyTextX;
	private int storyTextY;
	private Font storyTextStyle;
	private int storyFontSize = screenWidth / 25;
	private int maxLines = 7; // maximum number of lines the text box will fit
	private int numLines = 0;
	private ArrayList<String> storyWords = new ArrayList<String>();
	private ArrayList<String> storyLines = new ArrayList<String>();
	private int numCharsOnLine = screenWidth / 55;
	private int[] stringYCoords = new int[maxLines];
	private boolean isRolled = false; // true if the user pressed the button to
										// roll
	private boolean isStorySaved = false; // true if the user pressed the button
											// to enter a story
	private boolean isAnimDone = false; // true if the dice have finished
										// "rolling"
	private boolean isSplitStoryLinesCalled = false; // to make sure the
														// function is only
														// called once
	private boolean isDragging = false;
	// TODO private boolean is
	private String imgStrings[] = { "diceimages/appledie.png", "diceimages/bananadie.png", "diceimages/beakerdie.png",
			"diceimages/boxdie.png", "diceimages/bucketdie.png", "diceimages/candie.png",
			"diceimages/canwithwingsdie.png", "diceimages/chipbagdie.png", "diceimages/cleanvesseldie.png",
			"diceimages/clipboarddie.png", "diceimages/clockdie.png", "diceimages/crabfooddie.png",
			"diceimages/crabtrapdie.png", "diceimages/crumpledpaperdie.png", "diceimages/crushedcandie.png",
			"diceimages/deadfishdie.png", "diceimages/dirtyvesseldie.png", "diceimages/dogpoopbagdie.png",
			"diceimages/fishtagdie.png", "diceimages/flagdie.png" };
	private BufferedImage[] possibleDiceImgs;
	private BufferedImage[] diceImages;
	private BufferedImage[] finalImages;
	private int[] xCoordinates;
	private int[] yCoordinates;
	private int[] storyboardX;
	private int[] originalX;
	private int[] originalY;

	// Dice Rolling Animation
	int numAnimations = 0;
	int animsToDo = 50;

	// Buttons
	private JButton startGameButton;
	private JButton rollDiceButton;
	private JButton storyButton;
	private JButton rollAgainButton;

	// Button Appearance
	private int buttonFontSize = screenWidth / 50;
	private Font buttonFont;
	private int startButtonSizeX = screenWidth / 5;
	private int startButtonSizeY = screenWidth / 25;
	private Dimension startButtonSize;
	private int buttonSizeX = screenWidth / 10;
	private int buttonSizeY = screenWidth / 25;
	private Dimension buttonSize;
	private boolean showStoryButton;

	// TextArea
	JTextArea storyText;
	String storyString;
	

	// Images
	BufferedImage oceanBackground = createImage("background/dicebackground.jpg");
	BufferedImage startScreen = createImage("background/dicestartscreen.jpg");

	// Start Screen
	private boolean isStartScreenVisible = true;
	private String gameTitle = "Estuary Story Cubes!";
	private String titleFont = "Arial Narrow";
	private Color titleFontColor = new Color(10, 159, 214);
	private int titleFontSize = 28;
	private int titleX = 100;
	private int titleY = 100;

	// Mouse Listener
	private DiceListener listener = new DiceListener();
	private boolean toPlace = false;
	private int clickedIndex; // index of image that you clicked on
	private int releasedIndex;
	private boolean canPlace = true;

	// Constructor
	public DiceGameView() {

		dgame = new DiceGame(screenWidth, screenHeight, diceWidth);
		dgame.setDice();

		// Initialize Button Appearance
		buttonFont = new Font(titleFont, Font.BOLD, buttonFontSize);
		storyTextStyle = new Font("Tempus Sans ITC", Font.BOLD, storyFontSize);
		startButtonSize = new Dimension(startButtonSizeX, startButtonSizeY);
		buttonSize = new Dimension(buttonSizeX, buttonSizeY);
		showStoryButton = false;

		// Initialize Images
		possibleDiceImgs = new BufferedImage[dgame.getNumImgs()];
		diceImages = new BufferedImage[dgame.getNumDice()];
		finalImages = new BufferedImage[dgame.getNumDice()];
		xCoordinates = new int[dgame.getNumDice()];
		yCoordinates = new int[dgame.getNumDice()];
		storyboardX = new int[dgame.getNumDice()];
		originalX = new int[dgame.getNumDice()];
		originalY = new int[dgame.getNumDice()];

		// Initialize Distances
		// betweenDice = diceWidth + 10;
		// diceStartX = (screenWidth - (dgame.getNumDice() / 2 * diceWidth +
		// (dgame.getNumDice() / 2 - 1) * betweenDice))
		// / 2;
		storyStartX = (screenWidth - (dgame.getNumDice() * diceWidth + (dgame.getNumDice() - 1) * betweenStory)) / 2;
		// diceStartY = (screenHeight - (3 * diceWidth + 2 * betweenStory)) / 2;
		storyStartY = (screenHeight - diceWidth) / 2;
		storyTextX = diceWidth + 30;
		storyTextY = diceWidth + 60;

		// Buttons
		startGameButton = new JButton("Start Game");
		startGameButton.setFocusable(false);
		startGameButton.setFont(buttonFont);
		startGameButton.setPreferredSize(startButtonSize);

		rollDiceButton = new JButton("Roll Dice");
		rollDiceButton.setFocusable(false);
		rollDiceButton.setVisible(false);
		rollDiceButton.setFont(buttonFont);
		rollDiceButton.setPreferredSize(startButtonSize);

		storyButton = new JButton("Submit");
		storyButton.setFocusable(false);
		storyButton.setVisible(false);
		storyButton.setFont(buttonFont);
		storyButton.setPreferredSize(buttonSize);

		rollAgainButton = new JButton("Roll Again");
		rollAgainButton.setFocusable(false);
		rollAgainButton.setVisible(false);
		rollAgainButton.setFont(buttonFont);
		rollAgainButton.setPreferredSize(startButtonSize);

		// Text Fields
		storyText = new JTextArea("Enter Story Here");
		storyText.setVisible(false);
		storyText.setPreferredSize(new Dimension(400, 96));
		storyText.setFont(new Font(titleFont, Font.PLAIN, 16));

		// Add Buttons
		this.add(startGameButton);
		this.add(rollDiceButton);
		this.add(storyText);
		this.add(storyButton);
		this.add(rollAgainButton);
		this.setupListeners();
		this.setupMouseListener(listener);
	}

	// Getters
	public int getScreenWidth() {
		return this.screenWidth;
	}

	public int getScreenHeight() {
		return this.screenHeight;
	}

	// Set Initial Coordinates for Images
	public void initializeCoordinates() { // change coordinates
		for (int i = 0; i < dgame.getNumDice(); i++) {
			Die[] dice = dgame.getDice();
			Die tmpDie = dice[i];
			xCoordinates[i] = tmpDie.getXLoc();
			yCoordinates[i] = tmpDie.getYLoc();
			storyboardX[i] = storyStartX + (diceWidth + betweenStory) * i;
		}
	}

	// To Create BufferedImages
	public BufferedImage createImage(String fileName) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	// Creates all possible images
	public void makeImages() {
		if (!isRolled)
			dgame.setDice();
		for (int i = 0; i < dgame.getNumImgs(); i++) {
			BufferedImage temp = createImage(imgStrings[i]);
			possibleDiceImgs[i] = temp;
		}
	}

	// Sets images to dice
	public void setDiceImgs() {
		for (int i = 0; i < dgame.getNumDice(); i++) {
			int temp = dgame.imgNums[i];
			diceImages[i] = possibleDiceImgs[temp];
		}
		isRolled = true;
	}

	// paintComponent
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isStartScreenVisible) {
			g.drawImage(startScreen, 0, 0, screenWidth, screenHeight, this);
			g.setFont(new Font(titleFont, Font.BOLD, titleFontSize));
			g.setColor(titleFontColor);
			g.drawString(gameTitle, titleX, titleY);
		} else {
			g.drawImage(oceanBackground, 0, 0, screenWidth, screenHeight, this); // draws
																					// background

			// Dice
			for (int i = 0; i < dgame.getNumDice(); i++) {
				// g.drawRect(xCoordinates[i], yCoordinates[i], diceWidth,
				// diceWidth);
				if (isRolled) {
					g.drawImage(diceImages[i], xCoordinates[i], yCoordinates[i], diceWidth, diceWidth, this); // draws
																												// images

					if (isAnimDone && showStoryButton) {
						storyButton.setVisible(true);
						storyText.setVisible(true);
						g.drawRect(storyboardX[i], storyStartY, diceWidth, diceWidth); // draws
																						// storyboard
																						// slots
					}
				}
				if (isStorySaved) {
					Color storyBackground = new Color(136, 191, 246);
					g.setColor(storyBackground);
					g.fillRect(diceWidth, diceWidth, screenWidth - 2 * diceWidth, screenHeight - 2 * diceWidth);

					// Story Text
					Color navy = new Color(3, 0, 130);
					g.setColor(navy);
					g.setFont(storyTextStyle);
					if (dgame.getDiceStory().length() <= numCharsOnLine) {
						g.drawString(dgame.getDiceStory(), storyTextX, storyTextY);
					} else {
						String[] storyWordsTmp = dgame.getDiceStory().split(" ");
						for (String s: storyWordsTmp){
							if(s.length() > numCharsOnLine){
								int numSplits = s.length() / numCharsOnLine;
								for(int j = 0; j < numSplits; j++){
									String piece = s.substring(j * numCharsOnLine, (j + 1) * numCharsOnLine);
									storyWords.add(piece);
								}
								String lastPiece = s.substring(numSplits * numCharsOnLine);
								storyWords.add(lastPiece);
							}
							else
								storyWords.add(s);
						}
						if (!isSplitStoryLinesCalled) {
							storyLines = splitStoryLines();
							numLines = storyLines.size();
							if (numLines > maxLines) {
								numLines = maxLines;
							}
							isSplitStoryLinesCalled = true;
						}
						for (int j = 0; j < storyLines.size(); j++) {
							g.drawString(storyLines.get(j), storyTextX, stringYCoords[j]);
						}
					}
					// setFinalImages();
					for (int k = 0; k < dgame.getNumDice(); k++)
						g.drawImage(finalImages[k], storyboardX[k], screenHeight - 2 * (diceWidth + betweenStory),
								diceWidth, diceWidth, this);

				}
			}
		}
	}

	// Rolls Dice and Sets Images
	void rollDice() {
		makeImages();
		animDice();
		isRolled = true;
		repaint();
	}

	void saveStory() {
		dgame.diceStory = storyText.getText();
		isStorySaved = true;
	}

	// Splits strings of story into lines
		ArrayList<String> splitStoryLines() { 
			int currWord = 0;
			ArrayList<String> lines = new ArrayList<String>();
			stringYCoords[0] = storyTextY;
			for (int k = 1; k < maxLines; k++) { //initializing array to all 0's 
				stringYCoords[k] = 0;
			}
			int yCoordIndex = 0;
			int numStrings = storyWords.size();
			while (currWord < storyWords.size()) {
				String fragment = "";
				int j = currWord;
				boolean lineOver = false;
				int numChars = storyWords.get(currWord).length();
				while (j < storyWords.size() && !lineOver) {
					numChars += storyWords.get(j).length();
					fragment = fragment + storyWords.get(j) + " ";
					if (j < (storyWords.size() - 1) && numChars + storyWords.get(j + 1).length() > numCharsOnLine) {
						if (stringYCoords[yCoordIndex] == 0)
							stringYCoords[yCoordIndex] = stringYCoords[yCoordIndex - 1] + 50;
						lineOver = true;
						yCoordIndex++;
						lines.add(fragment);
					} else if (j == (storyWords.size() - 1) && yCoordIndex > 0) {
						stringYCoords[yCoordIndex] = stringYCoords[yCoordIndex - 1] + 50;
						lineOver = true;
						yCoordIndex++;
						lines.add(fragment);
					}
					j++;
					currWord = j;
				}
			}
			return lines;
		}
		
	// Set Button Listeners
	void setupListeners() {
		startGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rollDiceButton.setVisible(true);
				startGameButton.setVisible(false);
				isStartScreenVisible = false;
				repaint();
				initializeCoordinates();
				diceTimer.start();
			}
		});
		rollDiceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rollDice(); // roll dice function
				rollDiceButton.setVisible(false);
				showStoryButton = true;
				repaint();
			}
		});
		storyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveStory(); // save story function
				showStoryButton = false;
				storyButton.setVisible(false);
				storyText.setVisible(false);
				rollAgainButton.setVisible(true);
				repaint(); // print story function
			}
		});
		rollAgainButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rollAgainButton.setVisible(false);
				isRolled = false;
				isStorySaved = false;
				numAnimations = 0;
				isAnimDone = false;
				dgame.setDice();
				rollDice();
				showStoryButton = true;
				storyWords.clear();
				storyLines.clear();
				repaint();
				// storyButton.setVisible(true);
				// storyText.setVisible(true);
			}
		});
	}

	void animDice() {
		Random rand = new Random();
		Die[] dice = dgame.getDice();
		if(!isAnimDone){
			for (int i = 0; i < dgame.getNumDice(); i++) {
				dice[i].throwDie();
				xCoordinates[i] = dice[i].getXLoc();
				yCoordinates[i] = dice[i].getYLoc();
				diceImages[i] = possibleDiceImgs[rand.nextInt(dgame.getNumImgs())];
			}
		}
		else{
			System.out.println("else");
			for (int i = 0; i < dgame.getNumDice(); i++) {
				while(dice[i].getXLoc() != dice[i].getStartXLoc() | dice[i].getYLoc() != dice[i].getStartYLoc()){
					System.out.println("while");
					repaint();
					dice[i].finishThrowing();
					xCoordinates[i] = dice[i].getXLoc();
					yCoordinates[i] = dice[i].getYLoc();
					System.out.println("repainting");
					//diceImages[i] = possibleDiceImgs[rand.nextInt(dgame.getNumImgs())];
				}
			}		
		}
	}

	/*	void returnDice(){ //returns rolled dice to their original positions
	System.out.println("returnDice called");
	Die[] dice = dgame.getDice();
	for (int i = 0; i < dgame.getNumDice(); i++) {
		while(dice[i].getXLoc() != dice[i].getStartXLoc() | dice[i].getYLoc() != dice[i].getStartYLoc()){
			dice[i].finishThrowing();
			xCoordinates[i] = dice[i].getXLoc();
			yCoordinates[i] = dice[i].getYLoc();
			repaint();
			//diceImages[i] = possibleDiceImgs[rand.nextInt(dgame.getNumImgs())];
		}
	}
} */

public void actionPerformed(ActionEvent e) {
	if (numAnimations < animsToDo) {
		if (isRolled && !isAnimDone) {
			animDice();
			repaint();
			numAnimations++;
		}
	} else if (numAnimations == animsToDo) {
		isAnimDone = true;
		animDice();
		setDiceImgs();
		//repaint();
		// storyButton.setVisible(true);
		// storyText.setVisible(true);
		numAnimations++;
	}
}


	// Set up mouse listener
	private Component getMouseTarget() {
		return this;
	}

	void setupMouseListener(MouseInputListener listener) {
		getMouseTarget().addMouseListener(listener);
		getMouseTarget().addMouseMotionListener(listener);
	}

	// MouseInputListener class

	public class DiceListener implements MouseInputListener {
		boolean isPressing = false;
		Point point = new Point(0, 0);
		Point tmpPoint = new Point(0, 0);

		@Override
		public void mouseClicked(MouseEvent e) {
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (isRolled && !isStorySaved) {
				tmpPoint = e.getPoint();
				for (int i = 0; i < dgame.getNumDice(); i++) {
					if (tmpPoint.x > xCoordinates[i] && tmpPoint.x < (xCoordinates[i] + diceWidth)) {
						if (tmpPoint.y > yCoordinates[i] && tmpPoint.y < (yCoordinates[i] + diceWidth)) {
							clickedIndex = i;
							toPlace = true;
							isPressing = true;
							// isDiceUntouched = false;
						}
					}
					/*
					 * if(tmpPoint.x == storyboardX[i] && tmpPoint.y ==
					 * storyStartY){ numDicePlaced--; }
					 */
				}
			}
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			for (int i = 0; i < dgame.getNumDice(); i++) {
				if (tmpPoint.x > storyboardX[i] && tmpPoint.x < (storyboardX[i] + diceWidth)) {
					if (tmpPoint.y > storyStartY && tmpPoint.y < (storyStartY + diceWidth)) {
						releasedIndex = i;
					}
				}
				if (storyboardX[releasedIndex] == xCoordinates[i] && storyStartY == yCoordinates[i]) {
					canPlace = false;
				}
			}

			if (isRolled && isDragging) {
				if (canPlace) {
					xCoordinates[clickedIndex] = storyboardX[releasedIndex];
					yCoordinates[clickedIndex] = storyStartY;
					finalImages[releasedIndex] = diceImages[clickedIndex];
					// numDicePlaced++;
				} else if (toPlace) {
					xCoordinates[clickedIndex] = originalX[clickedIndex];
					yCoordinates[clickedIndex] = originalY[clickedIndex];
				}
			}
			isPressing = false;
			canPlace = true;
			toPlace = false;
			isDragging = false;
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			tmpPoint = e.getPoint();
			if (isPressing) {
				xCoordinates[clickedIndex] = tmpPoint.x - diceWidth / 2;
				yCoordinates[clickedIndex] = tmpPoint.y - diceWidth / 2;
			}
			if(!isDragging)
				isDragging = true;
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
	}
}