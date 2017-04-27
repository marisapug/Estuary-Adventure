package model;

import java.util.Random;

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
		direction = d;//0 = up, 1 = down, 2 = right, 3 = left
	}
	
	void attack(){}
	
	public void move(int d){
		if(d==0){
			yLoc -= speed;
		}else if(d==1){
			yLoc += speed;
		}else if(d==2){
			xLoc += speed;
		}else if(d==3){
			xLoc -= speed;
		}
	}
	
	public void setRandomDirection(int d){
		Random rand = new Random();
		int newDir = rand.nextInt(4);
		while(newDir == d){
			newDir = rand.nextInt(4);
		}
		direction = newDir;
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
