package model;

public class Crab extends GameFigure {
	int health;
	int ageState;
	int speed;
	
	
	
	String crabType;
	
	public Crab(int h, int a, int s, int xL, int yL){
		health = h;
		ageState = a;
		speed = s;
		xLoc = xL;
		yLoc = yL;
	}
	
	void moveLeft(){
		xLoc = xLoc - xIncr;
	}
		
	void moveRight(){
		xLoc = xLoc + xIncr;
	}
	
	void moveUp(){
		yLoc = yLoc - yIncr;
	}
	
	void moveDown(){
		yLoc = yLoc + yIncr;
	}
	
	void plantGrass(){}
	
	void die(){}
	
	void loseHealth(){
		health = health - 1;
	}
	
	void grow(){
		ageState = ageState + 1;
	}
}
