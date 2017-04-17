package model;

import java.util.*;
import java.util.Collection;

public class MazeBoard {
	int numRows;
	int numCols;
	int wallWidth;
	int wallHeight;
	ArrayList<MazeCell> stack = new ArrayList<MazeCell>();
	MazeCell[][] grid;
	
	public MazeBoard(int rows, int cols, int width, int height){
		numRows = rows;
		numCols = cols;
		wallWidth = width;
		wallHeight = height;
		grid = new MazeCell[numRows][numCols];
		makeGrid();
		generateMaze(grid[1][0]);
	}
	
	void makeGrid(){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				grid[i][j] = new MazeCell(i, j, wallWidth, wallHeight);
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
	
	/*
	boolean allChecked(){
		boolean temp = true;
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(grid[i][j].visited == false){
					temp = false;
				}
			}
		}
		return temp;
	}
	*/
	
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
}

//generate grid (with all walls) in view
