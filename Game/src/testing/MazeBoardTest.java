package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.MazeBoard;
import model.MazeCell;

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
	public void makeGridTest(){
		MazeBoard tempBoard = new MazeBoard(1,500,500);
	}

}
