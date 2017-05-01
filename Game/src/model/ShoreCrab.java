package model;

public class ShoreCrab extends GameFigure {
	
	public ShoreCrab(int xl, int yl){
		xLoc = xl;
		yLoc = yl;
		xIncr = 6;
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

	public void moveHorizontal(int xVel) {
		xLoc = xLoc + xVel;
	}
	
	

}
