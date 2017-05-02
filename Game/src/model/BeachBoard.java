package model;

public class BeachBoard {
	private int numRows = 10;
	private int numCols = 10;
	
	BeachCell[][] grid;
	
	void makeGrid(){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				grid[i][j] = new BeachCell(i, j, "sand");
			}
		}
	}
}
