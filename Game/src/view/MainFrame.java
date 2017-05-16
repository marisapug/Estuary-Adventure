package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import model.MazeKeyInputs;

public class MainFrame extends JFrame {


	
	//Serial Version
	private static final long serialVersionUID = 1L;

	//Frame
	
	//what's on piazza -- will it work?
	/*static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private final static int frameWidth=(int) screenSize.getWidth();
	private final static int frameHeight=(int) screenSize.getHeight();
	*/
	
	private final static int frameWidth = 1200;
	private final static int frameHeight = 700;
	

	//Main Layout
	private BorderLayout mainLayout = new BorderLayout();

	//Card Layout
	private CardLayout cardLayout = new CardLayout();
	private JPanel cardPanel = new JPanel(cardLayout);
	CardLayout cl = (CardLayout) cardPanel.getLayout();

	//JPanels (Game Views)
	StartScreenView currStartPanel;
	MazeGameView currMazePanel;
	BeachGameView currBeachPanel;
	StoryCubeView currDicePanel;

	//JMenuBar
	private JMenuBar menuBar = new JMenuBar();

	//JMenus
	private JMenu miniGamesMenu;

	//JMenuItems
	private JMenuItem startItem;
	private JMenuItem mazeItem;
	private JMenuItem beachItem;
	private JMenuItem diceItem;

	public MainFrame(){
		
		currMazePanel = new MazeGameView();
		currStartPanel = new StartScreenView();
		currDicePanel = new StoryCubeView();
		currBeachPanel = new BeachGameView();
		
		miniGamesMenu = new JMenu("Mini Games");
		startItem = new JMenuItem("Start Screen");
		startItem.setFocusable(false);
		mazeItem = new JMenuItem("Estuary Maze Adventure");
		mazeItem.setFocusable(false);
		beachItem = new JMenuItem("Estuary Defense");
		beachItem.setFocusable(false);
		diceItem = new JMenuItem("Estuary StoryBuilder");
		diceItem.setFocusable(false);
		
//		addKeyListener(currMazePanel);
		EventQueue.invokeLater(new Runnable(){
			public void run(){
			}
		});

		this.setTitle("Estuary Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(frameWidth, frameHeight);
		this.setLayout(mainLayout);

		miniGamesMenu.add(startItem);
		miniGamesMenu.add(mazeItem);
		miniGamesMenu.add(beachItem);
		miniGamesMenu.add(diceItem);
		currStartPanel.add(miniGamesMenu);
		menuBar.add(miniGamesMenu);
		this.setupListeners();
		this.setJMenuBar(menuBar);
	
		cardPanel.add(currStartPanel, "start");
		cardPanel.add(currMazePanel, "maze");
		cardPanel.add(currBeachPanel, "beach");
		cardPanel.add(currDicePanel, "dice");
		this.add(cardPanel);	
		cl.show(cardPanel, "start");

		this.setVisible(true);
	}

	
	//Getters
	public static int getFrameWidth(){
		return frameWidth;
	}

	public static int getFrameHeight(){
		return frameHeight;
	}
	
	public void resetMaze(){
		MazeGameView newView = new MazeGameView();
		newView.requestFocusInWindow();
		currMazePanel.requestFocus(false);
		this.currMazePanel = newView;
	}
	
	public void resetBeach(){
		this.currBeachPanel = new BeachGameView();
	}
	
	public void resetDice(){
		this.currDicePanel = new StoryCubeView();
	}
	
	public void resetStart(){
		this.currStartPanel = new StartScreenView();
	}
	

	public void setupListeners(){
		startItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cardPanel.remove(currStartPanel);
				resetStart();
				cardPanel.add(currStartPanel, "start");
				cl.show(cardPanel, "start");
			}
		});

		mazeItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				currMazePanel.nameTextField.removeKeyListener(currMazePanel);
				cardPanel.remove(currMazePanel);
				currMazePanel.t.stop();
				resetMaze();
				cardPanel.add(currMazePanel, "maze");
				addKeyListener(currMazePanel);
				cl.show(cardPanel, "maze");
				currMazePanel.requestFocusInWindow();
			}
		});

		beachItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				removeKeyListener(currMazePanel);
				removeKeyListener(currBeachPanel);
				currMazePanel.nameTextField.removeKeyListener(currMazePanel);
				cardPanel.remove(currBeachPanel);
				resetBeach();
				cardPanel.add(currBeachPanel, "beach");
				addKeyListener(currBeachPanel);
				cl.show(cardPanel, "beach");
				currBeachPanel.requestFocusInWindow();
			}
		});
		
		diceItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cardPanel.remove(currDicePanel);
				resetDice();
				cardPanel.add(currDicePanel, "dice");
				cl.show(cardPanel, "dice");
				currDicePanel.requestFocusInWindow();
			}
		});
	}


}