package model;

import java.util.ArrayList;

public class BeachBoard {
	
	private int screenWidth;
	private int screenHeight;
	
	private int numRows;
	private int numCols;
	
	private int cellWidth;
	private int cellHeight;
	private int shoreStartYLoc;
	
	private ArrayList<Grass> gameGrass = new ArrayList<Grass>();
	private ArrayList<Seawall> gameWalls = new ArrayList<Seawall>();
	private ArrayList<OysterGabion> gameGabions = new ArrayList<OysterGabion>();
	
	BeachCell[][] grid;
	
	public BeachBoard(int rows, int cols, int sWidth, int sHeight){
		numRows = rows;
		numCols = cols;
		screenWidth = sWidth;
		screenHeight = sHeight;
		cellWidth = screenWidth/numRows;
		cellHeight = (screenHeight/numCols)/2;
		shoreStartYLoc = screenHeight/2 - cellHeight;
		
		//initialize grid
		grid = new BeachCell[numRows][numCols];
		makeGrid(cellWidth, cellHeight);
		
		initializeCells();
	}
	
	private void makeGrid(int cWidth, int cHeight){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				grid[i][j] = new BeachCell(i, j, cWidth, cHeight, shoreStartYLoc, 0);
			}
		}
	}
	
	//intitialzes what type of item a cell can hold
	private void initializeCells(){
		for(int i = 0; i < numRows; i++){
			grid[i][2].setCanHoldGrass(true);
			grid[i][0].setCanHoldBarrier(true);
		}
	}


	//returns cell that contains the given x and y location
	public BeachCell inWhichCell(int xL, int yL){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(xL >= grid[i][j].getXLoc() && xL <= grid[i][j].getXLoc() + cellWidth &&
						yL >= grid[i][j].getYLoc() && yL <= grid[i][j].getYLoc() + cellHeight){
					return grid[i][j];
				}
			}
		}
		return grid[0][0];
	}
	
	//
	void remGrass(Grass g) { 
		gameGrass.remove(g);
	}
	
	void remWall(Seawall g) {
		gameGrass.remove(g);
	}
	
	void remOyster(OysterGabion g) { 
		gameGrass.remove(g);
	}
	
	public void addGrass(int x, int y){
		Grass g1 = new Grass(x,y);
		gameGrass.add(g1);
	}
	
	public void addWall(int x, int y){
		Seawall s1 = new Seawall(x,y);
		gameWalls.add(s1);
	}
	
	public void addGabion(int x, int y){
		OysterGabion s1 = new OysterGabion(x,y);
		gameGabions.add(s1);
	}
	
	
	//GETTERS
	public BeachCell[][] getGrid(){
		return grid;
	}
	
	public ArrayList<Grass> getGameGrass() {
		return gameGrass;
	}
	
	public ArrayList<Seawall> getGameSeawalls() {
		return gameWalls;
	}
	
	public ArrayList<OysterGabion> getGameGabs() {
		return gameGabions;
	}
	
	public int getCellWidth(){
		return this.cellWidth;
	}
	
	public int getCellHeight(){
		return cellHeight;
	}
	
}
