package model;

/**
 * Litter gives the information needed for all the litter that is
 * loaded into the maze game, including type, x-location, y-location, width,
 * height, and the float-x-increment, which indicates the speed the litter will
 * move in the maze.
 * @author Logan
 *
 */
public class Litter {
	private int type;
	private int xLoc;
	private int yLoc;
	private static int width;
	private static int height;
	static int floatXIncr = 2;
	
	/**
	 * Constructor that initializes the type, x-location, y-location, width, and height of a
	 * litter object. The width and height of the litter object are set to 50.
	 * @param t type of the litter
	 * @param xLoc x-location of the litter
	 * @param yLoc y-location of the litter
	 */
	public Litter(int t, int xLoc, int yLoc){
		type = t;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		width = 50;
		height = 50;
	}
	
	/**
	 * Increments the x and y location of the litter by x-increment
	 * and y-increment
	 * @param xIncr the current x-increment of the litter
	 * @param yIncr the current y-increment of the litter
	 */
	public void moveLitter(int xIncr, int yIncr){
		yLoc = yLoc + yIncr;
		xLoc = xLoc + xIncr;
	}
	
	/**
	 * Increments the x-location of the litter by the current float x-increment
	 */
	public void floatLitterRight(){
		xLoc = xLoc + floatXIncr;
	}
	
	/**
	 * Decrements the x-location of the litter by the current float x-increment
	 */
	public void floatLitterLeft(){
		xLoc = xLoc - floatXIncr;
	}
	
	/**
	 * Indicates whether a litter object has the same x-location and y-location of another object given
	 * the x-location and y-location of the current litter object and the width and height.
	 * @param xL current x-location of litter object
	 * @param yL current y-location of litter object
	 * @param w current width of litter object
	 * @param h current height of litter object
	 * @return a boolean indicating whether a litter object has contacted another object in the maze game
	 */
	public boolean hitLitter(int xL, int yL, int w, int h){
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
	

	
	//GETTERS
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