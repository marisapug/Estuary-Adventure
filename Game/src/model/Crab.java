package model;

/**
 * Crab gives the specific information needed for the crab 
 * that it utilized during the maze game, including the type, health,
 * age, and direction. Crab is a subclass of GameFigure.
 * 
 * @author Logan
 *
 */
public class Crab extends GameFigure {
	private int type; //0 horshoecrab, 1 bluecrab
	private int health;
	private int ageState;
	private int dir; // north 0, south 1, east 2, west 3

	/**
	 * Constructer that initializes the health, age, x-location, y-location of the crab for the maze game
	 * @param h health of the crab
	 * @param a age of the crab
	 * @param xl x-location of the crab
	 * @param yl y-location of the crab
	 */
	public Crab(int h, int a, int xl, int yl){
		health = h;
		ageState = a;
		xIncr = 6;
		yIncr = 6;
		xLoc = xl;
		yLoc = yl;
	}

	//Movers
	/**
	 * Decrements the x-location of the crab by the current x-increment
	 */
	public void moveLeft(){
		xLoc = xLoc - xIncr;
	}

	/**
	 * Increments the x-location of the crab x-increment by the current x-increment
	 */
	public void moveRight(){
		xLoc = xLoc + xIncr;
	}

	/**
	 * Decrements the y-location of the crab by the current y-increment
	 */
	public void moveUp(){
		yLoc = yLoc - yIncr;
	}

	/**
	 * Increments the y-location of the crab by the current y-increment
	 */
	public void moveDown(){
		yLoc = yLoc + yIncr;
	}

	/**
	 * Decrements the health of the crab by 1
	 */
	public void loseHealth(){
		health = health - 1;
	}

	/**
	 * Increments the age state of the crab by 1
	 */
	public void grow(){
		ageState = ageState + 1;
	}

	//Getters
	public int getXLoc(){
		return this.xLoc;
	}

	public int getYLoc(){
		return this.yLoc;
	}

	public int getXIncr(){
		return this.xIncr;
	}

	public int getYIncr(){
		return this.yIncr;
	}

	public int getHealth(){
		return this.health;
	}

	public int getAgeState(){
		return this.ageState;
	}

	public int getDir(){
		return this.dir;
	}

	public int getType(){
		return this.type;
	}


	//Setters
	public void setXLoc(int x){
		this.xLoc = x;
	}

	public void setYLoc(int y){
		this.yLoc = y;
	}

	public void setXIncr(int xi){
		this.xIncr = xi;
	}

	public void setYIncr(int yi){
		this.yIncr = yi;
	}

	public void setHealth(int h){
		this.health = h;
	}

	public void setAgeState(int a){
		this.ageState = a;
	}

	public void setDir(int d){
		this.dir = d;
	}

	public void setType(int t){
		this.type = t;
	}

}
