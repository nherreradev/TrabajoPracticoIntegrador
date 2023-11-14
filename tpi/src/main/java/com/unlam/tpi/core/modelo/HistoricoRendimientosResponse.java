package com.unlam.tpi.core.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;

public class HistoricoRendimientosResponse {

	String simbolo;
	BigDecimal rendimientoTotal;
	BigDecimal rendimientoTotalPorcentaje;
	LocalDateTime fecha;
	BigDecimal cantidadDeTitulos;
	BigDecimal valorInversion;

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public BigDecimal getRendimientoTotal() {
		return rendimientoTotal;
	}

	public void setRendimientoTotal(BigDecimal rendimientoTotal) {
		this.rendimientoTotal = rendimientoTotal;
	}

	public BigDecimal getRendimientoTotalPorcentaje() {
		return rendimientoTotalPorcentaje;
	}

	public void setRendimientoTotalPorcentaje(BigDecimal rendimientoTotalPorcentaje) {
		this.rendimientoTotalPorcentaje = rendimientoTotalPorcentaje;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getCantidadDeTitulos() {
		return cantidadDeTitulos;
	}

	public void setCantidadDeTitulos(BigDecimal cantidadDeTitulos) {
		this.cantidadDeTitulos = cantidadDeTitulos;
	}

	public BigDecimal getValorInversion() {
		return valorInversion;
	}

	public void setValorInversion(BigDecimal valorInversion) {
		this.valorInversion = valorInversion;
	}

}
