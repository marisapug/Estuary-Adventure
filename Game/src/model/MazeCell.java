package model;

public class MazeCell {
	int x;
	int y;
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
		y = row;
		x = column;
		this.width = width;
		this.height = height;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
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
