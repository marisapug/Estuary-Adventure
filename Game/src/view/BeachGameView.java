package view;

import model.Barrier;
import model.BeachBoard;
import model.BeachCell;
import model.Grass;
import model.Seawall;
import model.Shore;

import java.awt.BorderLayout;
import java.awt.Color;
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
	boolean check = false;
	boolean grassCheck = false;
	boolean oyCheck = false;
	boolean wallCheck = false;
	JButton grassButton = new JButton("Grass");
	JButton ogButton = new JButton("Oyster Gabion");
	JButton seawallButton = new JButton("Seawall");

	BeachBoard board = new BeachBoard(10, 10, screenWidth, screenHeight);
	Shore shore1 = new Shore();

	ShoreCrab crabimg = new ShoreCrab(screenWidth/2, screenHeight/2-100); 
	BufferedImage crabImg = createImage("characters/lilcrab.png");
	BufferedImage grassImg = createImage("characters/unnamed-1.png");
	BufferedImage wallImg = createImage("characters/seawall.png");
	int characterWidth = 100;
	int characterHeight = 100;
	int characterXLoc = crabimg.getXLoc();
	int characterYLoc = crabimg.getYLoc();
	int yIncr = crabimg.getXIncr();
	int xIncr = crabimg.getYIncr();
	int xVel = crabimg.getXVel();
	int yVel = crabimg.getYVel();
	int timeRemaining = 120;
	int timeCheck = 0;

	public BeachGameView(){

		Timer t = new Timer(10,this);
		t.start();
		Shore s1 = new Shore();
		
		grassButton.setFocusable(false);
		ogButton.setFocusable(false);
		seawallButton.setFocusable(false);

		JLabel score = new JLabel("Score: ");
		JLabel time = new JLabel("Time: ");
		add(score);
		add(time);
		add(grassButton, BorderLayout.SOUTH);
		add(ogButton, BorderLayout.SOUTH);
		add(seawallButton, BorderLayout.SOUTH);
		setupListeners();
		
		

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
		
		//drawing BeachBoard
		for(int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){
				BeachCell tmp = board.getGrid()[i][j];
				if(tmp.getHeight() * j > crabimg.getYLoc())
					g.setColor(Color.yellow);
				else if(tmp.getHeight() * j < (screenHeight / 16))
					g.setColor(Color.cyan);
				else
					g.setColor(Color.blue);
				g.fillRect(tmp.getWidth() * i, tmp.getHeight() * j, tmp.getWidth(), tmp.getHeight());
			}
		}
		
		g.drawImage(crabImg, crabimg.getXLoc(), crabimg.getYLoc(), characterWidth, characterHeight, this);
		//renderBarriers(g, crabimg);
//		if (grassCheck == true){
//			g.drawRect(crabimg.getXLoc(),crabimg.getYLoc()+105,70,70);
//		}
//		if (wallCheck == true){
//			g.drawRect(crabimg.getXLoc(),crabimg.getYLoc()+50,110,110);
//		}
//		if (grassCheck == true){
//			while (shore1.getGrass().size() != 0)
//				for (Grass k : shore1.getGrass()){
//					g.drawRect(k.getXLoc(), k.getYLoc(), 100, 100);
//				}
//			}
		for (int i = 0; i < shore1.getGrass().size(); i++){
			int x = (shore1.getGrass().get(i)).getXLoc();
			int y = (shore1.getGrass().get(i)).getYLoc();
			g.drawImage(grassImg, x, y, 50,50, this);
		}
		for (int i = 0; i < shore1.getSeawall().size(); i++){
			int x = (shore1.getSeawall().get(i)).getXLoc();
			int y = (shore1.getSeawall().get(i)).getYLoc();
			g.drawImage(wallImg, x, y, 75, 75, this);
		}
		
		}
		
		public void renderBarriers(Graphics g, ShoreCrab c){
			g.drawRect(c.getXLoc(),c.getYLoc()+105,70,70);
		}

	//Set Button Listeners 
	void setupListeners() {
		grassButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				grassCheck = true;
				shore1.addGrass(crabimg.getXLoc(), crabimg.getYLoc() + 100);
				repaint();
				
			}
		});
		ogButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ogButton.setFocusable(false);
				repaint();
			}
		});
		seawallButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				seawallButton.setFocusable(false);
				wallCheck = true;
				shore1.addWall(crabimg.getXLoc(), crabimg.getYLoc()+ 100);
				repaint();
			}
		});


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		if ((xVel < 0 && (crabimg.getXLoc() - crabimg.getXIncr() >= 0)) || 
				((xVel > 0) && crabimg.getXLoc() + characterWidth + crabimg.getXIncr() <= screenWidth)){
			crabimg.moveHorizontal(xVel);
		}
		if (e.getSource() == grassButton){
			grassCheck = true;
			repaint();
		}
		if(e.getSource() == ogButton){
			oyCheck = true;
			repaint();
		}
		if(e.getSource() == seawallButton){
			wallCheck = true;
			repaint();
		}


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
		}
		if(code == KeyEvent.VK_RIGHT){
			moveCrabRight();
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		xVel = 0;
		yVel = 0;
	}

}
