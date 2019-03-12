package de.juli.phaseten.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

@MappedSuperclass
public abstract class Model implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Version
	private Long version;
	private Timestamp createDate;
	private Timestamp updateDate;

	protected Model() {
	}

	@PrePersist
	@PreUpdate
	public void prePersist() {
		Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
		if (createDate == null) {
			createDate = timestamp;
		}
		updateDate = timestamp;
	}

	public Long getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	@Override
	public String toString() {
		return "Model [id=" + id + ", version=" + version + ", createDate=" + createDate + ", updateDate=" + updateDate + "]";
	}
}