package com.unlam.tpi.core.modelo;

import java.math.BigDecimal;
import java.util.Map;

public class ValuacionTotalRespuesta {

	private String totalCartera;

	private String totalInstrumentos;

	private String totalMonedas;

	private String procentajeGananciaPerdida;

	private Map<String, BigDecimal> cantidadPorInstrumento;

	public String getTotalCartera() {
		return totalCartera;
	}

	public void setTotalCartera(String totalCartera) {
		this.totalCartera = totalCartera;
	}

	public String getTotalInstrumentos() {
		return totalInstrumentos;
	}

	public void setTotalInstrumentos(String totalInstrumentos) {
		this.totalInstrumentos = totalInstrumentos;
	}

	public String getTotalMonedas() {
		return totalMonedas;
	}

	public void setTotalMonedas(String totalMonedas) {
		this.totalMonedas = totalMonedas;
	}

	public Map<String, BigDecimal> getCantidadPorInstrumento() {
		return cantidadPorInstrumento;
	}

	public void setCantidadPorInstrumento(Map<String, BigDecimal> cantidadPorInstrumento) {
		this.cantidadPorInstrumento = cantidadPorInstrumento;
	}

	public String getProcentajeGananciaPerdida() {
		return procentajeGananciaPerdida;
	}

	public void setProcentajeGananciaPerdida(String procentajeGananciaPerdida) {
		this.procentajeGananciaPerdida = procentajeGananciaPerdida;
	}

}