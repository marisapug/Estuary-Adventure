package model;

public class MazeWall {
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	
	public MazeWall(int sX, int sY, int eX, int eY){
		startX = sX;
		startY = sY;
		endX = eX;
		endY = eY;
	}
	
	public void moveWall(int xIncr, int yIncr){
		startX = startX + xIncr;
		endX = endX + xIncr;
		startY = startY + yIncr;
		endY = endY + yIncr;
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
