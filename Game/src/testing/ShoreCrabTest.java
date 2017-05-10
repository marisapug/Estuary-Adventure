package testing;

import static org.junit.Assert.*;
import model.ShoreCrab;

import org.junit.Test;

public class ShoreCrabTest {

	@Test
	public void constructorTest() {
		ShoreCrab s1 = new ShoreCrab(0,0);
		assertEquals(s1.getXLoc(),0);
		assertEquals(s1.getYLoc(),0);
		assertEquals(s1.getXIncr(),6);
		assertEquals(s1.getYIncr(),6);
		assertEquals(s1.getWidth(),50);
		assertEquals(s1.getHeight(),50);
		assertEquals(s1.getCurrObject(),0);
	}

	@Test
	public void getterTest() {
		ShoreCrab s1 = new ShoreCrab(10,10);
		assertEquals(s1.getXLoc(),10);
		assertEquals(s1.getYLoc(),10);
		assertEquals(s1.getXIncr(),6);
		assertEquals(s1.getYIncr(),6);
		assertEquals(s1.getWidth(),50);
		assertEquals(s1.getHeight(),50);
		assertEquals(s1.getCurrObject(),0);
	}
	
	
	@Test
	public void setterTest() {
		ShoreCrab s1 = new ShoreCrab(10,10);
		s1.setNumOysters(5);
		assertEquals(s1.getNumOysters(),5);
		s1.setCurrObject(10);
		assertEquals(s1.getCurrObject(),10);
	}
	
	@Test
	public void moveTest() {
		ShoreCrab s1 = new ShoreCrab(10,10);
		s1.move(10,10);
		assertEquals(s1.getXLoc(),20);
		assertEquals(s1.getYLoc(),20);
	}


}
