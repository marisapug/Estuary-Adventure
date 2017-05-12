package testing;
import model.BeachCell;

import static org.junit.Assert.*;

import org.junit.Test;

public class BeachCellTest {

	@Test
	public void constructorTest() {
		BeachCell bc = new BeachCell(100,100,10,10,1,2);
		assertEquals(bc.getX(),100);
		assertEquals(bc.getY(),100);
		assertEquals(bc.getXLoc(),1000);
		assertEquals(bc.getYLoc(),1001);
		assertEquals(bc.getWidth(),10);
		assertEquals(bc.getHeight(),10);
		assertEquals(bc.getType(),2);
		assertEquals(bc.getCanHoldGrass(),false);
		assertEquals(bc.getCanHoldBarrier(),false);
		assertEquals(bc.getCanHoldOyster(),false);
		assertEquals(bc.getHasGrass(),false);
		assertEquals(bc.getHasBarrier(),false);
	}
	
	@Test
	public void setterTest() {
		BeachCell bc = new BeachCell(100,100,10,10,1,2);
		bc.setCanHoldBarrier(true);
		bc.setCanHoldGrass(true);
		bc.setCanHoldOyster(true);
		bc.setHasBarrier(true);
		bc.setHasGrass(true);
		bc.setType(1);
		bc.setHealth(10);
		assertEquals(bc.getHealth(),10);
		assertEquals(bc.getType(),1);
		assertEquals(bc.getCanHoldGrass(),true);
		assertEquals(bc.getCanHoldBarrier(),true);
		assertEquals(bc.getCanHoldOyster(),true);
		assertEquals(bc.getHasGrass(),true);
		assertEquals(bc.getHasBarrier(),true);
	}

}