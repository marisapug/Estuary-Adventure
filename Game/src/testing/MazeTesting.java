package testing;

import static org.junit.Assert.*;
import model.Crab;
import model.MazeBoard;
import model.MazeCell;
import model.MiniMap;
import model.Litter;
import view.MazeGameView;
import org.junit.Test;

public class MazeTesting {

	//======== CRAB CLASS TESTING ======== //

	@Test
	public void crabConstructorTest() {
		Crab c1 = new Crab(3, 3, 2, 1);
		assertEquals(c1.getHealth(), 3);
		assertEquals(c1.getAgeState(), 3);
		assertEquals(c1.getXIncr(), 6);
		assertEquals(c1.getYIncr(), 6);
		assertEquals(c1.getXLoc(), 2);
		assertEquals(c1.getYLoc(), 1);
	}

	@Test
	public void crabSettersTest(){
		Crab c1 = new Crab(3, 3, 2, 1);
		c1.setXLoc(3);
		assertEquals(c1.getXLoc(), 3);
		c1.setYLoc(3);
		assertEquals(c1.getYLoc(), 3);
		c1.setXIncr(5);
		assertEquals(c1.getXIncr(), 5);
		c1.setYIncr(5);
		assertEquals(c1.getYIncr(), 5);
		c1.setHealth(2);
		assertEquals(c1.getHealth(), 2);
		c1.setAgeState(2);
		assertEquals(c1.getAgeState(), 2);
	}

	@Test
	public void crabMoversTest(){
		Crab c1 = new Crab(3, 3, 2, 1);
		c1.moveLeft();
		assertEquals(c1.getXLoc(), -4);
		c1.moveRight();
		assertEquals(c1.getXLoc(), 2);
		c1.moveUp();
		assertEquals(c1.getYLoc(), -5);
		c1.moveDown();
		assertEquals(c1.getYLoc(), 1);
	}

	@Test
	public void crabLoseHealthTest(){
		Crab c1 = new Crab(3, 3, 2, 1);
		c1.loseHealth();
		assertEquals(c1.getHealth(), 2);
	}
	
	@Test
	public void crabGrowTest(){
		Crab c1 = new Crab(3, 3, 2, 1);
		c1.grow();
		assertEquals(c1.getAgeState(), 4);
	}
	
	//======== MAZE BOARD CLASS TESTING ======== //

}
