package de.juli.phaseten.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	  @NamedQuery(name="PhaseItem.findAll", query="SELECT model FROM PhaseItem model"),
	  @NamedQuery(name="PhaseItem.findByName", query="SELECT model FROM PhaseItem model WHERE model.name = :name"),
	  @NamedQuery(name="PhaseItem.findByNumber", query="SELECT model FROM PhaseItem model WHERE model.number = :number")
	})
public class PhaseItem extends Model {
	private static final long serialVersionUID = 1L;
	private Integer number;
	private String name;
	@ManyToOne(cascade = CascadeType.ALL)
	private GameModus gameModus;
	
	public PhaseItem() {
		super();
	}

	public PhaseItem(Integer number, String name) {
		super();
		this.number = number;
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GameModus getGameModus() {
		return gameModus;
	}

	public void setGameModus(GameModus gameModus) {
		this.gameModus = gameModus;
	}
	
	
}
