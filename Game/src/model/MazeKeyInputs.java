package model;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import model.MazeBoard;

public class MazeKeyInputs extends KeyAdapter {

	private MazeBoard board;
	private Crab testCrab;
	private int crabDir;
	private int xIncr;
	private int yIncr;
	
	public MazeKeyInputs(MazeBoard b, Crab c){
		board = b;
		testCrab = c;
		xIncr = testCrab.getXIncr();
		yIncr = testCrab.getYIncr();
	}
	
	/**
	 * Moves crab image up on screen
	 */
	public void up(){
		testCrab.setDir(0);
		crabDir = testCrab.getDir();

		testCrab.setYVel(yIncr);
	}
	
	/**
	 * Moves crab image down on screen
	 */
	public void down(){
		//checks crab type, sets direction accordingly
		if(testCrab.getType() == 1){
			testCrab.setDir(0);
		}
		else{
			testCrab.setDir(1);
		}

		crabDir = testCrab.getDir();
		testCrab.setYVel(-yIncr);
	}
	
	/**
	 * Moves crab image left on screen
	 */
	public void left(){
		//checks crab type, sets direction accordingly
		if(testCrab.getType() == 1){
			testCrab.setDir(0);
		}
		else{
			testCrab.setDir(3);
		}

		crabDir = testCrab.getDir();
		testCrab.setXVel(xIncr);
	}
	
	/**
	 * Moves crab image right on screen
	 */
	public void right(){
		//checks crab type, sets direction accordingly
		if(testCrab.getType() == 1){
			testCrab.setDir(0);
		}
		else{
			testCrab.setDir(2);
		}
		crabDir = testCrab.getDir();
		testCrab.setXVel(-xIncr);
	}
	/**
	 * Handles events that occur when a key is pressed during the game
	 * @param arg0 key event
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_UP){
			up();
		}
		else if(code == KeyEvent.VK_DOWN){
			down();
		}
		else if(code == KeyEvent.VK_LEFT){
			left();
		}
		else if(code == KeyEvent.VK_RIGHT){
			right();
		}
	}

	/**
	 * Handles events that occur when a key is released during the game
	 * @param arg0 key event
	 */
	/**
	 * Handles events that occur when a key is released during the game
	 * @param arg0 key event
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_UP){
			testCrab.setYVel(0);
		}
		else if(code == KeyEvent.VK_DOWN){
			testCrab.setYVel(0);
		}
		else if(code == KeyEvent.VK_LEFT){
			testCrab.setXVel(0);
		}
		else if(code == KeyEvent.VK_RIGHT){
			testCrab.setXVel(0);
		}
	}
}
