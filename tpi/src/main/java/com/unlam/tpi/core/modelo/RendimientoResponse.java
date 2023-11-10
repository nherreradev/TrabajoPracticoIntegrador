package com.unlam.tpi.core.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RendimientoResponse {

	String simbolo;
	BigDecimal rendimientoTotal;
	BigDecimal rendimientoTotalPorcentaje;
	LocalDateTime fecha;

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public BigDecimal getRendimientoTotal() {
		return rendimientoTotal;
	}

	public void setRendimientoTotal(BigDecimal totalGananciaDelDia) {
		this.rendimientoTotal = totalGananciaDelDia;
	}

	public BigDecimal getRendimientoTotalPorcentaje() {
		return rendimientoTotalPorcentaje;
	}

	public void setRendimientoTotalPorcentaje(BigDecimal totalPorcentajeDelDia) {
		this.rendimientoTotalPorcentaje = totalPorcentajeDelDia;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

}
