package de.juli.phaseten.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.juli.phaseten.controller.Controller;
import de.juli.phaseten.model.Game;
import de.juli.phaseten.model.GameModus;
import de.juli.phaseten.model.Model;
import de.juli.phaseten.model.PhaseItem;
import de.juli.phaseten.model.Player;
import de.juli.phaseten.model.PlayerGroup;
import de.juli.phaseten.service.SessionService;
import de.juli.phaseten.model.PlaySession;

public class CreateTestData {
	private static final String GROUP_1 = "Braking Bad";
	private static final String GROUP_2 = "Musiker";
	private static final String GROUP_3 = "Politiker";
	private static final String GROUP_4 = "Double Sense";

	private Controller<Model> controller = new Controller<Model>();
	private SessionService service = new SessionService();	
	public void createGroups() {
		List<PlayerGroup> models = new ArrayList<>();
		models.add(new PlayerGroup(GROUP_1));
		models.add(new PlayerGroup(GROUP_2));
		models.add(new PlayerGroup(GROUP_3));
		models.add(new PlayerGroup(GROUP_4));
		models.forEach(m -> controller.create(m));
		createPlayerWithGrpup(models);
		models.forEach(m -> controller.create(m));
	}

	private void createPlayerWithGrpup(List<PlayerGroup> models) {
		models.forEach(m -> {
			if(m.getName().contentEquals(GROUP_1)) {
				m.addPlayer(new Player("Walter White", "Alisas Heisenberg", null, m));
				m.addPlayer(new Player("Jessy Pinkman", null, null, m));
				m.addPlayer(new Player("Gustavo Frings", null, null, m));
				m.addPlayer(new Player("Victor Salamanca", null, null, m));
			}
			if(m.getName().contentEquals(GROUP_2)) {
				m.addPlayer(new Player("Bruce Springsteen", "Saenger", null, m));
				m.addPlayer(new Player("Muddy  Waters", "Blues Saenger", null, m));
				m.addPlayer(new Player("Carlos Santana", "Gitarrist", null, m));
			}
			if(m.getName().contentEquals(GROUP_3)) {
				m.addPlayer(new Player("Andrea Nales", "???", null, m));
				m.addPlayer(new Player("Angela Merkel", "Wir schaffen das", null, m));
				m.addPlayer(new Player("Walter Ulbricht", "Niemand hat die Absicht eine Mauer zu bauen", null, m));
				m.addPlayer(new Player("Herbert Wener", "Der Herr badet gerne lauwarm", null, m));
			}
			if(m.getName().contentEquals(GROUP_4)) {
				m.addPlayer(new Player("Klara Korn", "Prost", null, m));
				m.addPlayer(new Player("Clair Grube", "Nimm mich auch wenn wenn ich stinke", null, m));
				m.addPlayer(new Player("Rita Lin", "Ich bin ganz ruhig", null, m));
				m.addPlayer(new Player("Tom Mate", "Ich bin der Freund von Peter", null, m));
				m.addPlayer(new Player("Peter Siele", "Ich suche Tom", null, m));
			}			
		});
	}

	public void createPlayer() {
		List<Player> models = new ArrayList<>();
		models.add(new Player("Walter White", "Alisas Heisenberg", null));
		models.add(new Player("Jessy Pinkman", null, null));
		models.add(new Player("Gustavo Frings", null, null));
		models.add(new Player("Bruce Springsteen", "Saenger", null));
		models.add(new Player("Muddy  Waters", "Blues Saenger", null));
		models.add(new Player("Carlos Santana", "Gitarrist", null));
		models.add(new Player("Klara Korn", "Prost", null));
		models.add(new Player("Clair Grube", null, null));
		models.add(new Player("Rita Lin", "Ich bin ganz ruhig", null));
		models.add(new Player("Tom Mate", "Ich bin der Freund von Peter", null));
		models.add(new Player("Peter Siele", "Ich suche Tom", null));
		models.add(new Player("Andrea Nales", null, null));
		models.add(new Player("Angela Merkel", null, null));
		models.add(new Player("Walter Ulbricht", "Niemand hat die Absicht eine Mauer zu bauen", null));
		models.add(new Player("Herbert Wener", "Der Herr badet gerne lauwarm", null));
		models.forEach(m -> controller.create(m));
	}
	
	public GameModus createGameModus() {
		GameModus model = new GameModus("Classic");
		createPhaseItemsPersist(model);
		controller.create(model);
		return model;
	}

	public void createSessionsGames() {
		Date date = new Date();
		GameModus modus = (GameModus) controller.findByName("GameModus", "Classic");
		PlaySession session;
		
		PlayerGroup group = (PlayerGroup) controller.findByName("PlayerGroup", GROUP_1);
		if(null != group) {
			session = new PlaySession(new java.sql.Date(date.getTime()));
			session.setPlayerGroup(group);
			for (int i = 0; i < 3; i++) {
				session = service.newGame(session, modus);
			}
			session.setWinner((Player) randomWinner(group.getPlayers()));
			session = (PlaySession) controller.create(session);
		} else {
			System.out.println(String.format("Gruppe %s nicht gefunden", GROUP_1));
			return;
		}
		
		group = (PlayerGroup) controller.findByName("PlayerGroup", GROUP_2);
		if(null != group) {
			session = new PlaySession(new java.sql.Date(date.getTime()));
			session.setPlayerGroup(group);
			for (int i = 0; i < 3; i++) {
				session = service.newGame(session, modus);
			}
			session.setWinner(group.getPlayers().get(0));
			controller.create(session);
		} else {
			System.out.println(String.format("Gruppe %s nicht gefunden", GROUP_2));
			return;
		}
		
		group = (PlayerGroup) controller.findByName("PlayerGroup", GROUP_3);
		if(null != group) {
			session = new PlaySession(new java.sql.Date(date.getTime()));
			session.setPlayerGroup(group);
			for (int i = 0; i < 3; i++) {
				session = service.newGame(session, modus);
			}
			session.setWinner(group.getPlayers().get(0));
			controller.create(session);
		}else {
			System.out.println(String.format("Gruppe %s nicht gefunden", GROUP_3));
			return;
		}
		
		group = (PlayerGroup) controller.findByName("PlayerGroup", GROUP_4);
		if(null != group) {
			session = new PlaySession(new java.sql.Date(date.getTime()));
			session.setPlayerGroup(group);
			for (int i = 0; i < 3; i++) {
				session = service.newGame(session, modus);
			}
			session.setWinner(group.getPlayers().get(0));
			controller.create(session);
		} else {
			System.out.println(String.format("Gruppe %s nicht gefunden", GROUP_4));
			return;
		}
	}

	public GameModus createPhaseItems(GameModus modus) {
		modus.addPhaseItem(new PhaseItem(1, "Zwei Drillinge"));
		modus.addPhaseItem(new PhaseItem(2, "Drilling und Viererfolge"));
		modus.addPhaseItem(new PhaseItem(3, "Vierling und Viererfolge"));
		modus.addPhaseItem(new PhaseItem(4, "Siebenerfolge"));
		modus.addPhaseItem(new PhaseItem(5, "Siebenerfolge"));
		modus.addPhaseItem(new PhaseItem(6, "Achterfolge"));
		modus.addPhaseItem(new PhaseItem(7, "Neunerfolge"));
		modus.addPhaseItem(new PhaseItem(8, "Sieben Karten einer Farbe"));
		modus.addPhaseItem(new PhaseItem(9, "Fuenfling und Zwilling"));
		modus.addPhaseItem(new PhaseItem(10, "Fuenfling und Drilling"));
		return modus;
	}

	public GameModus createPhaseItemsPersist(GameModus modus) {
		Controller<PhaseItem> controller = new Controller<>();
		modus.addPhaseItem((PhaseItem) controller.create(new PhaseItem(1, "Zwei Drillinge")));
		modus.addPhaseItem((PhaseItem) controller.create(new PhaseItem(2, "Drilling und Viererfolge")));
		modus.addPhaseItem((PhaseItem) controller.create(new PhaseItem(3, "Vierling und Viererfolge")));
		modus.addPhaseItem((PhaseItem) controller.create(new PhaseItem(4, "Siebenerfolge")));
		modus.addPhaseItem((PhaseItem) controller.create(new PhaseItem(5, "Siebenerfolge")));
		modus.addPhaseItem((PhaseItem) controller.create(new PhaseItem(6, "Achterfolge")));
		modus.addPhaseItem((PhaseItem) controller.create(new PhaseItem(7, "Neunerfolge")));
		modus.addPhaseItem((PhaseItem) controller.create(new PhaseItem(8, "Sieben Karten einer Farbe")));
		modus.addPhaseItem((PhaseItem) controller.create(new PhaseItem(9, "Fuenfling und Zwilling")));
		modus.addPhaseItem((PhaseItem) controller.create(new PhaseItem(10, "Fuenfling und Drilling")));
		return modus;
	}
	
	public static Object randomWinner(List<?> models) {
		int i = getRandomNumberInRange(0, models.size()-1);
		return models.get(i);
	}

	private static int getRandomNumberInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}