package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import model.BeachBoard;
import model.Boat;
import model.Grass;
import model.Wave;
import model.WaveCell;

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
		BeachBoard b = new BeachBoard(6,6,4,4);
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

	@Test
	public void addersTest(){
		BeachBoard b = new BeachBoard(6,6,4,4);
		b.addGrass(0, 0);
		b.addWall(0, 0);
		b.addGabion(0, 0);
		assertEquals(b.getGameGrass().size(),1);
		assertEquals(b.getGameSeawalls().size(),1);
		assertEquals(b.getGameGabs().size(),1);
	}

	@Test
	public void placeObjectTest(){
		BeachBoard b = new BeachBoard(6,6,4,4);
		b.placeObject(0, 1, 1, 2, 2);
	}

	@Test
	public void setObjectFromBucketTest(){
		BeachBoard b = new BeachBoard(10,10,1000,800);
		b.setObjectFromBucket();
		assertEquals(b.getGameCrab().getCurrObject(),0);
		int crabX = b.getGrassBucketXLoc();
		int crabY = b.getGrassBucketYLoc();
		b.getGameCrab().setXLoc(crabX);
		b.getGameCrab().setYLoc(crabY);
		b.getGameCrab().setWidth(2);
		b.getGameCrab().setHeight(2);
		b.setObjectFromBucket();
		assertEquals(b.getGameCrab().getCurrObject(),1);
		crabX = b.getSeawallBucketXLoc();
		crabY = b.getSeawallBucketYLoc();
		b.getGameCrab().setXLoc(crabX);
		b.getGameCrab().setYLoc(crabY);
		b.setObjectFromBucket();
		assertEquals(b.getGameCrab().getCurrObject(),2);
		crabX = b.getGabionBucketXLoc();
		crabY = b.getGabionBucketYLoc();
		b.getGameCrab().setXLoc(crabX);
		b.getGameCrab().setYLoc(crabY);
		b.setObjectFromBucket();
		assertEquals(b.getGameCrab().getCurrObject(),3);
	}
	
	@Test
	public void setSpecificObjectFromBuckTest(){
		BeachBoard b = new BeachBoard(10,10,1000,800);
		b.setSpecificObjectFromBucket(0);
		assertEquals(b.getGameCrab().getCurrObject(),0);
		int crabX = b.getGrassBucketXLoc();
		int crabY = b.getGrassBucketYLoc();
		b.getGameCrab().setXLoc(crabX);
		b.getGameCrab().setYLoc(crabY);
		b.getGameCrab().setWidth(2);
		b.getGameCrab().setHeight(2);
		b.setSpecificObjectFromBucket(1);
		assertEquals(b.getGameCrab().getCurrObject(),1);
		crabX = b.getSeawallBucketXLoc();
		crabY = b.getSeawallBucketYLoc();
		b.getGameCrab().setXLoc(crabX);
		b.getGameCrab().setYLoc(crabY);
		b.setSpecificObjectFromBucket(2);
		assertEquals(b.getGameCrab().getCurrObject(),2);
		crabX = b.getGabionBucketXLoc();
		crabY = b.getGabionBucketYLoc();
		b.getGameCrab().setXLoc(crabX);
		b.getGameCrab().setYLoc(crabY);
		b.setSpecificObjectFromBucket(3);
		assertEquals(b.getGameCrab().getCurrObject(),3);
	}
	
	@Test
	public void spawnOysterTest(){
		BeachBoard b = new BeachBoard(10,10,1000,800);
		b.spawnOyster();
		assertEquals(b.getGameOysters().size(),1);
	}

	@Test
	public void makeWavesTest(){
		BeachBoard b = new BeachBoard(10,10,1000,800);
		Boat boat = new Boat(500,0,0,0,0,0,0);
		Boat boat2 = new Boat(200,0,0,1,0,0,0);
		Boat boat3 = new Boat(300,0,1,0,0,0,0);
		Boat boat4 = new Boat(400,0,2,0,0,0,0);
		b.makeWaves(boat);
		b.makeWaves(boat2);
		b.makeWaves(boat3);
		b.makeWaves(boat4);
		assertEquals(b.getGameWaves().size(),1);
	}
	
	@Test
	public void resetWaveBasedOnBoatSizeTest(){
		BeachBoard b = new BeachBoard(10,10,1000,800);
		WaveCell wc = new WaveCell(5,1);
		b.resetWaveBasedOnBoatSize(wc, 0);
		b.resetWaveBasedOnBoatSize(wc, 1);
		b.resetWaveBasedOnBoatSize(wc, 2);
	}
	
	@Test
	public void resetWavesTest(){
		BeachBoard b = new BeachBoard(10,10,1000,800);
		Boat boat = new Boat(500,0,0,0,10,0,0);
		Boat boat2 = new Boat(500,0,0,1,-50,0,0);
		b.resetWaves(boat);
		b.resetWaves(boat2);
	}
	
	@Test
	public void removeHitWavesTest(){
		BeachBoard b = new BeachBoard(10,10,1000,800);
		Wave w = new Wave(1,1,0,50);
		b.getGameWaves().add(w);
		b.removeHitWaves();
	}
	
	@Test
	public void removeBarrierTest(){
		BeachBoard b = new BeachBoard(10,10,1000,800);
		
	}
	
}
