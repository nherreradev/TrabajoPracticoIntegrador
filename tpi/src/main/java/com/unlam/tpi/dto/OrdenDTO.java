package com.unlam.tpi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;

public class OrdenDTO {

	private Boolean esEfectivo;

	private Long instrumentoOid;

	private Long monedaOid;

	private LocalDate fecha_orden;

	private BigDecimal cantidad;

	private BigDecimal precio;

	private String simboloInstrumento;

	@Column(name = "SENTIDO")
	private String sentido;

	public Boolean getEsEfectivo() {
		return esEfectivo;
	}

	public void setEsEfectivo(Boolean esEfectivo) {
		this.esEfectivo = esEfectivo;
	}

	public Long getInstrumentoOid() {
		return instrumentoOid;
	}

	public void setInstrumentoOid(Long instrumentoOid) {
		this.instrumentoOid = instrumentoOid;
	}

	public Long getMonedaOid() {
		return monedaOid;
	}

	public void setMonedaOid(Long monedaOid) {
		this.monedaOid = monedaOid;
	}

	public LocalDate getFecha_orden() {
		return fecha_orden;
	}

	public void setFecha_orden(LocalDate fecha_orden) {
		this.fecha_orden = fecha_orden;
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

	public String getSimboloInstrumento() {
		return simboloInstrumento;
	}

	public void setSimboloInstrumento(String simboloInstrumento) {
		this.simboloInstrumento = simboloInstrumento;
	}

	public String getSentido() {
		return sentido;
	}

	public void setSentido(String sentido) {
		this.sentido = sentido;
	}

}
