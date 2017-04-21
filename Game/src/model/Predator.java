package model;

public class Predator extends Obstacle {
	int speed;
	int direction;
	int xLoc;
	int yLoc;
	int type;
	
	public Predator(int x, int y, int s, int d){
		xLoc = x;
		yLoc = y;
		speed = s;
		direction = d;
	}
	
	void attack(){}
	
	public void move(int x, int y){
		xLoc += x;
		yLoc += y;
	}
	
	//GETTERS
	public int getXLoc(){
		return xLoc;
	}
	public int getYLoc(){
		return yLoc;
	}
	public int getSpeed(){
		return speed;
	}
	public int getDirection(){
		return direction;
	}
}
