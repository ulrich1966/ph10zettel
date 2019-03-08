package de.juli.phaseten.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Sheed extends Model {
	@OneToOne
 	private Phase phase01;
 	@OneToOne
 	private Phase phase02;
 	@OneToOne
 	private Phase phase03;
 	@OneToOne
 	private Phase phase04;
 	@OneToOne
 	private Phase phase05;
 	@OneToOne
 	private Phase phase06;
 	@OneToOne
 	private Phase phase07;
 	@OneToOne
 	private Phase phase08;
 	@OneToOne
 	private Phase phase09;
 	@OneToOne
 	private Phase phase10;
 	@OneToOne
 	private Player player;
 	@ManyToOne(cascade = CascadeType.ALL)
 	private Game game;
 	
 	
 	public Sheed() {
 		super();
 	}
	
 	public Sheed(Player player) {
 		this();
 		this.player = player;
 	}

 	public Sheed(Player player, Game game) {
		this(player);
		this.game = game;
	}


	public Phase getPhase01() {
		return phase01;
	}

	public void setPhase01(Phase phase01) {
		this.phase01 = phase01;
	}

	public Phase getPhase02() {
		return phase02;
	}

	public void setPhase02(Phase phase02) {
		this.phase02 = phase02;
	}

	public Phase getPhase03() {
		return phase03;
	}

	public void setPhase03(Phase phase03) {
		this.phase03 = phase03;
	}

	public Phase getPhase04() {
		return phase04;
	}

	public void setPhase04(Phase phase04) {
		this.phase04 = phase04;
	}

	public Phase getPhase05() {
		return phase05;
	}

	public void setPhase05(Phase phase05) {
		this.phase05 = phase05;
	}

	public Phase getPhase06() {
		return phase06;
	}

	public void setPhase06(Phase phase06) {
		this.phase06 = phase06;
	}

	public Phase getPhase07() {
		return phase07;
	}

	public void setPhase07(Phase phase07) {
		this.phase07 = phase07;
	}

	public Phase getPhase08() {
		return phase08;
	}

	public void setPhase08(Phase phase08) {
		this.phase08 = phase08;
	}

	public Phase getPhase09() {
		return phase09;
	}

	public void setPhase09(Phase phase09) {
		this.phase09 = phase09;
	}

	public Phase getPhase10() {
		return phase10;
	}

	public void setPhase10(Phase phase10) {
		this.phase10 = phase10;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public String toString() {
		return "Sheed [phase01=" + phase01 + ", phase02=" + phase02 + ", phase03=" + phase03 + ", phase04=" + phase04 + ", phase05=" + phase05 + ", phase06=" + phase06 + ", phase07=" + phase07 + ", phase08=" + phase08 + ", phase09=" + phase09 + ", phase10=" + phase10 + ", player=" + player + ", game=" + game + "]";
	}
 	
}
