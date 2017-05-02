package model;

public class BeachBoard {
	private int numRows;
	private int numCols;
	private int screenWidth;
	private int screenHeight;
	
	BeachCell[][] grid;
	
	public BeachBoard(int rows, int cols, int sWidth, int sHeight){
		numRows = rows;
		numCols = cols;
		screenWidth = sWidth;
		screenHeight = sHeight;
		
		//initialize grid
		grid = new BeachCell[numRows][numCols];
		makeGrid(screenWidth, screenHeight);
	}
	
	void makeGrid(int sWidth, int sHeight){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				grid[i][j] = new BeachCell(i, j, sWidth, sHeight, "sand");
			}
		}
	}
	
	//getters
	public BeachCell[][] getGrid(){
		return grid;
	}
}
