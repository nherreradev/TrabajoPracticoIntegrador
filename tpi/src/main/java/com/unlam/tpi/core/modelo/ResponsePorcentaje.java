package com.unlam.tpi.core.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ResponsePorcentaje {

	List<ResponsePorcentaje> instrumento;

	String simbolo;
	BigDecimal totalDineroGeneral;
	BigDecimal totalPorcentajeGeneral;
	BigDecimal totalValorizadoGeneral;
	LocalDate fecha;

	public List<ResponsePorcentaje> getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(List<ResponsePorcentaje> instrumento) {
		this.instrumento = instrumento;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public BigDecimal getTotalDineroGeneral() {
		return totalDineroGeneral;
	}

	public void setTotalDineroGeneral(BigDecimal totalDineroGeneral) {
		this.totalDineroGeneral = totalDineroGeneral;
	}

	public BigDecimal getTotalPorcentajeGeneral() {
		return totalPorcentajeGeneral;
	}

	public void setTotalPorcentajeGeneral(BigDecimal totalPorcentajeGeneral) {
		this.totalPorcentajeGeneral = totalPorcentajeGeneral;
	}

	public BigDecimal getTotalValorizadoGeneral() {
		return totalValorizadoGeneral;
	}

	public void setTotalValorizadoGeneral(BigDecimal totalValorizadoGeneral) {
		this.totalValorizadoGeneral = totalValorizadoGeneral;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

}
