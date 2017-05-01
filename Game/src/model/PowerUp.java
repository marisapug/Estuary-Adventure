package model;

public class PowerUp {
	private int type; //0 = health, 1 = speed, 2 = invincibility
	private int xLoc;
	private int yLoc;
	private static int width;
	private static int height;
	
	public PowerUp(int t, int xLoc, int yLoc){
		type = t;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		width = 30;
		height = 30;
	}
	
	//checks if something hit the PowerUp
	public boolean hitPowerUp(int xL, int yL, int w, int h){
		if((
				(xL > xLoc && xL < xLoc + width)   || (xL + w > xLoc && xL + w < xLoc + width)) &&
				((yL > yLoc && yL < yLoc + height) || (yL + h > yLoc && yL + h < yLoc + height))
				||
				((xLoc > xL && xLoc < xL + w )|| (xLoc + width > xL && xLoc + width < xL + w)) &&
				((yLoc > yL && yLoc < yL + h) ||(yLoc + height > yL && yLoc + height < yL + h))
				){
			return true;
		}
		else return false;
	}
	
	public void movePowerUp(int xIncr, int yIncr){
		xLoc += xIncr;
		yLoc += yIncr;
	}
	

	
	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}
	
	public int getType(){
		return type;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}