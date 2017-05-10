package model;

import java.util.Random;

/**
 * Predator contains all the information including direction, x-location, y-location
 * width, and height of a predator in the maze game. Predator is a subclass of Obstacle.
 * @author Logan
 *
 */
public class Predator extends Obstacle {
	private int direction;
	private int xLoc;
	private int yLoc;
	private int width;
	private int height;

	/**
	 * Constructor that creates a new predator object
	 * @param x x-location of the predator
	 * @param y y-location of the predator
	 * @param d direction of the predator
	 * @param w width of the predator
	 * @param h height of the predator
	 */
	public Predator(int x, int y, int d, int w, int h){
		xLoc = x;
		yLoc = y;
		direction = d;//0 = up, 1 = down, 2 = right, 3 = left
		width = w;
		height = h;
	}

	/**
	 * Moves the predator object by incrementing the x-location and y-location
	 * @param xIncr the x-increment
	 * @param yIncr the y-increment
	 */
	public void movePred(int xIncr, int yIncr){
		yLoc = yLoc + yIncr;
		xLoc = xLoc + xIncr;
	}

	/**
	 * Moves the predator object relating to its current direction
	 * @param s speed
	 * @param d direction
	 */
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

	/**
	 * Sets a new random direction for a predator object
	 * @param d direction
	 */
	public void setRandomDirection(int d){
		Random rand = new Random();
		int newDir = rand.nextInt(4);
		while(newDir == d){
			newDir = rand.nextInt(4);
		}
		direction = newDir;
	}

	/**
	 * Checks if another object has collides with a predator object
	 * @param xL x-location of object
	 * @param yL y-location of object
	 * @param w width of object
	 * @param h height of object
	 * @return a boolean indicating if an object has hit a predator object
	 */
	public boolean hitPred(int xL, int yL, int w, int h){
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
