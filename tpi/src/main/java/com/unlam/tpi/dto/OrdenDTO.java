package com.unlam.tpi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrdenDTO {

	private Long oid;

	private BigDecimal cantidad;

	private BigDecimal precio;

	private LocalDate fecha_orden;

	private Long monedaOid;

	private String sentido;

	private String simboloInstrumento;

	private Long usuarioOid;

	private String categoriaInstrumento;

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

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
