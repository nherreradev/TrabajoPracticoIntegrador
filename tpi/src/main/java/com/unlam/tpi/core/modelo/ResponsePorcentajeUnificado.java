package com.unlam.tpi.core.modelo;

import java.math.BigDecimal;

public class ResponsePorcentajeUnificado {

	String simbolo;
	BigDecimal totalGananciaUnificado;
	BigDecimal totalPorcentajeUnificado;

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public BigDecimal getTotalGananciaUnificado() {
		return totalGananciaUnificado;
	}

	public void setTotalGananciaUnificado(BigDecimal totalGananciaUnificado) {
		this.totalGananciaUnificado = totalGananciaUnificado;
	}

	public BigDecimal getTotalPorcentajeUnificado() {
		return totalPorcentajeUnificado;
	}

	public void setTotalPorcentajeUnificado(BigDecimal totalPorcentajeUnificado) {
		this.totalPorcentajeUnificado = totalPorcentajeUnificado;
	}

}
