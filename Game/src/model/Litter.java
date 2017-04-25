package model;

import java.util.Random;

public class Litter extends Obstacle {
	private int type;
	private int xLoc;
	private int yLoc;
	static int floatXIncr = 2;
	
	public Litter(int t, int xLoc, int yLoc){
		type = t;
		Random rand = new Random();
		this.xLoc = xLoc;
		this.yLoc = yLoc;
	}
	
	//moves litter by the given x and y increments
	public void moveLitter(int xIncr, int yIncr){
		yLoc = yLoc + yIncr;
		xLoc = xLoc + xIncr;
	}
	
	//changes litters xLoc
	void floatLitterRight(){
		xLoc = xLoc + floatXIncr;
	}
	
	void floatLitterLeft(){
		xLoc = xLoc - floatXIncr;
	}
	

	
	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}
	
	public int getType(){
		return type;
	}
	
	public int getFloatXIncr(){
		return floatXIncr;
	}
	
}