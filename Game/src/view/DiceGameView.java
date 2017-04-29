package view;

import java.util.Random;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import model.*;

public class DiceGameView extends JPanel {

	private static final long serialVersionUID = 1L;
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
			"diceimages/dogpoopbag.png", "diceimages/fishtag.png", "diceimages.flagFull.png" };
	private BufferedImage[] possibleDiceImgs;
	private BufferedImage[] diceImages;

	// Buttons
	private JButton rollDiceButton;
	private JButton storyButton;

	// TextFields
	JTextField storyText;

	// Images
	BufferedImage oceanBackground = createImage("background/dicebackground.jpg");

	// dgame.diceStory = storyText.getText();

	// Constructor
	public DiceGameView() {

		dgame = new DiceGame();

		possibleDiceImgs = new BufferedImage[dgame.getNumImgs()];
		diceImages = new BufferedImage[dgame.getNumDice()];

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

		storyText = new JTextField("Enter Story Here");

		this.add(rollDiceButton);
		this.add(storyText);
		this.add(storyButton);
		this.setupListeners();
		
		//makeImages();
	}

	// Getters
	public int getScreenWidth() {
		return this.screenWidth;
	}

	public int getScreenHeight() {
		return this.screenHeight;
	}

	public BufferedImage createImage(String fileName) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	/*
	// Creates all possible images
	public void makeImages() {
		for (int i = 0; i < dgame.getNumImgs(); i++) {
			BufferedImage temp = createImage(imgStrings[i]);
			possibleDiceImgs[i] = temp;
		}
	}
	*/

	// Sets images to dice
	public void setDiceImgs() {
		
	}

	// paintComponent
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(oceanBackground, 0, 0, screenWidth, screenHeight, this);

		// Dice
		for (int i = 0; i < dgame.getNumDice(); i++) {
			if (i < 3) {
				g.drawRect(diceStartX + (diceWidth + betweenDice) * i - (diceWidth + betweenDice) / 2, diceStartY,
						diceWidth, diceWidth);
			} else {
				g.drawRect(diceStartX + (diceWidth + betweenDice) * (i % (dgame.getNumDice() / 2)),
						screenHeight - diceStartY - diceWidth, diceWidth, diceWidth);
			}
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
				g.drawRect(storyStartX + (diceWidth + betweenStory) * i, storyStartY, diceWidth, diceWidth);
				// Numbers -- to be images
				if (i < 3)
					g.drawString("" + dgame.getAnimNum(),
							diceStartX + diceWidth / 2 + (diceWidth + betweenDice) * i - (diceWidth + betweenDice) / 2,
							diceStartY + diceWidth / 2);
				else
					g.drawString("" + dgame.getAnimNum(),
							diceStartX + (diceWidth + betweenDice) * (i % (dgame.getNumDice() / 2)) + diceWidth / 2,
							screenHeight - diceStartY - diceWidth + diceWidth / 2);
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
		System.out.println("about to animate dice");
		// animateDice();
		System.out.println("animation done");
		isAnimDone = true;
		System.out.println("boolean set");
		dgame.setDice();
		isRolled = true;
		repaint();
	}

	void saveStory() {
		dgame.diceStory = storyText.getText();
		isStorySaved = true;
	}

	/*
	 * public void animateDice(){ Random rand = new Random(); int animsDone = 0;
	 * Timer timer = new Timer(50, new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent ae){ int animsToDo =
	 * 100; //if(!isAnimDone && isRolled){ System.out.println("timer");
	 * dgame.setAnimNum(rand.nextInt(dgame.getNumImgs())); repaint(); } });
	 * timer.start(); }
	 */

	/*
	 * void animateDice(){ for(int i = 0; i < 200000; i++){ repaint(); }
	 * isAnimDone = true; }
	 */

	// Set Button Listeners
	void setupListeners() {
		rollDiceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rollDice(); // roll dice function
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

}
