package model;

public class ShoreCrab extends GameFigure {
	
	public ShoreCrab(int xl, int yl){
		xLoc = xl;
		yLoc = yl;
		xIncr = 6;
		yIncr = 6;
		width = 50;
		height = 50;
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
	

	public void move(int xVel, int yVel) {
		xLoc = xLoc + xVel;
		yLoc = yLoc + yVel;
	}
	
	
	

}
