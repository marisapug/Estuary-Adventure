package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Litter;
import model.PowerUp;

public class PowerUpTest {

	@Test
	public void constructorTest() {
		PowerUp p = new PowerUp(0,10,10);
		assertEquals(p.getType(), 0);
		assertEquals(p.getXLoc(), 10);
		assertEquals(p.getYLoc(), 10);
		assertEquals(p.getWidth(), 30);
		assertEquals(p.getHeight(), 30);
	}
	

	@Test
	public void getterTest() {
		PowerUp p = new PowerUp(2,100,100);
		assertEquals(p.getType(), 2);
		assertEquals(p.getXLoc(), 100);
		assertEquals(p.getYLoc(), 100);
		assertEquals(p.getWidth(), 30);
		assertEquals(p.getHeight(), 30);
	}

	@Test
	public void hitTest(){
		PowerUp p = new PowerUp(10,10,10);
		assertEquals(p.hitPowerUp(10, 10, 0, 0),false);
		assertEquals(p.hitPowerUp(10, 10, 0, 0),false);
		assertEquals(p.hitPowerUp(100, 200, 0, 0),false);
		assertEquals(p.hitPowerUp(500, 1000, 0, 0),false);
		assertEquals(p.hitPowerUp(20, 5, 10, 10),true);
		assertEquals(p.hitPowerUp(20, 50, 100, 100),false);
		PowerUp p2 = new PowerUp(0,10,10);
		assertEquals(p2.hitPowerUp(20, 70, 100, 100),false);
		assertEquals(p2.hitPowerUp(0, 0, 0, 0),false);
		assertEquals(p2.hitPowerUp(10, 10, 4, 4),true);
		assertEquals(p2.hitPowerUp(1,12,1,1),false);
		assertEquals(p2.hitPowerUp(0,20,0,0),false);
	}	
	
	@Test
	public void movePowerUpTest(){
		PowerUp p = new PowerUp(2,100,100);
		p.movePowerUp(5,5);
		assertEquals(p.getXLoc(), 105);
		assertEquals(p.getYLoc(), 105);
	}


		
}