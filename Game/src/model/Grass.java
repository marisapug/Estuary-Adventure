package model;

/**
 * Grass gives the specific information needed for the grass the crab can plant
 * during the estuary defense game -- this includes, xLoc and yLoc
 * 
 * @author Marisa
 *
 */
public class Grass{
	private int xLoc;
	private int yLoc;

/**
	 * Constructor that initializes the x-location, y-location of the grass
	 * All parameters are ints
	 * @param x, x-location of grass
	 * @param y, y-location of grass
*/
	public Grass(int x, int y){
		xLoc = x;
		yLoc = y;
	}
	
	//GETTERS
	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}
	
}

