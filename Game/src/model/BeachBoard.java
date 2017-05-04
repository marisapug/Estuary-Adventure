package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	
	//grid
	private BeachCell[][] grid;

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
	private int mediumSpeed = 2;
	private int smallSpeed = 3;

	//barrier
	private int totalGabionHealth = 300;
	private int totalSeawallHealth = 100;

	//sand
	private int totalSandHealth = 100;
	private int grassHealIncr = 1;
	
	//start cell
	private int crabGridStartX;
	private int crabGridStartY;
	private BeachCell crabStartCell;
	
	//crab
	ShoreCrab gameCrab;
	
	//Wave stuff
	private WaveCell[] waveCells;
	private int waveCellWidth;
	private ArrayList<Wave> gameWaves = new ArrayList<Wave>();
	private int waveSpeed = 3;

	private int largeStrength = 40;
	private int mediumStrength = 25;
	private int smallStrength = 10;


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
		
		crabGridStartX = numRows/2 - 1;
		crabGridStartY = numCols/2;
		crabStartCell = grid[crabGridStartX][crabGridStartY];
		
		//initialize crab
		gameCrab = new ShoreCrab(crabStartCell.getXLoc(),crabStartCell.getYLoc());

		//initialize wave cell array
		waveCells = new WaveCell[numRows];
		initializeWaveCells();
		waveCellWidth = waveCells[0].getWidth();

		initializeCells();
	}

	//creates beach board grid
	private void makeGrid(int cWidth, int cHeight){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				grid[i][j] = new BeachCell(i, j, cWidth, cHeight, shoreStartYLoc, 0);
			}
		}
	}

	//intitialzes what type of item a cell can hold and the type of the cell
	private void initializeCells(){
		for(int i = 0; i < numRows; i++){
			grid[i][3].setCanHoldGrass(true);
			grid[i][0].setCanHoldBarrier(true);
			for(int j = 2; j < numCols; j++){
				grid[i][j].setType(0);
				grid[i][j].setHealth(totalSandHealth);
			}
			for(int j = 0; j < 2; j++){
				grid[i][j].setType(1);
			}

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

	//shore stuff
	public void sandToOcean(){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols-1; j++){
				BeachCell tempCell = grid[i][j];
				if(tempCell.getHealth() <= 0 && tempCell.getType() == 0){
					tempCell.setType(1);
					tempCell.setCanHoldGrass(false);
					//shifts can hold grass down if at least two from bottom
					if((numCols - 1) - j > 2){
						grid[i][j+2].setCanHoldGrass(true);
					}
					if((numCols - 1) - j > 1){
						BeachCell remGrassCell = grid[i][j+1];
						removeDestroyedGrass(remGrassCell.getXLoc(), remGrassCell.getYLoc());
						remGrassCell.setHasGrass(false);
						remGrassCell.setCanHoldGrass(false);
					}
				}
			}
		}
	}

	//removes grass given a xL and yL; called in sandToOcean method
	public void removeDestroyedGrass(int xL, int yL){
		Iterator<Grass> grassI = gameGrass.iterator();
		while(grassI.hasNext()){
			Grass currGrass = grassI.next();
			if(currGrass.getXLoc() == xL && currGrass.getYLoc() == yL){
				grassI.remove();
			}
		}
	}

	//heals the cell above a grass cell
	public void healCellsAboveGrass(){
		for(Grass gr: gameGrass){
			BeachCell healCell = inWhichCell(gr.getXLoc()+1, gr.getYLoc()-1);
			if(healCell.getHealth() < totalSandHealth - grassHealIncr){
				healCell.setHealth(healCell.getHealth() + grassHealIncr);
			}else{
				healCell.setHealth(totalSandHealth);
			}
		}
	}


	//PLACE OBJECT STUFF
	public void placeObject(int object, int xL, int yL, int width, int height){
		switch(object){
		case 1: //GRASS
			for(int i = -1; i < 2; i++){
				BeachCell tempCell = inWhichCell(xL + width/2, yL + height/2 + (i * cellHeight));
				if(tempCell != null && tempCell.getCanHoldGrass() && !tempCell.getHasGrass()){
					addGrass(tempCell.getXLoc(), tempCell.getYLoc());
					tempCell.setHasGrass(true);
				}
			}
			break;

		case 2: //SEAWALL
			for(int i = -1; i < 1; i++){
				BeachCell tempCell = inWhichCell(xL + width/2, yL + height/2 + (i * cellHeight));
				if(tempCell != null && tempCell.getCanHoldBarrier() && !tempCell.getHasBarrier()){
					addWall(tempCell.getXLoc(), tempCell.getYLoc());
					tempCell.setCanHoldBarrier(false);
					tempCell.setHasBarrier(true);
					tempCell.setHealth(totalSeawallHealth);
					if(tempCell.getX() > 0 ){
						grid[tempCell.getX() - 1][tempCell.getY()].setCanHoldBarrier(false);

					}
					if(tempCell.getX() < numRows-1){
						grid[tempCell.getX() + 1][tempCell.getY()].setCanHoldBarrier(false);

					}
				}
			}
			break;
		
		case 3: //GABION
			for(int i = -1; i < 1; i++){
				BeachCell tempCell = inWhichCell(xL + width/2, yL + height/2 + (i * cellHeight));
				if(tempCell != null && tempCell.getCanHoldBarrier() && !tempCell.getHasBarrier()){
					addGabion(tempCell.getXLoc(), tempCell.getYLoc());
					tempCell.setCanHoldBarrier(false);
					tempCell.setHasBarrier(true);
					tempCell.setHealth(totalGabionHealth);
					if(tempCell.getX() > 0 ){
						grid[tempCell.getX() - 1][tempCell.getY()].setCanHoldBarrier(false);
					}
					if(tempCell.getX() < numRows-1){
						grid[tempCell.getX() + 1][tempCell.getY()].setCanHoldBarrier(false);
					}
				}
			}
			break;
		}//switch

	}//method


	//WAVE STUFF

	//makes waves per cell per boat
	public void makeWaves(Boat b){
		WaveCell tempCell;
		//FIX THIS MAAZ
		if(b.getDirection() == 0){
			tempCell = inWhichWaveCell(b.getXLoc());
		}else{
			tempCell = inWhichWaveCell(b.getXLoc() + cellWidth);
		}
		if(tempCell!=null){
			int tempStrength;
			if(b.getSize() == 0){
				tempStrength = smallStrength;
			}
			else if(b.getSize() == 1){
				tempStrength = mediumStrength;
			}
			else{
				tempStrength = largeStrength;
			}
			Wave tempWave = new Wave((tempCell.getXLoc() + tempCell.getWidth()/2 - b.getWidth()/2), b.getYLoc(), tempStrength, b.getWidth());
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
			WaveCell forwardCell = inWhichWaveCell(b.getXLoc());
			WaveCell backCell = inWhichWaveCell(b.getXLoc() - b.getSpeed());
			if(backCell != forwardCell){
				resetWaveBasedOnBoatSize(backCell, b.getSize());
			}
		}
		else if(b.getDirection() == 1){
			WaveCell forwardCell = inWhichWaveCell(b.getXLoc() + b.getWidth() + cellWidth);
			WaveCell backCell = inWhichWaveCell(b.getXLoc() + b.getWidth() + b.getSpeed() + cellWidth);
			if(backCell != forwardCell){
				resetWaveBasedOnBoatSize(backCell, b.getSize());
			}
		}
	}

	//removes a wave from the list when it has hit a sand cell
	public void removeHitWaves(){
		Iterator<Wave> wv = gameWaves.iterator();
		while(wv.hasNext()){
			Wave currWave = wv.next();
			BeachCell tempCell = inWhichCell(currWave.getXLoc() + currWave.getWidth()/2, currWave.getYLoc() - cellHeight/2);
			if(tempCell != null && ((tempCell.getType() == 0))){
				tempCell.setHealth(tempCell.getHealth() - currWave.getStrength());
				wv.remove();
			}
			else if(currWave.getYLoc() > screenHeight){
				wv.remove();
			}
		}
	}

	//removes a barrier gives the xLoc and yLoc of the cell (same as its own xLoc and yLoc)
	public void removeBarrier(int xL, int yL){
		Iterator<OysterGabion> gi = gameGabions.iterator();
		Iterator<Seawall> si = gameWalls.iterator();

		while(gi.hasNext()){
			OysterGabion currGab = gi.next();
			if(currGab.getXLoc() == xL && currGab.getYLoc() == yL){
				gi.remove();
			}
		}
		while(si.hasNext()){
			Seawall currWall = si.next();
			if(currWall.getXLoc() == xL && currWall.getYLoc() == yL){
				si.remove();
			}
		}

	}


	//removes barriers with zero-health barriers, updates cells that can hold barriers/has barriers
	public void removeDeadBarriers(){
		Iterator<Wave> wv = gameWaves.iterator();
		while(wv.hasNext()){
			Wave currWave = wv.next();
			BeachCell tempCell = inWhichCell(currWave.getXLoc() + currWave.getWidth()/2, currWave.getYLoc() - cellHeight/2);
			if(tempCell != null && (tempCell.getHasBarrier())){
				tempCell.setHealth(tempCell.getHealth() - currWave.getStrength());
				if(tempCell.getHealth() <= 0){
					removeBarrier(tempCell.getXLoc(), tempCell.getYLoc());
					tempCell.setHasBarrier(false);
					tempCell.setCanHoldBarrier(true);
					setAdjacent(tempCell);
					tempCell.setHealth(0);
				}
				wv.remove();	
			}
		}
	}

	//sets what cells can hold barriers
	public void setAdjacent(BeachCell tempCell){
		if(tempCell.getX() == 1){
			grid[tempCell.getX()-1][tempCell.getY()].setCanHoldBarrier(true);
			setRightAdjacent(tempCell);
		}else if(tempCell.getX() == numRows-2){
			grid[tempCell.getX()+1][tempCell.getY()].setCanHoldBarrier(true);
			setLeftAdjacent(tempCell);
		}else if (tempCell.getX() == 0){
			setRightAdjacent(tempCell);
		}else if(tempCell.getX() == numRows-1){
			setLeftAdjacent(tempCell);
		}else{
			setRightAdjacent(tempCell);
			setLeftAdjacent(tempCell);
		}
	}

	public void setRightAdjacent(BeachCell tempCell){
		if(grid[tempCell.getX()+2][tempCell.getY()] != null && !grid[tempCell.getX()+2][tempCell.getY()].getHasBarrier())
			grid[tempCell.getX()+1][tempCell.getY()].setCanHoldBarrier(true);
	}
	public void setLeftAdjacent(BeachCell tempCell){
		if(grid[tempCell.getX()-2][tempCell.getY()] != null && !grid[tempCell.getX()-2][tempCell.getY()].getHasBarrier())
			grid[tempCell.getX()-1][tempCell.getY()].setCanHoldBarrier(true);
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

	public int getTotalSandHealth(){
		return totalSandHealth;
	}

	public int getTotalSeawallHealth(){
		return totalSeawallHealth;
	}

	public int getTotalGabionHealth(){
		return totalGabionHealth;
	}
	
	public ShoreCrab getGameCrab(){
		return gameCrab;
	}
	
	public int getCrabGridStartX(){
		return crabGridStartX;
	}
	
	public int getCrabGridStartY(){
		return crabGridStartY;
	}

}
