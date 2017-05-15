package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Barrier;

public class BarrierTest {

	@Test
	public void barrierTest() {
		Barrier b = new Barrier();
		b.setXLoc(3);
		b.setYLoc(2);
		b.setWidth(9);
		b.setHeight(10);
		assertEquals(b.getXLoc(),3);
		assertEquals(b.getYLoc(),2);
		assertEquals(b.getWidth(),9);
		assertEquals(b.getHeight(),10);
	}

}
