package model;

import java.util.*;

public class MazeBoard {
	int numRows;
	int numCols;
	int wallWidth;
	int wallHeight;
	int xStartIndex;
	int yStartIndex;
	int xEndIndex;
	int yEndIndex;
	int screenWidth;
	int screenHeight;
	
	ArrayList<MazeCell> stack = new ArrayList<MazeCell>();
	ArrayList<MazeCell> correctPath = new ArrayList<MazeCell>();
	MazeCell[][] grid;

	public MazeBoard(int rows, int cols, int width, int height, int sWidth, int sHeight){
		numRows = rows;
		numCols = cols;
		wallWidth = width;
		wallHeight = height;
		xStartIndex = 1;
		yStartIndex = 1;
		xEndIndex = rows-1;
		yEndIndex = cols-1;
		
		screenWidth = sWidth;
		screenHeight = sHeight;
		
		grid = new MazeCell[numRows][numCols];
		makeGrid(xStartIndex, yStartIndex);
		generateMaze(grid[xStartIndex][yStartIndex]);
		resetVisited();
		generateShortestPath(grid[xStartIndex][yStartIndex], grid[xEndIndex][yEndIndex]);
		getPath();
		
	}

	void makeGrid(int xStart, int yStart){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				grid[i][j] = new MazeCell(i, j, wallWidth, wallHeight, xStart, yStart, screenWidth, screenHeight);
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
	
	MazeCell checkCorrectNeighbors(MazeCell curr){
		ArrayList<MazeCell> neighbors = new ArrayList<MazeCell>();
		
		if(!curr.hasTopWall)
			curr.correctTop = grid[curr.y-1][curr.x];
		if(!curr.hasBottomWall)
			curr.correctBottom = grid[curr.y+1][curr.x];	
		if(!curr.hasRightWall)
			curr.correctRight = grid[curr.y][curr.x+1];
		if(!curr.hasLeftWall)
			curr.correctLeft = grid[curr.y][curr.x-1];
		
		if(curr.correctTop!=null && !curr.correctTop.visited)
			neighbors.add(curr.correctTop);
		if(curr.correctBottom!=null && !curr.correctBottom.visited)
			neighbors.add(curr.correctBottom);
		if(curr.correctLeft!=null && !curr.correctLeft.visited)
			neighbors.add(curr.correctLeft);
		if(curr.correctRight!=null && !curr.correctRight.visited)
			neighbors.add(curr.correctRight);
		
		if(neighbors.size() != 0){
			Random r = new Random();
			int rand = r.nextInt(neighbors.size());
			return neighbors.get(rand);
		} else
			return curr;
	}
	
	void resetVisited(){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				grid[i][j].visited = false;
			}
		} 
	}
	
	
	void generateShortestPath(MazeCell curr, MazeCell end){
		curr.isCorrectPath = true;
		curr.visited = true;
		if(curr.x != end.x || curr.y != end.y){
			MazeCell next = checkCorrectNeighbors(curr);
			MazeCell prev;
			if(next.x != curr.x || next.y != curr.y){
				stack.add(curr);
				generateShortestPath(next,end);
			}
			else if(stack.size() > 0){
				prev = stack.get(stack.size() - 1);
				stack.remove(stack.size() - 1);
				curr.isCorrectPath = false;
				generateShortestPath(prev,end);
			}
		}
	}
	
	void getPath(){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(grid[i][j].isCorrectPath){
					correctPath.add(grid[i][j]);
				}
			}
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
	
	public boolean isOnCorrectPath(int x, int y){
		for(MazeCell m : correctPath){
			if(x >= m.xLoc && x <= m.xLoc + m.width &&
					y >= m.yLoc && y <= m.yLoc + m.height){
				return true;
			}
		}
		return false;
	}

	public MazeCell inWhichCell(int xL, int yL){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(xL >= grid[i][j].xLoc && xL <= grid[i][j].xLoc + wallWidth &&
						yL >= grid[i][j].yLoc && yL <= grid[i][j].yLoc + wallHeight){
					return grid[i][j];
				}
			}
		}
		return grid[0][0];
	}

	//GETTERS
	public int getNumRows(){
		return this.numRows;
	}

	public int getNumCols(){
		return this.numCols;
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
	
	public ArrayList<MazeCell> getCorrectPath(){
		return correctPath;
	}

	//SETTERS
	public void setNumRows(int rows){
		this.numRows = rows;
	}

	public void setNumCols(int cols){
		this.numCols = cols;
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