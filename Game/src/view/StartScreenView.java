package view;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class StartScreenView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	//Screen dimensions
	private int screenWidth = MainFrame.getFrameWidth();
	private int screenHeight = MainFrame.getFrameHeight();
	
	//Background Image
	private BufferedImage backgroundImg = createImage("background/start.png");
	
	public StartScreenView(){
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//draws background
		g.drawImage(backgroundImg,0,0,screenWidth,screenHeight,this);		
	}
	
	public BufferedImage createImage(String fileName){
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(fileName)); 
			return bufferedImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
