package model;

import java.util.Random;


public class Boat extends GameFigure {
	int size; //0 (small), 1 (medium), 2 (large)
	int direction; //0 (right) or 1 (left)
	int speed; // 0 (slow), 1 (medium), 2 (fast)

	Boat(int size, int direction, int speed, int x, int y){
		this.size = size;
		this.direction = direction;
		this.speed = speed;
		this.xLoc = x;
		this.yLoc = y;
	}

	public int getSize(){
		return size;
	}

	public int getDirection(){
		return direction;
	}

	public int getXLoc(){
		return this.xLoc;
	}

	public void setXLoc(int xc){
		this.xLoc = xc;
	}

	public int getSpeed(){
		return this.speed;
	}

	public void setSpeed(int speed){
		this.speed = speed;
	}

	public void launchWave(){

	}

	public Boat createBoat(){

		Boat b;
		
		Random rand = new Random();
		int n = rand.nextInt(3)+1;

		if (n == 1){
			b = new Boat(0, 0, 5, 0, 0);
		} 
		else if (n == 2){
			b = new Boat(1, 0, 3, 0, 0);
		} 
		else
			b = new Boat(2, 1, 1, 0, 0);

		return b;
	}

	public void move(){
		if (getDirection() == 0){
			setXLoc(getXLoc() + getSpeed());
		}
		if (getDirection() == 1){
			setXLoc(getXLoc() - getSpeed());
		}
	}


}
