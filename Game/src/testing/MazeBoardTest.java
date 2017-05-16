package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Litter;
import model.MazeBoard;
import model.MazeCell;
import model.MazeWall;
import model.PowerUp;

public class MazeBoardTest {

	@Test
	public void mazeBoardConstructorTest() {
		MazeBoard tempBoard = new MazeBoard(200,200);
		assertEquals(tempBoard.getGameLitter().size(), 1);
		assertEquals(tempBoard.getPredators().size(), 1);
		assertEquals(tempBoard.getGamePowerUps().size(), 1);
		assertEquals(tempBoard.getTutWalls().size(), 1);
	}
	
	@Test
	public void moveGridTest(){
		MazeBoard tempBoard = new MazeBoard(500,500);
		MazeCell tempCell = tempBoard.getGrid()[0][0];
		int orgX = tempCell.getXLoc();
		int orgY = tempCell.getYLoc();
		tempBoard.moveGrid(5,5);
		assertEquals(tempCell.getXLoc(), orgX + 5);
		assertEquals(tempCell.getYLoc(), orgY + 5);
	}
	
	@Test
	public void isOnCorrectPathTest(){
		MazeBoard tempBoard = new MazeBoard(2,500,500);
		MazeCell tempCell = tempBoard.getGrid()[0][0];
		assertTrue(tempBoard.isOnCorrectPath(tempCell.getXLoc(),tempCell.getYLoc()));
		tempBoard.getCorrectPath().remove(tempCell);
		assertFalse(tempBoard.isOnCorrectPath(tempCell.getXLoc(),tempCell.getYLoc()));
	}

	@Test
	public void inWhichCellTest(){
		MazeBoard tempBoard = new MazeBoard(2,500,500);
		MazeCell tempCell = tempBoard.getGrid()[0][0];
		assertEquals(tempBoard.inWhichCell(tempCell.getXLoc(), tempCell.getYLoc()), tempCell);
		assertEquals(tempBoard.inWhichCell(-1,-1), tempCell);
	}
	
	@Test
	public void inHittingAnyWallsTest(){
		MazeBoard tB = new MazeBoard(500,500);
		MazeCell tC = tB.getGrid()[0][0];
		MazeWall tC2 = tB.getTutWalls().get(0);
		assertTrue(tB.isHittingAnyWalls(tC.getXLoc(), tC.getYLoc(), 10, 10));
		assertTrue(tB.isHittingAnyWalls(tC2.getStartX(), tC2.getStartY(), 10, 10));
	}
	
	@Test
	public void floatLitterTest(){
		MazeBoard tB = new MazeBoard(500,500);
		Litter lit1 = tB.getGameLitter().get(0);
		int orgX = lit1.getXLoc();
		tB.floatAllLitterRight();
		assertTrue(lit1.getXLoc() > orgX);
		int orgX2 = lit1.getXLoc();
		tB.floatAllLitterLeft();
		assertTrue(lit1.getXLoc() < orgX2);
	}
	
	@Test
	public void hitAnyLitterTest(){
		MazeBoard tB = new MazeBoard(500,500);
		Litter lit1 = tB.getGameLitter().get(0);
		assertTrue(tB.hitAnyLitter(lit1.getXLoc(), lit1.getYLoc(), 10, 10));
		assertFalse(tB.hitAnyLitter(-100,-100, 10, 10));
	}
	
	@Test
	public void hitAnyPowerUpTest(){
		MazeBoard tB = new MazeBoard(500,500);
		PowerUp pow1 = tB.getGamePowerUps().get(0);
		assertTrue(tB.hitAnyPowerUps(pow1.getXLoc(), pow1.getYLoc(), 10, 10));
		assertFalse(tB.hitAnyPowerUps(-100,-100, 10, 10));
	}
	
}
