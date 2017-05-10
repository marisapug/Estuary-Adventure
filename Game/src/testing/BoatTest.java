package testing;

import static org.junit.Assert.*;
import model.Boat;

import org.junit.Test;

public class BoatTest {

	@Test
	public void constructorTest(){
		Boat b1 = new Boat(0,0,10,10,10,100,100);
		assertEquals(b1.getSize(), 10);
		assertEquals(b1.getDirection(), 10);
		assertEquals(b1.getXLoc(), 0);
		assertEquals(b1.getYLoc(), 0);
		assertEquals(b1.getSpeed(), 10);
		assertEquals(b1.getWidth(), 100);
		assertEquals(b1.getHeight(), 100);
	}
		
	@Test
	public void moveTest(){
		Boat b1 = new Boat(0,0,10,0,5,100,100);
		b1.move();
		Boat b2 = new Boat(10,10,10,1,5,100,100);
		b2.move();
		assertEquals(b1.getXLoc(), 5);
		assertEquals(b1.getXLoc(), 5);
	}

	@Test
	public void setterTest(){
		Boat b1 = new Boat(0,0,10,0,5,100,100);
		b1.setXLoc(20);
		b1.setYLoc(30);
		b1.setSpeed(40);
		assertEquals(b1.getXLoc(), 20);
		assertEquals(b1.getYLoc(), 30);
		assertEquals(b1.getSpeed(), 40);
	}
	
	@Test
	public void getterTest(){
		Boat b1 = new Boat(100,100,30,40,50,100,100);
		assertEquals(b1.getXLoc(), 100);
		assertEquals(b1.getYLoc(), 100);
		assertEquals(b1.getSize(), 30);
		assertEquals(b1.getDirection(), 40);
		assertEquals(b1.getSpeed(), 50);
		assertEquals(b1.getWidth(), 100);
		assertEquals(b1.getHeight(), 100);
	}



}
