package model;

/**
 * GameFigure is a super class for a multitude of classes used in all games, such as Crab
 * It is used as a general outline of what objects in each game should have.
 * @author Logan
 *
 */
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
 