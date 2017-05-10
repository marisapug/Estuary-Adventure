package model;

/**
 * Seawall gives the specific information needed for the seawall
 * that the crab can place on the shoreline
 * during the estuary defense game -- this includes, xLoc and yLoc
 * 
 * @author Marisa
 *
*/
public class Seawall extends Barrier {
	
/**
	 * Constructor that initializes the x-location, y-location of the seawall
	 * All parameters are ints
	 * @param x, x-location of seawall
	 * @param y, y-location of seawall
*/
	public Seawall(int x, int y){
		xLoc = x;
		yLoc = y;
	}
	
	
	
}

