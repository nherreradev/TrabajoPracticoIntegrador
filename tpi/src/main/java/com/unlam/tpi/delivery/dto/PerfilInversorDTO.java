package com.unlam.tpi.delivery.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import com.unlam.tpi.core.modelo.PerfilInversor;
import com.unlam.tpi.core.modelo.ServiceException;

public class PerfilInversorDTO {

	private Long oid;

	private Integer version;

	private Boolean deleted = false;

	private Integer horizonteTemporal;

	private Integer toleranciaRiesgo;

	private TipoPerfilInversor tipoPerfilSubjetivo;

	private Integer nivelConocimiento;

	private TipoNivelConocimiento tipoNivelConocimiento;

	private TipoPerfilInversor perfilInversor;

	private UsuarioDTO usuarioDTO;

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

	public TipoNivelConocimiento getTipoNivelConocimiento() {
		return tipoNivelConocimiento;
	}

	public void setTipoNivelConocimiento(TipoNivelConocimiento tipoNivelConocimiento) {
		this.tipoNivelConocimiento = tipoNivelConocimiento;
	}

	public TipoPerfilInversor getPerfilInversor() {
		return perfilInversor;
	}

	public void setPerfilInversor(TipoPerfilInversor perfilInversor) {
		this.perfilInversor = perfilInversor;
	}

	public Integer getResultadoPerfilado() {
		if (toleranciaRiesgo != null && nivelConocimiento != null) {
			return (toleranciaRiesgo + nivelConocimiento) / 2;
		}
		return 0;
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

	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

}