package com.unlam.tpi.core.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.unlam.tpi.delivery.dto.TipoNivelConocimiento;
import com.unlam.tpi.delivery.dto.TipoPerfilInversor;
import com.unlam.tpi.infraestructura.arquitectura.ObjetoPersistente;

@Entity
@Table(name = "PERFIL_INVERSOR")
public class PerfilInversor extends ObjetoPersistente {

	private static final long serialVersionUID = 1L;

	@Column(name = "HORIZONTE_TEMPORAL")
	private Integer horizonteTemporal;

	@Column(name = "TOLERANCIA_RIESGO")
	private Integer toleranciaRiesgo;

	@Column(name = "TIPO_PERFIL_SUBJETIVO")
	@Enumerated(EnumType.STRING)
	private TipoPerfilInversor tipoPerfilSubjetivo;

	@Column(name = "NIVEL_CONOCIMIENTO")
	private Integer nivelConocimiento;

	@Column(name = "TIPO_NIVEL_CONOCIMIENTO")
	@Enumerated(EnumType.STRING)
	private TipoNivelConocimiento tipoNivelConocimiento;

	@Column(name = "PERFIL_INVERSOR")
	@Enumerated(EnumType.STRING)
	private TipoPerfilInversor perfilInversor;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "USUARIO_OID", referencedColumnName = "OID_")
	private Usuario usuario;

	public Integer getHorizonteTemporal() {
		return horizonteTemporal;
	}

	public void setHorizonteTemporal(Integer horizonteTemporal) {
		this.horizonteTemporal = horizonteTemporal;
	}

	public Integer getToleranciaRiesgo() {
		return toleranciaRiesgo;
	}

	public void setToleranciaRiesgo(Integer toleranciaRiesgo) {
		this.toleranciaRiesgo = toleranciaRiesgo;
	}

	public TipoPerfilInversor getTipoPerfilSubjetivo() {
		return tipoPerfilSubjetivo;
	}

	public void setTipoPerfilSubjetivo(TipoPerfilInversor tipoPerfilSubjetivo) {
		this.tipoPerfilSubjetivo = tipoPerfilSubjetivo;
	}

	public Integer getNivelConocimiento() {
		return nivelConocimiento;
	}

	public void setNivelConocimiento(Integer nivelConocimiento) {
		this.nivelConocimiento = nivelConocimiento;
	}

	public TipoPerfilInversor getPerfilInversor() {
		return perfilInversor;
	}

	public void setPerfilInversor(TipoPerfilInversor perfilInversor) {
		this.perfilInversor = perfilInversor;
	}

	public Integer getResultadoPerfilado() {
		return toleranciaRiesgo + nivelConocimiento / 2;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public TipoNivelConocimiento getTipoNivelConocimiento() {
		return tipoNivelConocimiento;
	}

	public void setTipoNivelConocimiento(TipoNivelConocimiento tipoNivelConocimiento) {
		this.tipoNivelConocimiento = tipoNivelConocimiento;
	}

}
