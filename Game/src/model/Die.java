package model;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Die {
	BufferedImage dieImg;
	int dieImgNum = 0;
	static int imgWidth;
	static int screenWidth;
	static int screenHeight;
	final int xIncr = 9;
	final int yIncr = 6;
	int xLoc;
	int yLoc;
	int startXLoc;
	int startYLoc;
	int initXLoc;
	int initYLoc;
	int dir;
	int storyIndex;
	boolean isSelected = false;
	Random rand = new Random();
	
	public Die(int n, int x, int y, int imgW, int sWidth, int sHeight){
		dir = rand.nextInt(4);
		dieImgNum = n;
		startXLoc = x;
		startYLoc = y;
		imgWidth = imgW;
		screenWidth = sWidth;
		screenHeight = sHeight;
		xLoc = screenWidth / 2 - (imgWidth / 2);
		yLoc = screenHeight / 2 - (imgWidth / 2);
	}
	
	public void throwDie(){
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
	
	public int getInitXLoc(){
		return initXLoc;
	}
	
	public int getInitYLoc(){
		return initYLoc;
	}
	
	public BufferedImage getDieImg(){
		return dieImg;
	}
	
	public boolean getSelection(){
		return isSelected;
	}
	
	public int getStoryIndex(){
		return storyIndex;
	}
	
	//Setters
	public void setXLoc(int x){
		xLoc = x;
	}
	
	public void setYLoc(int y){
		yLoc = y;
	}
	
	public void setStartXLoc(int sx){
		startXLoc = sx;
	}
	
	public void setStartYLoc(int sy){
		startYLoc = sy;
	}
	
	public void setInitXLoc(int ix){
		initXLoc = ix;
	}
	
	public void setInitYLov(int iy){
		initYLoc = iy;
	}
	
	public void setDieImg(BufferedImage img){
		dieImg = img;
	}
	
	public void setSelection(boolean b){
		isSelected = b;
	}
	
	public void setStoryIndex(int si){
		storyIndex = si;
	}
}
