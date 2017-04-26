package model;

import java.util.Random;

public class Litter {
	private int type;
	private int xLoc;
	private int yLoc;
	private static int width;
	private static int height;
	static int floatXIncr = 2;
	
	public Litter(int t, int xLoc, int yLoc){
		type = t;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		width = 50;
		height = 50;
	}
	
	//moves litter by the given x and y increments
	public void moveLitter(int xIncr, int yIncr){
		yLoc = yLoc + yIncr;
		xLoc = xLoc + xIncr;
	}
	
	//changes litters xLoc
	public void floatLitterRight(){
		xLoc = xLoc + floatXIncr;
	}
	
	public void floatLitterLeft(){
		xLoc = xLoc - floatXIncr;
	}
	
	//checks if something hit the litter
	public boolean hitLitter(int xL, int yL, int w, int h){
		//NEEDS FIXING TO WORK
		if((
				(xL > xLoc && xL < xLoc + width)   || (xL + w > xLoc && xL + w < xLoc + width)) &&
				((yL > yLoc && yL < yLoc + height) || (yL + h > yLoc && yL + h < yLoc + height))
				||
				((xLoc > xL && xLoc < xL + w )|| (xLoc + width > xL && xLoc + width < xL + w)) &&
				((yLoc > yL && yLoc < yL + h) ||(yLoc + height > yL && yLoc + height < yL + h))
				){
			return true;
		}
		else return false;
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
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}