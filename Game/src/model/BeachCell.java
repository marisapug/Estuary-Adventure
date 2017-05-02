package model;

public class BeachCell {
	int x; //location in grid
	int y; //location in grid
	int width;
	int height;
	String matterType;
	// int xLoc for pixels
	//int yLoc for pixels

	BeachCell(int xInp, int yInp, String matter){
		x = xInp;
		y = yInp;
		matterType = matter;
		width = 200;
		height = 200;
	}
}
