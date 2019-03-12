package de.juli.phaseten;

import static org.junit.Assert.*;

import org.junit.Test;

import de.juli.phaseten.controller.Controller;
import de.juli.phaseten.model.Player;

public class ControllerTest {

	@Test
	public void test() {
		Controller<Player> c = new Controller<Player>();
		Player findById = c.findById(5l, Player.class);
		System.out.println(findById);
	}

}
