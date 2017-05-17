package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * Class containing all elements of the Maze Game
 * includes: maze grid, predators, litters, power ups, crab, etc...
 * @author Maaz
 *
 */
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
	
	//tutorial stuff
	private Litter tutLitter;
	private int tutLitterType = 0;
	private int tutLitterStartIndex = 5;
	private int tutLitterTextIndex = tutLitterStartIndex - 1;
	private Predator tutPredator;
	private int tutPredatorStartIndex = 10;
	private int tutPredatorTextIndex = tutPredatorStartIndex - 1;
	private PowerUp tutPowerUp;
	private int tutPowerUpStartIndex = 15;
	private int tutPowerUpTextIndex = tutPowerUpStartIndex - 1;
	
	private int tutSalinityTextIndex = tutPredatorTextIndex - 1;
	private String tutSalinityText = "Use the SALINITY INDICATOR to make sure the crab is on the right path and can grow!";
	
	private int tutMiniMapTextIndex = tutPowerUpTextIndex - 1;
	private String tutMiniMapText = "Use the mini map to guide you!";
	
	private ArrayList<MazeWall> tutWalls = new ArrayList<MazeWall>();
	private boolean isTutorial = false;
	
	//===END TUTORIAL STUFF===//
	
	private int characterW;
	private int characterH;
	
	
	ArrayList<MazeCell> stack = new ArrayList<MazeCell>();
	ArrayList<MazeCell> correctPath = new ArrayList<MazeCell>();
	MazeCell[][] grid;
	
	//WALLS
	ArrayList<MazeWall> mazeWalls = new ArrayList<MazeWall>();
	
	//LITTER
	private int numLitter;
	private ArrayList<Litter> gameLitter = new ArrayList<Litter>();
	
	//PRETEDTORS
	private int numPred;
	private int predSpeed;
	private int predWidth;
	private int predHeight;
	ArrayList<Predator> predators = new ArrayList<Predator>();
	
	//POWER UPs
	private int numPowerUps;
	private ArrayList<PowerUp> gamePowerUps = new ArrayList<PowerUp>();
	
	//LEADERBOARD STUFF
	private int numScores = 10; //dont alter
	private PlayerScore[] highScores = new PlayerScore[numScores];
	private ArrayList<String> badWordsList = new ArrayList<String>();
	
	private int easyScore = 10000;
	private int mediumScore = 20000;
	private int hardScore = 30000;
	private int scoreDecrementOnHit = 500;

	/**
	 * Constructor, creates and instance of a game MazeBoard
	 * @param dif difficulty of maze (0-2)
	 * @param sWidth width of the screen
	 * @param sHeight height of the screen
	 */
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
		numLitter = (numRows * numCols)/10;
		generateLitter(numLitter);
		
		//power ups initialization
		numPowerUps = (numRows * numCols) / 20;
		generatePowerUps(numPowerUps);
		
		//predator initialization
		numPred = (numRows * numCols) / 15;
		predSpeed = 3;
		predWidth = 70;
		predHeight = 70;
		generatePredators(numPred);
		
	}
	
	/**
	 * Constructor, creates an instance of a tutorial MazeBoard
	 * @param sWidth width of screen
	 * @param sHeight height of screen
	 */
	public MazeBoard(int sWidth, int sHeight){
		screenWidth = sWidth;
		screenHeight = sHeight;
		numRows = 1;
		numCols = 20;
		isTutorial = true;
		
		characterW = cellWidth/4;
		characterH = cellHeight/4;
		
		xStartIndex = 0;
		yStartIndex = 0;
		xEndIndex = numRows-1;
		yEndIndex = numCols-1;
		
		//predSetUp
		predSpeed = 3;
		predWidth = 70;
		predHeight = 70;
		
		//initializes grid
		grid = new MazeCell[numRows][numCols];
		makeGrid(xStartIndex, yStartIndex);
		//generates maze from grid
		generateMaze(grid[0][0]);
		//resets all visited attributes of every cell to false
		resetVisited();
		//generates the correct path from a given start and end
		generateShortestPath(grid[xStartIndex][yStartIndex], grid[xEndIndex][yEndIndex]);
		getPath();
		
		setUpTutObjects();
		setWalls();
		setUpTutWalls();
	}
	
	//-----------------------------------------------------------------------------------------
	//TUTORIAL STUFF---------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------

	/**
	 * Initializes litter, predator and power up for the tutorial board
	 */
	public void setUpTutObjects(){
		tutLitter = new Litter(tutLitterType, grid[0][tutLitterStartIndex].getXLoc(), grid[0][tutLitterStartIndex].getYLoc() + cellHeight/2);
		tutPredator = new Predator(grid[0][tutPredatorStartIndex].getXLoc(), grid[0][tutPredatorStartIndex].getYLoc() + cellHeight/2 - predHeight/2, 3, predWidth, predHeight);
		tutPowerUp = new PowerUp(0, grid[0][tutPowerUpStartIndex].getXLoc(), grid[0][tutPowerUpStartIndex].getYLoc() + cellHeight/2);
		gameLitter.add(tutLitter);
		predators.add(tutPredator);
		gamePowerUps.add(tutPowerUp);
		
	}
	
	/**
	 * Initializes extra walls for the tutorial
	 */
	public void setUpTutWalls(){
		MazeCell tempCell = grid[0][tutPowerUpStartIndex];
		MazeWall tempWall = new MazeWall(tempCell.getXLoc() + cellWidth,tempCell.getYLoc(),tempCell.getXLoc() + cellWidth,tempCell.getYLoc() + cellHeight,1);
		tutWalls.add(tempWall);
	}
	
	//-----------------------------------------------------------------------------------------
	//END OF TUTORIAL STUFF---------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	
	/**
	 * Makes grid based on number of rows and number of columns 
	 * @param xStart x Location of the full Maze (top left)
	 * @param yStart y Location of the full Maze (top Left)
	 */
	void makeGrid(int xStart, int yStart){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				grid[i][j] = new MazeCell(i, j, cellWidth, cellHeight, xStart, yStart, screenWidth, screenHeight);
			}
		}
	}
	
	/**
	 * Checks neighbors (adjacent) of a cell that haven't been visited and returns a random cell
	 * @param curr MazeCell whose neighbors are being checked
	 * @return previously unvisited neighbor. If no unvisited neighbor then returns current cell
	 */
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

	/**
	 * Recursively creates Maze by removing walls in cells
	 * @param curr starting location for the construction of the maze
	 */
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
	
	/**
	 * Checks neighbors that do not have walls between them and have not been visited
	 * @param curr MazeCell whose neighbors are being checked
	 * @return Appropriate neighbor (No wall, not previously visited). if no neighbor then returns current MazeCell
	 */
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
	
	/**
	 * resets visited boolean of every MazeCell to false
	 */
	void resetVisited(){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				grid[i][j].visited = false;
			}
		} 
	}
	
	/**
	 * Generates the a path from one maze cell to another
	 * Sets boolean isCorrect path to true if a MazeCell is on the solved path
	 */
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
	
	/**
	 * Adds all the cells on the correct path to an array list correctPath
	 */
	void getPath(){
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numCols; j++){
				if(grid[i][j].isCorrectPath){
					correctPath.add(grid[i][j]);
				}
			}
		} 
	}

	/**
	 * removes walls between two adjacent cells
	 * @param a MazeCell 1
	 * @param b MazeCell 2
	 */
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



	/**
	 * moves the board by an x and y increment (including all elements)
	 * @param xIncr change in the x direction
	 * @param yIncr change in the y direction
	 */
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
		for(MazeWall wall: tutWalls){
			wall.moveWall(xIncr,yIncr);
		}
		for(Predator pred: predators){
			pred.movePred(xIncr,yIncr);
		}
		for(PowerUp pow: gamePowerUps){
			pow.movePowerUp(xIncr,yIncr);
		}
	}

	/**
	 * checks if a given x and y location falls on a correct path MazeCell
	 * @param x x location of the object
	 * @param y y location of the object
	 * @return boolean of whether the object is in a correct path MazeCell
	 */
	public boolean isOnCorrectPath(int x, int y){
		for(MazeCell m : correctPath){
			if(x >= m.xLoc && x <= m.xLoc + m.width &&
					y >= m.yLoc && y <= m.yLoc + m.height){
				return true;
			}
		}
		return false;
	}

	/**
	 * returns cell that contains the given x and y location
	 * @param xL any x location 
	 * @param yL any y location
	 * @return MazeCell containing the given x and y coordinate (if no such MazeCell exists return top left MazeCell) 
	 */
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
	/**
	 * Creates MazeWalls corresponding to the walls of the MazeCells in the grid
	 */
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
	
	/**
	 * determines if a given x and y location is hitting a wall
	 * @param xLoc
	 * @param yLoc
	 * @param w
	 * @param h
	 * @return
	 */
	public boolean isHittingAnyWalls(int xLoc, int yLoc, int w, int h){
		boolean tempBool = false;
		for(MazeWall wall: mazeWalls){
			if(wall.isHittingWall(xLoc, yLoc, w, h)){
				tempBool = true;
			}
		}
		if(isTutorial){
			for(MazeWall wall: tutWalls){
				if(wall.isHittingWall(xLoc, yLoc, w, h)){
					tempBool = true;
				}
			}
		}
		return tempBool;
	}
	
	
	//LITTER STUFF----------------------------------------------------------
	
	/**
	 * gets a random cell from the grid
	 * @return random cell
	 */
	public MazeCell getRandomCell(){
		Random rand = new Random();
		int x = rand.nextInt(numRows-1);
		int y = rand.nextInt(numCols-1);
		return grid[x][y];
	}
	
	/**
	 * generates set amount of litter randomly throughout maze
	 * @param amount amount of litter to be generated
	 */
	public void generateLitter(int amount){
		Random rand = new Random();
		for(int i = 0; i < amount; i++){
			int litInd = rand.nextInt(4); 
			MazeCell cell = getRandomCell();
			Litter lit = new Litter(litInd, cell.getXLoc(),cell.getYLoc() + cellHeight/3);
			gameLitter.add(lit);
		}
	}
	
	/**
	 * floats all pieces of litter to the right
	 */
	public void floatAllLitterRight(){
		for(Litter lit: gameLitter){
			lit.floatLitterRight();
		}
	}
	
	/**
	 * floats all pieces of litter to the left
	 */
	public void floatAllLitterLeft(){
		for(Litter lit: gameLitter){
			lit.floatLitterLeft();
		}
	}
	
	/**
	 * checks if anyLitter is hit
	 * @param xL x location of object
	 * @param yL y location of object
	 * @param w width of object
	 * @param h height of object
	 * @return boolean of whether a litter was hit (true) or not (false)
	 */
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
	/**
	 * generates a set amount of predators randomly in the board
	 * @param amount amount of predators to be generated
	 */
	public void generatePredators(int amount){
		Random rand = new Random();
		for(int i = 0; i < amount; i++){
			int predDir = rand.nextInt(4); 
			MazeCell cell = getRandomCell();
			Predator pred = new Predator(cell.getXLoc()+cellWidth/3,cell.getYLoc()+cellHeight/3, predDir, predWidth, predHeight);
			predators.add(pred);
		}
	}
	
	/**
	 * sets a random direction for predators that have hit a wall
	 */
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
	
	/**
	 * Moves all predators on the board
	 */
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
	
	/**
	 * checks if any predator is hit
	 * @param xL x location of the object
	 * @param yL y location of the object
	 * @param w width of the object
	 * @param h height of the object
	 * @return boolean of whether a predator was hit (true) or not (false)
	 */
	public boolean hitAnyPreds(int xL, int yL, int w, int h){
		for(Predator pred: predators){
			if(pred.hitPred(xL, yL, w, h)){
				return true;
			}
		}
		return false;
	}
	
	
	//===== POWER UPS ======
	
	/**
	 * generates a set amount of power ups randomly throughout the board
	 * @param amount amount of power ups to be generated
	 */
	public void generatePowerUps(int amount){
		Random rand = new Random();
		for(int i = 0; i < amount; i++){
			int puType = rand.nextInt(3); 
			MazeCell cell = getRandomCell();
			PowerUp pu = new PowerUp(puType,cell.getXLoc()+cellWidth/3,cell.getYLoc()+cellHeight/3);
			gamePowerUps.add(pu);
		}
	}
	
	/**
	 * checks if any power up is hit
	 * @param xL x location of the object
	 * @param yL y location of the object
	 * @param w width of the object
	 * @param h height of the object
	 * @return boolean of whether a power up was hit (true) or not (false)
	 */
	public boolean hitAnyPowerUps(int xL, int yL, int w, int h){
		for(PowerUp pu: gamePowerUps){
			if(pu.hitPowerUp(xL, yL, w, h)){
				return true;
			}
		}
		return false;
	}
	
	
	//HIGHSCORE UPDATING 
	/**
	 * updates leader board of the given score is ranked
	 * @param newName name of the Player
	 * @param newScore score achieved by the Player
	 */
	public void insertScore(String newName, int newScore){
		PlayerScore prevPlayer;
		PlayerScore currPlayer;
		PlayerScore tempPlayer;
		if(highScores[numScores - 1] == null || highScores[numScores - 1].getScore() < newScore){
			highScores[numScores - 1] = new PlayerScore(newName, newScore);
			
			for(int i = numScores - 2; i >= 0; i--){
				prevPlayer = highScores[i + 1];
				currPlayer = highScores[i];
				if(currPlayer == null || prevPlayer.getScore() > currPlayer.getScore()){
					tempPlayer = currPlayer;
					highScores[i] = prevPlayer;
					highScores[i + 1] = tempPlayer;
				}
			}//for
		}//if
	}//insertScore
	
	/**
	 * writes highscores to a file
	 * @throws IOException
	 */
	public void writeScoresToFile() throws IOException{
		File file = new File("highScores.tmp");
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(highScores);
		oos.close();
	}
	
	/**
	 * reads previous highscores from a file
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void readScoresFromFile() throws IOException, ClassNotFoundException{
		FileInputStream fis = new FileInputStream("highScores.tmp");
		ObjectInputStream ois = new ObjectInputStream(fis);
		highScores = (PlayerScore[]) ois.readObject();
		ois.close();
	}
	
	
		//profanity checker
	/**
	 * writes profane words to a file (if new bad words are to be added)
	 * @throws IOException
	 */
	public void writeBadWordsToFile() throws IOException{
		File file = new File("badWords.tmp");
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(badWordsList);
		oos.close();
	}
	
	/**
	 * reads into an arrayList a list of profane words
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void readBadWordsFromFile() throws IOException, ClassNotFoundException{
		FileInputStream fis = new FileInputStream("badWords.tmp");
		ObjectInputStream ois = new ObjectInputStream(fis);
		badWordsList = (ArrayList<String>) ois.readObject();
		ois.close();
	}
	
	/**
	 * adds a new word to the arrayList of bad words
	 * @param s
	 */
	public void addBadWordToList(String s){
		badWordsList.add(s);
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
	
	public ArrayList<MazeWall> getTutWalls(){
		return tutWalls;
	}
	
	public ArrayList<Litter> getGameLitter(){
		return gameLitter;
	}
	
	public ArrayList<PowerUp> getGamePowerUps(){
		return gamePowerUps;
	}
	
	public ArrayList<Predator> getPredators(){
		return predators;
	}
	
	public boolean getIsTutorial(){
		return isTutorial;
	}
	
	public int getTutLitterStartIndex(){
		return tutLitterStartIndex;
	}
	
	public int getTutPredatorStartIndex(){
		return tutPredatorStartIndex;
	}
	
	public int getTutPowerUpStartIndex(){
		return tutPowerUpStartIndex;
	}
	
	public int getTutLitterTextIndex(){
		return tutLitterTextIndex;
	}
	
	public int getTutPredatorTextIndex(){
		return tutPredatorTextIndex;
	}
	
	public int getTutPowerUpTextIndex(){
		return tutPowerUpTextIndex;
	}
	
	public int getTutSalinityTextIndex(){
		return tutSalinityTextIndex;
	}
	
	public String getTutSalinityText(){
		return tutSalinityText;
	}
	
	public int getTutMiniMapTextIndex(){
		return tutMiniMapTextIndex;
	}
	
	public String getTutMiniMapText(){
		return tutMiniMapText;
	}
	
	public int getEasyScore(){
		return easyScore;
	}
	
	public int getMediumScore(){
		return mediumScore;
	}
	
	public int getHardScore(){
		return hardScore;
	}
	
	public int getScoreDecrementOnHit(){
		return scoreDecrementOnHit;
	}
	
	public PlayerScore[] getHighScores(){
		return highScores;
	}
	
	public ArrayList<String> getBadWordsList(){
		return badWordsList;
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