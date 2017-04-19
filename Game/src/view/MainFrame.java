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
	private final static int frameWidth = 800;
	private final static int frameHeight = 600;

	//Main Layout
	private BorderLayout mainLayout = new BorderLayout();

	//Card Layout
	private CardLayout cardLayout = new CardLayout();
	private JPanel cardPanel = new JPanel(cardLayout);
	CardLayout cl = (CardLayout) cardPanel.getLayout();

	//JPanels (Game Views)
	StartScreenView startPanel = new StartScreenView();
	MazeGameView mazePanel = new MazeGameView();
	BeachGameView beachPanel = new BeachGameView();
	DiceGameView dicePanel = new DiceGameView();

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
		addKeyListener(mazePanel);
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
		startPanel.add(miniGamesMenu);
		menuBar.add(miniGamesMenu);
		this.setupListeners();
		this.setJMenuBar(menuBar);
	
		cardPanel.add(startPanel, "start");
		cardPanel.add(mazePanel, "maze");
		cardPanel.add(beachPanel, "beach");
		cardPanel.add(dicePanel, "dice");
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

	public void setupListeners(){
		startItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cl.show(cardPanel, "start");
			}
		});

		mazeItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cl.show(cardPanel, "maze");
			}
		});

		beachItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cl.show(cardPanel, "beach");
			}
		});
		
		diceItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cl.show(cardPanel, "dice");
			}
		});
	}


}