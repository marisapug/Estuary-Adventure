
package view;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.*;

public class DiceGameView extends JPanel {
	// Screen
	private int screenWidth = 500;
	private int screenHeight = 300;

	// Dice
	DiceGame dgame = new DiceGame();
	private int diceWidth = 120;
	private int diceStartX = 30;
	private int diceStartY = 100;
	private int betweenDice = 10;
	private int storyTextX = 50;
	private int storyTextY = 250;
	private boolean isRolled = false;
	private boolean isStorySaved = false;

	// Buttons
	private JButton rollDiceButton = new JButton("Roll Dice");
	private JButton storyButton = new JButton("Submit Story");


	// TextFields
	JTextField storyText = new JTextField("Enter Story Here");
	//dgame.diceStory = storyText.getText();

	// Constructor
	public DiceGameView() {
		this.add(rollDiceButton);
		this.add(storyText);
		this.add(storyButton);
		this.setupListeners();
	}

	// Getters
	public int getScreenWidth() {
		return this.screenWidth;
	}

	public int getScreenHeight() {
		return this.screenHeight;
	}

	// paintComponent
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Dice
		for (int i = 0; i < dgame.numDice; i++) {
			g.drawRect(diceStartX + (diceWidth + betweenDice) * i, diceStartY, diceWidth, diceWidth);
			if (isRolled)
				g.drawString("" + dgame.imgNums[i], diceStartX + diceWidth / 2 + (diceWidth + betweenDice) * i,
						diceStartY + diceWidth / 2);
			if (isStorySaved)
				g.drawString(dgame.diceStory, storyTextX, storyTextY);
		}
	}

	// Rolls Dice and Sets Images
	void rollDice() {
		dgame.setDice();
		isRolled = true;
		repaint();
	}
	
	void saveStory(){
		dgame.diceStory = storyText.getText();
		isStorySaved = true;
	}

	// Set Button Listeners
	void setupListeners() {
		rollDiceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rollDice(); // roll dice function
			}
		});
		storyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				saveStory(); // save story function
				repaint(); // print story function
			}
		});

	}

}
