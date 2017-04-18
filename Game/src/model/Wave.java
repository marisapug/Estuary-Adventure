package model;

import java.util.Random;

public class Wave {
	int strength; // 1 = small, 2 = medium, 3 = large
	int x;
	int y;

	Wave(int s, int xc, int yc) {
		strength = s;
		xc = x;
		yc = y;
	}

	public int getStrength() {
		return this.strength;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public Wave createWave(Boat b) {
		Wave w = new Wave(b.getSize(), b.getXLoc(), b.getYLoc()); // should we
																	// just
																	// initialize
																	// y
																	// location
																	// of boats?
		return w;
	}

}
