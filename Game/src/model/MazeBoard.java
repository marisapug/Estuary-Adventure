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
	MazeCell current;
	
	public MazeBoard(int rows, int cols, int width, int height){
		numRows = rows;
		numCols = cols;
		wallWidth = width;
		wallHeight = height;
		grid = new MazeCell[numRows][numCols];
		makeGrid();	
		current = grid[0][0];
		System.out.println("current initialized");
		System.out.println("makeGrid()");
		generateMaze();
		System.out.println("generateMaze()");
		System.out.println(stack.size());
	}
	
	void makeGrid(){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				grid[i][j] = new MazeCell(i, j, wallWidth, wallHeight);
			}
		}
	}
	MazeCell checkNeighbors(){
		ArrayList<MazeCell> neighbors = new ArrayList<MazeCell>();
		
		if(current.y != 0)
			current.top = grid[current.x][current.y - 1];
		if(current.y != numRows)
			current.bottom = grid[current.x][current.y + 1];
		if(current.x != numCols)
			current.right = grid[current.x + 1][current.y];
		if(current.x != 0)
			current.left = grid[current.x - 1][current.y];
		
		if(current.top!=null && !current.top.visited)
			neighbors.add(current.top);
		if(current.bottom!=null && !current.bottom.visited)
			neighbors.add(current.bottom);
		if(current.left!=null && !current.left.visited)
			neighbors.add(current.left);
		if(current.right!=null && !current.right.visited)
			neighbors.add(current.right);
		
		if(neighbors.size() != 0){
			Random r = new Random();
			int rand = r.nextInt(neighbors.size());
			return neighbors.get(rand);
		} else
			return current;
	}
	
	void generateMaze(){
		current.visited = true;
		MazeCell next = checkNeighbors();
		if(next != current){
			next.visited = true;
			stack.add(current);
			removeWalls(current, next);
			current = next;
		}
		else if(stack.size() > 0){
			current = stack.get(stack.size() - 1);
			stack.remove(stack.size() - 1);
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
	
	public ArrayList<MazeCell> getStack(){
		return stack;
	}
}

//generate grid (with all walls) in view
