package model;

/**
 * Boat gives the specific information needed for a cell in the BeachBoard
 * that is utilized during the estuary defense game
 * Each cell has a x-location,y-location, x and y in terms of the grid, a width,
 * a height, a cellType (sand or ocean), and booleans that tell if the cell is 
 * available to hold grass, oysters, grass, or barriers.
 * 
 * @author Marisa
 *
 */
public class BeachCell {
	private int x; //location in grid
	private int y; //location in grid
	private int xLoc; //location in pixels
	private int yLoc; //location in pixels
	private int width;
	private int height;
	private int health;
	private int cellType; //0 = sand, // 1 = ocean
	private boolean canHoldGrass = false;
	private boolean canHoldBarrier = false;
	private boolean canHoldOyster = false;
	private boolean hasGrass = false;
	private boolean hasBarrier = false;

/**
	 * Constructor that initializes a cell's x,y,width, height, y-location, and cell-type
	 * All parameters are ints
	 * @param xInp, x in terms of grid
	 * @param yInp, y in terms of grid 
	 * @param cWidth, width of cell
	 * @param cHeight,height of cell
	 * @param startYLoc, the beginning location, will be added to the height
	 * @param matter, 0 = sand, 1 = ocean
*/
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
	
	public int getHealth(){
		return health;
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
	
	public boolean getCanHoldOyster(){
		return canHoldOyster;
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
	
	public void setHealth(int h){
		health = h;
	}
	
	public void setCanHoldOyster(boolean b){
		canHoldOyster = b;
	}
	
	
}

