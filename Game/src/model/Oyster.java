package model;

import java.util.Random;

public class Oyster {
	int xLoc;
	int yLoc;
	
	public Oyster(int x, int y){
		Random r = new Random();
		xLoc = r.nextInt(x);
		yLoc = r.nextInt(y) + 350;
	}
	
	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}
	
	
}
