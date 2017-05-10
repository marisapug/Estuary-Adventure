package testing;

import static org.junit.Assert.*;
import model.Grass;

import org.junit.Test;

import model.Boat;

public class GrassTest {

	@Test
	public void constructorTest(){
		Grass g1 = new Grass(0,0);
		assertEquals(g1.getXLoc(), 0);
		assertEquals(g1.getYLoc(), 0);
	}
	
	@Test
	public void getterTest(){
		Grass g1 = new Grass(10,10);
		assertEquals(g1.getXLoc(), 10);
		assertEquals(g1.getYLoc(), 10);
	}

}
