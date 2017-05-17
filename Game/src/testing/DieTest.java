package testing;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import org.junit.Test;
import model.Die;

public class DieTest {

	@Test
	public void constructorTest() {
		Die d = new Die(10, 1,2,50,50,30,0);
		assertEquals(d.getXLoc(),-10);
		assertEquals(d.getYLoc(),-25);
		assertEquals(d.getStartXLoc(),1);
		assertEquals(d.getStartYLoc(),2);
	}
	
	@Test
	public void setterTest(){
		Die d = new Die(0,0,0,0,0,0,0);
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
		d.setDieImgNum(4);
		assertEquals(d.getDieImg(), bi);
		assertEquals(d.getInitXLoc(), 50);
		assertEquals(d.getInitYLoc(), 30);
		assertEquals(d.getSelection(), true);
		assertEquals(d.getStartXLoc(), 0);
		assertEquals(d.getStartYLoc(),0);
		assertEquals(d.getStoryIndex(), 4);
		assertEquals(d.getXLoc(), 0);
		assertEquals(d.getYLoc(),0);
		assertEquals(d.getDieImgNum(), 4);
	}


	@Test
	public void throwTest(){
		Die d = new Die(0,0,0,0,0,0,0);
		d.throwDie();
		assertTrue(d.getXLoc()>=-10);
		assertTrue(d.getYLoc()>=-10);
		Die d1 = new Die(1,1,1,1,1,1,1);
		d1.throwDie();
		assertTrue(d1.getDir()>=0);
		assertTrue(d1.getXLoc()>=-10);
		assertTrue(d1.getXLoc()>=-10);
		Die d2 = new Die(2,2,2,2,2,2,2);
		d2.throwDie();
		assertTrue(d2.getXLoc()>=-10);
		assertTrue(d2.getXLoc()>=-10);
		Die d3 = new Die(10,10,10,10,10,10,10);
		d3.setDir(1);
		d3.throwDie();
		d3.setStartXLoc(75);
		d3.setStartYLoc(100);
		d3.throwDie();
		assertTrue(d3.getXLoc()>=0);
		assertTrue(d3.getXLoc()>=0);
		Die d4 = new Die(10,10,10,10,10,10,10);
		d4.throwDie();
		d4.setStartXLoc(75);
		d4.setStartYLoc(100);
		d4.throwDie();
		assertTrue(d4.getXLoc()>=-5);
		assertTrue(d4.getXLoc()>=-5);
		Die d5 = new Die(-5,-5,-5,-5,-5,-5,-5);
		d5.setStartXLoc(75);
		d5.setStartYLoc(100);
		d5.throwDie();
		assertTrue(d5.getXLoc()>=-20);
		assertTrue(d5.getXLoc()>=-20);
		assertTrue(d5.getDir()>=0 && d5.getDir()<=5);
		Die d6 = new Die(-5,-5,-5,-5,-5,-5,-5);
		d6.setStartXLoc(75);
		d6.setStartYLoc(100);
		d6.throwDie();
		assertTrue(d6.getXLoc()>=-20);
		assertTrue(d6.getXLoc()>=-20);
		Die d7 = new Die(-5,-5,-5,-5,-5,-5,-5);
		d7.setStartXLoc(0);
		d7.setStartYLoc(20);
		d7.setXLoc(10);
		d7.setYLoc(10);
		d7.throwDie();
		assertTrue(d7.getXLoc()>=-20);
		assertTrue(d7.getXLoc()>=-20);
		d7.setStartXLoc(10);
		d7.setStartYLoc(10);
		d7.setXLoc(0);
		d7.setYLoc(20);
		d7.throwDie();
		assertTrue(d7.getXLoc()>=-20);
		assertTrue(d7.getXLoc()>=-20);
		d7.setStartXLoc(10);
		d7.setStartYLoc(10);
		d7.setXLoc(100);
		d7.setYLoc(20);
		d7.throwDie();
		assertTrue(d7.getXLoc()>=-20);
		assertTrue(d7.getXLoc()>=-20);
		d7.setStartXLoc(100);
		d7.setStartYLoc(10);
		d7.setXLoc(100);
		d7.setYLoc(20);
		d7.throwDie();
		assertTrue(d7.getXLoc()>=-20);
		assertTrue(d7.getXLoc()>=-20);
		d7.setStartXLoc(10);
		d7.setStartYLoc(100);
		d7.setXLoc(100);
		d7.setYLoc(10);
		d7.throwDie();
		assertTrue(d7.getXLoc()>=-20);
		assertTrue(d7.getXLoc()>=-20);
		d7.setStartXLoc(1000);
		d7.setStartYLoc(100);
		d7.setXLoc(100);
		d7.setYLoc(10);
		d7.throwDie();
		assertTrue(d7.getXLoc()>=-20);
		assertTrue(d7.getXLoc()>=-20);
	}
	

}


