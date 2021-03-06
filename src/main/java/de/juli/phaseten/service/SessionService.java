package de.juli.phaseten.service;

import java.util.ArrayList;
import java.util.List;

import de.juli.phaseten.controller.Controller;
import de.juli.phaseten.model.Game;
import de.juli.phaseten.model.GameModus;
import de.juli.phaseten.model.Model;
import de.juli.phaseten.model.Phase;
import de.juli.phaseten.model.PhaseItem;
import de.juli.phaseten.model.PlaySession;
import de.juli.phaseten.model.Player;
import de.juli.phaseten.model.Sheed;

public class SessionService {
	Controller<Model> controller = new Controller<>(); 
	
	/**
	 * Erzeugt neue Game-Instanz, fuellt diese fuer jeden Spieler mit Spielblaettern und setzt sie in die Session
	 */
	public PlaySession addNewGame(PlaySession session, GameModus modus) {
		List<Player> players = session.getPlayerGroup().getPlayers();
		List<Sheed> sheeds = new ArrayList<Sheed>();
		Game game = new Game();
		players.forEach(m -> createSeeds(m, modus, sheeds, game));
		assert (players.size() == sheeds.size());
		session.addGame(game);			
		return session;
	}

	private void createSeeds(Player player, GameModus modus, List<Sheed> sheeds, Game game) {
		Sheed sheed = new Sheed(player, game);
		
		for (PhaseItem item : modus.getPhaseItems()) {
			switch (item.getNumber()) {
			case 1:
				sheed.setPhase01(new Phase(item, 0));
				break;
			case 2:
				sheed.setPhase02(new Phase(item, 0));
				break;
			case 3:
				sheed.setPhase03(new Phase(item, 0));
				break;
			case 4:
				sheed.setPhase04(new Phase(item, 0));
				break;
			case 5:
				sheed.setPhase05(new Phase(item, 0));
				break;
			case 6:
				sheed.setPhase06(new Phase(item, 0));
				break;
			case 7:
				sheed.setPhase07(new Phase(item, 0));
				break;
			case 8:
				sheed.setPhase08(new Phase(item, 0));
				break;
			case 9:
				sheed.setPhase09(new Phase(item, 0));
				break;
			case 10:
				sheed.setPhase10(new Phase(item, 0));
				break;
				
			default:
				break;
			}
		}
		sheeds.add(sheed);
	}

}
