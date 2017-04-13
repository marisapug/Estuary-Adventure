package model;

public class MazeBoard {
	Crab crab;
	int[][] backGround;
	
	public MazeBoard(int h, int a, int s, int xL, int yL){
		crab = new Crab(h, a, s, xL,yL);
	}
}
