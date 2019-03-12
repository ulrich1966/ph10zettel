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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@NamedQueries({
	  @NamedQuery(name="Player.findAll", query="SELECT model FROM Player model"),
	  @NamedQuery(name="Player.findByName", query="SELECT model FROM Player model WHERE model.name = :name")
	})
@JsonIgnoreProperties({ "playerGroups" })
public class Player extends Model {
	private static final long serialVersionUID = 1L;
	@Column(unique = true, nullable = false)
	private String name;
	private String comment;
	private String pic;
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
	private List<PlayerGroup> playerGroups;
	
	public Player() {
		super();
	}
	
	public Player(String name, String comment, String pic) {
		this();
		this.name = name;
		this.comment = comment;
		this.pic = pic;
	}

	public Player(String name, String comment, String pic, PlayerGroup playerGroup) {
		this(name, comment, pic);
		this.addPlayerGroup(playerGroup);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	public List<PlayerGroup> getPlayerGroups() {
		return playerGroups;
	}

	public void setPlayerGroups(List<PlayerGroup> playerGroups) {
		this.playerGroups = playerGroups;
	}
	
	public void addPlayerGroup(PlayerGroup playerGroup) {
		if(this.playerGroups == null) {
			this.playerGroups = new ArrayList<PlayerGroup>();
		}
		if(!this.playerGroups.contains(playerGroup)) {
			this.playerGroups.add(playerGroup);
			playerGroup.addPlayer(this);			
		}
	}
	
	public void removePlayerGroup(PlayerGroup playerGroup) {
		if(this.playerGroups != null && this.playerGroups.contains(playerGroup)) {
			this.playerGroups.remove(playerGroup);
			playerGroup.removePlayer(this);
		}		
	}

	@Override
	public String toString() {
		return "Player [Id=" + getId() + "name=" + name + ", comment=" + comment + ", pic=" + pic + "]";
	}
}
