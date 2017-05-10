package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.OysterGabion;

public class OysterGabionTest {

	@Test
	public void constructorTest(){
		OysterGabion g1 = new OysterGabion(0,0);
		assertEquals(g1.getXLoc(), 0);
		assertEquals(g1.getYLoc(), 0);
	}

}
