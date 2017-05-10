package model;

import java.util.Random;
import java.util.Scanner;

public class DiceGame {
	public int numDice = 5;
	int numImgs = 20;
	public String diceStory;
	int animNum;
	Die[] dice;
	public int[] imgNums;
	boolean[] imgBools;
	int screenWidth;
	int screenHeight;
	int diceWidth;
	int betweenDice;
	int betweenStory;
	int diceStartX;
	int diceStartY;
	
	public DiceGame(int sWidth, int sHeight, int dWidth){
		dice = new Die[numDice];
		imgNums = new int[numDice];
		imgBools = new boolean[numImgs];
		screenWidth = sWidth;
		screenHeight = sHeight;
		diceWidth = dWidth;
		betweenDice = diceWidth + 10;
		diceStartX = (screenWidth - (numDice / 2 * diceWidth + (numDice / 2 - 1) * betweenDice)) / 2;
		diceStartY = (screenHeight - (3 * diceWidth + 2 * betweenStory)) / 2;
		
		
	}
	
	//Getters
	
	public int getNumDice(){
		return numDice;
	}
	
	public int getNumImgs(){
		return numImgs;
	}
	
	public String getDiceStory(){
		return diceStory;
	}
	
	public int getAnimNum(){
		return animNum;
	}
	
	public Die[] getDice(){
		return dice;
	}
	
	//Setters
	public void setAnimNum(int a){
		animNum = a;
	}
	
	//Makes array of length numDice signaling whether or not an image has been chosen
	void setImgBools(){
		for(int i = 0; i < numImgs; i++){
			imgBools[i] = false;
		}
	}
	
	public void setDice() {
		setImgBools();
		Random rand = new Random();
		int dieX = 0;
		int dieY = 0;
		for (int i = 0; i < numDice; i++) {
			if (i < 3) {
				dieX = diceStartX + (diceWidth + betweenDice) * i - (diceWidth + betweenDice) / 2;
				dieY = diceStartY;
			} else {
				dieX = diceStartX + (diceWidth + betweenDice) * (i % (numDice / 2));
				dieY = screenHeight - diceStartY - diceWidth;
			}
			Die tempDie = new Die(i, dieX, dieY, diceWidth, screenWidth, screenHeight);
			int randInt = rand.nextInt(numImgs);
			while(imgBools[randInt]) {
				randInt = rand.nextInt(numImgs);
			}
			tempDie.dieImg = randInt;
			imgBools[randInt] = true;
			imgNums[i] = randInt;
		//	System.out.println(randInt);
			dice[i] = tempDie;
		}
	} // makes all dice with images
}
