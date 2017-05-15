package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import model.BeachBoard;
import model.Boat;
import model.Grass;

public class BeachBoardTest {

	@Test
	public void beachBoardConstructorTest() {
		BeachBoard b = new BeachBoard(4,4,4,4);
		assertEquals(b.getNumRows(), 4);
		assertEquals(b.getNumCols(), 4);
		assertEquals(b.getScreenWidth(), 4);
		assertEquals(b.getScreenHeight(), 4);
	}

	@Test
	public void spawnBoatsTest(){
		BeachBoard b = new BeachBoard(4,4,4,4);
		b.generateRandomBoat();
		assertEquals(b.getGameBoats().size(), 1);
		b.spawnSmallBoat();
		assertEquals(b.getGameBoats().size(), 2);
		b.spawnMediumBoat();
		assertEquals(b.getGameBoats().size(), 3);
		b.spawnLargeBoat();
		assertEquals(b.getGameBoats().size(), 4);
	}

	@Test
	public void inWhichCell(){
		BeachBoard b = new BeachBoard(4,4,4,4);
		assertEquals(b.inWhichCell(0, 2), b.getGrid()[0][0]);
		assertEquals(b.inWhichCell(-1, -1), null);
	}

	@Test
	public void resetShoreTest(){
		BeachBoard b = new BeachBoard(4,4,4,4);
		b.resetShore();
		assertEquals(b.getGameBoats().size(), 0);
		assertEquals(b.getGameGrass().size(), 0);
		assertEquals(b.getGameOysters().size(), 0);
		assertEquals(b.getGameSeawalls().size(), 0);
		assertEquals(b.getGameGabs().size(), 0);
	}

	@Test
	public void inWhichWaveCellTest(){
		BeachBoard b = new BeachBoard(4,4,4,4);
		assertEquals(b.inWhichWaveCell(1), b.getWaveCells()[1]);
		assertEquals(b.inWhichWaveCell(-1), null);
	}

	@Test
	public void removeBoatsOffScreenTest(){
		BeachBoard b = new BeachBoard(4,4,4,4);
		Boat boat = new Boat(1000000,0,0,0,0,0,0);
		Boat boat2 = new Boat(-1000000,0,0,1,0,0,0);
		b.getGameBoats().add(boat);
		b.removeBoatsOffScreen();
		assertEquals(b.getGameBoats().size(),0);
		b.getGameBoats().add(boat2);
		b.removeBoatsOffScreen();
		assertEquals(b.getGameBoats().size(),0);
	}

	@Test
	public void sandToOceanTest(){
		BeachBoard b = new BeachBoard(4,4,4,4);
		b.sandToOcean();
	}

	@Test
	public void removeDestroyedGrassTest(){
		Grass g = new Grass(0,0);
		BeachBoard b = new BeachBoard(4,4,4,4);
		b.getGameGrass().add(g);
		b.removeDestroyedGrass(0, 0);
		assertEquals(b.getGameGrass().size(), 0);
	}


}
