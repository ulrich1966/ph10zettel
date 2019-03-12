package de.juli.phaseten.util;

import java.util.List;
import java.util.Random;

import de.juli.phaseten.controller.Controller;
import de.juli.phaseten.model.Player;
import de.juli.phaseten.model.PlayerGroup;

public class RandomModel {
	
	public static PlayerGroup fetchRandomGroup() {
		PlayerGroup random = (PlayerGroup) getRandom(fetchAllPlayerGrpoups());
		return random;
	}
	
	public static Player fetchRandomPlayer() {
		Player random = (Player) getRandom(fetchAllPlayers());
		return random;
	}

	public static List<PlayerGroup> fetchAllPlayerGrpoups() {
		Controller<PlayerGroup> ctrl = new Controller<>();
		return ctrl.findAll(PlayerGroup.class);
	}
	
	public static List<Player> fetchAllPlayers() {
		Controller<Player> ctrl = new Controller<>();
		return ctrl.findAll(Player.class);
	}
	
	private static Object getRandom(List<?> models) {
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
