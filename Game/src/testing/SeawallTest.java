package testing;

import static org.junit.Assert.*;
import model.Seawall;

import org.junit.Test;

public class SeawallTest {

	@Test
	public void constructorTest(){
		Seawall g1 = new Seawall(0,0);
		assertEquals(g1.getXLoc(), 0);
		assertEquals(g1.getYLoc(), 0);
	}


}
