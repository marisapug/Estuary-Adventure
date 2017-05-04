package view;

import java.util.Random;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;
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
	private int betweenDice;
	private int betweenStory = 10;
	private int diceStartX;
	private int storyStartX;
	private int diceStartY;
	private int storyStartY;
	private int storyTextX = 50;
	private int storyTextY;
	private boolean isRolled = false; // true if the user pressed the button to
										// roll
	private boolean isStorySaved = false; // true if the user pressed the button
											// to enter a story
	private boolean isAnimDone = false; // true if the dice have finished
										// "rolling"
	private String imgStrings[] = { "diceimages/apple.png", "diceimages/banana.png", "diceimages/beaker.png",
			"diceimages/box.png", "diceimages/bucket.png", "diceimages/can.png", "diceimages/canwithwings.png",
			"diceimages/chipBag.png", "diceimages/cleanvessel.png", "diceimages/clipboard.png", "diceimages/clock.png",
			"diceimages/crabfoodpile.png", "diceimages/crabtrap.png", "diceimages/crumbledpaper.png",
			"diceimages/crushedcan.png", "diceimages/deadfish.png", "diceimages/dirtyvessel.png",
			"diceimages/dogpoopbag.png", "diceimages/fishtag.png", "diceimages/flagFull.png" };
	private BufferedImage[] possibleDiceImgs;
	private BufferedImage[] diceImages;
	private int[] xCoordinates;
	private int[] yCoordinates;
	private int[] storyboardX;

	// Dice Rolling Animation
	int numAnimations = 0;
	int animsToDo = 50;

	// Buttons
	private JButton rollDiceButton;
	private JButton storyButton;

	// TextFields
	JTextField storyText;

	// Images
	BufferedImage oceanBackground = createImage("background/dicebackground.jpg");

	// Constructor
	public DiceGameView() {

		dgame = new DiceGame();

		possibleDiceImgs = new BufferedImage[dgame.getNumImgs()];
		diceImages = new BufferedImage[dgame.getNumDice()];
		xCoordinates = new int[dgame.getNumDice()];
		yCoordinates = new int[dgame.getNumDice()];
		storyboardX = new int[dgame.getNumDice()];

		betweenDice = diceWidth + 10;
		diceStartX = (screenWidth - (dgame.getNumDice() / 2 * diceWidth + (dgame.getNumDice() / 2 - 1) * betweenDice))
				/ 2;
		storyStartX = (screenWidth - (dgame.getNumDice() * diceWidth + (dgame.getNumDice() - 1) * betweenStory)) / 2;
		diceStartY = (screenHeight - (3 * diceWidth + 2 * betweenStory)) / 2;
		storyStartY = (screenHeight - diceWidth) / 2;
		storyTextY = screenHeight - 2 * diceStartY / 3;

		rollDiceButton = new JButton("Roll Dice");
		storyButton = new JButton("Submit Story");
		rollDiceButton.setFocusable(false);
		storyButton.setFocusable(false);
		storyButton.setVisible(false);

		storyText = new JTextField("Enter Story Here");
		storyText.setVisible(false);

		this.add(rollDiceButton);
		this.add(storyText);
		this.add(storyButton);
		this.setupListeners();

		initializeCoordinates();
		diceTimer.start();
	}

	// Getters
	public int getScreenWidth() {
		return this.screenWidth;
	}

	public int getScreenHeight() {
		return this.screenHeight;
	}

	// Set Initial Coordinates for Images
	// TODO
	public void initializeCoordinates() {
		for (int i = 0; i < dgame.getNumDice(); i++) {
			if (i < 3) {
				xCoordinates[i] = diceStartX + (diceWidth + betweenDice) * i - (diceWidth + betweenDice) / 2;
				yCoordinates[i] = diceStartY;
			} else {
				xCoordinates[i] = diceStartX + (diceWidth + betweenDice) * (i % (dgame.getNumDice() / 2));
				yCoordinates[i] = screenHeight - diceStartY - diceWidth;
			}
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
		System.out.println("makeImages called");
		if (!isRolled)
			dgame.setDice();
		for (int i = 0; i < dgame.getNumImgs(); i++) {
			BufferedImage temp = createImage(imgStrings[i]);
			possibleDiceImgs[i] = temp;
		}
	}

	// Sets images to dice
	public void setDiceImgs() {
		System.out.println("setDiceImgs called");
		for (int i = 0; i < dgame.getNumDice(); i++) {
			int temp = dgame.imgNums[i];
			diceImages[i] = possibleDiceImgs[temp];
		}
		isRolled = true;
	}

	// paintComponent
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(oceanBackground, 0, 0, screenWidth, screenHeight, this);

		// Dice
		for (int i = 0; i < dgame.getNumDice(); i++) {
			g.drawRect(xCoordinates[i], yCoordinates[i], diceWidth, diceWidth);
			if (isRolled) {
				if (isAnimDone) {
					System.out.println("animation done");
					Random myRand = new Random();
					dgame.setAnimNum(myRand.nextInt(dgame.getNumImgs()));
				} else {
					System.out.println("rolling done");
					dgame.setAnimNum(dgame.imgNums[i]);
				}
				// Storyboard Slots
				g.drawRect(storyboardX[i], storyStartY, diceWidth, diceWidth);
				// Images
				g.drawImage(diceImages[i], xCoordinates[i], yCoordinates[i], diceWidth, diceWidth, this);
			}
			if (isStorySaved) {
				// Story Text
				g.drawString(dgame.getDiceStory(), storyTextX, storyTextY);
			} // if
		} // for loop

		// testing
		g.drawString(".", screenWidth, screenHeight);
	}

	// Rolls Dice and Sets Images
	void rollDice() {
		System.out.println("rollDice called");
		makeImages();
		animDice();
		isRolled = true;
		repaint();
	}

	void saveStory() {
		dgame.diceStory = storyText.getText();
		isStorySaved = true;
	}

	// Set Button Listeners
	void setupListeners() {
		rollDiceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rollDice(); // roll dice function
				rollDiceButton.setVisible(false);
			}
		});
		storyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				repaint(); // print story function
				saveStory(); // save story function
			}
		});
	}

	void animDice() {
		Random rand = new Random();
		for (int i = 0; i < dgame.getNumDice(); i++) {
			diceImages[i] = possibleDiceImgs[rand.nextInt(dgame.getNumImgs())];
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (numAnimations < animsToDo) {
			if (isRolled && !isAnimDone) {
				animDice();
				repaint();
				numAnimations++;
			}
		} else {
			isAnimDone = true;
			System.out.println("setDiceImgs called after animation");
			setDiceImgs();
			repaint();
			storyButton.setVisible(true);
			storyText.setVisible(true);
		}
	}

}
