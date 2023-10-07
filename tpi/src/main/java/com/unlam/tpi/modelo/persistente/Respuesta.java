package com.unlam.tpi.modelo.persistente;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unlam.tpi.arquitectura.ObjetoPersistente;

@Entity
@Table(name = "RESPUESTA")
public class Respuesta extends ObjetoPersistente{

	private static final long serialVersionUID = 1L;

	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "Valor")
	private Integer valor;
	
	@Column(name = "ORDEN")
	private Integer orden;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "PREGUNTA_OID")
	private Pregunta pregunta;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	
}