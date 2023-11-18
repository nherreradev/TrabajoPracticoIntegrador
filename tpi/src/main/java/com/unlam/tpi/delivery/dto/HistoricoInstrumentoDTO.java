package com.unlam.tpi.delivery.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoricoInstrumentoDTO {

	@JsonProperty("simbolo")
	String simbolo;

	@JsonProperty("apertura")
	BigDecimal apertura;

	@JsonProperty("cierre")
	BigDecimal cierre;

	@JsonProperty("maximo")
	BigDecimal maximo;

	@JsonProperty("minimo")
	BigDecimal minimo;

	@JsonProperty("fechaHora")
	String fechaHora;

	@JsonProperty("simbolo")
	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	@JsonProperty("apertura")
	public BigDecimal getApertura() {
		return apertura;
	}

	public void setApertura(BigDecimal apertura) {
		this.apertura = apertura;
	}

	@JsonProperty("cierre")
	public BigDecimal getCierre() {
		return cierre;
	}

	public void setCierre(BigDecimal cierre) {
		this.cierre = cierre;
	}

	@JsonProperty("maximo")
	public BigDecimal getMaximo() {
		return maximo;
	}

	public void setMaximo(BigDecimal maximo) {
		this.maximo = maximo;
	}

	@JsonProperty("minimo")
	public BigDecimal getMinimo() {
		return minimo;
	}

	public void setMinimo(BigDecimal minimo) {
		this.minimo = minimo;
	}

	@JsonProperty("fechaHora")
	public String getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}

}
