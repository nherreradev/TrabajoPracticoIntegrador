package com.unlam.tpi.core.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RendimientoResponse {

	String simbolo;
	BigDecimal rendimientoTotal;
	BigDecimal rendimientoTotalPorcentaje;
	LocalDateTime fecha;
	BigDecimal cantidadDeTitulos;
	BigDecimal valorActualDeLaInversion;

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

	public BigDecimal getCantidadDeTitulos() {
		return cantidadDeTitulos;
	}

	public void setCantidadDeTitulos(BigDecimal cantidadDeTitulos) {
		this.cantidadDeTitulos = cantidadDeTitulos;
	}

	public BigDecimal getValorActualDeLaInversion() {
		return valorActualDeLaInversion;
	}

	public void setValorActualDeLaInversion(BigDecimal valorActualDeLaInversion) {
		this.valorActualDeLaInversion = valorActualDeLaInversion;
	}

}
