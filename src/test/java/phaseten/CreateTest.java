package phaseten;

import org.junit.Test;

import de.juli.phaseten.util.CreateTestData;

public class CreateTest {

	@Test
	public void test() {
		CreateTestData td = new CreateTestData();
		td.createGroups();
		td.createGameModus();
		td.createSessionsGames();
	}
}
