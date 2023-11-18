package com.unlam.tpi.core.modelo;

import java.time.LocalDate;

public class FechaRequestHistorico {
	
	private LocalDate fechaDesde;
	private LocalDate mesesAtras;
	private String instrumento;

	public LocalDate getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(LocalDate fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public LocalDate getMesesAtras() {
		return mesesAtras;
	}

	public void setMesesAtras(LocalDate mesesAtras) {
		this.mesesAtras = mesesAtras;
	}

	public String getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	}
}
