package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.*;

public class DiceGameTest {

	@Test
	public void getterTest(){
		
	}
	
	@Test
	public void setterTest() {
		DiceGame dg = new DiceGame(100, 200, 10);
		dg.setAnimNum(3);
		assertEquals(dg.getAnimNum(), 3);
		dg.setDiceStory("This is a dice story");
		assertEquals(dg.getDiceStory(), "This is a dice story");
		
		//dicestory
		
	}

}
