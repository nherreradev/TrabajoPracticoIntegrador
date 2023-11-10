package com.unlam.tpi.core.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RendimientoResponse {

	String simbolo;
	BigDecimal totalGananciaDelDia;
	BigDecimal totalPorcentajeDelDia;
	LocalDateTime fecha;
	BigDecimal costoTotalDeLasComprasDelDia;

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public BigDecimal getTotalGananciaDelDia() {
		return totalGananciaDelDia;
	}

	public void setTotalGananciaDelDia(BigDecimal totalGananciaDelDia) {
		this.totalGananciaDelDia = totalGananciaDelDia;
	}

	public BigDecimal getTotalPorcentajeDelDia() {
		return totalPorcentajeDelDia;
	}

	public void setTotalPorcentajeDelDia(BigDecimal totalPorcentajeDelDia) {
		this.totalPorcentajeDelDia = totalPorcentajeDelDia;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getCostoTotalDeLasComprasDelDia() {
		return costoTotalDeLasComprasDelDia;
	}

	public void setCostoTotalDeLasComprasDelDia(BigDecimal costoTotalDeLasComprasDelDia) {
		this.costoTotalDeLasComprasDelDia = costoTotalDeLasComprasDelDia;
	}

}
