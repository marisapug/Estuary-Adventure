package model;

import java.util.Random;

public class DiceGame {
	int numDice = 5;
	int numImgs = 20;
		
	Die[] dice = new Die[numDice];
	int[] imgNums = new int[numDice];
	boolean[] imgBools = new boolean[numImgs];
	
	void setImgBools(){
		for(int i = 0; i < numDice; i++){
			imgBools[i] = false;
		}
	}
	
	public void setDice() {
		Random rand = new Random();
		for (int i = 0; i < numDice; i++) {
			Die tempDie = new Die(i);
			int randInt = rand.nextInt(numImgs);
			while(imgBools[randInt]) {
				randInt = rand.nextInt(numImgs);
			}
			tempDie.dieImg = randInt;
			imgBools[randInt] = true;
			System.out.println(randInt);
			dice[i] = tempDie;
		}
	} // makes all dice with images
	
	void setImgs(){
		Random rand = new Random();
		for(int i = 0; i < numDice; i++){
			int randInt = rand.nextInt(numImgs);
			if(imgBools[randInt]){
				randInt = rand.nextInt(numImgs);
			}
			dice[i].dieImg = randInt;
		}
	}
}
