package model;

public class Crab extends GameFigure {
	int health;
	int ageState;
	
	public Crab(int h, int a){
		health = h;
		ageState = a;
		this.xIncr = 2;
		this.yIncr = 2;
	}
	
	//Getters
	public int getXLoc(){
		return this.xLoc;
	}
	
	public int getYLoc(){
		return this.yLoc;
	}
	
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
