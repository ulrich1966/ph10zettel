package de.juli.phaseten.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@NamedQueries({
	  @NamedQuery(name="Game.findAll", query="SELECT model FROM Game model"),
	  @NamedQuery(name="Game.findByNumber", query="SELECT model FROM Game model WHERE model.currentNumber = :number"),
	  @NamedQuery(name="Game.findByWinner", query="SELECT model FROM Game model WHERE model.winner = :winner"),
	  @NamedQuery(name="Game.findByPlaySession", query="SELECT model FROM Game model WHERE model.playSession = :session")
	})
@JsonIgnoreProperties({ "playSession", "sheeds" })
public class Game extends Model {
	private static final long serialVersionUID = 1L;
	private Integer currentNumber;
	@OneToOne
	private Player winner;
	@ManyToOne(cascade=CascadeType.ALL)
	private PlaySession playSession;
	@OneToMany(mappedBy="game", cascade = CascadeType.ALL)
	private List<Sheed> sheeds;
	
	public Game() {
		super();
	}

	public Game(Player winner, PlaySession playSession) {
		super();
		this.winner = winner;
		this.playSession = playSession;
	}

	public Integer getCurrentNumber() {
		if(this.currentNumber == null) {
			this.currentNumber = 0;
		}
		return currentNumber;
	}

	public void setCurrentNumber(Integer currentNumber) {
		this.currentNumber = currentNumber;
	}
	
	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public List<Sheed> getSheeds() {
		return sheeds;
	}

	public void setSheeds(List<Sheed> sheeds) {
		this.sheeds = sheeds;
	}
	
	public PlaySession getPlaySession() {
		return playSession;
	}

	public void setPlaySession(PlaySession playSession) {
		this.playSession = playSession;
		setCurrentNumber(getCurrentNumber()+1);
	}

	public void addSheed(Sheed sheed) {
		if (this.sheeds == null) {
			this.sheeds = new ArrayList<Sheed>();
		}
		this.sheeds.add(sheed);
		sheed.setGame(this);			
	}

	@Override
	public String toString() {
		return "Game [currentNumber=" + currentNumber + ", winner=" + winner + ", playSession=" + playSession + ", sheeds=" + sheeds + "]";
	}
}
