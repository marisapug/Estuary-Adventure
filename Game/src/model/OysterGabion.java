package model;

import java.util.List;

/**
 * OysterGabion gives the specific information needed for the oyster gabion
 * that the crab can place on the shoreline
 * during the estuary defense game -- this includes, xLoc and yLoc
 * 
 * @author Marisa
 *
 */
public class OysterGabion extends Barrier {

/**
	 * Constructor that initializes the x-location, y-location of the OysterGabion
	 * All parameters are ints
	 * @param x, x-location of oyster gabion
	 * @param y, y-location of oyster gabion
*/
	public OysterGabion(int x, int y){
		xLoc = x;
		yLoc = y;
	}
	
	
}
