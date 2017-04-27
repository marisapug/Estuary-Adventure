package model;

import java.util.Random;

public class Predator extends Obstacle {
	private int direction;
	private int xLoc;
	private int yLoc;
	private int width;
	private int height;
	private int type;
	
	public Predator(int x, int y, int d, int w, int h){
		xLoc = x;
		yLoc = y;
		direction = d;//0 = up, 1 = down, 2 = right, 3 = left
		width = w;
		height = h;
	}
	
	void attack(){}
	
	public void movePred(int xIncr, int yIncr){
		yLoc = yLoc + yIncr;
		xLoc = xLoc + xIncr;
	}
	
	public void move(int s, int d){
		if(d==0){
			yLoc -= s;
		}else if(d==1){
			yLoc += s;
		}else if(d==2){
			xLoc += s;
		}else if(d==3){
			xLoc -= s;
		}
	}
	
	public void setRandomDirection(int d){
		Random rand = new Random();
		int newDir = rand.nextInt(4);
		while(newDir == d){
			newDir = rand.nextInt(4);
		}
		direction = newDir;
	}
	
	//GETTERS
	public int getXLoc(){
		return xLoc;
	}
	public int getYLoc(){
		return yLoc;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}

	public int getDirection(){
		return direction;
	}
}
