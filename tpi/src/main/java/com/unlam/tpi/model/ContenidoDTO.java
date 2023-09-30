package com.unlam.tpi.model;

import java.util.List;

import org.json.JSONObject;

public class ContenidoDTO extends JSONObject{

	private Long oid;
	
	private String nombre;
	
	private String descripcion;

	private Integer orden;
	
	private CategoriaDTO categoria;
	
	private NivelDTO nivel;
	
	List<PreguntaDTO> preguntas;
	
	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public CategoriaDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDTO categoria) {
		this.categoria = categoria;
	}

	public NivelDTO getNivel() {
		return nivel;
	}

	public void setNivel(NivelDTO nivel) {
		this.nivel = nivel;
	}

	public List<PreguntaDTO> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<PreguntaDTO> preguntas) {
		this.preguntas = preguntas;
	}
	
}