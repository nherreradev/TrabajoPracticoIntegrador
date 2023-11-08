package com.unlam.tpi.core.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RendimientoResponse {

	String simbolo;
	BigDecimal totalGananciaDelDia;
	BigDecimal totalPorcentajeDelDia;
	LocalDate fecha;
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

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getCostoTotalDeLasComprasDelDia() {
		return costoTotalDeLasComprasDelDia;
	}

	public void setCostoTotalDeLasComprasDelDia(BigDecimal costoTotalDeLasComprasDelDia) {
		this.costoTotalDeLasComprasDelDia = costoTotalDeLasComprasDelDia;
	}

}
