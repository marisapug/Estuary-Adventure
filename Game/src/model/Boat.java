package model;

import java.util.Random;

public class Boat extends GameFigure {
	//String boatType;
	String size; // "small" "medium" or "large"
	String direction;
	int speed; // 1 , 3, or 5
	int x;
	int y;
	
	Boat(String size, String direction, int inc, int xc, int yc){
		size = s;
		direction = d;
		inc = i;
		x = xc;
		y = yc;
	}
	
	String getSize(){
		return size;
	}
	
	String getDirection(){
		return direction;
	}
	
	int getInc(){
		return inc;
	}
	
	int getX(){
		return this.x;
	}
	
	void setX(int xc){
		this.x == xc
	}
	
	void launchWave(){}
	
	Boat createBoat(){
		Random rand = new Random();
		int n = rand.nextInt(3)+1;
		if (n == 1){
			Boat b = new Boat("small", "right", 5, 0, 0)
		} if (n == 2){
			Boat b = new Boat("medium", "right", 3, 0, 0)
		} if (n == 3){
			Boat b = new Boat("large", "left", 1, 0, 0)
		}
	
	void move(){
		if (getDirection() == "right"){
			setX(getX() + getInc());
		}
		if (getDirection() == "left"){
			setX(getX() - getInc());
		}
	}
		
			
}
