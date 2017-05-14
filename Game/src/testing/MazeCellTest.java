package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.MazeCell;

public class MazeCellTest {

	@Test
	public void mazeCellConstructorTest() {
		MazeCell m = new MazeCell(0,0,0,0,0,0,0,0);
		assertEquals(m.getX(), 0);
		assertEquals(m.getY(), 0);
		assertEquals(m.getWidth(), 0);
		assertEquals(m.getHeight(), 0);
		assertEquals(m.getXLoc(), 0);
		assertEquals(m.getYLoc(), 0);
	}
	
	@Test
	public void moveMazeCellTest(){
		MazeCell m = new MazeCell(0,0,0,0,0,0,0,0);
		m.moveCell(1, 1);
		assertEquals(m.getXLoc(),1);
		assertEquals(m.getYLoc(),1);
	}
	
	@Test
	public void inCellTest(){
		MazeCell m = new MazeCell(0,0,0,0,0,0,0,0);
		assertEquals(m.inCell(0, 0), true);
	}
	
	@Test
	public void hitCellWallTest(){
		MazeCell m = new MazeCell(0,0,0,0,0,0,0,0);
		assertEquals(m.hitCellWall(0, 0, 0, 0, 0), true);
		assertEquals(m.hitCellWall(0, 0, 0, 0, 1), true);
		assertEquals(m.hitCellWall(0, 0, 0, 0, 2), true);
		assertEquals(m.hitCellWall(0, 0, 0, 0, 3), true);
	}

	@Test
	public void extraGettersTest(){
		MazeCell m = new MazeCell(0,0,0,0,0,0,0,0);
		assertEquals(m.getHasBottomWall(),true);
		assertEquals(m.getHasTopWall(),true);
		assertEquals(m.getHasLeftWall(),true);
		assertEquals(m.getHasRightWall(),true);
	}
	
	@Test
	public void settersTest(){
		MazeCell m = new MazeCell(0,0,0,0,0,0,0,0);
		m.setY(3);
		assertEquals(m.getY(),3);
		m.setX(3);
		assertEquals(m.getX(),3);
		m.setXLoc(2);
		assertEquals(m.getXLoc(),2);
		m.setYLoc(2);
		assertEquals(m.getYLoc(),2);
		m.setHasBottomWall(false);
		assertEquals(m.getHasBottomWall(), false);
		m.setHasTopWall(false);
		assertEquals(m.getHasTopWall(), false);
		m.setHasRightWall(false);
		assertEquals(m.getHasRightWall(), false);
		m.setHasLeftWall(false);
		assertEquals(m.getHasLeftWall(), false);
	}
}
