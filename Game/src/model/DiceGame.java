package model;

import java.util.Random;
import java.util.Scanner;

public class DiceGame {
	public int numDice = 5;
	int numImgs = 20;
	public String diceStory;
	int animNum;
		
	Die[] dice = new Die[numDice];
	public int[] imgNums = new int[numDice];
	boolean[] imgBools = new boolean[numImgs];
	
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
		for (int i = 0; i < numDice; i++) {
			Die tempDie = new Die(i);
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
