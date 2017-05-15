package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.GameFigure;

public class GameFigureTest {

	@Test
	public void gameFigureTest() {
		GameFigure gf = new GameFigure();
		gf.setXVel(3);
		gf.setYVel(3);
		assertEquals(gf.getXVel(),3);
		assertEquals(gf.getYVel(),3);
		
	}

}
