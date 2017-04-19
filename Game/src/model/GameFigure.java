package model;

public class GameFigure {
	protected int xLoc;
	protected int yLoc;
	protected int xIncr;
	protected int yIncr;
	protected int xVel = 0;
	protected int yVel = 0;
	protected int width;
	protected int height;
	
	//GETTERS
	public int getXVel(){
		return this.xVel;
	}
	
	public int getYVel(){
		return this.yVel;
	}
	
	//SETTERS
	public void setXVel(int x){
		this.xVel = x;
	}
	
	public void setYVel(int y){
		this.yVel = y;
	}
}
 