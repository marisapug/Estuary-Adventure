package model;

public class Barrier {
	protected String barrType;
	protected int health;
	protected int xLoc;
	protected int yLoc;
	protected int width;
	protected int height;
	
	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}
	
	public void setWidth(int w){
		width = w;
	}
	
	public void setHeight(int h){
		height = h;
	}
	
}
