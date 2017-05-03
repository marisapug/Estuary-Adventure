package model;

import java.util.ArrayList;
import java.util.Iterator;
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
	private WaveCell[] waveCells;
	private int waveCellWidth;
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
		waveCells = new WaveCell[numRows];
		initializeWaveCells();
		waveCellWidth = waveCells[0].getWidth();
		
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
	
	public void removeBoatsOffScreen(){
		Iterator<Boat> bt = gameBoats.iterator();
		while(bt.hasNext()){
			Boat currBoat = bt.next();
			if(currBoat.getDirection() == 0 && currBoat.getXLoc() > screenWidth){
				bt.remove();
			}
			else if(currBoat.getDirection() == 1 && (currBoat.getXLoc() + currBoat.getWidth()) < 0){
				bt.remove();
			}
		}
		
	}
	
	public WaveCell inWhichWaveCell(int xl){
		for(int i = 0; i < numRows;i++){
			if(xl > waveCells[i].getXLoc() && xl < waveCells[i].getXLoc() + waveCellWidth)
				return waveCells[i];
		}
		return null;
	}
	
	//makes waves per cell per boat
	public void makeWaves(Boat b){
		WaveCell tempCell = inWhichWaveCell(b.getXLoc() + b.getWidth()/2);
		if(tempCell!=null){
			Wave tempWave = new Wave((tempCell.getXLoc() + tempCell.getWidth()/2 - b.getWidth()/2), b.getYLoc(), b.getSize(), b.getWidth());
			if((b.getSize() == 0 && !tempCell.getHasSmallWave())){
				gameWaves.add(tempWave);
				tempCell.setHasSmallWave(true);
			}
			else if(b.getSize() == 1 && !tempCell.getHasMediumWave()){
				gameWaves.add(tempWave);
				tempCell.setHasMediumWave(true);
			}
			else if(b.getSize() == 2 && !tempCell.getHasLargeWave()){
				gameWaves.add(tempWave);
				tempCell.setHasLargeWave(true);
			}
		}
	}

	//resets hasWave for a cell
	public void resetWaveBasedOnBoatSize(WaveCell cell, int size){
		if(cell != null){
			if(size == 0){
				cell.setHasSmallWave(false);
			}
			else if(size == 1){
				cell.setHasMediumWave(false);
			}
			else if(size == 2){
				cell.setHasLargeWave(false);
			}
		}
	}

	//resets a cell once a boat passes
	public void resetWaves(Boat b){
		if(b.getDirection() == 0){
			WaveCell forwardCell = inWhichWaveCell(b.getXLoc() + b.getSpeed());
			WaveCell backCell = inWhichWaveCell(b.getXLoc());
			if(backCell != forwardCell){
				resetWaveBasedOnBoatSize(backCell, b.getSize());
			}
		}
		else if(b.getDirection() ==1){
			WaveCell forwardCell = inWhichWaveCell(b.getXLoc() + b.getWidth() - b.getSpeed());
			WaveCell backCell = inWhichWaveCell(b.getXLoc() + b.getWidth());
			if(backCell != forwardCell){
				resetWaveBasedOnBoatSize(backCell, b.getSize());
			}
		}
	}
	
		//initializes waveCell to false
	public void initializeWaveCells(){
		for(int i = 0; i < numRows; i++){
			waveCells[i] = new WaveCell(cellWidth, i);
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
	
	public WaveCell[] getWaveCells(){
		return waveCells;
	}
	
	public int getWaveSpeed(){
		return waveSpeed;
	}
	
}
