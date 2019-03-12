package de.juli.phaseten;

import java.util.List;

import org.junit.Test;

import de.juli.phaseten.model.GameModus;
import de.juli.phaseten.model.PlaySession;
import de.juli.phaseten.model.Player;
import de.juli.phaseten.model.PlayerGroup;
import de.juli.phaseten.service.SessionService;
import de.juli.phaseten.util.RandomModel;

public class PostSessionTest {

	@Test
	public void test() {
		SessionService service = new SessionService();
		
		PlayerGroup group = RandomModel.fetchRandomGroup();
		System.out.println(group.toString());
		
		List<Player> players = group.getPlayers();
		players.forEach(p -> System.out.println(p));
		PlaySession session = new PlaySession();
		group.addSession(session);
		
		GameModus gm = RandomModel.fetchRandomGameModus();
		System.out.println(gm.toString());
		session = service.addNewGame(session, gm);
		
		System.out.println(session.toString());
		System.out.println(session.getGames().toString());
		//session.addGame(game);
	}

}
