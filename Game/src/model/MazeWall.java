package model;

public class MazeWall {
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private int dir;// 0 = horizontal; 1 = vertical
	
	public MazeWall(int sX, int sY, int eX, int eY, int d){
		startX = sX;
		startY = sY;
		endX = eX;
		endY = eY;
		dir = d;
	}
	
	public void moveWall(int xIncr, int yIncr){
		startX = startX + xIncr;
		endX = endX + xIncr;
		startY = startY + yIncr;
		endY = endY + yIncr;
	}
	
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
	
}
