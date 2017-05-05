package view;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class StartScreenView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	
	//Labels
	//private JLabel welcomeMessage  = new JLabel("Welcome to Estuary Adventure!");
	//private JLabel selectMessage = new JLabel("Please select a game mode.");
	

	public StartScreenView(){
		//this.add(welcomeMessage);
		//this.add(selectMessage);
		//Background Image
		BufferedImage backgroundImg = createImage("background/start.png");
		JLabel picLabel = new JLabel(new ImageIcon(backgroundImg));
		this.add(picLabel);
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
