package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainFrame extends JFrame {


	
	//Serial Version
	private static final long serialVersionUID = 1L;

	//Frame
	private final static int frameWidth = 900;
	private final static int frameHeight = 500;

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
	DiceGameView currDicePanel;

	//JMenuBar
	private JMenuBar menuBar = new JMenuBar();

	//JMenus
	private JMenu miniGamesMenu = new JMenu("Mini Games");

	//JMenuItems
	private JMenuItem startItem = new JMenuItem("Start Screen");
	private JMenuItem mazeItem = new JMenuItem("Maze Game");
	private JMenuItem beachItem = new JMenuItem("Estuary Defense Game");
	private JMenuItem diceItem = new JMenuItem("Dice Game");

	public MainFrame(){
		
		currMazePanel = new MazeGameView();
		currStartPanel = new StartScreenView();
		currDicePanel = new DiceGameView();
		currBeachPanel = new BeachGameView();
		
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
		this.add(cardPanel, BorderLayout.CENTER);	
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
		this.currMazePanel = new MazeGameView();
	}
	
	public void resetBeach(){
		this.currBeachPanel = new BeachGameView();
	}
	
	public void resetDice(){
		this.currDicePanel = new DiceGameView();
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
				
				cardPanel.remove(currMazePanel);
				resetMaze();
				addKeyListener(currMazePanel);
				cardPanel.add(currMazePanel, "maze");
				cl.show(cardPanel, "maze");
			}
		});

		beachItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cardPanel.remove(currBeachPanel);
				resetBeach();
				addKeyListener(currBeachPanel);
				cardPanel.add(currBeachPanel, "beach");
				cl.show(cardPanel, "beach");
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
			}
		});
	}


}