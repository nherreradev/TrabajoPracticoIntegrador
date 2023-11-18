package com.unlam.tpi.delivery.dto;

import java.util.List;

public class PreguntaDTO {

	private Long oid;

	private Integer version;

	private Boolean deleted = false;

	private String enunciado;

	private String descripcion;

	private CategoriaDTO categoria;

	private SeccionDTO seccion;

	private Integer orden;
	
	private String codigo;

	private TipoComponente tipoComponente;

	private List<RespuestaDTO> respuestas;

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public CategoriaDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDTO categoria) {
		this.categoria = categoria;
	}

	public SeccionDTO getSeccion() {
		return seccion;
	}

	public void setSeccion(SeccionDTO seccion) {
		this.seccion = seccion;
	}

	public List<RespuestaDTO> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<RespuestaDTO> respuestas) {
		this.respuestas = respuestas;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
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

	public TipoComponente getTipoComponente() {
		return tipoComponente;
	}

	public void setTipoComponente(TipoComponente tipoComponente) {
		this.tipoComponente = tipoComponente;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
}