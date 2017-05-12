package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Litter;
import model.Predator;
import java.util.Random;

public class PredatorTest {

	@Test
	public void constructorTest() {
		Predator p1 = new Predator(10,10,2,15,25);
		assertEquals(p1.getDirection(),2);
		assertEquals(p1.getXLoc(),10);
		assertEquals(p1.getYLoc(),10);
		assertEquals(p1.getWidth(),15);
		assertEquals(p1.getHeight(),25);
	}

	@Test
	public void movePredTest(){
		Predator p1 = new Predator(10,10,2,15,25);
		p1.movePred(10, 15);
		assertEquals(p1.getXLoc(),20);
		assertEquals(p1.getYLoc(),25);
	}
	
	@Test
	public void moveTest(){
		Predator p1 = new Predator(10,10,2,15,25);
		p1.move(5,0);
		assertEquals(p1.getYLoc(),5);
		Predator p2 = new Predator(10,10,2,15,25);
		p2.move(5,1);
		assertEquals(p2.getYLoc(),15);
		Predator p3 = new Predator(10,10,2,15,25);
		p3.move(5,3);
		assertEquals(p3.getYLoc(),10);
		Predator p4 = new Predator(10,10,2,15,25);
		p4.move(5,2);
		assertEquals(p4.getXLoc(), 15);
	}

	@Test
	public void setRandomTest(){
		Predator p1 = new Predator(10,10,2,15,25);
		p1.setRandomDirection(1);
		assertEquals(((p1.getDirection() == 0) ||
						(p1.getDirection() == 1) ||
						(p1.getDirection() == 2) ||
						(p1.getDirection() == 3) ||
						(p1.getDirection() == 4)),true);
		assertEquals((p1.getDirection() == 5),false);
	}
	
	@Test
	public void hitPredTest(){
		Predator p1 = new Predator(10,10,10,10,10);
		assertEquals(p1.hitPred(10, 10, 0, 0),false);
		assertEquals(p1.hitPred(10, 10, 0, 0),false);
		assertEquals(p1.hitPred(100, 200, 0, 0),false);
		assertEquals(p1.hitPred(500, 1000, 0, 0),false);
		assertEquals(p1.hitPred(20, 5, 10, 10),false);
		assertEquals(p1.hitPred(20, 50, 100, 100),false);
		Predator p2 = new Predator(53,70,10,500,10);
		assertEquals(p2.hitPred(20, 70, 100, 100),true);
		assertEquals(p2.hitPred(0, 0, 0, 0),false);
		assertEquals(p2.hitPred(10, 10, 4, 4),false);
		assertEquals(p2.hitPred(1,12,1,1),false);
		assertEquals(p2.hitPred(0,20,0,0),false);
	}
	


}