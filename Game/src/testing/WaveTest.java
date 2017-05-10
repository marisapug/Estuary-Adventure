package testing;

import static org.junit.Assert.*;
import model.Wave;

import org.junit.Test;

public class WaveTest {
	
	@Test
	public void constructorTest() {
		Wave w1 = new Wave(0,1,2,3);
		assertEquals(w1.getXLoc(),0);
		assertEquals(w1.getYLoc(),1);
		assertEquals(w1.getStrength(),2);
		assertEquals(w1.getWidth(),3);
		assertEquals(w1.getHeight(),10);
	}

	@Test
	public void getterTest() {
		Wave w1 = new Wave(100,200,500,700);
		assertEquals(w1.getXLoc(),100);
		assertEquals(w1.getYLoc(),200);
		assertEquals(w1.getStrength(),500);
		assertEquals(w1.getWidth(),700);
		assertEquals(w1.getHasHit(), false);
	}
	
	
	@Test
	public void setterTest() {
		Wave w1 = new Wave(1,2,3,4);
		w1.setHasHit(true);
		assertEquals(w1.getHasHit(), true);
	}

	
	@Test
	public void moveTest() {
		Wave w1 = new Wave(10,20,30,40);
		w1.move(10);
		assertEquals(w1.getYLoc(),30);
	}
	
}
