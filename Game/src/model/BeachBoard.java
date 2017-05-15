package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * BeachBoard class contains all information and data used during the entire duration of the beach game.
 * Info and data includes crab, boats, grass, barriers, and shore.
 * @author Logan
 *
 */
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

	//ArrayList of objects
	private ArrayList<Grass> gameGrass = new ArrayList<Grass>();
	private ArrayList<Seawall> gameWalls = new ArrayList<Seawall>();
	private ArrayList<OysterGabion> gameGabions = new ArrayList<OysterGabion>();
	private ArrayList<Oyster> gameOysters = new ArrayList<Oyster>();
	
	//features bar
	private int featuresBarWidth;
	private int featuresBarHeight;


	//boat stuff
	private ArrayList<Boat> gameBoats = new ArrayList<Boat>();
	private int largeWidth = 100;
	private int largeHeight = 40;
	private int mediumWidth = 60;
	private int mediumHeight = 40;
	private int smallWidth = 30;
	private int smallHeight = 20;

	private int largeBoatY;
	private int mediumBoatY;
	private int smallBoatY;

	private int largeSpeed = 1;
	private int mediumSpeed = 2;
	private int smallSpeed = 3;

	//barrier
	private int totalGabionHealth = 300;
	private int totalSeawallHealth = 100;

	//sand
	private int totalSandHealth = 100;
	private int grassHealIncr = 2;

	//specific cells
	private int crabGridStartX;
	private int crabGridStartY;
	private BeachCell crabStartCell;

	private int crabGridTopX;
	private int crabGridTopY;
	private BeachCell crabTopLeftCell;

	private int crabGridBottomX;
	private int crabGridBottomY;
	private BeachCell crabBottomLeftCell;

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

	//Buckets Stuff
	//seawall
	private BeachCell seawallStartCell;
	private int seawallBucketXLoc;
	private int seawallBucketYLoc;
	private int seawallBucketWidth;
	private int seawallBucketHeight;
	//grass
	private int grassBucketXLoc;
	private int grassBucketYLoc;
	private int grassBucketWidth;
	private int grassBucketHeight;
	//gabion
	private int gabionBucketXLoc;
	private int gabionBucketYLoc;
	private int gabionBucketWidth;
	private int gabionBucketHeight;
	
	//Shore Health
	private int currentShoreHealth; 
	private int totalShoreHealth;
	
	private int totalCellsHealth;
	private int currentCellsHealth;



	/**
	 * Constructor, creates an initialized instance of a BeachBoard object
	 * @param rows number of rows
	 * @param cols number of columns
	 * @param sWidth screen width
	 * @param sHeight screen height
	 */
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

		crabGridTopX = 1;
		crabGridTopY = 0;
		crabTopLeftCell = grid[crabGridTopX][crabGridTopY];

		crabGridBottomX = numRows-1;
		crabGridBottomY = 0;
		crabBottomLeftCell = grid[crabGridBottomX][crabGridBottomY];

		//initialize crab
		gameCrab = new ShoreCrab(crabStartCell.getXLoc(),crabStartCell.getYLoc());

		//initialize wave cell array
		waveCells = new WaveCell[numRows];
		initializeWaveCells();
		waveCellWidth = waveCells[0].getWidth();

		initializeCells();

		//set up Bucket locations and sizes
		//grass
		seawallStartCell = grid[numRows-1][numCols-3];
		seawallBucketXLoc = 0;
		seawallBucketYLoc = seawallStartCell.getYLoc();
		seawallBucketWidth = screenWidth/5;
		seawallBucketHeight = screenHeight/10;
		//seawall
		grassBucketXLoc = seawallBucketXLoc + 2*seawallBucketWidth;
		grassBucketYLoc = seawallBucketYLoc;
		grassBucketWidth = screenWidth/5;
		grassBucketHeight = seawallBucketHeight;
		//gabion
		gabionBucketXLoc = grassBucketXLoc + 2*grassBucketWidth;
		gabionBucketYLoc = grassBucketYLoc;
		gabionBucketWidth = screenWidth/5;
		gabionBucketHeight = grassBucketHeight;
		
		//features bar initialization
		featuresBarWidth = screenWidth;
		featuresBarHeight = screenHeight/16;
		
		//boat height initialization
		largeBoatY = featuresBarHeight;
		mediumBoatY = largeBoatY + largeHeight;
		smallBoatY = mediumBoatY + mediumHeight;
		
		//Shore Health
		updateCurrentCellsHealth();
		totalCellsHealth = currentCellsHealth;
		totalShoreHealth = totalCellsHealth/3;
		currentShoreHealth = totalShoreHealth;
	}

	/**
	 * Creates a grid for the shore 
	 * @param cWidth cell width
	 * @param cHeight cell height
	 */
	private void makeGrid(int cWidth, int cHeight){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				grid[i][j] = new BeachCell(i, j, cWidth, cHeight, shoreStartYLoc, 0);
			}
		}
	}

	/**
	 * intitialzes what type of item a cell can hold and the type of the cell
	 */
	private void initializeCells(){
		for(int i = 0; i < numRows; i++){
			grid[i][3].setCanHoldGrass(true);
			grid[i][3].setHasGrass(false);
			grid[i][0].setCanHoldBarrier(true);
			grid[i][0].setHasBarrier(false);
			for(int j = 2; j < numCols; j++){
				grid[i][j].setType(0);
				grid[i][j].setHealth(totalSandHealth);
			}
			for(int j = 0; j < 2; j++){
				grid[i][j].setType(1);
			}
			for(int j = 3; j < numCols; j++){
				grid[i][j].setCanHoldOyster(true);
			}

		}
	}
	
	/**
	 * resets the shore back to its intial state
	 */
	public void resetShore(){
		gameGrass = new ArrayList<Grass>();
		gameWalls = new ArrayList<Seawall>();
		gameGabions = new ArrayList<OysterGabion>();
		gameOysters = new ArrayList<Oyster>();
		initializeCells();
	}


	/**
	 * returns cell that contains the given x and y location
	 * @param xL x-location
	 * @param yL y-location
	 * @return cell at given location
	 */
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

	/**
	 * Generates a random-sized boat
	 */
	public void generateRandomBoat(){
		Random rand = new Random();
		int boatType = rand.nextInt(30);
		if(boatType < 15){
			spawnSmallBoat();
		}
		else if(boatType < 25){
			spawnMediumBoat();
		}
		else if(boatType <= 30){
			spawnLargeBoat();
		}
	}
	
	/**
	 * creates a small-sized boat object and adds it into game boats array list
	 */
	public void spawnSmallBoat(){
		Boat tempBoat = new Boat(-smallWidth, smallBoatY, 0, 0, smallSpeed,smallWidth,smallHeight);
		gameBoats.add(tempBoat);
	}
	/**
	 * creates a medium-sized boat object and adds it into game boats array list
	 */
	public void spawnMediumBoat(){
		Boat tempBoat = new Boat(screenWidth, mediumBoatY, 1, 1, mediumSpeed,mediumWidth,mediumHeight);
		gameBoats.add(tempBoat);
	}
	/**
	 * creats a large-sized boat object and adds it into game boats array list
	 */
	public void spawnLargeBoat(){
		Boat tempBoat = new Boat(-largeWidth, largeBoatY, 2, 0, largeSpeed,largeWidth,largeHeight);
		gameBoats.add(tempBoat);
	}

	/**
	 * iterates through gameBoats array list and removes boats that exit the screen view
	 */
	public void removeBoatsOffScreen(){
		Iterator<Boat> bt = gameBoats.iterator();
		while(bt.hasNext()){
			Boat currBoat = bt.next();
			if(currBoat.getDirection() == 0 && currBoat.getXLoc() > screenWidth + currBoat.getWidth()){
				bt.remove();
			}
			else if(currBoat.getDirection() == 1 && (currBoat.getXLoc() + currBoat.getWidth()) < -(currBoat.getWidth() + cellWidth)){
				bt.remove();
			}
		}

	}

	/**
	 * Determines which wave cell a given x-location is in
	 * @param xl x-location
	 * @return wave cell that given x-location falls in
	 */
	public WaveCell inWhichWaveCell(int xl){
		for(int i = 0; i < numRows;i++){
			if(xl >= waveCells[i].getXLoc() && xl < waveCells[i].getXLoc() + waveCellWidth)
				return waveCells[i];
		}
		return null;
	}

	//shore stuff
	/**
	 * Turns a given "sand" cell into an "ocean" cell and resets the cell's properties, such as what items it can hold
	 */
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
					if((numCols - 1) - j > 3){
						grid[i][j+1].setCanHoldOyster(false);
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

	/**
	 * removes grass given a xL and yL; called in sandToOcean method
	 * @param xL x-location
	 * @param yL y-location
	 */
	public void removeDestroyedGrass(int xL, int yL){
		Iterator<Grass> grassI = gameGrass.iterator();
		while(grassI.hasNext()){
			Grass currGrass = grassI.next();
			if(currGrass.getXLoc() == xL && currGrass.getYLoc() == yL){
				grassI.remove();
			}
		}
	}

	/**
	 * 	heals the cell above a cell that contains a grass object
	 */
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

	//OBJECT STUFF
	
	/**
	 * places the crab's current held object at given x and y locations
	 * @param object current obkect
	 * @param xL x-location
	 * @param yL y-location
	 * @param width cell width
	 * @param height cell height
	 */
	public void placeObject(int object, int xL, int yL, int width, int height){
		switch(object){
		case 1: //GRASS
			for(int i = -1; i < 2; i++){
				BeachCell tempCell = inWhichCell(xL + width/2, yL + height/2 + (i * cellHeight));
				if(tempCell != null && tempCell.getCanHoldGrass() && !tempCell.getHasGrass()){
					addGrass(tempCell.getXLoc(), tempCell.getYLoc());
					gameCrab.setCurrObject(0);
					tempCell.setHasGrass(true);
				}
			}
			break;

		case 2: //SEAWALL
			for(int i = -1; i < 1; i++){
				BeachCell tempCell = inWhichCell(xL + width/2, yL + height/2 + (i * cellHeight));
				if(tempCell != null && tempCell.getCanHoldBarrier() && !tempCell.getHasBarrier()){
					addWall(tempCell.getXLoc(), tempCell.getYLoc());
					gameCrab.setCurrObject(0);
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
				if(tempCell != null && tempCell.getCanHoldBarrier() && !tempCell.getHasBarrier() && gameCrab.getNumOysters() >= 3){
					addGabion(tempCell.getXLoc(), tempCell.getYLoc());
					gameCrab.setCurrObject(0);
					tempCell.setCanHoldBarrier(false);
					tempCell.setHasBarrier(true);
					tempCell.setHealth(totalGabionHealth);
					gameCrab.setNumOysters(gameCrab.getNumOysters() - 3);
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

	/**
	 * Sets crab current object based on which "bucket" its x and y location fall into
	 */
	public void setObjectFromBucket(){
		int width = gameCrab.getWidth();
		int height = gameCrab.getHeight();
		int xL = gameCrab.getXLoc() + width/2;
		int yL = gameCrab.getYLoc() + height/2;


		//grassBucket = 1
		if(xL >= grassBucketXLoc && xL <= grassBucketXLoc + grassBucketWidth && 
				yL >= grassBucketYLoc && yL <= grassBucketYLoc + grassBucketHeight){
			gameCrab.setCurrObject(1);
		}
		//seawallBucket = 2
		else if(xL >= seawallBucketXLoc && xL <= seawallBucketXLoc + seawallBucketWidth && 
				yL >= seawallBucketYLoc && yL <= seawallBucketYLoc + seawallBucketHeight){
			gameCrab.setCurrObject(2);
		}
		//gabionBucket = 3
		else if(xL >= gabionBucketXLoc && xL <= gabionBucketXLoc + gabionBucketWidth && 
				yL >= gabionBucketYLoc && yL <= gabionBucketYLoc + gabionBucketHeight){
			gameCrab.setCurrObject(3);
		}
	}
	
	/**
	 * Sets crab's current object if it matches the specific objectNum given and if crab is within specific bucket
	 * @param objectNum object
	 */
	public void setSpecificObjectFromBucket(int objectNum){
		int width = gameCrab.getWidth();
		int height = gameCrab.getHeight();
		int xL = gameCrab.getXLoc() + width/2;
		int yL = gameCrab.getYLoc() + height/2;
		
		//grassBucket = 1
		if((xL >= grassBucketXLoc && xL <= grassBucketXLoc + grassBucketWidth && 
				yL >= grassBucketYLoc && yL <= grassBucketYLoc + grassBucketHeight) && 
				objectNum == 1){
			gameCrab.setCurrObject(1);
		}
		//seawallBucket = 2
		else if((xL >= seawallBucketXLoc && xL <= seawallBucketXLoc + seawallBucketWidth && 
				yL >= seawallBucketYLoc && yL <= seawallBucketYLoc + seawallBucketHeight) && 
				objectNum == 2){
			gameCrab.setCurrObject(2);
		}
		//gabionBucket = 3
		else if((xL >= gabionBucketXLoc && xL <= gabionBucketXLoc + gabionBucketWidth && 
				yL >= gabionBucketYLoc && yL <= gabionBucketYLoc + gabionBucketHeight) && 
				objectNum == 3){
			gameCrab.setCurrObject(3);
		}
	}

	

	/**
	 * generates an oyster in a random cell that can hold an oyster object
	 */
	public void spawnOyster(){
		Random rand = new Random();
		boolean spawned = false;
		int x = 0;
		while(!spawned && x != 10){
			int randNumRows = rand.nextInt(numRows-1);
			int randNumCols = rand.nextInt(seawallStartCell.getY());
			x++;
			BeachCell tempCell = grid[randNumRows][randNumCols];
			if(tempCell.getCanHoldOyster()){
				Oyster tempO = new Oyster(tempCell.getXLoc(), tempCell.getYLoc());
				gameOysters.add(tempO);
				spawned = true;
			}	
		}
	}

	/**
	 * makes waves for each cell column that a boat passes through
	 * @param b boat
	 */
	public void makeWaves(Boat b){
		WaveCell tempCell;
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

	/**
	 * resets hasWave for a cell
	 * @param cell cell
	 * @param size size of boat
	 */
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

	/**
	 * resets a a wave cell once a boat passes through it
	 * @param b boat
	 */
	public void resetWaves(Boat b){
		if(b.getDirection() == 0){
			WaveCell forwardCell = inWhichWaveCell(b.getXLoc());
			WaveCell backCell = inWhichWaveCell(b.getXLoc() - b.getSpeed());
			if(backCell != forwardCell){
				resetWaveBasedOnBoatSize(forwardCell, b.getSize());
			}
		}
		else if(b.getDirection() == 1){
			WaveCell forwardCell = inWhichWaveCell(b.getXLoc() + b.getWidth() + cellWidth);
			WaveCell backCell = inWhichWaveCell(b.getXLoc() + b.getWidth() + b.getSpeed() + cellWidth );
			if(backCell != forwardCell){
				resetWaveBasedOnBoatSize(backCell, b.getSize());
			}
		}
	}

	/**
	 * removes a wave from the game waves list when it has hit a sand cell
	 */
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

	/**
	 * removes a barrier from given the xLoc and yLoc of the cell (same as its own xLoc and yLoc)
	 * @param xL x-location
	 * @param yL y-location
	 */
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


	/**
	 * removes barriers with zero-health barriers, updates cells that can hold barriers/has barriers
	 */
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

	/**
	 * sets what cells can hold barriers
	 * @param tempCell temporary cell
	 */
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

	/**
	 * updates right adjacent cells status on whether it can hold a barrier or not
	 * @param tempCell
	 */
	public void setRightAdjacent(BeachCell tempCell){
		if(grid[tempCell.getX()+2][tempCell.getY()] != null && !grid[tempCell.getX()+2][tempCell.getY()].getHasBarrier())
			grid[tempCell.getX()+1][tempCell.getY()].setCanHoldBarrier(true);
	}
	/**
	 * updates left adjacent cells status on whether it can hold a barrier or not
	 * @param tempCell
	 */
	public void setLeftAdjacent(BeachCell tempCell){
		if(grid[tempCell.getX()-2][tempCell.getY()] != null && !grid[tempCell.getX()-2][tempCell.getY()].getHasBarrier())
			grid[tempCell.getX()-1][tempCell.getY()].setCanHoldBarrier(true);
	}
	/**
	 * initializes waveCell to false
	 */
	public void initializeWaveCells(){
		for(int i = 0; i < numRows; i++){
			waveCells[i] = new WaveCell(cellWidth, i);
		}
	}

	/**
	 * removes oysters from the list once it is picked up
	 * @param s crab
	 */
	public void removeOyster(ShoreCrab s){
		Iterator<Oyster> it = gameOysters.iterator();
		int xL = s.getXLoc();
		int yL = s.getYLoc();
		int w = s.getWidth();
		int h = s.getHeight() ;
		while(it.hasNext()) {
			Oyster bye = it.next();
			int xLoc = bye.getXLoc();
			int yLoc = bye.getYLoc();
			int width = bye.getWidth();
			int height = bye.getHeight();
			BeachCell tempCell = inWhichCell(xLoc+1, yLoc+1);
			if(tempCell != null && !tempCell.getCanHoldOyster()){
				it.remove();
			}
			else 
				if((
					(xL > xLoc && xL < xLoc + width)   || (xL + w > xLoc && xL + w < xLoc + width)) &&
					((yL > yLoc && yL < yLoc + height) || (yL + h > yLoc && yL + h < yLoc + height))
					||
					((xLoc > xL && xLoc < xL + w )|| (xLoc + width > xL && xLoc + width < xL + w)) &&
					((yLoc > yL && yLoc < yL + h) ||(yLoc + height > yL && yLoc + height < yL + h))
					){
				it.remove();
				gameCrab.setNumOysters(gameCrab.getNumOysters() + 1);
			}
		}
	}
	
	/**
	 * updates current shore health
	 */
	public void updateCurrentCellsHealth(){
		int total = 0;
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(grid[i][j].getType() == 0){
					total += grid[i][j].getHealth();
				}
			}
		}
		currentCellsHealth = total;
	}
	
	public void updateCurrentShoreHealth(){
		int dif = (totalCellsHealth - currentCellsHealth);
		currentShoreHealth = totalShoreHealth - dif;
	}


	/**
	 * adds a grass object to the game grass list
	 * @param x x-location
	 * @param y y-location
	 */
	public void addGrass(int x, int y){
		Grass g1 = new Grass(x,y);
		gameGrass.add(g1);
	}

	/**
	 * adds a seawall object to the game seawall list
	 * @param x x-location
	 * @param y y-location
	 */
	public void addWall(int x, int y){
		Seawall s1 = new Seawall(x,y);
		gameWalls.add(s1);
	}

	/**
	 * adds a gabion object to the game gabion list
	 * @param x x-location
	 * @param y y-location
	 */
	public void addGabion(int x, int y){
		OysterGabion s1 = new OysterGabion(x,y);
		gameGabions.add(s1);
	}


	//GETTERS
	public int getScreenWidth(){
		return this.screenWidth;
	}
	
	public int getScreenHeight(){
		return screenHeight;
	}
	
	public int getNumRows(){
		return numRows;
	}
	
	public int getNumCols(){
		return numCols;
	}
	
	public BeachCell[][] getGrid(){
		return grid;
	}

	public ArrayList<Grass> getGameGrass() {
		return gameGrass;
	}

	public ArrayList<Seawall> getGameSeawalls() {
		return gameWalls;
	}

	public ArrayList<Oyster> getGameOysters(){
		return gameOysters;
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

	public BeachCell getCrabTopLeftCell(){
		return crabTopLeftCell;
	}

	public BeachCell getCrabBottomLeftCell(){
		return crabBottomLeftCell;
	}


	public int getSeawallBucketXLoc(){
		return seawallBucketXLoc;
	}

	public int getSeawallBucketYLoc(){
		return seawallBucketYLoc;
	}

	public int getSeawallBucketWidth(){
		return seawallBucketWidth;
	}

	public int getSeawallBucketHeight(){
		return seawallBucketHeight;
	}


	public int getGrassBucketXLoc(){
		return grassBucketXLoc;
	}

	public int getGrassBucketYLoc(){
		return grassBucketYLoc;
	}

	public int getGrassBucketWidth(){
		return grassBucketWidth;
	}

	public int getGrassBucketHeight(){
		return grassBucketHeight;
	}


	public int getGabionBucketXLoc(){
		return gabionBucketXLoc;
	}

	public int getGabionBucketYLoc(){
		return gabionBucketYLoc;
	}

	public int getGabionBucketWidth(){
		return gabionBucketWidth;
	}

	public int getGabionBucketHeight(){
		return gabionBucketHeight;
	}
	
	public int getCurrentShoreHealth(){
		return currentShoreHealth;
	}
	
	public int getTotalShoreHealth(){
		return totalShoreHealth;
	}
	
	public int getFeaturesBarWidth(){
		return this.featuresBarWidth;
	}
	
	public int getFeaturesBarHeight(){
		return this.featuresBarHeight;
	}
	
	

}
