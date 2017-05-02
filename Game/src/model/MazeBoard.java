package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

public class MazeBoard {
	private int numRows;
	private int numCols;
	private int xStartIndex;
	private int yStartIndex;
	private int xEndIndex;
	private int yEndIndex;
	private int screenWidth;
	private int screenHeight;
	private static int cellWidth = 200;
	private static int cellHeight = 200;
	
	private int easyNumRows = 10;
	private int easyNumCols = 10;
	private int mediumNumRows = 20;
	private int mediumNumCols = 20;
	private int hardNumRows = 25;
	private int hardNumCols = 25;
	
	private int characterW;
	private int characterH;
	
	
	
	ArrayList<MazeCell> stack = new ArrayList<MazeCell>();
	ArrayList<MazeCell> correctPath = new ArrayList<MazeCell>();
	MazeCell[][] grid;
	
	//WALLS
	ArrayList<MazeWall> mazeWalls = new ArrayList<MazeWall>();
	
	//LITTER
	private int numLitter;
	Litter[] gameLitter;
	
	//PRETEDTORS
	private int numPred;
	private int predSpeed;
	private int predWidth;
	private int predHeight;
	ArrayList<Predator> predators = new ArrayList<Predator>();
	
	//POWER UPs
	private int numPowerUps;
//	private PowerUp[] gamePowerUps;
	private ArrayList<PowerUp> gamePowerUps = new ArrayList<PowerUp>();

	//CONSTRUCTOR initializes board
	public MazeBoard(int dif, int sWidth, int sHeight){
		
		if(dif == 0){
		numRows = easyNumRows;
		numCols = easyNumCols;
		}else if(dif == 1){
		numRows = mediumNumRows;
		numCols = mediumNumCols;
		}else{
		numRows = hardNumRows;
		numCols = hardNumCols;
		}
		
		characterW = cellWidth/4;
		characterH = cellHeight/4;
		
		xStartIndex = 0;
		yStartIndex = 0;
		xEndIndex = numRows-1;
		yEndIndex = numCols-1;
		//pixels the litter moves
		
		screenWidth = sWidth;
		screenHeight = sHeight;
		
		//initializes grid
		grid = new MazeCell[numRows][numCols];
		makeGrid(xStartIndex, yStartIndex);
		//generates maze from grid
		generateMaze(grid[xStartIndex][yStartIndex]);
		//resets all visited attributes of every cell to false
		resetVisited();
		//generates the correct path from a given start and end
		generateShortestPath(grid[xStartIndex][yStartIndex], grid[xEndIndex][yEndIndex]);
		getPath();
		
		//Initializes all walls
		setWalls();
		
		//generates litter 
		numLitter = numRows;
		gameLitter = generateLitter(numLitter);
		
		//power ups initialization
		numPowerUps = 4;
		generatePowerUps(numPowerUps);
		
		//predator initialization
		numPred = numRows;
		predSpeed = 3;
		predWidth = 70;
		predHeight = 70;
		generatePredators(numPred);
		
		
	}

	//Makes grid based on user num rows and cols input
	void makeGrid(int xStart, int yStart){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				grid[i][j] = new MazeCell(i, j, cellWidth, cellHeight, xStart, yStart, screenWidth, screenHeight);
			}
		}
	}
	
	//Checks neighbors of a cell that havent been visited
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

	//Recursively creates Maze by removing walls in cells
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
	
	//Checks neighbors without walls between them and havent been visited
	MazeCell checkCorrectNeighbors(MazeCell curr){
		ArrayList<MazeCell> neighbors = new ArrayList<MazeCell>();
		
		if(!curr.hasTopWall)
			curr.correctTop = grid[curr.y-1][curr.x];
		if(!curr.hasBottomWall)
			curr.correctBottom = grid[curr.y+1][curr.x];	
		if(!curr.hasRightWall)
			curr.correctRight = grid[curr.y][curr.x+1];
		if(!curr.hasLeftWall)
			curr.correctLeft = grid[curr.y][curr.x-1];
		
		if(curr.correctTop!=null && !curr.correctTop.visited)
			neighbors.add(curr.correctTop);
		if(curr.correctBottom!=null && !curr.correctBottom.visited)
			neighbors.add(curr.correctBottom);
		if(curr.correctLeft!=null && !curr.correctLeft.visited)
			neighbors.add(curr.correctLeft);
		if(curr.correctRight!=null && !curr.correctRight.visited)
			neighbors.add(curr.correctRight);
		
		if(neighbors.size() != 0){
			Random r = new Random();
			int rand = r.nextInt(neighbors.size());
			return neighbors.get(rand);
		} else
			return curr;
	}
	
	//resets visited of every cell to false
	void resetVisited(){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				grid[i][j].visited = false;
			}
		} 
	}
	
	//Generates the correct path from one maze cell to another
	void generateShortestPath(MazeCell curr, MazeCell end){
		curr.isCorrectPath = true;
		curr.visited = true;
		if(curr.x != end.x || curr.y != end.y){
			MazeCell next = checkCorrectNeighbors(curr);
			MazeCell prev;
			if(next.x != curr.x || next.y != curr.y){
				stack.add(curr);
				generateShortestPath(next,end);
			}
			else if(stack.size() > 0){
				prev = stack.get(stack.size() - 1);
				stack.remove(stack.size() - 1);
				curr.isCorrectPath = false;
				generateShortestPath(prev,end);
			}
		}
	}
	
	//adds all the cells on the correct path to an array list correctPath
	void getPath(){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(grid[i][j].isCorrectPath){
					correctPath.add(grid[i][j]);
				}
			}
		} 
	}

	//removes walls between two cells
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

	//Checks if something has hit a wall of the maze
	public boolean hitGridWalls(int xCor, int yCor, int xIncr, int yIncr, int dir){
		boolean result = false;
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(grid[i][j].hitCellWall(xCor,yCor,xIncr,yIncr,dir)){
					result = true;
				}
			}
		}
		return result;
	}

	//moves the grid by and x and y increment
	public void moveGrid(int xIncr, int yIncr){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				grid[i][j].moveCell(xIncr, yIncr);
			}
		}
		for(Litter lit: gameLitter){
			lit.moveLitter(xIncr,yIncr);
		}
		for(MazeWall wall: mazeWalls){
			wall.moveWall(xIncr,yIncr);
		}
		for(Predator pred: predators){
			pred.movePred(xIncr,yIncr);
		}
		for(PowerUp pow: gamePowerUps){
			pow.movePowerUp(xIncr,yIncr);
		}
	}
	
	//checks if a given x and y location falls on a correct path cell
	public boolean isOnCorrectPath(int x, int y){
		for(MazeCell m : correctPath){
			if(x >= m.xLoc && x <= m.xLoc + m.width &&
					y >= m.yLoc && y <= m.yLoc + m.height){
				return true;
			}
		}
		return false;
	}

	//returns cell that contains the given x and y location
	public MazeCell inWhichCell(int xL, int yL){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(xL >= grid[i][j].xLoc && xL <= grid[i][j].xLoc + cellWidth &&
						yL >= grid[i][j].yLoc && yL <= grid[i][j].yLoc + cellHeight){
					return grid[i][j];
				}
			}
		}
		return grid[0][0];
	}
	
	//-------------------------------------------------------------------------------
	//WALL STUFF-------------------------------------------------------------------
	//--------------------------------------------------------------------------------
	private void setWalls(){
		for(int i = 0; i < numRows; i++){
			for(int j =0; j < numCols; j++){
				MazeCell currG = grid[i][j];
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
					mazeWalls.add(tempWall);
				}
				if(currG.getHasBottomWall()){
					MazeWall tempWall = new MazeWall(bottomLX, bottomLY, bottomRX, bottomRY, 0);
					mazeWalls.add(tempWall);
				}
				if(currG.getHasRightWall()){
					MazeWall tempWall = new MazeWall(topRX, topRY, bottomRX, bottomRY, 1);
					mazeWalls.add(tempWall);
				}
				if(currG.getHasLeftWall()){
					MazeWall tempWall = new MazeWall(topLX, topLY, bottomLX, bottomLY, 1);
					mazeWalls.add(tempWall);
				}
			}
		}
	}
	
	public boolean isHittingAnyWalls(int xLoc, int yLoc, int w, int h){
		for(MazeWall wall: mazeWalls){
			if(wall.isHittingWall(xLoc, yLoc, w, h)){
				return true;
			}
		}
		return false;
	}
	
	
	//LITTER STUFF----------------------------------------------------------
	
	//gets a random cell from the grid
	public MazeCell getRandomCell(){
		Random rand = new Random();
		int x = rand.nextInt(numRows-1);
		int y = rand.nextInt(numCols-1);
		return grid[x][y];
	}
	
	//generates given amount of litter randomly throughout maze
	public Litter[] generateLitter(int amount){ 
		Litter[] pile = new Litter[amount];
		Random rand = new Random();
		for(int i = 0; i < amount; i++){
			int litInd = rand.nextInt(4); 
			MazeCell cell = getRandomCell();
			Litter lit = new Litter(litInd, cell.getXLoc(),cell.getYLoc() + cellHeight/3);
			pile[i] = lit;
		}
		return pile;
	}
	
	//floats all pieces of litter
	public void floatAllLitterRight(){
		for(Litter lit: gameLitter){
			lit.floatLitterRight();
		}
	}
	
	//floats all pieces of litter
	public void floatAllLitterLeft(){
		for(Litter lit: gameLitter){
			lit.floatLitterLeft();
		}
	}
	
	//checks if anyLitter is hit
	public boolean hitAnyLitter(int xL, int yL, int w, int h){
		for(Litter lit: gameLitter){
			if(lit.hitLitter(xL, yL, w, h)){
				return true;
			}
		}
		return false;
	}

	//----------------------------------------------------------------------------------
	//PREDATOR STUFF-------------------------------------------------------------------
	//-------------------------------------------------------------------------------------
	
	public void generatePredators(int amount){
		Random rand = new Random();
		for(int i = 0; i < amount; i++){
			int predDir = rand.nextInt(4); 
			MazeCell cell = getRandomCell();
			Predator pred = new Predator(cell.getXLoc()+cellWidth/3,cell.getYLoc()+cellHeight/3, predDir, predWidth, predHeight);
			predators.add(pred);
		}
	}
	
	public void setRandomDirections(){
		for(Predator pred: predators){
			if(pred.getDirection() == 0){
				if(isHittingAnyWalls(pred.getXLoc(), pred.getYLoc() - predSpeed , predWidth, predHeight)){
					pred.setRandomDirection(pred.getDirection());
				}
			}else if(pred.getDirection() == 1){
				if(isHittingAnyWalls(pred.getXLoc(), pred.getYLoc() + predSpeed , predWidth, predHeight)){
					pred.setRandomDirection(pred.getDirection());
				}
			}else if(pred.getDirection() == 2){
				if(isHittingAnyWalls(pred.getXLoc() + predSpeed, pred.getYLoc() , predWidth, predHeight)){
					pred.setRandomDirection(pred.getDirection());
				}
			}else if(pred.getDirection() == 3){
				if(isHittingAnyWalls(pred.getXLoc() - predSpeed, pred.getYLoc() , predWidth, predHeight)){
					pred.setRandomDirection(pred.getDirection());
				}
			}
		}
	}
	
	public void moveAllPredators(){
		for(Predator pred: predators){
			if((pred.getDirection() == 0) &&
				isHittingAnyWalls(pred.getXLoc(), pred.getYLoc() - predSpeed , predWidth, predHeight)){
					
			}else if((pred.getDirection() == 1)&&
				isHittingAnyWalls(pred.getXLoc(), pred.getYLoc() + predSpeed , predWidth, predHeight)){
					
			}else if((pred.getDirection() == 2) &&
				isHittingAnyWalls(pred.getXLoc() + predSpeed, pred.getYLoc() , predWidth, predHeight)){
				
			}else if((pred.getDirection() == 3) &&
				isHittingAnyWalls(pred.getXLoc() - predSpeed, pred.getYLoc() , predWidth, predHeight)){
					
			}
			else pred.move(predSpeed, pred.getDirection());
		}
	}
	
	//checks if anyLitter is hit
	public boolean hitAnyPreds(int xL, int yL, int w, int h){
		for(Predator pred: predators){
			if(pred.hitPred(xL, yL, w, h)){
				return true;
			}
		}
		return false;
	}
	
	
	//===== POWER UPS ======
	
	public void generatePowerUps(int amount){
		Random rand = new Random();
		for(int i = 0; i < amount; i++){
			int puType = rand.nextInt(3); 
			MazeCell cell = getRandomCell();
			PowerUp pu = new PowerUp(puType,cell.getXLoc()+cellWidth/3,cell.getYLoc()+cellHeight/3);
			gamePowerUps.add(pu);
		}
	}
	
	//checks if any power up is hit
	public boolean hitAnyPowerUps(int xL, int yL, int w, int h){
		for(PowerUp pu: gamePowerUps){
			if(pu.hitPowerUp(xL, yL, w, h)){
				return true;
			}
		}
		return false;
	}
	
	
	
	//GETTERS-----------------------------------------------------------------------------------------
	
	public MazeCell[][] getGrid(){
		return grid;
	}
	
	public int getNumRows(){
		return this.numRows;
	}

	public int getNumCols(){
		return this.numCols;
	}


	public int getcellWidth(){
		return this.cellWidth;
	}

	public int getcellHeight(){
		return this.cellHeight;
	}

	public int getYStart(){
		return yStartIndex;
	}

	public int getXStart(){
		return xStartIndex;
	}
	
	public int getYEnd(){
		return yEndIndex;
	}

	public int getXEnd(){
		return xEndIndex;
	}
	
	public int getCellWidth(){
		return cellWidth;
	}
	
	public int getCellHeight(){
		return cellHeight;
	}
	
	public int getCharacterWidth(){
		return characterW;
	}
	
	public int getCharacterHeight(){
		return characterH;
	}
	
	public ArrayList<MazeCell> getCorrectPath(){
		return correctPath;
	}
	
	public ArrayList<MazeWall> getMazeWalls(){
		return mazeWalls;
	}
	
	public Litter[] getGameLitter(){
		return gameLitter;
	}
	
	public ArrayList<PowerUp> getGamePowerUps(){
		return gamePowerUps;
	}
	
	public ArrayList<Predator> getPredators(){
		return predators;
	}

	//SETTERS
	public void setNumRows(int rows){
		this.numRows = rows;
	}

	public void setNumCols(int cols){
		this.numCols = cols;
	}


	public void setcellWidth(int width){
		this.cellWidth = width;
	}

	public void setcellHeight(int height){
		this.cellHeight = height;
	}

	public void setYStart(int y){
		this.yStartIndex = y;
	}

	public void setXStart(int x){
		 this.xStartIndex = x;
	}
	


}