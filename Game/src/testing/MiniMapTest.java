package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.MiniMap;

public class MiniMapTest {

	@Test
	public void constructorTest() {
		MiniMap m = new MiniMap();
		assertEquals(m.getWidth(), 7);
		assertEquals(m.getHeight(), 7);
	}

}

