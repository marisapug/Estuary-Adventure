package model;

/**
 * WaveCell contains information for wave cells, which indicate whether a boat can spawn a wave or not
 * @author Logan
 *
 */
public class WaveCell {

	private boolean hasSmallWave = true;
	private boolean hasMediumWave = false;
	private boolean hasLargeWave = true;
	private int width;
	private int xLoc;
	
	
	/**
	 * Constructor, creates an instance of a wave cellobject
	 * @param w width
	 * @param x x-location
	 */
	public WaveCell(int w, int x){
		width = w;
		xLoc = w * x;
	}
	
	
	//Getters
	public boolean getHasSmallWave(){
		return hasSmallWave;
	}
	
	public boolean getHasMediumWave(){
		return hasMediumWave;
	}
	
	public boolean getHasLargeWave(){
		return hasLargeWave;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getXLoc(){
		return xLoc;
	}
	
	//setters
	public void setHasSmallWave(boolean b){
		hasSmallWave = b;
	}
	
	public void setHasMediumWave(boolean b){
		hasMediumWave = b;
	}
	
	public void setHasLargeWave(boolean b){
		hasLargeWave = b;
	}
}
