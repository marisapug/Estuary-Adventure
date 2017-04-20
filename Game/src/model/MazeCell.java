package model;

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
	
	//getters and setters
	
	MazeCell(int row, int column, int width, int height, int xStart, int yStart, int sWidth, int sHeight){
		y = row;
		x = column;
		this.width = width;
		this.height = height;
		xLoc = width*(x-xStart) + sWidth/2;
		yLoc = height*(y-yStart) + sHeight/2;
	}
	
	public void moveCell(int xIncr, int yIncr){
		yLoc = yLoc + yIncr;
		xLoc = xLoc + xIncr;
	}
	
	public boolean inCell(int xCor, int yCor){
		return(xCor >= xLoc && xCor <= xLoc + width && yCor >= yLoc && yCor <= yLoc + height);
	}

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
}