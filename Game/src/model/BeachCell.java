package model;

public class BeachCell {
	int x; //location in grid
	int y; //location in grid
	int width;
	int height;
	String matterType;
	// int xLoc for pixels
	//int yLoc for pixels

	BeachCell(int xInp, int yInp, int sWidth, int sHeight, String matter){
		x = xInp;
		y = yInp;
		matterType = matter;
		width = sWidth / 10; //magic num
		height = sHeight / 10;
	}
	
	//getters
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}
