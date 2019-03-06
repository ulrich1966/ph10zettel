package de.juli.phaseten.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity()
@NamedQueries({
	  @NamedQuery(name="GameModus.findAll", query="SELECT model FROM GameModus model"),
	  @NamedQuery(name="GameModus.findByName", query="SELECT model FROM GameModus model WHERE model.name = :name")
	})
public class GameModus extends Model {
	@Column(nullable=false)
	private String name; 
	@OneToMany(mappedBy="gameModus", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PhaseItem> phaseItems;
	
	public GameModus() {
		super();
	}

	public GameModus(String name) {
		this();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<PhaseItem> getPhaseItems() {
		return phaseItems;
	}

	public void setPhaseItems(List<PhaseItem> phaseItems) {
		this.phaseItems = phaseItems;
	}

	public void addPhaseItem(PhaseItem phaseItem) {
		if (this.phaseItems == null) {
			this.phaseItems = new ArrayList<PhaseItem>();
		}
		this.phaseItems.add(phaseItem);
		phaseItem.setGameModus(this);			
	}

	@Override
	public String toString() {
		return "GameModi [name=" + name + ", items=" + phaseItems + "]";
	}
}
