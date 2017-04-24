package model;

import java.util.Random;

public class Litter extends Obstacle {
	int type;
	MazeCell loc;
	int xLoc;
	int yLoc;
	
	public Litter(int t, MazeBoard board){
		type = t;
		Random rand = new Random();
		int xInd = rand.nextInt(board.getNumRows());
		int yInd = rand.nextInt(board.getNumCols());
		loc = board.getGrid()[xInd][yInd];
		xLoc =  loc.getXLoc() + (loc.getWidth() / 2);
		yLoc = loc.getYLoc() - (loc.getHeight() / 2);
	}
	
	public int getXLoc(){
		return xLoc;
	}
	
	public int getYLoc(){
		return yLoc;
	}
	
	public int getType(){
		return type;
	}
	
	void floatLitter(){}
}