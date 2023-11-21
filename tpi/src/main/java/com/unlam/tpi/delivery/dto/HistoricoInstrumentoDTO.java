package com.unlam.tpi.delivery.dto;

import java.math.BigDecimal;
import java.util.Objects;

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

	@JsonProperty("fecha")
	String fecha; 
	
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha, simbolo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoricoInstrumentoDTO other = (HistoricoInstrumentoDTO) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(simbolo, other.simbolo);
	}
	

}
