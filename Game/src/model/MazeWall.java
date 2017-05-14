package model;

/**
 * The MazeWall class contains the information necessary for each individual wall in the maze game.
 * @author Logan
 *
 */
public class MazeWall {
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private int dir;// 0 = horizontal; 1 = vertical
	
	/**
	 * MazeWall constructor, creates a MazeWall object
	 * @param sX start x-location of the wall
	 * @param sY start y-location of the wall
	 * @param eX end x-location
	 * @param eY end y-location
	 * @param d direction of the wall (horizontal or vertical)
	 */
	public MazeWall(int sX, int sY, int eX, int eY, int d){
		startX = sX;
		startY = sY;
		endX = eX;
		endY = eY;
		dir = d;
	}
	
	/**
	 * Moves a given wall's x-location and y-location
	 * @param xIncr x-increment
	 * @param yIncr y-increment
	 */
	public void moveWall(int xIncr, int yIncr){
		startX = startX + xIncr;
		endX = endX + xIncr;
		startY = startY + yIncr;
		endY = endY + yIncr;
	}
	
	/**
	 * Checks if given object has hit a MazeWall object
	 * @param xLoc x-location of given object
	 * @param yLoc y-location of given object
	 * @param w width of given object
	 * @param h height of given object
	 * @return a boolean indicating if given object has hit a MazeWall object
	 */
	public boolean isHittingWall(int xLoc, int yLoc, int w, int h){
		if(dir == 0){
			if(
					( ((xLoc >= startX) && (xLoc <= endX)) || ((xLoc + w >= startX) && (xLoc + w <= endX)) ) && 
					((yLoc <= endY) && (yLoc + h >= endY))
					){
				return true;
			}
		}
		else if(dir == 1){
			if(
					((xLoc <= startX) && (xLoc + w >= endX)) && 
					( ((yLoc >= startY) && (yLoc <= endY)) || ((yLoc + h >= startY) && (yLoc + h <= endY)) )
					){
				return true;
			}
		}
		return false;
	}
	
	//GETTERS
	public int getStartX(){
		return startX;
	}
	public int getStartY(){
		return startY;
	}
	public int getEndX(){
		return endX;
	}
	public int getEndY(){
		return endY;
	}
	public int getDir(){
		return dir;
	}
	
}
