package model;

import java.util.ArrayList;
import java.util.Random;

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
	
	//boat stuff
	private ArrayList<Boat> gameBoats = new ArrayList<Boat>();
	private int largeWidth = 100;
	private int largeHeight = 40;
	private int mediumWidth = 60;
	private int mediumHeight = 40;
	private int smallWidth = 30;
	private int smallHeight = 20;
	
	private int largeSpeed = 1;
	private int mediumSpeed = 3;
	private int smallSpeed = 5;
	
	//Wave stuff
	private boolean[][] waveCell;
	private ArrayList<Wave> gameWaves = new ArrayList<Wave>();
	private int waveSpeed = 3;
	
	private BeachCell[][] grid;
	
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
		
		//initialize something
		waveCell = new boolean[3][numCols];
		setAllWaveCellTrue();
		
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
		return null;
	}
	
	//BOAT STUFF
	public void generateRandomBoat(){
		Random rand = new Random();
		int boatType = rand.nextInt(30);
		Boat tempBoat;
		if(boatType < 15){
			tempBoat = new Boat(screenWidth, largeHeight + mediumHeight, 0, 1, smallSpeed,smallWidth,smallHeight);
			gameBoats.add(tempBoat);
		}
		else if(boatType < 25){
			tempBoat = new Boat(-mediumWidth, largeHeight, 1, 0, mediumSpeed,mediumWidth,mediumHeight);
			gameBoats.add(tempBoat);
		}
		else if(boatType <= 30){
			tempBoat = new Boat(screenWidth, 0, 2, 1, largeSpeed,largeWidth,largeHeight);
			gameBoats.add(tempBoat);
		}
	}
	
	
	//MODIFY AF
	public void makeWaves(Boat b){
		BeachCell tempCell = inWhichCell(b.getXLoc() + b.getWidth()/2, screenHeight/2 +10);
		if(tempCell!=null){
			Wave tempWave = new Wave(tempCell.getXLoc(), b.getYLoc(), b.getSize(), b.getWidth());
			if(waveCell[b.getSize()][tempCell.getY()]){
//				waveCell[b.getSize()][tempCell.getY()] = false;
				gameWaves.add(tempWave);
			}
		}
	}
	
		//initializes waveCell to false
	public void setAllWaveCellTrue(){
		for(int i = 0; i < numCols; i++){
			for(int j = 0; j < 3; j++){
			waveCell[j][i] = true;
			}
		}
	}
	
	
	//modifying arraylists
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
	
	public ArrayList<Boat> getGameBoats() {
		return gameBoats;
	}
	
	public ArrayList<Wave> getGameWaves() {
		return gameWaves;
	}
	
	public int getCellWidth(){
		return this.cellWidth;
	}
	
	public int getCellHeight(){
		return cellHeight;
	}
	
	public boolean[][] getWaveCell(){
		return waveCell;
	}
	
	public int getWaveSpeed(){
		return waveSpeed;
	}
	
}
