package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import model.BeachBoard;
import model.BeachCell;
import model.Boat;
import model.Grass;
import model.Oyster;
import model.OysterGabion;
import model.Seawall;
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
		BeachCell tempCell = b.getGrid()[2][1];
		tempCell.setType(0);
		tempCell.setHealth(-1);
		b.sandToOcean();
		assertEquals(tempCell.getType(),1);
	}
	
	@Test
	public void healCellsAboveGrassTest(){
		BeachBoard b = new BeachBoard(10,10,1000,800);
		BeachCell tempCell = b.getGrid()[5][5];
		BeachCell tempCell2 = b.getGrid()[5][4];
		int orgHealth = tempCell2.getHealth();
		tempCell2.setHealth(tempCell2.getHealth()/2);
		int midHealth = tempCell2.getHealth();
		Grass grass = new Grass(tempCell.getXLoc(),tempCell.getYLoc());
		b.getGameGrass().add(grass);
		b.healCellsAboveGrass();
		int endHealth = tempCell2.getHealth();
		
		assertTrue(midHealth < orgHealth);
		assertTrue(endHealth > midHealth);

		tempCell2.setHealth(orgHealth);
		b.healCellsAboveGrass();
		endHealth = tempCell2.getHealth();
		assertFalse(endHealth > orgHealth);
		
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
		BeachBoard b = new BeachBoard(10,10,1000,800);
		BeachCell tempCell = b.getGrid()[5][5];
		tempCell.setCanHoldGrass(true);
		tempCell.setHasGrass(false);
		b.placeObject(1, tempCell.getXLoc(), tempCell.getYLoc(), 10, 10);
		assertTrue(tempCell.getHasGrass());
		
		tempCell = b.getGrid()[5][5];
		tempCell.setCanHoldBarrier(true);
		tempCell.setHasBarrier(false);
		b.placeObject(2, tempCell.getXLoc(), tempCell.getYLoc(), 10, 10);
		assertTrue(tempCell.getHasBarrier());
		
		tempCell = b.getGrid()[5][5];
		tempCell.setCanHoldBarrier(true);
		tempCell.setHasBarrier(false);
		b.getGameCrab().setNumOysters(3);
		b.placeObject(3, tempCell.getXLoc(), tempCell.getYLoc(), 10, 10);
		assertTrue(tempCell.getHasBarrier());
		
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
		assertTrue(!wc.getHasLargeWave());
		assertTrue(!wc.getHasMediumWave());
		assertTrue(!wc.getHasSmallWave());
	}
	
	@Test
	public void resetWavesTest(){
		BeachBoard b = new BeachBoard(10,10,1000,800);
		Boat boat = new Boat(500,0,0,0,10,0,0);
		Boat boat2 = new Boat(500,0,1,1,-50,0,0);
		Boat boat3 = new Boat(500,0,2,0,20,0,0);;
		WaveCell wc = b.inWhichWaveCell(boat.getXLoc());
		b.resetWaves(boat);
		assertTrue(!wc.getHasSmallWave());
		b.resetWaves(boat2);
		assertTrue(!wc.getHasMediumWave());
		b.resetWaves(boat3);
		assertTrue(!wc.getHasLargeWave());
	}
	
	@Test
	public void removeHitWavesTest(){
		BeachBoard b = new BeachBoard(10,10,1000,800);
		BeachCell tempCell = b.getGrid()[5][5];
		Wave w = new Wave(tempCell.getXLoc(),tempCell.getYLoc(),0,50);
		b.getGameWaves().add(w);
		assertEquals(b.getGameWaves().size(),1);
		b.removeHitWaves();
		assertEquals(b.getGameWaves().size(), 0);
		Wave w1 = new Wave(tempCell.getXLoc(),b.getScreenHeight()*2,0,50);
		b.getGameWaves().add(w1);
		assertEquals(b.getGameWaves().size(),1);
		b.removeHitWaves();
		assertEquals(b.getGameWaves().size(),0);
	}
	
	
	
	@Test
	public void removeBarrierTest(){
		BeachBoard b = new BeachBoard(10,10,1000,800);
		BeachCell tempCell = b.getGrid()[0][5];
		Seawall sw = new Seawall(tempCell.getXLoc(),tempCell.getYLoc());
		b.getGameSeawalls().add(sw);
		assertEquals(b.getGameSeawalls().size(),1);
		b.removeBarrier(tempCell.getXLoc(),tempCell.getYLoc());
		assertEquals(b.getGameSeawalls().size(),0);
		
		OysterGabion gb = new OysterGabion(tempCell.getXLoc(),tempCell.getYLoc());
		b.getGameGabs().add(gb);
		assertEquals(b.getGameGabs().size(),1);
		b.removeBarrier(tempCell.getXLoc(),tempCell.getYLoc());
		assertEquals(b.getGameGabs().size(),0);
	}
	
	@Test
	public void removeDeadBarriersTest(){
		BeachBoard b = new BeachBoard(10,10,1000,800);
		BeachCell tempCell = b.getGrid()[0][5];
		tempCell.setHasBarrier(true);
		tempCell.setHealth(-1);
		assertTrue(tempCell.getHasBarrier());
		Wave w = new Wave(tempCell.getXLoc() + 25,tempCell.getYLoc() + 25,0,50);
		b.getGameWaves().add(w);
		b.removeDeadBarriers();
		assertFalse(tempCell.getHasBarrier());
	}
	
	@Test
	public void removeOysterTest(){
		BeachBoard b = new BeachBoard(10,10,1000,800);
		BeachCell tempCell = b.getGrid()[5][5];
		b.getGameCrab().setXLoc(tempCell.getXLoc());
		b.getGameCrab().setYLoc(tempCell.getYLoc());
		Oyster oyster = new Oyster(tempCell.getXLoc(),tempCell.getYLoc());
		b.getGameOysters().add(oyster);
		assertEquals(b.getGameCrab().getNumOysters(), 0);
		b.removeOyster(b.getGameCrab());
		assertEquals(b.getGameCrab().getNumOysters(),1);
	}
	
	
	@Test
	public void gettersTest(){
		BeachBoard b = new BeachBoard(10,10,1000,800);
		assertEquals(b.getCellWidth(),1000/10);
		assertEquals(b.getCellHeight(),400/10);
		assertEquals(b.getWaveSpeed(),3);
		assertEquals(b.getTotalSandHealth(),100);
		assertEquals(b.getTotalSeawallHealth(),100);
		assertEquals(b.getTotalGabionHealth(),300);
		assertEquals(b.getCrabGridStartX(),10/2 - 1);
		assertEquals(b.getCrabGridStartY(),10/2);
		assertEquals(b.getCrabTopLeftCell(),b.getGrid()[1][0]);
		assertEquals(b.getCrabBottomLeftCell(),b.getGrid()[9][0]);
		assertEquals(b.getSeawallBucketWidth(),1000/5);
		assertEquals(b.getSeawallBucketHeight(),800/10);
		assertEquals(b.getGrassBucketWidth(),1000/5);
		assertEquals(b.getGrassBucketHeight(),800/10);
		assertEquals(b.getGabionBucketWidth(),1000/5);
		assertEquals(b.getGabionBucketHeight(),800/10);
		assertEquals(b.getCurrentShoreHealth(),2666);
		assertEquals(b.getTotalShoreHealth(),2666);
		assertEquals(b.getFeaturesBarWidth(),1000);
		assertEquals(b.getFeaturesBarHeight(),800/16);
	}
	
}
