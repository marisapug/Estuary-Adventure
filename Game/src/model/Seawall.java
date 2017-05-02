package model;

public class Seawall extends Barrier {
	boolean isHit;
	
	public Seawall(int x, int y){
		isHit = false;
		xLoc = x;
		yLoc = y;
	}

	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}
	
}
