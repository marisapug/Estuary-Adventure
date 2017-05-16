/*package testing;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import org.junit.Test;
import model.Die;

public class DieTest {

	@Test
	public void test() {
		Die d = new Die(10, 1,2,50,50,30);
		assertEquals(d.getXLoc(),1);
		assertEquals(d.getYLoc(),2);
		assertEquals(d.getStartXLoc(),1);
		assertEquals(d.getStartYLoc(),2);
	}
	
	@Test
	public void setterTest(){
		Die d = new Die(0,0,0,0,0,0);
		BufferedImage bi = new BufferedImage(10,10,10);
		d.setDieImg(bi);
		d.setInitXLoc(50);
		d.setInitYLov(30);
		d.setSelection(true);
		d.setStartXLoc(0);
		d.setStartYLoc(0);
		d.setStoryIndex(4);
		d.setXLoc(0);
		d.setYLoc(0);
		assertEquals(d.getDieImg(), bi);
		assertEquals(d.getInitXLoc(), 50);
		assertEquals(d.getInitYLoc(), 30);
		assertEquals(d.getSelection(), true);
		assertEquals(d.getStartXLoc(), 0);
		assertEquals(d.getStartYLoc(),0);
		assertEquals(d.getStoryIndex(), 4);
		assertEquals(d.getXLoc(), 0);
		assertEquals(d.getYLoc(),0);
	}


	@Test
	public void throwTest(){
		Die d = new Die(0,0,0,0,0,0,0);
		d.throwDie();
		assertTrue(d.getXLoc()>=-10);
		assertTrue(d.getYLoc()>=-10);
		assertTrue(d.getDir()>=0 || d.getDir()<=4);
		Die d1 = new Die(1,1,1,1,1,1,1);
		d1.throwDie();
		assertTrue(d1.getDir()>=0);
		assertTrue(d1.getXLoc()>=-10);
		assertTrue(d1.getXLoc()>=-10);
		Die d2 = new Die(2,2,2,2,2,2,2);
		d2.setDir(2);
		d2.throwDie();
		assertTrue(d2.getXLoc()>=-10);
		assertTrue(d2.getXLoc()>=-10);
		assertTrue(d2.getDir()>=0 && d2.getDir()<=4);
		Die d3 = new Die(10,10,10,10,10,10,10);
		d3.setDir(1);
		d3.throwDie();
		d3.setStartXLoc(75);
		d3.setStartYLoc(100);
		d3.finishThrowing();
		assertTrue(d3.getXLoc()>=0);
		assertTrue(d3.getXLoc()>=0);
		assertTrue(d3.getDir()>=0 && d3.getDir()<=4);
		Die d4 = new Die(10,10,10,10,10,10,10);
		d4.setDir(5);
		d4.throwDie();
		d4.setStartXLoc(75);
		d4.setStartYLoc(100);
		d4.finishThrowing();
		assertTrue(d4.getXLoc()>=0);
		assertTrue(d4.getXLoc()>=0);
		assertTrue(d4.getDir()>=0 && d4.getDir()<=4);
		Die d5 = new Die(-5,-5,-5,-5,-5,-5,-5);
		d5.setDir(0);
		d5.throwDie();
		d5.setStartXLoc(75);
		d5.setStartYLoc(100);
		d5.finishThrowing();
		assertTrue(d5.getXLoc()>=0);
		assertTrue(d5.getXLoc()>=0);
		assertTrue(d5.getDir()>=0 && d5.getDir()<=4);
		Die d6 = new Die(-5,-5,-5,-5,-5,-5,-5);
		d6.setDir(2);
		d6.throwDie();
		d6.setStartXLoc(75);
		d6.setStartYLoc(100);
		d6.finishThrowing();
		assertTrue(d6.getXLoc()>=0);
		assertTrue(d6.getXLoc()>=0);
		assertTrue(d6.getDir()>=0 && d6.getDir()<=4);
	}
	
	@Test
	public void finishTest(){
		Die d = new Die(0,0,0,0,0,0,0);
		d.setStartXLoc(10);
		d.setStartYLoc(10);
		d.finishThrowing();
		assertEquals(d.getXLoc(), 10);
		assertEquals(d.getYLoc(), 7);
		Die d1 = new Die(10,10,10,10,10,10);
		d1.setStartXLoc(10);
		d1.setStartYLoc(-5);
		d1.finishThrowing();
		Die d2 = new Die(100,100,100,100,100,100);
		d2.setStartXLoc(20);
		d2.setStartYLoc(100);
		d2.finishThrowing();
		assertEquals(d2.getXLoc(), 90);
		assertEquals(d2.getYLoc(), 100);
		Die d3 = new Die(80,80,80,80,80,80);
		d3.setStartXLoc(75);
		d3.setStartYLoc(100);
		d3.finishThrowing();
		assertEquals(d3.getXLoc(), 70);
		assertEquals(d3.getYLoc(), 87);
		Die d4 = new Die(10,10,10,10,10,10);
		d4.setXLoc(100);
		d4.setStartXLoc(0);
		d4.setStartYLoc(0);
		d4.finishThrowing();
		assertEquals(d4.getXLoc(), 90);
		assertEquals(d4.getYLoc(), 3);
		Die d5 = new Die(10,10,10,10,10,10);
		d5.setXLoc(100);
		d5.setStartXLoc(500);
		d5.setYLoc(500);
		d5.setStartYLoc(100);
		d5.finishThrowing();
		assertEquals(d5.getXLoc(), 110);
		assertEquals(d5.getYLoc(), 493);
		Die d6 = new Die(10,10,10,10,10,10);
		d6.setXLoc(100);
		d6.setStartXLoc(500);
		d6.setYLoc(500);
		d6.setStartYLoc(500);
		d6.finishThrowing();
		assertEquals(d6.getXLoc(), 110);
		assertEquals(d6.getYLoc(), 500);
		Die d7 = new Die(10,10,10,10,10,10);
		d7.setXLoc(100);
		d7.setStartXLoc(100);
		d7.setYLoc(500);
		d7.setStartYLoc(500);
		d7.finishThrowing();
		assertEquals(d7.getXLoc(), 100);
		assertEquals(d7.getYLoc(), 500);
		Die d8 = new Die(10,10,10,10,10,10);
		d8.setXLoc(100);
		d8.setStartXLoc(100);
		d8.setYLoc(501);
		d8.setStartYLoc(500);
		d8.finishThrowing();
		assertEquals(d8.getXLoc(), 100);
		assertEquals(d8.getYLoc(), 494);
	}

} */
