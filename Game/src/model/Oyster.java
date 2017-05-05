package model;

import java.util.Random;

public class Oyster {
	int xLoc;
	int yLoc;
	static int width = 30;
	static int height = 30;
	
	public Oyster(int x, int y){
		xLoc = x;
		yLoc = y;
	}
	
	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	
}
