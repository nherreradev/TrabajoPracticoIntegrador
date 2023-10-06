package com.unlam.tpi.modelo.rest;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class ValuacionTotalRespuesta {

	@JsonProperty("totalCartera")
	private String totalCartera;

	@JsonProperty("totalInstrumentos")
	private String totalInstrumentos;

	@JsonProperty("totalMonedas")
	private String totalMonedas;

	@JsonProperty("cantidadPorInstrumento")
	private Map<String, BigDecimal> cantidadPorInstrumento;

	@JsonProperty("totalCartera")
	public String getTotalCartera() {
		return totalCartera;
	}

	public void setTotalCartera(String totalCartera) {
		this.totalCartera = totalCartera;
	}

	@JsonProperty("totalInstrumentos")
	public String getTotalInstrumentos() {
		return totalInstrumentos;
	}

	public void setTotalInstrumentos(String totalInstrumentos) {
		this.totalInstrumentos = totalInstrumentos;
	}

	@JsonProperty("totalMonedas")
	public String getTotalMonedas() {
		return totalMonedas;
	}

	public void setTotalMonedas(String totalMonedas) {
		this.totalMonedas = totalMonedas;
	}

	@JsonProperty("cantidadPorInstrumento")
	public Map<String, BigDecimal> getCantidadPorInstrumento() {
		return cantidadPorInstrumento;
	}

	public void setCantidadPorInstrumento(Map<String, BigDecimal> cantidadPorInstrumento) {
		this.cantidadPorInstrumento = cantidadPorInstrumento;
	}

}
