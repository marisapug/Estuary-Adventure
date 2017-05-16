package view;

import model.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class StoryCubeView extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

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
	private int dicePlaced = 0;

	// Story Slots
	private int[] storyboardX;
	private int storyStartX;
	private int storyStartY;
	private int betweenStory = 10;

	// Dice Rolling Animation
	int numAnimations = 0;
	int animsToDo = 30;
	boolean isAnimDone = false;

	// Game Booleans
	private boolean isRolled = false;
	private boolean isDieSelected = false;
	private boolean canSaveStory = true;
	private boolean isDialogUp = false;

	// Buttons
	private JButton startGameButton;
	private JButton rollDiceButton;
	private JButton helpButton;
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
	private Font errorTextStyle;
	Color errorTextColor;
	private int errorFontSize = screenWidth / 40;
	private boolean isStorySaved = false;
	private int storyTextX;
	private int storyTextY;
	private ArrayList<String> storyWords = new ArrayList<String>();
	private ArrayList<String> storyLines = new ArrayList<String>();
	private int numLines = 0;
	private int maxLines = 7; // maximum number of lines the text box will fit
	private int numCharsOnLine;
	private int[] stringYCoords = new int[maxLines];
	private boolean isStoryShowing = false;
	private int diceLength;
    private int diceSpacePixels;
	private int diceSpaceWidth;
	private String diceSpaceString;

	// TextArea
	JTextArea storyText;
	String storyString;
	
	// Text Panes
	private JTextPane storyPane;
	private Color storyBackground;
	private Color storyTextColor;
	private int storyFontSize = screenWidth / 40;
	private Font storyTextStyle;

	private JTextPane instructPane;
	private Color instructTextColor;
	private Color instructBackground;
	private int instructFontSize;
	private Font instructFont;
	private Border instructBorder;

	// Images
	BufferedImage oceanBackground = createImage("background/dicebackground.jpg");
	BufferedImage startScreen = createImage("background/dicestartscreen.jpg");
	private String imgStrings[] = { "diceimages/bananadie.png", "diceimages/bassdie.png", "diceimages/bluecrabdie.png",
			"diceimages/bogturtledie.png", "diceimages/candie.png", "diceimages/cattailsdie.png",
			"diceimages/chipbagdie.png", "diceimages/clamdie.png", "diceimages/cleanvesseldie.png",
			"diceimages/crabscientistdie.png", "diceimages/deadfishdie.png", "diceimages/fisheggsdie.png",
			"diceimages/fishermandie.png", "diceimages/fishgroupdie.png", "diceimages/mittencrabsdie.png",
			"diceimages/oilspilldie.png", "diceimages/seagrassdie.png", "diceimages/seamonsterdie.png",
			"diceimages/toxicclouddie.png", "diceimages/troutdie.png"};

	private BufferedImage[] possibleDiceImgs;

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
		dgame = new DiceGame(screenWidth, screenHeight, diceWidth);

		storyStartY = (screenHeight - diceWidth) / 2;
		storyStartX = (screenWidth - (dgame.getNumDice() * diceWidth + (dgame.getNumDice() - 1) * betweenStory)) / 2;
		storyTextX = diceWidth + 30;
		storyTextY = diceWidth + 60;

		// Initialize Button Appearance
		buttonFont = new Font(titleFont, Font.BOLD, buttonFontSize);
		storyTextStyle = new Font("Verdana", Font.PLAIN, storyFontSize);
		errorTextStyle = new Font("Verdana", Font.PLAIN, errorFontSize);
		startButtonSize = new Dimension(startButtonSizeX, startButtonSizeY);
		buttonSize = new Dimension(buttonSizeX, buttonSizeY);
		showStoryButton = false;

		// Initialize Arrays
		possibleDiceImgs = new BufferedImage[dgame.getNumImgs()];
		storyboardX = new int[dgame.getNumDice()];
		gameDice = dgame.getDice();

		// Buttons
		startGameButton = new JButton("Start Game");
		startGameButton.setFocusable(false);
		startGameButton.setFont(buttonFont);
		startGameButton.setPreferredSize(startButtonSize);
		startGameButton.setBackground(Color.black);
		startGameButton.setForeground(Color.white);

		rollDiceButton = new JButton("Roll Dice");
		rollDiceButton.setFocusable(false);
		rollDiceButton.setVisible(false);
		rollDiceButton.setFont(buttonFont);
		rollDiceButton.setPreferredSize(startButtonSize);
		rollDiceButton.setBackground(Color.black);
		rollDiceButton.setForeground(Color.white);
		
		helpButton = new JButton("HELP");
		helpButton.setFocusable(false);
		helpButton.setVisible(false);
		helpButton.setFont(buttonFont);
		helpButton.setPreferredSize(startButtonSize);
		helpButton.setBackground(Color.red);
		helpButton.setForeground(Color.white);

		storyButton = new JButton("Submit");
		storyButton.setFocusable(false);
		storyButton.setVisible(false);
		storyButton.setFont(buttonFont);
		storyButton.setPreferredSize(buttonSize);
		storyButton.setBackground(Color.black);
		storyButton.setForeground(Color.white);

		rollAgainButton = new JButton("Roll Again");
		rollAgainButton.setFocusable(false);
		rollAgainButton.setVisible(false);
		rollAgainButton.setFont(buttonFont);
		rollAgainButton.setPreferredSize(startButtonSize);
		rollAgainButton.setBackground(Color.black);
		rollAgainButton.setForeground(Color.white);

		// Text Fields
		storyText = new JTextArea("Enter Story Here");
		storyText.setLineWrap(true);
		storyText.setVisible(false);
		storyText.setPreferredSize(new Dimension(400, 96));
		storyText.setFont(new Font(titleFont, Font.PLAIN, 16));
		numCharsOnLine = screenWidth / 28;
		diceLength = diceWidth * dgame.numDice;
        diceSpacePixels = ((screenWidth - 2 * diceWidth) - diceLength) / 2 ;
		diceSpaceWidth = 0;
		diceSpaceString = "";

		// Add Buttons
		this.add(startGameButton);
		this.add(rollDiceButton);
		this.add(helpButton);
		this.add(storyText);
		this.add(storyButton);
		this.add(rollAgainButton);
		this.setupListeners();

		// Final Text Display
		storyBackground = new Color(136, 191, 246);
		storyTextColor = new Color(3, 0, 130);
		storyPane = new JTextPane();
		this.add(storyPane);
		storyPane.setVisible(false);
		
		// Game Instructions
		instructBackground = new Color(194, 238, 231);
		instructTextColor = Color.BLACK;
		instructFontSize = screenWidth / 30;
		instructFont = new Font("Verdana", Font.PLAIN, instructFontSize);
		instructBorder = new LineBorder(Color.black, 1);
		instructPane = new JTextPane();
		this.add(instructPane);
		instructPane.setVisible(false);
		makeInstructionPane();

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
			if (!isStoryShowing) {
				for (int i = 0; i < dgame.getNumDice(); i++) {
					if (isRolled) {
						g.drawImage(gameDice[i].getDieImg(), gameDice[i].getXLoc(), gameDice[i].getYLoc(), diceWidth,
								diceWidth, this); // draws
						// images
						if (isAnimDone) {
							// slots to drag dice into
							g.drawRect(storyboardX[i], storyStartY, diceWidth, diceWidth); // draws
							if (showStoryButton) {
								storyButton.setVisible(true);
								storyText.setVisible(true);
							}
						}
					}
				}
				if (isStorySaved) {
					// Color storyBackground = new Color(136, 191, 246);

					// Story Text
					if (canSaveStory) {
						saveStory();
						diceSpaceWidth = g.getFontMetrics(storyTextStyle).stringWidth(diceSpaceString);
						while(diceSpaceWidth < diceSpacePixels){
							diceSpaceString += " ";
							diceSpaceWidth = g.getFontMetrics(storyTextStyle).stringWidth(diceSpaceString);
						}
						if(diceSpaceWidth >= diceSpacePixels){
							makeStory(storyPane, dgame.getDiceStory(), diceSpaceString, storyTextColor);
							isStoryShowing = true;
						}

					} else if(isDialogUp){
						//TODO change text to make bigger and better
						g.drawString("Inappropriate language detected! Please edit your story so it doesn't include this word: " + dgame.getCurseWord(), titleX, titleY);
						isStoryShowing = false;
						isRolled = false;
                        storyPane.setVisible(false);
					}

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
		// if !iscurseword
		dgame.setDiceStory(storyText.getText());
		dgame.separateStory(dgame.diceStory);
		if (dgame.isCurseWord()) {
			canSaveStory = false;
			isDialogUp = true;
		} else{
			canSaveStory = true;
		}
		isStorySaved = true;
	}

	// Add Story to Text Pane
	 public void makeStory(JTextPane pane, String text, String diceSpace, Color color) {
	        StyledDocument doc = pane.getStyledDocument();
	        
	        pane.setBounds(diceWidth, diceWidth, screenWidth - 2 * diceWidth, (screenHeight - (2 * diceWidth)));
	        pane.setPreferredSize(new Dimension(screenWidth - 2 * diceWidth, screenHeight - (2 * diceWidth)));
	        pane.setFont(storyTextStyle);
	        pane.setBackground(storyBackground);
	        pane.setEditable(false);
	        
	        // add dice cubes to text pane
	        ImageIcon[] sizedIcons = new ImageIcon[dgame.getNumDice()];
	        for (Die d: gameDice){
	        	if(d.getStoryIndex() == 0)
	        		sizedIcons[0] = new ImageIcon(d.getDieImg().getScaledInstance(diceWidth, diceWidth, Image.SCALE_DEFAULT));
	        	else if(d.getStoryIndex() == 1)
	        		sizedIcons[1] = new ImageIcon(d.getDieImg().getScaledInstance(diceWidth, diceWidth, Image.SCALE_DEFAULT));
	        	else if(d.getStoryIndex() == 2)
	        		sizedIcons[2] = new ImageIcon(d.getDieImg().getScaledInstance(diceWidth, diceWidth, Image.SCALE_DEFAULT));
	        	else if(d.getStoryIndex() == 3)
	        		sizedIcons[3] = new ImageIcon(d.getDieImg().getScaledInstance(diceWidth, diceWidth, Image.SCALE_DEFAULT));
	        	else if(d.getStoryIndex() == 4)
	        		sizedIcons[4] = new ImageIcon(d.getDieImg().getScaledInstance(diceWidth, diceWidth, Image.SCALE_DEFAULT));
	        }
	        
	        /*(int i = 0; i < dgame.getNumDice(); i++) {
	            sizedIcons[i] = new ImageIcon(
	                    gameDice[i].getDieImg().getScaledInstance(diceWidth, diceWidth, Image.SCALE_DEFAULT));
	        }*/
	        
	        
	        Style style = pane.addStyle("Color Style", null);
	        StyleConstants.setForeground(style, color);
	        try {
	        	doc.insertString(doc.getLength(), diceSpace, style);
	        	for (ImageIcon icon : sizedIcons) {
	                pane.insertIcon(icon);
	            }
	        	/*pane.insertIcon(sizedIcon0);
		        pane.insertIcon(sizedIcon1);
		        pane.insertIcon(sizedIcon2);
		        pane.insertIcon(sizedIcon3);
		        pane.insertIcon(sizedIcon4);*/
		        doc.insertString(doc.getLength(), "\n", style);
	            doc.insertString(doc.getLength(), text, style);
	        } 
	        catch (BadLocationException e) {
	            e.printStackTrace();
	        } 
	 }

	 // Set up Instructions Text Pane
	 public void makeInstructionPane() {
		 StyledDocument doc = instructPane.getStyledDocument();

		 instructPane.setBounds(diceWidth, diceWidth, (screenWidth - 2 * diceWidth) - (diceWidth / 2), (screenHeight - (2 * diceWidth)));
		 instructPane.setPreferredSize(new Dimension(screenWidth - 2 * diceWidth, screenHeight - (2 * diceWidth)));
		 instructPane.setFont(instructFont);
		 instructPane.setBackground(instructBackground);
		 instructPane.setBorder(instructBorder);
		 instructPane.setEditable(false);

		 Style style = instructPane.addStyle("Color Style", null);
		 StyleConstants.setForeground(style, Color.black);
		 StyleConstants.setBold(style, true);
		 StyleConstants.setFontSize(style, screenWidth / 25);
		 try {
			 doc.insertString(doc.getLength(), "  Estuary Story Cubes Instructions", style);
			 doc.insertString(doc.getLength(), "\n", style);
		 } 
		 catch (BadLocationException e) {
			 e.printStackTrace();
		 }
		 StyleConstants.setBold(style, false);
		 StyleConstants.setFontSize(style, screenWidth / 35);
		 try {
			 doc.insertString(doc.getLength(), "\n  Roll the dice, then let the icons inspire you! Starting \n  with 'Once "
			 		+ "upon a time...', pick the icon that catches \n  your eye first. As you create a story that links "
			 		+ "all the \n  icons together, click and drag each die into a box in \n  the order they appear in your "
			 		+ "story. When you're all \n  done, type your estuary adventure story in the box!", style);	
		 } 
		 catch (BadLocationException e) {
			 e.printStackTrace();
		 } 
		 StyleConstants.setFontSize(style, screenWidth / 55);
		 try {
			 doc.insertString(doc.getLength(), "\n \n \n                                                 -click HELP again to exit-", style);	
		 } 
		 catch (BadLocationException e) {
			 e.printStackTrace();
		 } 
	 }
		 
	void setupListeners() {
		startGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setupMouseListener(listener);
				rollDiceButton.setVisible(true);
				startGameButton.setVisible(false);
				isStartScreenVisible = false;

				// Story Stuff
				try {
					dgame.readCurseWordsFromFile();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				repaint();
				diceTimer.start();
			}
		});
		rollDiceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rollDice(); 
				rollDiceButton.setVisible(false);
				//rollAgainButton.setVisible(true);
				helpButton.setVisible(true);
				repaint();
			}
		});
		helpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!instructPane.isVisible())
					instructPane.setVisible(true);
				else
					instructPane.setVisible(false);
				repaint();
			}
		});
		storyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveStory(); // save story function showStoryButton = false;
				repaint();
				storyButton.setVisible(false);
				storyText.setVisible(false);
				rollAgainButton.setVisible(true);
				showStoryButton = false;
				storyPane.setVisible(true);

				repaint(); // print story function

			}
		});
		rollAgainButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isDialogUp = false;
				rollAgainButton.setVisible(false);
				isRolled = false;
				isStorySaved = false;
				numAnimations = 0;
				dicePlaced = 0;
				isAnimDone = false;
				isStoryShowing = false;
				storyPane.setVisible(false);
				dgame.setDice();
				rollDice();
				repaint();
				storyPane.setText("");

			}
		});
	}

	void animDice() {
		Random rand = new Random();
		if (!isAnimDone) {
			for (int i = 0; i < dgame.getNumDice(); i++) {
				gameDice[i].throwDie();
				gameDice[i].setDieImg(possibleDiceImgs[rand.nextInt(dgame.getNumImgs())]);
			}
		}
	}

	// Timer
	public void actionPerformed(ActionEvent e) {
		if (numAnimations < animsToDo) {
			if (isRolled && !isAnimDone) {
				animDice();
				repaint();
				numAnimations++;
			}
		} else if (numAnimations == animsToDo && !isAnimDone) {
			isAnimDone = true;
			animDice();
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
		storyText.addMouseListener(listener);
		//instructPane.addMouseListener(listener);
	}

	public class DiceListener implements MouseInputListener {
		Point point = new Point(0, 0);

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() == storyText)
				storyText.setText("");
		//	else if(e.getSource() == instructPane)
		//		instructPane.setVisible(false);
		}

		@Override
		public void mousePressed(MouseEvent e) {
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
			for (int sbx : storyboardX) {
				if (point.x >= sbx && point.x <= sbx + diceWidth && point.y >= storyStartX
						&& point.y <= storyStartY + diceWidth && isDieSelected) {
					dicePlaced--;
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			point = e.getPoint();
			boolean isPlaced = false;
			if (isDieSelected) {
				if (selectedDie != null) {
					for (int i = 0; i < dgame.getNumDice(); i++) {
						// for (int xStory : storyboardX) {
						if (point.x >= storyboardX[i] && point.x <= storyboardX[i] + diceWidth && point.y >= storyStartY
								&& point.y <= storyStartY + diceWidth) {
							selectedDie.setXLoc(storyboardX[i]);
							selectedDie.setYLoc(storyStartY);
							isPlaced = true;
							selectedDie.setStoryIndex(i);
							dicePlaced++;
						}
					}
					for (int i = 0; i < dgame.getNumDice(); i++) {
						if (selectedDie != gameDice[i]) {
							if (selectedDie.getXLoc() == gameDice[i].getXLoc()
									&& selectedDie.getYLoc() == gameDice[i].getYLoc()) {
								selectedDie.setXLoc(selectedDie.getInitXLoc());
								selectedDie.setYLoc(selectedDie.getInitYLoc());
								isPlaced = false;
								dicePlaced--;
							}
						}
					}
					if (!isPlaced) {
						selectedDie.setXLoc(selectedDie.getInitXLoc());
						selectedDie.setYLoc(selectedDie.getInitYLoc());
					}

					selectedDie.setSelection(false);
					isDieSelected = false;
				}
			}
			if (dicePlaced == dgame.getNumDice() && !isStoryShowing) {
				showStoryButton = true;
			}
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mouseDragged(MouseEvent e) {
			point = e.getPoint();
			if (isDieSelected && selectedDie != null) {
				selectedDie.setXLoc(point.x - diceWidth / 2);
				selectedDie.setYLoc(point.y - diceWidth / 2);
			}
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {}
	}
}