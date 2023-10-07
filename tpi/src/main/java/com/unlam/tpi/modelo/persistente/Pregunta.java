package com.unlam.tpi.modelo.persistente;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.unlam.tpi.arquitectura.ObjetoPersistente;

@Entity
@Table(name = "PREGUNTA")
public class Pregunta extends ObjetoPersistente{

	private static final long serialVersionUID = 1L;

	@Column(name = "Enunciado")
	private String enunciado;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	@Column(name = "ORDEN")
	private Integer orden;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "CATEGORIA_OID")
	private Categoria categoria;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "SECCION_OID")
	private Seccion seccion;
	
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "PREGUNTA_OID", foreignKey = @ForeignKey(name = "FK_PREGUNTA__RESPUESTA"))
	private List<Respuesta> respuestas;

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

	public List<Respuesta> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Seccion getSeccion() {
		return seccion;
	}

	public void setSeccion(Seccion seccion) {
		this.seccion = seccion;
	}

}