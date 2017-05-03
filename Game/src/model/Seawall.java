package model;

public class Seawall extends Barrier {
	boolean isHit;
	
	public Seawall(int x, int y){
		isHit = false;
		xLoc = x;
		yLoc = y;
	}
	
}
