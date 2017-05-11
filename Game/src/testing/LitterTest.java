package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Litter;

public class LitterTest {

	@Test
	public void constTest() {
		Litter lit = new Litter(1,0,0);
		assertEquals(lit.getType(), 1);
		assertEquals(lit.getXLoc(), 0);
		assertEquals(lit.getYLoc(), 0);
		assertEquals(lit.getWidth(), 50);
		assertEquals(lit.getHeight(), 50);
	}
	
	@Test
	public void moveTest() {
		Litter lit = new Litter(1,0,0);
		lit.moveLitter(10, 15);
		assertEquals(lit.getXLoc(),10);
		assertEquals(lit.getYLoc(),15);
	}
	
	@Test
	public void floatRTest() {
		Litter lit = new Litter(1,0,0);
		lit.floatLitterRight();
		assertEquals(lit.getXLoc(),0+lit.getFloatXIncr());
	}
	
	@Test
	public void floatLTest() {
		Litter lit = new Litter(1,0,0);
		lit.floatLitterLeft();
		assertEquals(lit.getXLoc(),0-lit.getFloatXIncr());
	}
	
	@Test
	public void getterTest() {
		Litter lit = new Litter(2,10,100);
		assertEquals(lit.getType(),2);
		assertEquals(lit.getXLoc(),10);
		assertEquals(lit.getYLoc(),100);
		assertEquals(lit.getWidth(),50);
		assertEquals(lit.getHeight(),50);
	}
	
	@Test
	public void hitLitterTest(){
		Litter lit = new Litter(10,10,10);
		assertEquals(lit.hitLitter(10, 10, 0, 0),false);
		assertEquals(lit.hitLitter(10, 10, 0, 0),false);
		assertEquals(lit.hitLitter(100, 200, 0, 0),false);
		assertEquals(lit.hitLitter(500, 1000, 0, 0),false);
		assertEquals(lit.hitLitter(20, 5, 10, 10),true);
		assertEquals(lit.hitLitter(20, 50, 100, 100),true);
		Litter lit2 = new Litter(0,10,10);
		assertEquals(lit2.hitLitter(20, 70, 100, 100),false);
		assertEquals(lit2.hitLitter(0, 0, 0, 0),false);
		assertEquals(lit2.hitLitter(10, 10, 4, 4),true);
		assertEquals(lit2.hitLitter(1,12,1,1),false);
		assertEquals(lit2.hitLitter(0,20,0,0),false);
		
		
	}


}
