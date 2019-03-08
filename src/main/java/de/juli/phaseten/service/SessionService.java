package de.juli.phaseten.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.juli.phaseten.controller.Controller;
import de.juli.phaseten.model.Game;
import de.juli.phaseten.model.GameModus;
import de.juli.phaseten.model.Model;
import de.juli.phaseten.model.Phase;
import de.juli.phaseten.model.PhaseItem;
import de.juli.phaseten.model.PlaySession;
import de.juli.phaseten.model.Player;
import de.juli.phaseten.model.Sheed;
import de.juli.phaseten.util.CreateTestData;

public class SessionService {
	Controller<Model> controller = new Controller<>(); 
	
	/**
	 * @param session
	 * @param modus
	 * @return
	 */
	public PlaySession newGame(PlaySession session, GameModus modus) {
		List<Player> players = session.getPlayerGroup().getPlayers();
		List<Sheed> sheeds = new ArrayList<Sheed>();
		Game game = (Game) controller.create(new Game((Player) CreateTestData.randomWinner(players), session));
		players.forEach(m -> createSeeds(m, modus, sheeds, game));
		assert (players.size() == sheeds.size());
		controller.create(game);
		if(game.getId() != null) {
			session.addGame(game);			
			return session;
		}
		return null;
	}

	private void createSeeds(Player player, GameModus modus, List<Sheed> sheeds, Game game) {
		Sheed sheed = new Sheed(player, game);
		
		for (PhaseItem item : modus.getPhaseItems()) {
			switch (item.getNumber()) {
			case 1:
				sheed.setPhase01((Phase) controller.create(new Phase(item, 0)));
				break;
			case 2:
				sheed.setPhase02((Phase) controller.create(new Phase(item, 0)));
				break;
			case 3:
				sheed.setPhase03((Phase) controller.create(new Phase(item, 0)));
				break;
			case 4:
				sheed.setPhase04((Phase) controller.create(new Phase(item, 0)));
				break;
			case 5:
				sheed.setPhase05((Phase) controller.create(new Phase(item, 0)));
				break;
			case 6:
				sheed.setPhase06((Phase) controller.create(new Phase(item, 0)));
				break;
			case 7:
				sheed.setPhase07((Phase) controller.create(new Phase(item, 0)));
				break;
			case 8:
				sheed.setPhase08((Phase) controller.create(new Phase(item, 0)));
				break;
			case 9:
				sheed.setPhase09((Phase) controller.create(new Phase(item, 0)));
				break;
			case 10:
				sheed.setPhase10((Phase) controller.create(new Phase(item, 0)));
				break;
				
			default:
				break;
			}
		}
		sheed = (Sheed) controller.create(sheed);
		sheeds.add(sheed);
	}

}
