package model;

public class MazeCell {
	int x;
	int y;
	int xLoc;
	int yLoc;
	int width;
	int height;
	boolean hasTopWall = true;
	boolean hasBottomWall = true;
	boolean hasLeftWall = true;
	boolean hasRightWall = true;
	boolean visited = false;
	MazeCell top;
	MazeCell bottom;
	MazeCell left;
	MazeCell right;
	
	//getters and setters
	
	MazeCell(int row, int column, int width, int height){
		x = row;
		y = column;
		this.width = width;
		this.height = height;
		xLoc = x * width;
		yLoc = y * height;
	}
	
	public int getX(){
		return xLoc;
	}
	
	public int getY(){
		return yLoc;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}