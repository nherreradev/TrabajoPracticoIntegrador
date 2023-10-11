package com.unlam.tpi.arquitectura;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

@MappedSuperclass
public abstract class ObjetoPersistente implements Serializable {

	public static final int BIG_DEC_PRESISION = 30;
	public static final int BIG_DEC_SCALE = 8;
	private static final long serialVersionUID = 1L;

	@Transient
	private String transientUUID;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OID_", unique = true, nullable = false)
	private Long oid;

	@Column(name = "VERSION_", nullable = false)
	@Version
	private Integer version;

	@Column(name = "DELETED_", nullable = false)
	private Boolean deleted = false;

	@Override
	public int hashCode() {
		return Objects.hash(oid, transientUUID);
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjetoPersistente other = (ObjetoPersistente) obj;
		return Objects.equals(oid, other.oid) && Objects.equals(transientUUID, other.transientUUID);
	}

}
