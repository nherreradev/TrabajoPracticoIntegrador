package com.unlam.tpi.core.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.unlam.tpi.infraestructura.arquitectura.ObjetoPersistente;

@Entity
@Table(name = "ORDEN")
public class Orden extends ObjetoPersistente {

	@Column(name = "CANTIDAD")
	private BigDecimal cantidad;

	@Column(name = "PRECIO")
	private BigDecimal precio;

	@Column(name = "FECHA_ORDEN")
	private LocalDate fecha_orden;

	@Column(name = "MONEDA_OID")
	private Long monedaOid;

	@Column(name = "SENTIDO")
	private String sentido;

	@Column(name = "SIMBOLO_INSTRUMENTO")
	private String simboloInstrumento;

	@Column(name = "USUARIO_OID")
	private Long usuarioOid;

	@Column(name = "CATEGORIA_INSTRUMENTO")
	private String categoriaInstrumento;

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public LocalDate getFecha_orden() {
		return fecha_orden;
	}

	public void setFecha_orden(LocalDate fecha_orden) {
		this.fecha_orden = fecha_orden;
	}

	public Long getMonedaOid() {
		return monedaOid;
	}

	public void setMonedaOid(Long monedaOid) {
		this.monedaOid = monedaOid;
	}

	public String getSentido() {
		return sentido;
	}

	public void setSentido(String sentido) {
		this.sentido = sentido;
	}

	public String getSimboloInstrumento() {
		return simboloInstrumento;
	}

	public void setSimboloInstrumento(String simboloInstrumento) {
		this.simboloInstrumento = simboloInstrumento;
	}

	public Long getUsuarioOid() {
		return usuarioOid;
	}

	public void setUsuarioOid(Long usuarioOid) {
		this.usuarioOid = usuarioOid;
	}

	public String getCategoriaInstrumento() {
		return categoriaInstrumento;
	}

	public void setCategoriaInstrumento(String categoriaInstrumento) {
		this.categoriaInstrumento = categoriaInstrumento;
	}

}
