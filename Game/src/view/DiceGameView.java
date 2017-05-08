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
    private int betweenDice;
    private int betweenStory = 10;
    private int diceStartX;
    private int storyStartX;
    private int diceStartY;
    private int storyStartY;
    private int storyTextX;
    private int storyTextY;
    private Font storyTextStyle;
	private int storyFontSize = screenWidth/25;
	private int maxLines = 7; //maximum number of lines the text box will fit
	private int numLines = 0;
	private String[] storyWords = {};
	private ArrayList<String> storyLines = new ArrayList<String>();
	private int[] stringYCoords = new int[maxLines];
    private boolean isRolled = false; // true if the user pressed the button to
                                        // roll
    private boolean isStorySaved = false; // true if the user pressed the button
                                            // to enter a story
    private boolean isAnimDone = false; // true if the dice have finished
                                        // "rolling"
    private boolean isSplitStoryLinesCalled = false; //to make sure the function is only called once
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

    // Mouse Listener
    DiceListener listener = new DiceListener();
    Point currPoint = listener.tmpPoint;
    private boolean toPlace = false;
    int clickedIndex;
    boolean isImgClicked = false;
    BufferedImage clickedImg;
    int releasedIndex;
    boolean canPlace = true;

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
        storyTextX = diceWidth + 30;
        storyTextY = diceWidth + 60;
        storyTextStyle = new Font("Tempus Sans ITC", Font.BOLD, storyFontSize);
        clickedImg = diceImages[0];

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
        this.setupMouseListener(listener);

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
     //   System.out.println("makeImages called");
        if (!isRolled)
            dgame.setDice();
        for (int i = 0; i < dgame.getNumImgs(); i++) {
            BufferedImage temp = createImage(imgStrings[i]);
            possibleDiceImgs[i] = temp;
        }
    }

    // Sets images to dice
    public void setDiceImgs() {
        //System.out.println("setDiceImgs called");
        for (int i = 0; i < dgame.getNumDice(); i++) {
            int temp = dgame.imgNums[i];
            diceImages[i] = possibleDiceImgs[temp];
        }
        isRolled = true;
    }

    // paintComponent
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (listener.isPressing)
            currPoint = listener.point;

        g.drawImage(oceanBackground, 0, 0, screenWidth, screenHeight, this);

        // Dice
        for (int i = 0; i < dgame.getNumDice(); i++) {
            g.drawRect(xCoordinates[i], yCoordinates[i], diceWidth, diceWidth);
            if (isRolled) {
                // Storyboard Slots
                g.drawRect(storyboardX[i], storyStartY, diceWidth, diceWidth);
                // Images
                g.drawImage(diceImages[i], xCoordinates[i], yCoordinates[i], diceWidth, diceWidth, this);
            }

            if (isStorySaved) {
				// maybe do a text box?
				g.setColor(Color.white);
				g.fillRect(diceWidth, diceWidth, screenWidth - 2 * diceWidth, screenHeight - 2 * diceWidth);

				// Story Text
				Color navy = new Color(3, 0, 130);
				g.setColor(navy);
				g.setFont(storyTextStyle);
				if(dgame.getDiceStory().length() <= 22){
					g.drawString(dgame.getDiceStory(), storyTextX, storyTextY);
				}
				else{
					storyWords = dgame.getDiceStory().split(" ");
					if(!isSplitStoryLinesCalled){
						storyLines = splitStoryLines();
						numLines = storyLines.size();
						if(numLines > maxLines){
							numLines = maxLines;
						}
						isSplitStoryLinesCalled = true;
					}
					for (int j = 0; j < numLines; j++){
						g.drawString(storyLines.get(j), storyTextX, stringYCoords[j] );		
					}
				}
            }
        }
    }

    // Rolls Dice and Sets Images
    void rollDice() {
       // System.out.println("rollDice called");
        makeImages();
        animDice();
        isRolled = true;
        repaint();
    }

    void saveStory() {
        dgame.diceStory = storyText.getText();
        isStorySaved = true;
    }
    
  //Splits strings of story into lines
  	ArrayList<String> splitStoryLines(){ //iterate through story words, adding a new fragment to arraylist 
  		// every time the words are almost 22 characters
  		int currWord = 0;
  		ArrayList<String> lines = new ArrayList<String>();
  		stringYCoords[0] = storyTextY;
  		System.out.println(storyTextY);
  		for(int k = 1; k < maxLines; k++){
  			stringYCoords[k] = 0;
  		}
  		int yCoordIndex = 0;
  		while(currWord < storyWords.length){
  			String fragment = "";
  			int j = currWord;
  			boolean lineOver = false;
  			int numChars = storyWords[currWord].length();
  			while(j < storyWords.length && !lineOver){
  				numChars += storyWords[j].length();
  				fragment = fragment + storyWords[j] + " ";
  				if(j < (storyWords.length - 1) && numChars + storyWords[j+1].length() > 22){
  					if(stringYCoords[yCoordIndex] == 0)
  						stringYCoords[yCoordIndex] = stringYCoords[yCoordIndex - 1] + 50;
  					lineOver = true;
  					yCoordIndex++;
  					lines.add(fragment);
  				}
  				else if(j == (storyWords.length - 1) && yCoordIndex > 0){
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
        rollDiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDice(); // roll dice function
                rollDiceButton.setVisible(false);
            }
        });
        storyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        } else if (numAnimations == animsToDo) {
            isAnimDone = true;
            //System.out.println("setDiceImgs called after animation");
            setDiceImgs();
            repaint();
            storyButton.setVisible(true);
            storyText.setVisible(true);
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
           // System.out.println("mouseClicked called");
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
           // System.out.println("mousePressed called");
            if (isRolled) {
                tmpPoint = e.getPoint();
                toPlace = true;
                for (int i = 0; i < dgame.getNumDice(); i++) {
                    if (tmpPoint.x > xCoordinates[i] && tmpPoint.x < (xCoordinates[i] + diceWidth)) {
                        if (tmpPoint.y > yCoordinates[i] && tmpPoint.y < (yCoordinates[i] + diceWidth)) {
                            clickedIndex = i;
                        }
                    }
                }
                xCoordinates[clickedIndex] = tmpPoint.x - diceWidth / 2;
                yCoordinates[clickedIndex] = tmpPoint.y - diceWidth / 2;
                if (!isPressing)
                    isPressing = true;
                // tmpPoint = e.getPoint();
            }
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
           // System.out.println("mouseReleased called");
            isPressing = false;
            toPlace = false;
            for (int i = 0; i < dgame.getNumDice(); i++) {
                if (tmpPoint.x > storyboardX[i] && tmpPoint.x < (storyboardX[i] + diceWidth)) {
                    if (tmpPoint.y > storyStartY && tmpPoint.y < (storyStartY + diceWidth)) {
                        releasedIndex = i;
                    }
                }
            }
            for (int i = 0; i < dgame.getNumDice(); i++) {
                if (storyboardX[releasedIndex] == xCoordinates[i] && storyStartY == yCoordinates[i]) {
                    canPlace = false;
                }
            }
            if (canPlace) {
                xCoordinates[clickedIndex] = storyboardX[releasedIndex];
                yCoordinates[clickedIndex] = storyStartY;
            }
            canPlace = true;
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
          //  System.out.println("mousePressed called");
        }

        @Override
        public void mouseExited(MouseEvent e) {
         //   System.out.println("mouseExited called");
        }

        @Override
        public void mouseDragged(MouseEvent e) {
          //  System.out.println("mouseDragged called");
            tmpPoint = e.getPoint();
            if (toPlace) {
                xCoordinates[clickedIndex] = tmpPoint.x - diceWidth / 2;
                yCoordinates[clickedIndex] = tmpPoint.y - diceWidth / 2;
            }
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
           // System.out.println("mouseMoved called");
            tmpPoint = e.getPoint();
            if (toPlace) {
                xCoordinates[clickedIndex] = tmpPoint.x - diceWidth / 2;
                yCoordinates[clickedIndex] = tmpPoint.y - diceWidth / 2;
            }
            repaint();
        }

    }
}