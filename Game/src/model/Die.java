package model;

import java.util.Random;

public class Die {
	int dieImg = 0;
	int dieNum;
	static int imgWidth;
	static int screenWidth;
	static int screenHeight;
	final int xIncr = 10;
	final int yIncr = 7;
	int xLoc;
	int yLoc;
	int startXLoc;
	int startYLoc;
	int dir;
	Random rand = new Random();
	
	public Die(int n, int x, int y, int imgW, int sWidth, int sHeight){
		dir = rand.nextInt(4);
		dieNum = n;
		xLoc = x;
		startXLoc = x;
		yLoc = y;
		startYLoc = y;
		imgWidth = imgW;
		screenWidth = sWidth;
		screenHeight = sHeight;
	}

	public void throwDie(){
		if(dir == 0){//north east
			if(xLoc + imgWidth <= screenWidth && yLoc >= 0){
				xLoc += xIncr;
				yLoc -= yIncr;
			}
			else if(yLoc <= 0){
				dir = 2;
			}
			else{
				dir = 1;
			}
		}
		else if(dir == 1){//northwest
			if(xLoc >= 0 && yLoc >= 0){
				xLoc -= xIncr;
				yLoc -= yIncr;
			}
			else if(yLoc - yIncr >= 0){
				dir = 0;
			}
			else{
				dir = 3;
			}
		}
		else if(dir == 2){//southeast
			if(xLoc + imgWidth <= screenWidth && yLoc + (imgWidth * 1.5) <= screenHeight){
				xLoc += xIncr;
				yLoc += yIncr;
			}
			else if(yLoc + (imgWidth * 1.5) <= screenHeight){
				dir = 3;
			}
			else{
				dir = 0;
			}
		}
		else{//southwest
			if(xLoc >= 0 && yLoc + (imgWidth * 1.5) <= screenHeight){
				xLoc -= xIncr;
				yLoc += yIncr;
			}
			else if(yLoc + (imgWidth * 1.5) <= screenHeight){
				dir = 2;
			}
			else{
				dir = 1;
			}
		}
	}
	
	public void finishThrowing(){
		System.out.println("finishthrowing called");
		if(xLoc < startXLoc){
			if(yLoc < startYLoc){
				xLoc += xIncr;
				yLoc += yIncr;
			}
			else if(yLoc > startYLoc){
				xLoc += xIncr;
				yLoc -= yIncr;
			}
			else{
				xLoc += xIncr;
			}
		}
		else if(xLoc > startXLoc){
			if(yLoc < startYLoc){
				xLoc -= xIncr;
				yLoc += yIncr;
			}
			else if(yLoc > startYLoc){
				xLoc -= xIncr;
				yLoc -= yIncr;
			}
			else{
				xLoc -= xIncr;
			}
		}
		else{
			if(yLoc < startYLoc){
				yLoc += yIncr;
			}
			else if(yLoc > startYLoc){
				yLoc -= yIncr;
			}
			else{
				System.out.println("dice placed");
			}
		}
	}

	
	// Getters
	
	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}
	
	public int getStartXLoc(){
		return startXLoc;
	}
	
	public int getStartYLoc(){
		return startYLoc;
	}
}
