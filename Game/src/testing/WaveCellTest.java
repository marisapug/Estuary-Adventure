package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.WaveCell;

public class WaveCellTest {

	@Test
	public void waveCellTest() {
		WaveCell wc = new WaveCell(5,1);
		assertEquals(wc.getHasLargeWave(),true);
		assertEquals(wc.getHasMediumWave(),false);
		assertEquals(wc.getHasSmallWave(),true);
		assertEquals(wc.getXLoc(),5);
		assertEquals(wc.getWidth(),5);
	}

}
