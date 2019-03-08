package de.juli.phaseten.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The better name 'Group' causes trouble with Hibernate/Sql becaus of keywords 
 * @author uli
 *
 */
@Entity
@NamedQueries({ 
	@NamedQuery(name = "PlayerGroup.findAll", query = "SELECT model FROM PlayerGroup model"), 
	@NamedQuery(name = "PlayerGroup.findByName", query = "SELECT model FROM PlayerGroup model WHERE model.name = :name")
})
@JsonIgnoreProperties({ "players", "sessions" })
public class PlayerGroup extends Model {
	@Column(unique = true, nullable = false)
	private String name;
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY, mappedBy="playerGroups")
	private List<Player> players;
	@OneToMany(mappedBy = "playerGroup", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.LAZY)
	private List<PlaySession> sessions;

	public PlayerGroup() {
		super();
	}

	public PlayerGroup(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<PlaySession> getSessions() {
		return sessions;
	}

	public void setSessions(List<PlaySession> sessions) {
		this.sessions = sessions;
	}

	public void addPlayer(Player player) {
		if (this.players == null) {
			this.players = new ArrayList<Player>();
		}
		if(!this.players.contains(player)) {
			this.players.add(player);
			player.addPlayerGroup(this);			
		}
	}

	public void addSession(PlaySession session) {
		if (this.sessions == null) {
			this.sessions = new ArrayList<PlaySession>();
		}
		this.sessions.add(session);
		session.setPlayerGroup(this);
	}

	@Override
	public String toString() {
		return "PlayerGroup [name=" + name + ", players=" + players + ", sessions=" + sessions + "]";
	}
	
}
