package model;

public class ShoreCrab extends GameFigure {
	
	private int currObject; //0 nothing, 1 grass, 2 wall, 3 gabion
	private int numOysters;
	
	public ShoreCrab(int xl, int yl){
		xLoc = xl;
		yLoc = yl;
		xIncr = 6;
		yIncr = 6;
		width = 50;
		height = 50;
		currObject = 0;
	}

	public int getXLoc() {
		return xLoc;
	}

	public int getXIncr() {
		return xIncr;
	}

	public int getYIncr() {
		return yIncr;
	}

	public int getYLoc() {
		return yLoc;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getCurrObject(){
		return currObject;
	}
	
	public int getNumOysters(){
		return numOysters;
	}
	
	public void setNumOysters(int n){
		numOysters = n;
	}
	
	public void setCurrObject(int curr){
		currObject = curr;
	}
	

	public void move(int xVel, int yVel) {
		xLoc = xLoc + xVel;
		yLoc = yLoc + yVel;
	}
	

}
