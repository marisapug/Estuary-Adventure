package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import model.PlayerScore;

public class PlayerScoreTest {

	@Test
	public void playerScoreTest() {
		PlayerScore ps = new PlayerScore("Logan", 100);
		ps.setName("Maaz");
		ps.setScore(30);
		assertEquals(ps.getName(),"Maaz");
		assertEquals(ps.getScore(),30);
	}

}
