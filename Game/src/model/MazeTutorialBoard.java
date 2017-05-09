package model;

import java.util.ArrayList;

public class MazeTutorialBoard {

	private int screenWidth;
	private int screenHeight;
	
	private MazeCell[] grid;
	private int numRows = 20;
	
	private int cellWidth;
	private int cellHeight;

	
	private Litter tutLitter;
	private int litterType = 0;
	private int litterStartIndex = 5;
	
	private Predator tutPredator;
	private int predatorStartIndex = 10;
	private int predWidth = 50;
	private int predHeight = 50;
	
	private PowerUp tutPowerUp;
	private int powerUpStartIndex = 15;
	
	private ArrayList<MazeWall> tutWalls;
	
	public MazeTutorialBoard(int cellW, int cellH){
		cellWidth = cellW;
		cellHeight = cellH;
		generateTutGrid(0,0);
		setUpTutObjects();
		setUpTutWalls();
	}
	
	public void generateTutGrid(int xStart, int yStart){
		grid[0] = new MazeCell(0, 0, cellWidth, cellHeight, xStart, yStart, screenWidth, screenHeight);
		grid[0].setHasRigthWall(false);
		for(int i = 1; i < numRows-1; i++){
			grid[i] = new MazeCell(i, 0, cellWidth, cellHeight, xStart, yStart, screenWidth, screenHeight);
			grid[i].setHasLeftWall(false);
			grid[i].setHasRigthWall(false);
		}
		grid[numRows-1] = new MazeCell(numRows-1, 0, cellWidth, cellHeight, xStart, yStart, screenWidth, screenHeight);
		grid[numRows-1].setHasLeftWall(false);
	}

	public void setUpTutObjects(){
		tutLitter = new Litter(litterType, litterStartIndex * cellWidth, grid[litterStartIndex].getYLoc() + cellHeight/2);
		tutPredator = new Predator(predatorStartIndex * cellWidth, grid[predatorStartIndex].getYLoc() + cellHeight/2, 0, predWidth, predHeight);
		tutPowerUp = new PowerUp(0, powerUpStartIndex * cellWidth, grid[powerUpStartIndex].getYLoc() + cellHeight/2);
	}
	
	public void moveTutGrid(int xIncr, int yIncr){
		for(int i = 0; i < numRows; i++){
			grid[i].moveCell(xIncr,yIncr);
		}
		tutPredator.move(xIncr, yIncr);
		tutLitter.moveLitter(xIncr, yIncr);
		tutPowerUp.movePowerUp(xIncr, yIncr);
	}

	public void setUpTutWalls(){
		for(int i = 0; i < numRows; i++){
			MazeCell currG = grid[i];
			int topLX = currG.getXLoc(); //top left corner x value
			int topLY = currG.getYLoc(); //top left corner y value
			int topRX = topLX + currG.getWidth(); //top right corner x value
			int topRY = topLY; //top right corner y value
			int bottomLX = topLX; //bottom left corner x value
			int bottomLY = topLY + currG.getHeight(); //bottom left corner y value
			int bottomRX = topRX; //bottom right corner x value
			int bottomRY = bottomLY; //bottom right corner y value

			if(currG.getHasTopWall()){
				MazeWall tempWall = new MazeWall(topLX, topLY, topRX, topRY, 0);
				tutWalls.add(tempWall);
			}
			if(currG.getHasBottomWall()){
				MazeWall tempWall = new MazeWall(bottomLX, bottomLY, bottomRX, bottomRY, 0);
				tutWalls.add(tempWall);
			}
			if(currG.getHasRightWall()){
				MazeWall tempWall = new MazeWall(topRX, topRY, bottomRX, bottomRY, 1);
				tutWalls.add(tempWall);
			}
			if(currG.getHasLeftWall()){
				MazeWall tempWall = new MazeWall(topLX, topLY, bottomLX, bottomLY, 1);
				tutWalls.add(tempWall);
			}
		}
	}
	
	public boolean isHittingAnyWalls(int xLoc, int yLoc, int w, int h){
		for(MazeWall wall: tutWalls){
			if(wall.isHittingWall(xLoc, yLoc, w, h)){
				return true;
			}
		}
		return false;
	}

	
	public ArrayList<MazeWall> getTutWalls(){
		return tutWalls;
	}
	
}
