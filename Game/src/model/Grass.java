package model;

public class Grass extends Barrier{
	boolean isHit;
	
	public Grass(int x, int y){
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
	
	void die(){}

}
