package model;

import java.util.Random;

public class Wave {
	private int strength; // 0 = small, 1 = medium, 2 = large
	private int xLoc;
	private int yLoc;
	private int width;
	private int height = 10;
	private boolean hasHit = false;

	Wave(int xc, int yc, int s, int w) {
		strength = s;
		xLoc = xc;
		yLoc = yc;
		width = w;
	}
	
	public void move(int speed){
		yLoc += speed;
	}
	

	//getters
	public int getStrength() {
		return this.strength;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}
	
	public boolean getHasHit(){
		return hasHit;
	}
	
	//setters
	public void setHasHit(boolean b){
		hasHit = b;
	}

}
