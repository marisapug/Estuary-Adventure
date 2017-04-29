package view;

import model.Barrier;
import model.Grass;
import model.Seawall;
import model.Shore;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.ShoreCrab;

public class BeachGameView extends JPanel implements KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;
	int screenWidth = MainFrame.getFrameWidth();
	int screenHeight = MainFrame.getFrameHeight();

	JButton grassButton = new JButton("Grass");
	JButton ogButton = new JButton("Oyster Gabion");
	JButton seawallButton = new JButton("Seawall");

	BufferedImage bkg = createImage("background/B3.jpg");
	Shore shore1 = new Shore();

	ShoreCrab crabimg = new ShoreCrab(screenWidth/2, screenHeight/2-100); 
	BufferedImage crabImg = createImage("characters/lilcrab.png");
	int characterWidth = 100;
	int characterHeight = 100;
	int characterXLoc = crabimg.getXLoc();
	int characterYLoc = crabimg.getYLoc();
	int yIncr = crabimg.getXIncr();
	int xIncr = crabimg.getYIncr();
	int xVel = crabimg.getXVel();
	int yVel = crabimg.getYVel();

	public BeachGameView(){

		//Timer t = new Timer(10,this);
		//int timeRemaining = 120;
		//int timeCheck = 0;
		//t.start();

		//ImageIcon image = new ImageIcon("background/B3.jpg");
		//JLabel label = new JLabel();
		//ImageIcon bucket1 = new ImageIcon (getClass().getResource("lilcrab.png"));
		//JLabel label2 = new JLabel(bucket1, JLabel.CENTER);
		//label.setLayout(new FlowLayout());
		JLabel score = new JLabel("Score: ");
		JLabel time = new JLabel("Time: ");
		//add(label);
		add(score);
		add(time);
		add(grassButton, BorderLayout.SOUTH);
		add(ogButton, BorderLayout.SOUTH);
		add(seawallButton, BorderLayout.SOUTH);
		//label.setVisible(true);
		//label.add(label2, BorderLayout.NORTH);

		//t.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

	}

	public BufferedImage createImage(String fileName){
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			System.out.println("no");
		}
		return img;
	}


	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//g.drawImage(crabImg, crabimg.getXLoc(), crabimg.getYLoc(), characterWidth, characterHeight, this);
		//g.drawString("Time Remaining: ", timeRemainingLabelXLoc, timeRemainingLabelYLoc);
		//g.drawString(""+timeRemaining, screenWidth/2 + 120, 10);
		g.drawImage(bkg, 0,0,screenWidth,screenHeight, this);
		g.drawImage(crabImg, crabimg.getXLoc(), crabimg.getYLoc(), characterWidth, characterHeight, this);
		//renderBarriers(shore1, g, crabimg);
	}
	/*		
		public void renderBarriers(Shore s, Graphics g, ShoreCrab c){
			for (Barrier b : s.getShore()){
				if (b instanceof Grass){
					g.drawRect(c.getXLoc(),c.getYLoc(),100,100);
				}
				else if (b instanceof Seawall){
					g.drawRect(c.getXLoc(),c.getYLoc(),200,200);
				}
				else return;
			}
		}*/

	//Set Button Listeners 
	void setupListeners() {
		grassButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Grass g1 = new Grass();
				//shore1.addBarr(g1);
			}
		});
		ogButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//tbd
			}
		});
		seawallButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// tbd
				//Seawall s1 = new Seawall();
				//shore1.addBarr(s1);
			}
		});


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		if ((xVel < 0 && (crabimg.getXLoc() - crabimg.getXIncr() >= 0)) || 
				((xVel > 0) && crabimg.getXLoc() + characterWidth + crabimg.getXIncr() <= screenWidth))
			crabimg.moveHorizontal(xVel);


	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void moveCrabLeft(){
		xVel = -crabimg.getXIncr();

	}
	public void moveCrabRight(){
		xVel = crabimg.getXIncr();

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_LEFT){
			moveCrabLeft();
			crabimg.moveHorizontal(crabimg.getXVel());
		}
		if(code == KeyEvent.VK_RIGHT){
			moveCrabRight();
			crabimg.moveHorizontal(crabimg.getXVel());
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		xVel = 0;
		yVel = 0;
	}

}