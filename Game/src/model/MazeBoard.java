package model;

import java.util.*;

public class MazeBoard {
	int numRows;
	int numCols;
	int xIncr;
	int yIncr;
	int wallWidth;
	int wallHeight;
	int xStartIndex;
	int yStartIndex;
	ArrayList<MazeCell> stack = new ArrayList<MazeCell>();
	MazeCell[][] grid;

	public MazeBoard(int rows, int cols, int width, int height){
		numRows = rows;
		numCols = cols;
		wallWidth = width;
		wallHeight = height;
		xIncr = 12;
		yIncr = 12;
		xStartIndex = 0;
		yStartIndex = 0;
		grid = new MazeCell[numRows][numCols];
		makeGrid(xStartIndex, yStartIndex);
		generateMaze(grid[0][0]);
	}

	void makeGrid(int xStart, int yStart){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				grid[i][j] = new MazeCell(i, j, wallWidth, wallHeight, xStart, yStart);
			}
		}
	}
	MazeCell checkNeighbors(MazeCell curr){
		ArrayList<MazeCell> neighbors = new ArrayList<MazeCell>();

		if(curr.y != 0)
			curr.top = grid[curr.y-1][curr.x];
		if(curr.y != numRows-1)
			curr.bottom = grid[curr.y + 1][curr.x];
		if(curr.x != numCols-1)
			curr.right = grid[curr.y][curr.x+1];
		if(curr.x != 0)
			curr.left = grid[curr.y][curr.x-1];

		if(curr.top!=null && !curr.top.visited)
			neighbors.add(curr.top);
		if(curr.bottom!=null && !curr.bottom.visited)
			neighbors.add(curr.bottom);
		if(curr.left!=null && !curr.left.visited)
			neighbors.add(curr.left);
		if(curr.right!=null && !curr.right.visited)
			neighbors.add(curr.right);

		if(neighbors.size() != 0){
			Random r = new Random();
			int rand = r.nextInt(neighbors.size());
			return neighbors.get(rand);
		} else
			return curr;
	}


	void generateMaze(MazeCell curr){
		curr.visited = true;
		MazeCell next = checkNeighbors(curr);
		MazeCell prev;
		if(next.x != curr.x || next.y != curr.y){
			stack.add(curr);
			removeWalls(curr, next);
			generateMaze(next);
		}
		else if(stack.size() > 0){
			prev = stack.get(stack.size() - 1);
			stack.remove(stack.size() - 1);
			generateMaze(prev);
		}
	}

	void removeWalls(MazeCell a, MazeCell b){
		int xWall = a.x - b.x;
		int yWall = a.y - b.y;
		if(xWall == 1){
			a.hasLeftWall = false;
			b.hasRightWall = false;
		}
		else if(xWall == -1){
			a.hasRightWall = false;
			b.hasLeftWall = false;
		}
		else if(yWall == 1){
			a.hasTopWall = false;
			b.hasBottomWall = false;
		}
		else if(yWall == -1){
			a.hasBottomWall = false;
			b.hasTopWall = false;
		}

	}

	public MazeCell[][] getGrid(){
		return grid;
	}

	public boolean hitGridWalls(int xCor, int yCor, int xIncr, int yIncr, int dir){
		boolean result = false;
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(grid[i][j].hitCellWall(xCor,yCor,xIncr,yIncr,dir)){
					result = true;
				}
			}
		}
		return result;
	}

	public void moveGrid(int xIncr, int yIncr){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				grid[i][j].moveCell(xIncr,yIncr);
			}
		}
	}


	//GETTERS
	public int getNumRows(){
		return this.numRows;
	}

	public int getNumCols(){
		return this.numCols;
	}

	public int getXIncr(){
		return this.xIncr;
	}

	public int getYIncr(){
		return this.yIncr;
	}

	public int getWallWidth(){
		return this.wallWidth;
	}

	public int getWallHeight(){
		return this.wallHeight;
	}

	public int getYStart(){
		return yStartIndex;
	}

	public int getXStart(){
		return xStartIndex;
	}

	//SETTERS
	public void setNumRows(int rows){
		this.numRows = rows;
	}

	public void setNumCols(int cols){
		this.numCols = cols;
	}

	public void setXIncr(int xIncr){
		this.xIncr = xIncr;
	}

	public void setYIncr(int yIncr){
		this.yIncr = yIncr;
	}

	public void setWallWidth(int width){
		this.wallWidth = width;
	}

	public void setWallHeight(int height){
		this.wallHeight = height;
	}

	public void setYStart(int y){
		this.yStartIndex = y;
	}

	public void setXStart(int x){
		 this.xStartIndex = x;
	}


}

