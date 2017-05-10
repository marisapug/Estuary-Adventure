package model;

import java.util.Random;

/**
 * Boat gives the specific information needed for the random boats 
 * that are utilized during the estuary defense game, including the size, direction,
 * speed, location, damage, width, height, and direction. Crab is a subclass of GameFigure.
 * 
 * @author Marisa
 *
 */
public class Boat extends GameFigure {
	private int size; //0 (small), 1 (medium), 2 (large)
	private int direction; //0 (right) or 1 (left)
	private int speed; // 0 (slow), 1 (medium), 2 (fast)
	private int damage;
	private int xLoc;
	private int yLoc;
	private int width;
	private int height;

/**
	 * Constructor that initializes the x-location, y-location, size,
	 * direction, speed, width, and height of 
	 * the boats for the estuary defense game. 
	 * All parameters are ints
	 * @param x, x-location of boat
	 * @param y, y-location of boat 
	 * @param size, size of boat 
	 * @param direction, direction of boat 
	 * @param speed, speed of boat 
	 * @param w, width of boat 
	 * @param h, height of boat
*/
	public Boat(int x, int y, int size, int direction, int speed, int w, int h){
		this.size = size;
		this.direction = direction;
		this.speed = speed;
		this.xLoc = x;
		this.yLoc = y;
		width = w;
		height = h;
	}
	
	/**
	 * move()
	 * Increments the x-location of the boat by its speed
	 */
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
