package model;

/**
 * MazeCell class contains all information pertaining to each individual cell in the maze board.
 * @author Logan
 *
 */
public class MazeCell {
	int x; //location in grid
	int y; //location in grid
	int width;
	int height;
	int xLoc; //pixels
	int yLoc; //pixels
	boolean hasTopWall = true;
	boolean hasBottomWall = true;
	boolean hasLeftWall = true;
	boolean hasRightWall = true;
	boolean visited = false;
	boolean isCorrectPath = false;
	MazeCell top;
	MazeCell bottom;
	MazeCell left;
	MazeCell right;
	MazeCell correctTop;
	MazeCell correctBottom;
	MazeCell correctRight;
	MazeCell correctLeft;
	
	
	/**
	 * Constructor, creates a new instance of a MazeCell object.
	 * @param row row location
	 * @param column column location
	 * @param width cell width
	 * @param height cell height
	 * @param xStart x starting location of cell
	 * @param yStart y starting locatio of cell
	 * @param sWidth screen width
	 * @param sHeight screen height
	 */
	public MazeCell(int row, int column, int width, int height, int xStart, int yStart, int sWidth, int sHeight){
		y = row;
		x = column;
		this.width = width;
		this.height = height;
		xLoc = width*(x-xStart) + sWidth/2;
		yLoc = height*(y-yStart) + sHeight/2;
	}
	
	/**
	 * Changes cell's x and y location
	 * @param xIncr x-increment
	 * @param yIncr y-increment
	 */
	public void moveCell(int xIncr, int yIncr){
		yLoc = yLoc + yIncr;
		xLoc = xLoc + xIncr;
	}
	
	/**
	 * Checks if a given x and y coordinate falls within a certain cell
	 * @param xCor x-coordinate
	 * @param yCor y-coordinate
	 * @return a boolean indicating whether x and y are within the cell
	 */
	public boolean inCell(int xCor, int yCor){
		return(xCor >= xLoc && xCor <= xLoc + width && yCor >= yLoc && yCor <= yLoc + height);
	}

	/**
	 * Checks if a given x and y coordinate collide with x and y of a given cell well
	 * @param xCor x-coordinate
	 * @param yCor y-coordinate
	 * @param xIncr x-increment
	 * @param yIncr y-increment
	 * @param dir direction
	 * @return boolean indicating if x and y coordinate equal x and y of given cell wall
	 */
	public boolean hitCellWall(int xCor, int yCor, int xIncr, int yIncr, int dir){
		if(inCell(xCor,yCor)){
			if(hasTopWall && dir == 0){// dir 0 = up
				if(yCor-yIncr <= yLoc ){
					return true;
				}
			}
			else if(hasBottomWall && dir == 1){// dir 1 = down
				if(yCor + yIncr >= yLoc+height){
					return true;
				}
			}
			else if(hasRightWall && dir == 2){// dir 2 = right
				if(xCor + xIncr >= xLoc+width){
					return true;
				}
			}
			else if(hasLeftWall && dir == 3){// dir 3 = left
				if(xCor - xIncr <= xLoc){
					return true;
				}
			}
		}
		return false;
	}
	
	
	//GETTERS
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
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
	
	public boolean getHasTopWall(){
		return hasTopWall;
	}
	
	public boolean getHasBottomWall(){
		return hasBottomWall;
	}
	
	public boolean getHasLeftWall(){
		return hasLeftWall;
	}
	
	public boolean getHasRightWall(){
		return hasRightWall;
	}
	
	
	//SETTERS
	public void setY(int y){
		this.y = y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setYLoc(int y){
		this.yLoc = y;
	}
	
	public void setXLoc(int x){
		this.xLoc = x;
	}
	
	public void setHasLeftWall(boolean b){
		hasLeftWall = b;
	}
	
	public void setHasRightWall(boolean b){
		hasRightWall = b;
	}
	
	public void setHasTopWall(boolean b){
		hasTopWall = b;
	}
	
	public void setHasBottomWall(boolean b){
		hasBottomWall = b;
	}
}