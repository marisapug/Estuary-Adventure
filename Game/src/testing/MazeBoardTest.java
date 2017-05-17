package testing;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import model.Litter;
import model.MazeBoard;
import model.MazeCell;
import model.MazeWall;
import model.PlayerScore;
import model.PowerUp;
import model.Predator;

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
		MazeBoard tB = new MazeBoard(1,500,500);
		PowerUp pow1 = tB.getGamePowerUps().get(0);
		assertTrue(tB.hitAnyPowerUps(pow1.getXLoc(), pow1.getYLoc(), 10, 10));
		assertFalse(tB.hitAnyPowerUps(-100,-100, 10, 10));
	}
	
	@Test 
	public void insertScoreTest(){
		MazeBoard tB = new MazeBoard(0,500,500);
		PlayerScore[] highscores = tB.getHighScores();
		assertTrue(tB.getHighScores()[0] == null);
		tB.insertScore("LOGAN!!!!" , 9001);
		assertEquals(tB.getHighScores()[0].getScore(), 9001);
		
		for(int i = 0; i < 9; i++){
			tB.insertScore("LOGAN!!!!" , 9001);
		}

		tB.insertScore("LOGAN!!!!" , 8000);
		assertEquals(tB.getHighScores()[0].getScore(), 9001);
		
	}
	
	@Test
	public void readRightScoresTest(){
		MazeBoard tB = new MazeBoard(0,500,500);
		assertTrue(tB.getHighScores()[0] == null);
		try {
			tB.readScoresFromFile();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			tB.writeScoresToFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(tB.getHighScores()[0] == null);
	}
	
	@Test
	public void readWriteBadWordsTest(){
		MazeBoard tB = new MazeBoard(0,500,500);
		assertFalse(tB.getBadWordsList() == null);
		try {
			tB.readBadWordsFromFile();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			tB.writeBadWordsToFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(tB.getBadWordsList().get(0) == null);
	}
	
	@Test
	public void addBadWordToListTest(){
		MazeBoard tB = new MazeBoard(0,500,500);
		int origSize = tB.getBadWordsList().size();
		tB.addBadWordToList("doodoo");
		int endSize = tB.getBadWordsList().size();

		assertTrue(endSize > origSize);
	}
	
	@Test
	public void setRandomDirectionTest(){
		MazeBoard tB = new MazeBoard(0,500,500);
		Predator p1 = new Predator(-200,-200,0,10,10);
		Predator p2 = new Predator(-200,-200,1,10,10);
		Predator p3 = new Predator(-200,-200,2,10,10);
		Predator p4 = new Predator(-200,-200,3,10,10);
		tB.getPredators().add(p1);
		tB.getPredators().add(p2);
		tB.getPredators().add(p3);
		tB.getPredators().add(p4);
		int predDir = p1.getDirection();
		tB.moveAllPredators();
		assertTrue(p1.getDirection() == predDir);
		MazeWall mw = new MazeWall(-195,-195, 205, -195, 0);
		MazeWall mw1 = new MazeWall(-200,-200, 205, -195, 0);
		tB.getMazeWalls().add(mw);
		tB.getMazeWalls().add(mw1);
		tB.setRandomDirections();
		assertFalse(p1.getDirection() == predDir);
	}
	
	@Test
	public void hitAnyPredsTest(){
		MazeBoard tB = new MazeBoard(0,500,500);
		Predator p1 = new Predator(-200,-200,0,10,10);
		tB.getPredators().add(p1);
		assertTrue(!tB.hitAnyPreds(p1.getXLoc(), p1.getYLoc(), p1.getWidth(), p1.getHeight()));
	}
	
}
