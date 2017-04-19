package model;

public class Crab extends GameFigure {
	private int health;
	private int ageState;
	
	public Crab(int h, int a, int xl, int yl){
		health = h;
		ageState = a;
		xIncr = 3;
		yIncr = 3;
		xLoc = xl;
		yLoc = yl;
	}
	
	//Getters
	public int getXLoc(){
		return this.xLoc;
	}
	
	public int getYLoc(){
		return this.yLoc;
	}
	
	public int getXIncr(){
		return this.xIncr;
	}
	
	public int getYIncr(){
		return this.yIncr;
	}
	
	//Setters
	public void setXLoc(int x){
		this.xLoc = x;
	}
	
	public void setYLoc(int y){
		this.yLoc = y;
	}
	
	public void setXIncr(int xi){
		this.xIncr = xi;
	}
	
	public void setYIncr(int yi){
		this.yIncr = yi;
	}
	
	//Movers
	public void moveLeft(){
		xLoc = xLoc - xIncr;
	}
		
	public void moveRight(){
		xLoc = xLoc + xIncr;
	}
	
	public void moveUp(){
		yLoc = yLoc - yIncr;
	}
	
	public void moveDown(){
		yLoc = yLoc + yIncr;
	}
	
	public void plantGrass(){}
	
	public void die(){}
	
	public void loseHealth(){
		health = health - 1;
	}
	
	public void grow(){
		ageState = ageState + 1;
	}
}
