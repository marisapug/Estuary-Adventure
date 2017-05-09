package model;

/**
 * Power gives the information needed for each individual power up 
 * that it used during the maze game, including the type, x-location
 * y-location, width, and height.
 * 
 * @author Logan
 *
 */
public class PowerUp {
	private int type; //0 = health, 1 = speed, 2 = invincibility
	private int xLoc;
	private int yLoc;
	private static int width;
	private static int height;
	
	/**
	 * Constructer that initializes the health, age, x-location, y-location of the power up object
	 * @param t type of the power up
	 * @param xLoc x-location of the power up in the maze board
	 * @param yLoc y-location of the power up in the maze board
	 */
	public PowerUp(int t, int xLoc, int yLoc){
		type = t;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		width = 30;
		height = 30;
	}
	
	/**
	 * Indicates whether a given power up collides with another object in the maze
	 * @param xL x-location of the object hitting the power up
	 * @param yL y-location of the object hitting the power up
	 * @param w width of the object hitting the power up
	 * @param h height of the object hitting the power up
	 * @return a boolean indicating whether a power up has been hit
	 */
	public boolean hitPowerUp(int xL, int yL, int w, int h){
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
	
	/**
	 * Increments the x-location and y-location of the power up by the respective incremements
	 * @param xIncr x-increment 
	 * @param yIncr y-increment
	 */
	public void movePowerUp(int xIncr, int yIncr){
		xLoc += xIncr;
		yLoc += yIncr;
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
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}