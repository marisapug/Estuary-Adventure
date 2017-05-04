package model;

public class BeachCell {
	private int x; //location in grid
	private int y; //location in grid
	private int xLoc; //location in pixels
	private int yLoc; //location in pixels
	private int width;
	private int height;
	private int cellType; //0 = sand, // 1 = ocean
	private boolean canHoldGrass = false;
	private boolean canHoldBarrier = false;
	private boolean hasGrass = false;
	private boolean hasBarrier = false;


	BeachCell(int xInp, int yInp, int cWidth, int cHeight,int startYLoc ,int matter){
		x = xInp;
		y = yInp;
		xLoc = x*cWidth;
		yLoc = y*cHeight + startYLoc;
		cellType = matter;
		width = cWidth; 
		height = cHeight;
	}
	
	//getters
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getType(){
		return cellType;
	}
	
	public boolean getCanHoldGrass(){
		return canHoldGrass;
	}
	
	public boolean getCanHoldBarrier(){
		return canHoldBarrier;
	}
	
	public boolean getHasGrass(){
		return hasGrass;
	}
	
	public boolean getHasBarrier(){
		return hasBarrier;
	}
	
	//Setters
	public void setCanHoldGrass(boolean b){
		canHoldGrass = b;
	}
	
	public void setCanHoldBarrier(boolean b){
		canHoldBarrier = b;
	}
	
	public void setHasGrass(boolean b){
		hasGrass = b;
	}
	
	public void setHasBarrier(boolean b){
		hasBarrier = b;
	}
	
	public void setType(int t){
		cellType = t;
	}
	
	
}
