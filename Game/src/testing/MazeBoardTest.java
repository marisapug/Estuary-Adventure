package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.MazeBoard;

public class MazeBoardTest {

	@Test
	public void mazeBoardConstructorTest() {
		MazeBoard tempBoard = new MazeBoard(200,200);
		assertEquals(tempBoard.getGameLitter().size(), 1);
		assertEquals(tempBoard.getPredators().size(), 1);
		assertEquals(tempBoard.getGamePowerUps().size(), 1);
	}

}
