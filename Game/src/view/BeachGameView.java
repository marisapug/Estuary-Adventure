package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class BeachGameView extends JPanel {
	
	public BeachGameView(){
		ImageIcon image = new ImageIcon(getClass().getResource("B3.jpg"));
		JLabel label = new JLabel(image, JLabel.CENTER);
		ImageIcon bucket1 = new ImageIcon (getClass().getResource("lilcrab.png"));
		JLabel label2 = new JLabel(bucket1, JLabel.CENTER);
		label.setLayout(new FlowLayout());
		JLabel score = new JLabel("Score: ");
		JLabel time = new JLabel("Time: ");
		add(label);
		label.add(score);
		label.add(time);
		label.add(grassButton, BorderLayout.SOUTH);
		label.add(ogButton, BorderLayout.SOUTH);
		label.add(seawallButton, BorderLayout.SOUTH);
		add(label2);
		//label.add(label2, BorderLayout.NORTH);


	}
	
	// Buttons
	private JButton grassButton = new JButton("Grass");
	private JButton ogButton = new JButton("Oyster Gabion");
	private JButton seawallButton = new JButton("Seawall");
	
	//Set Button Listeners 
	void setupListeners() {
		grassButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		ogButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		seawallButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
					
			}
		});
	}

	
}
