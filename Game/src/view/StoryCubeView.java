package view;

import model.*;
import view.DiceGameView.DiceListener;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

public class StoryCubeView extends JPanel implements ActionListener {
	DiceGame dgame;

	// Timer
	Timer diceTimer = new Timer(50, this);

	// Screen
	private int screenWidth = MainFrame.getFrameWidth();
	private int screenHeight = MainFrame.getFrameHeight();

	// Dice
	private Die[] gameDice;
	private int diceWidth = 120;
	private Die selectedDie;

	// Story Slots
	private int[] storyboardX;
	private int storyStartX;
	private int storyStartY;
	private int betweenStory = 10;

	// Dice Rolling Animation
	int numAnimations = 0;
	int animsToDo = 50;
	boolean isAnimDone = false;

	// Game Booleans
	private boolean isRolled = false;
	private boolean isDieSelected = false;

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

	// Story
	private Font storyTextStyle;
	private int storyFontSize = screenWidth / 25;
	private boolean isStorySaved = false;
	private int storyTextX;
	private int storyTextY;
	private String[] storyWords = {};
	private boolean isSplitStoryLinesCalled = false; // to make sure the
	// function is only
	// called once
	private ArrayList<String> storyLines = new ArrayList<String>();
	private int numLines = 0;
	private int maxLines = 7; // maximum number of lines the text box will fit
	private int[] stringYCoords = new int[maxLines];

	// TextArea
	JTextArea storyText;

	// Images
	BufferedImage oceanBackground = createImage("background/dicebackground.jpg");
	BufferedImage startScreen = createImage("background/dicestartscreen.jpg");
	private String imgStrings[] = { "diceimages/appledie.png", "diceimages/bananadie.png", "diceimages/beakerdie.png",
			"diceimages/boxdie.png", "diceimages/bucketdie.png", "diceimages/candie.png",
			"diceimages/canwithwingsdie.png", "diceimages/chipbagdie.png", "diceimages/cleanvesseldie.png",
			"diceimages/clipboarddie.png", "diceimages/clockdie.png", "diceimages/crabfooddie.png",
			"diceimages/crabtrapdie.png", "diceimages/crumpledpaperdie.png", "diceimages/crushedcandie.png",
			"diceimages/deadfishdie.png", "diceimages/dirtyvesseldie.png", "diceimages/dogpoopbagdie.png",
			"diceimages/fishtagdie.png", "diceimages/flagdie.png" };
	private BufferedImage[] possibleDiceImgs;
	private BufferedImage[] finalImages;

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

	// Constructor
	public StoryCubeView() {
		// TODO
		dgame = new DiceGame(screenWidth, screenHeight, diceWidth);

		storyStartY = (screenHeight - diceWidth) / 2;
		storyStartX = (screenWidth - (dgame.getNumDice() * diceWidth + (dgame.getNumDice() - 1) * betweenStory)) / 2;
		storyTextX = diceWidth + 30;
		storyTextY = diceWidth + 60;

		// Initialize Button Appearance
		buttonFont = new Font(titleFont, Font.BOLD, buttonFontSize);
		storyTextStyle = new Font("Tempus Sans ITC", Font.BOLD, storyFontSize);
		startButtonSize = new Dimension(startButtonSizeX, startButtonSizeY);
		buttonSize = new Dimension(buttonSizeX, buttonSizeY);
		showStoryButton = false;

		// Initialize Arrays
		possibleDiceImgs = new BufferedImage[dgame.getNumImgs()];
		storyboardX = new int[dgame.getNumDice()];
		gameDice = dgame.getDice();
		finalImages = new BufferedImage[dgame.getNumDice()];

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

		initializeCoordinates();
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
		// do i need this?
		for (int i = 0; i < dgame.getNumDice(); i++) {
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
			possibleDiceImgs[i] = createImage(imgStrings[i]);
		}
	}

	// Sets images to dice
	public void setDiceImgs() {
		for (int i = 0; i < dgame.getNumDice(); i++) {
			int temp = dgame.imgNums[i];
			gameDice[i].setDieImg(possibleDiceImgs[temp]);
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

			// Dice
			for (int i = 0; i < dgame.getNumDice(); i++) {
				// g.drawRect(xCoordinates[i], yCoordinates[i], diceWidth,
				// diceWidth);
				if (isRolled) {
					g.drawImage(gameDice[i].getDieImg(), gameDice[i].getXLoc(), gameDice[i].getYLoc(), diceWidth,
							diceWidth, this); // draws
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
					if (dgame.getDiceStory().length() <= 35) {
						g.drawString(dgame.getDiceStory(), storyTextX, storyTextY);
					} else {
						storyWords = dgame.getDiceStory().split(" ");
						if (!isSplitStoryLinesCalled) {
							storyLines = splitStoryLines();
							numLines = storyLines.size();
							if (numLines > maxLines) {
								numLines = maxLines;
							}
							isSplitStoryLinesCalled = true;
						}
						for (int j = 0; j < numLines; j++) {
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

	public void rollDice() {
		makeImages();
		animDice();
		isRolled = true;
		repaint();
	}

	// Saves Story the user entered
	void saveStory() {
		dgame.diceStory = storyText.getText();
		isStorySaved = true;
	}

	// iterate through story words, adding a new fragment to arraylist every
	// time the words are almost 22 characters
	ArrayList<String> splitStoryLines() {
		// TODO

		int currWord = 0;
		ArrayList<String> lines = new ArrayList<String>();
		stringYCoords[0] = storyTextY;
		for (int k = 1; k < maxLines; k++) {
			stringYCoords[k] = 0;
		}
		int yCoordIndex = 0;
		while (currWord < storyWords.length) {
			String fragment = "";
			int j = currWord;
			boolean lineOver = false;
			int numChars = storyWords[currWord].length();
			while (j < storyWords.length && !lineOver) {
				numChars += storyWords[j].length();
				fragment = fragment + storyWords[j] + " ";
				if (j < (storyWords.length - 1) && numChars + storyWords[j + 1].length() > 35) {
					if (stringYCoords[yCoordIndex] == 0)
						stringYCoords[yCoordIndex] = stringYCoords[yCoordIndex - 1] + 50;
					lineOver = true;
					yCoordIndex++;
					lines.add(fragment);
				} else if (j == (storyWords.length - 1) && yCoordIndex > 0) {
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

	void setupListeners() {
		startGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				rollDiceButton.setVisible(true);
				startGameButton.setVisible(false);
				isStartScreenVisible = false;
				repaint();
				// initializeCoordinates();
				diceTimer.start();
			}
		});
		rollDiceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO

				rollDice(); // roll dice function
				rollDiceButton.setVisible(false);
				showStoryButton = true;
				repaint();

			}
		});
		storyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO

				saveStory(); // save story function showStoryButton = false;
				storyButton.setVisible(false);
				storyText.setVisible(false);
				rollAgainButton.setVisible(true);

				repaint(); // print story function

			}
		});
		rollAgainButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO

				rollAgainButton.setVisible(false);
				isRolled = false;
				isStorySaved = false;
				numAnimations = 0;
				isAnimDone = false;
				dgame.setDice();
				rollDice();
				showStoryButton = true;
				repaint(); // storyButton.setVisible(true); //
				storyText.setVisible(true);

			}
		});
	}

	void animDice() {
		Random rand = new Random();
		for (int i = 0; i < dgame.getNumDice(); i++) {
			gameDice[i].throwDie();
			// xCoordinates[i] = dice[i].getXLoc();
			// yCoordinates[i] = dice[i].getYLoc();
			gameDice[i].setDieImg(possibleDiceImgs[rand.nextInt(dgame.getNumImgs())]);
		}
	}

	void returnDice() {
		// TODO
		System.out.println("returnDice called");
		for (int i = 0; i < dgame.getNumDice(); i++) {
			while (gameDice[i].getXLoc() != gameDice[i].getStartXLoc()
					| gameDice[i].getYLoc() != gameDice[i].getStartYLoc()) {
				gameDice[i].finishThrowing();
				// xCoordinates[i] = dice[i].getXLoc();
				// yCoordinates[i] = dice[i].getYLoc();
				repaint();
				// diceImages[i] =
				// possibleDiceImgs[rand.nextInt(dgame.getNumImgs())];
			}
		}
	}

	// Timer
	public void actionPerformed(ActionEvent e) {
		// TODO
		if (numAnimations < animsToDo) {
			if (isRolled && !isAnimDone) {
				animDice();
				repaint();
				numAnimations++;
			}
		} else if (numAnimations == animsToDo) {
			isAnimDone = true;
			returnDice();
			setDiceImgs();
			repaint();
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

	public class DiceListener implements MouseInputListener {
		Point point = new Point(0, 0);

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			// if die is clicked on, isSelected = true
			point = e.getPoint();
			for (Die d : gameDice) {
				if (point.x >= d.getXLoc() && point.x <= d.getXLoc() + diceWidth && point.y >= d.getYLoc()
						&& point.y <= d.getYLoc() + diceWidth) {
					d.setSelection(true);
					isDieSelected = true;
				}
			}
			for (Die d : gameDice) {
				if (d.getSelection() == true)
					selectedDie = d;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			point = e.getPoint();
			boolean isPlaced = false;
			// if in range of storycubes
			if (selectedDie != null) {
				for(int i = 0; i < dgame.getNumDice(); i++){
				//for (int xStory : storyboardX) {
					if (point.x >= storyboardX[i] && point.x <= storyboardX[i] + diceWidth && point.y >= storyStartY
							&& point.y <= storyStartY + diceWidth) {
						selectedDie.setXLoc(storyboardX[i]);
						selectedDie.setYLoc(storyStartY);
						isPlaced = true;
						finalImages[i] = selectedDie.getDieImg();
					} 
				}
				for (int i = 0; i < dgame.getNumDice(); i++) {
					if (selectedDie != gameDice[i]) {
						if (selectedDie.getXLoc() == gameDice[i].getXLoc()
								&& selectedDie.getYLoc() == gameDice[i].getYLoc()) {
							selectedDie.setXLoc(selectedDie.getInitXLoc());
							selectedDie.setYLoc(selectedDie.getInitYLoc());
							isPlaced = false;
						}
					}
				}
				if(!isPlaced){
					selectedDie.setXLoc(selectedDie.getInitXLoc());
					selectedDie.setYLoc(selectedDie.getInitYLoc());
				}
				
				selectedDie.setSelection(false);
				isDieSelected = false;
			}
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			// update selectedDie's location to point.x and point.y
			point = e.getPoint();
			if (isDieSelected && selectedDie != null) {
				selectedDie.setXLoc(point.x - diceWidth / 2);
				selectedDie.setYLoc(point.y - diceWidth / 2);
			}
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}
}
