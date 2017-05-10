package model;

public class Grass {
	private boolean isHit;
	private int xLoc;
	private int yLoc;
	
	public Grass(int x, int y){
		xLoc = x;
		yLoc = y;
	}
	
	//GETTERS
	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}
	
}

