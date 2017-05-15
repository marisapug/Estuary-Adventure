package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Oyster;

public class OysterTest {

	@Test
	public void oysterTest() {
		Oyster o = new Oyster(3,2);
		assertEquals(o.getXLoc(),3);
		assertEquals(o.getYLoc(),2);
		assertEquals(o.getWidth(),30);
		assertEquals(o.getHeight(),30);
		
	}

}
