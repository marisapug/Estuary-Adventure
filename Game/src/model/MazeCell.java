package model;

public class MazeCell {
	int x;
	int y;
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
	
	MazeCell(int row, int column){
		y = row;
		x = column;
	}
	
}