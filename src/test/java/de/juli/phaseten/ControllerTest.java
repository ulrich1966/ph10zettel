package de.juli.phaseten;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import de.juli.phaseten.controller.Controller;
import de.juli.phaseten.model.Player;
import de.juli.phaseten.model.PlayerGroup;

public class ControllerTest {

	@Test
	public void test() {
		Controller<Player> c = new Controller<Player>();
		Player findById = c.findById(8l, Player.class);
		System.out.println(findById);
		List<PlayerGroup> playerGroups = findById.getPlayerGroups();
		playerGroups.forEach(g -> System.out.println(g.getName()));
	}

}
