package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.*;

public class DiceGameTest {
	
	@Test
	public void setterTest() {
		DiceGame dg = new DiceGame(100, 200, 10);
		dg.setAnimNum(3);
		assertEquals(dg.getAnimNum(), 3);
		dg.setDiceStory("This is a dice story");
		assertEquals(dg.getDiceStory(), "This is a dice story");
		dg.setNumDice(4);
		assertEquals(dg.getNumDice(), 4);
		dg.setNumImgs(17);
		assertEquals(dg.getNumImgs(), 17);
		Die[] dicearr = {new Die(1,2,3,4,5,6,7), new Die(10,9,8,7,6,5,4)};
		dg.diceSetter(dicearr);
		assertEquals(dg.getDice(), dicearr);
		Die tmp = new Die(3,3,3,3,3,3,3);
		dg.setDieElem(tmp, 0);
		assertEquals(dg.getDieElem(0), tmp); 
		dg.setCurseWord("curseword");
		assertEquals(dg.getCurseWord(), "curseword");
		dg.setImgBools();
		boolean isAllFalse = true;
		for(boolean b: dg.imgBools){
			if(b)
				isAllFalse = false;
		}
		assertEquals(isAllFalse, true);
	}
	
	@Test
	public void separateStoryTest(){
		DiceGame dg = new DiceGame(100,100,100);
		dg.separateStory("This is a story to be separated.");
		String[] tmpstory = {"this", "is", "a", "story", "to", "be", "separated"};
		boolean isEqual = true;
		for(int i = 0; i < 7; i++){//7 is the number of words in both arrays
			if(tmpstory[i].compareTo(dg.storyEntered[i]) != 0){
				isEqual = false;
			}
		}
		assertEquals(isEqual, true);
	}
	
	@Test
	public void isCurseWordTest(){
		DiceGame dg = new DiceGame(100,100,100);
		dg.addCurseWordToList("crap");		
		dg.addCurseWordToList("shit");
		dg.setDiceStory("This crap should say there is a curse word");
		dg.separateStory(dg.getDiceStory());
		boolean isCurse = dg.isCurseWord();
		assertEquals(isCurse, true);
		dg.setDiceStory("This story should not contain a curse word");
		dg.separateStory(dg.getDiceStory());
		isCurse = dg.isCurseWord();
		assertEquals(isCurse, false);
	}
	
	@Test
	public void setDiceTest(){
		DiceGame dg = new DiceGame(200,100,40);
		dg.setDice();
		Die[] tmpDice = dg.getDice();
		boolean dieImgNum = true;
		for(Die d: tmpDice){
			if(d.getDieImgNum() < 0 || d.getDieImgNum() > dg.getNumImgs()){
				dieImgNum = false;
			}
		}
		assertEquals(dieImgNum, true);
		//boolean 
	}

}
