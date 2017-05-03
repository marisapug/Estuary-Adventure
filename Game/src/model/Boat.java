package model;

import java.util.Random;


public class Boat extends GameFigure {
	private int size; //0 (small), 1 (medium), 2 (large)
	private int direction; //0 (right) or 1 (left)
	private int speed; // 0 (slow), 1 (medium), 2 (fast)
	private int damage;
	private int xLoc;
	private int yLoc;
	private int width;
	private int height;

	Boat(int x, int y, int size, int direction, int speed, int w, int h){
		this.size = size;
		this.direction = direction;
		this.speed = speed;
		this.xLoc = x;
		this.yLoc = y;
		width = w;
		height = h;
	}
	
	public void move(){
		if (getDirection() == 0){
			xLoc += speed;
		}
		if (getDirection() == 1){
			xLoc -= speed;
		}
	}
	
	//getters
	public int getSize(){
		return size;
	}

	public int getDirection(){
		return direction;
	}

	public int getXLoc(){
		return this.xLoc;
	}
	
	public int getYLoc(){
		return this.yLoc;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	public int getSpeed(){
		return this.speed;
	}

	
	//setters
	public void setXLoc(int xc){
		this.xLoc = xc;
	}
	
	public void setYLoc(int yc){
		this.yLoc = yc;
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}


}
