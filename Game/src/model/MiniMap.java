package model;

/**
 * MiniMap gives the specific information needed for the mini-map 
 * that will be rendered in the corner of the screen during the maze game
 * 
 * @author Marisa
 *
 */
public class MiniMap {
	private int dotX;
	private int dotY;
	private int miniWidth = 7;
	private int miniHeight = 7;
		
	public int getWidth(){
		return miniWidth;
	}
	public int getHeight(){
		return miniHeight;
	}
}
