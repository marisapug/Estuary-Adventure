package model;

import java.util.Random;

/**
 * Oyster class contains x and y location for oyster objects, as well as width and height
 * @author Logan
 *
 */
public class Oyster {
	int xLoc;
	int yLoc;
	static int width = 30;
	static int height = 30;
	
	
	/**
	 * Constructor, creates an instance of an oyster object
	 * @param x x-location
	 * @param y y-location
	 */
	public Oyster(int x, int y){
		xLoc = x;
		yLoc = y;
	}
	
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

	
}
