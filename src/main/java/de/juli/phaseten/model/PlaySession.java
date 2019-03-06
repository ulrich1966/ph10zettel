package de.juli.phaseten.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
	  @NamedQuery(name="PlaySession.findAll", query="SELECT model FROM PlaySession model"),
	  @NamedQuery(name="PlaySession.findByDate", query="SELECT model FROM PlaySession model WHERE model.date = :date"),
	  @NamedQuery(name="PlaySession.findByWinner", query="SELECT model FROM PlaySession model WHERE model.winner = :winner"),
	})
public class PlaySession extends Model {
	private Date date;
	@OneToOne
	private Player winner;
	@ManyToOne(cascade = CascadeType.ALL)
	private PlayerGroup playerGroup;
	@OneToMany(mappedBy="playSession", cascade = CascadeType.ALL)
	private List<Game> games;
	
	public PlaySession() {
		super();
	}

	public PlaySession(Date date) {
		super();
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public PlayerGroup getPlayerGroup() {
		return playerGroup;
	}

	public void setPlayerGroup(PlayerGroup playerGroup) {
		this.playerGroup = playerGroup;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public void addGame(Game game) {
		if(this.games == null) {
			this.games = new ArrayList<Game>();
		}
		this.games.add(game);
		game.setPlaySession(this);
	}

	@Override
	public String toString() {
		return "Session [date=" + date + ", winner=" + winner + ", playerGroup=" + playerGroup + "]";
	}
}
