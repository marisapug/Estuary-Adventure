package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.MazeWall;

public class MazeWallTest {

	@Test
	public void mazeWallConstructorTest() {
		MazeWall testWall = new MazeWall(1,1,2,2,0);
		assertEquals(testWall.getStartX(),1);
		assertEquals(testWall.getStartY(),1);
		assertEquals(testWall.getEndX(),2);
		assertEquals(testWall.getEndY(),2);
		assertEquals(testWall.getDir(),0);
	}
	
	@Test
	public void moveCellTest(){
		MazeWall testWall = new MazeWall(1,1,2,2,0);
		testWall.moveWall(2, 3);
		assertEquals(testWall.getStartX(), 3);
		assertEquals(testWall.getEndX(), 4);
		assertEquals(testWall.getStartY(), 4);
		assertEquals(testWall.getEndY(), 5);
	}
	
	@Test
	public void isHittingWallTest(){
		MazeWall testWall = new MazeWall(1,1,2,2,0);
		MazeWall testWall2 = new MazeWall(1,1,2,2,1);
		assertEquals(testWall.isHittingWall(1, 2, 0, 0), true);
		assertEquals(testWall2.isHittingWall(1, 2, 3, 0), true);
	}

}
