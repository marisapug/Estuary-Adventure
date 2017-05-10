package model;

import java.util.Random;

/**
 * Wave gives the specific information needed for the waves caused by the boats
 * during the estuary defense game -- this includes strength, x-location,
 * y-location, width, height, and a boolean that tells if a wave has hit
 * 
 * @author Marisa
 *
 */
public class Wave {
	private int strength; // 0 = small, 1 = medium, 2 = large
	private int xLoc;
	private int yLoc;
	private int width;
	private int height = 10;
	private boolean hasHit = false;
	
/**
	 * Constructor that initializes the x-location, y-location, strength,
	 * and width of the wave
	 * All parameters are ints
	 * @param xc, x-location of wave
	 * @param yc, y-location of wave
	 * @param w, width of wave
	 * @param s, strength of wave
*/
	public Wave(int xc, int yc, int s, int w) {
		strength = s;
		xLoc = xc;
		yLoc = yc;
		width = w;
	}
	
	//move(int speed)
	/**
	 * Increments the wave by the speed
	 */
	public void move(int speed){
		yLoc += speed;
	}
	

	//getters
	public int getStrength() {
		return this.strength;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}
	
	public boolean getHasHit(){
		return hasHit;
	}
	
	//setters
	public void setHasHit(boolean b){
		hasHit = b;
	}

}
