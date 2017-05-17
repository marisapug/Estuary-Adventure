package model;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
* Die creates all of the information needed for a specific die in the dice game, including its starting
* x and y locations, current (constantly updated) x and y locations, direction, image on the die, 
* position in the dice story, and whether it is currently selected by the user, as well as giving the 
* die a method for incrementing its location to be used for the rolling dice animation.
* 
* @author Natalie
*
*/


public class Die {
	BufferedImage dieImg;
	int dieImgNum = 0;
	static int imgWidth;
	static int screenWidth;
	static int screenHeight;
	final int xIncr;
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
	
	/**
	 * The constructor initializes the direction (which is random), image on the die, starting x and y locations,
	 * x-increment for movement, image width/height (same since it's square), and the screen width and height.
	 * @param n index of the image that will be painted to represent the die
	 * @param x starting and current x-location
	 * @param y starting and current y-location
	 * @param xInc x-increment (how much the x-location changes each time the die moves)
	 * @param imgW width/size of the die image
	 * @param sWidth screen width
	 * @param sHeight screen height
	 */
	public Die(int n, int x, int y, int xInc, int imgW, int sWidth, int sHeight){
		dir = rand.nextInt(4);
		dieImgNum = n;
		startXLoc = x;
		startYLoc = y;
		xIncr = xInc;
		imgWidth = imgW;
		screenWidth = sWidth;
		screenHeight = sHeight;
		xLoc = screenWidth / 2 - (imgWidth / 2);
		yLoc = screenHeight / 2 - (imgWidth / 2);
	}

	/**
	 * Increments the x- and y-locations of the die, by the x- and y-increment variable values,
	 * to give it the appearance of moving from its initial location to the final ending location of the dice
	 * animation.
	 * 
	 */
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
	
	public int getDir(){
		return dir;
	}
	
	public int getDieImgNum(){
		return dieImgNum;
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
	
	public void setDir(int d){
		dir = d;
	}
	
	public void setDieImgNum(int din){
		dieImgNum = din;
	}
}