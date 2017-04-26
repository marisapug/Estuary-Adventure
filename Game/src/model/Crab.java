package model;

public class Crab extends GameFigure {
	private int health;
	private int ageState;
	private int dir; // north 0, south 1, east 2, west 3
	
	public Crab(int h, int a, int xl, int yl){
		health = h;
		ageState = a;
		dir = 2;
		xIncr = 6;
		yIncr = 6;
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
	
	public int getHealth(){
		return this.health;
	}
	
	public int getAgeState(){
		return this.ageState;
	}
	
	public int getDir(){
		return this.dir;
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
	
	public void setHealth(int h){
		this.health = h;
	}
	
	public void setAgeState(int a){
		this.ageState = a;
	}
	
	public void setDir(int d){
		this.dir = d;
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
	
	public void plantGrass(){
		
	}
	
	public void die(){
		
	}
	
	public void loseHealth(){
		health = health - 1;
	}
	
	public void grow(){
		ageState = ageState + 1;
	}
}
